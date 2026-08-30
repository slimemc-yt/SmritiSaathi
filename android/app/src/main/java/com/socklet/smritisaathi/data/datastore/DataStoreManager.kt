package com.socklet.smritisaathi.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "smritisaathi_prefs")

@Singleton
class DataStoreManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    companion object {
        val KEY_PAIRED_PATIENT_ID = stringPreferencesKey("paired_patient_id")
        val KEY_PAIRED_PATIENT_NAME = stringPreferencesKey("paired_patient_name")
        val KEY_USER_ROLE = stringPreferencesKey("user_role")
        val KEY_PREFERRED_LANGUAGE = stringPreferencesKey("preferred_language")
        val KEY_DEMENTIA_TIER = intPreferencesKey("dementia_tier")
        val KEY_IS_DEVICE_PAIRED = booleanPreferencesKey("is_device_paired")
    }

    val pairedPatientIdFlow: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[KEY_PAIRED_PATIENT_ID]
    }

    val pairedPatientNameFlow: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[KEY_PAIRED_PATIENT_NAME]
    }

    val userRoleFlow: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[KEY_USER_ROLE]
    }

    val preferredLanguageFlow: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[KEY_PREFERRED_LANGUAGE] ?: "as"
    }

    val dementiaTierFlow: Flow<Int> = context.dataStore.data.map { preferences ->
        preferences[KEY_DEMENTIA_TIER] ?: 1
    }

    val isDevicePairedFlow: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[KEY_IS_DEVICE_PAIRED] ?: false
    }

    suspend fun savePatientPairing(patientId: String, patientName: String, dementiaTier: Int = 1) {
        context.dataStore.edit { preferences ->
            // Scope by current Firebase UID to avoid Account A's pairing leaking into Account B
            val uid = com.google.firebase.auth.FirebaseAuth.getInstance().currentUser?.uid
            if (uid != null) {
                preferences[stringPreferencesKey("paired_patient_id_$uid")] = patientId
                preferences[stringPreferencesKey("paired_patient_name_$uid")] = patientName
                preferences[intPreferencesKey("dementia_tier_$uid")] = dementiaTier
                preferences[booleanPreferencesKey("is_device_paired_$uid")] = true
                preferences[stringPreferencesKey("user_role_$uid")] = "PATIENT"
            }
            // Keep legacy keys for backward compatibility
            preferences[KEY_PAIRED_PATIENT_ID] = patientId
            preferences[KEY_PAIRED_PATIENT_NAME] = patientName
            preferences[KEY_DEMENTIA_TIER] = dementiaTier
            preferences[KEY_IS_DEVICE_PAIRED] = true
            preferences[KEY_USER_ROLE] = "PATIENT"
        }
    }

    suspend fun saveUserRole(role: String) {
        context.dataStore.edit { preferences ->
            val uid = com.google.firebase.auth.FirebaseAuth.getInstance().currentUser?.uid
            if (uid != null) {
                preferences[stringPreferencesKey("user_role_$uid")] = role
            }
            preferences[KEY_USER_ROLE] = role
        }
    }

    suspend fun savePreferredLanguage(langCode: String) {
        context.dataStore.edit { preferences ->
            preferences[KEY_PREFERRED_LANGUAGE] = langCode
        }
    }

    suspend fun clearSession() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    suspend fun clearUserSession(uid: String? = null) {
        context.dataStore.edit { preferences ->
            val targetUid = uid ?: com.google.firebase.auth.FirebaseAuth.getInstance().currentUser?.uid
            if (targetUid != null) {
                preferences.remove(stringPreferencesKey("paired_patient_id_$targetUid"))
                preferences.remove(stringPreferencesKey("paired_patient_name_$targetUid"))
                preferences.remove(intPreferencesKey("dementia_tier_$targetUid"))
                preferences.remove(booleanPreferencesKey("is_device_paired_$targetUid"))
                preferences.remove(stringPreferencesKey("user_role_$targetUid"))
            }
            preferences.remove(KEY_PAIRED_PATIENT_ID)
            preferences.remove(KEY_PAIRED_PATIENT_NAME)
            preferences.remove(KEY_DEMENTIA_TIER)
            preferences.remove(KEY_IS_DEVICE_PAIRED)
            preferences.remove(KEY_USER_ROLE)
        }
    }
}
