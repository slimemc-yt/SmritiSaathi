package com.socklet.smritisaathi.domain.model

import java.util.Date

enum class UserRole(val displayName: String) {
    PATIENT("Patient"),
    FAMILY("Family Member"),
    DOCTOR("Doctor/Health Worker")
}

data class User(
    val id: String = "",
    val phoneNumber: String = "",
    val role: UserRole = UserRole.FAMILY,
    val name: String = "",
    val email: String? = null,
    val profilePhotoUrl: String? = null,
    val createdAt: Date = Date(),
    val lastLoginAt: Date = Date(),
    val fcmToken: String? = null,
    val linkedPatientIds: List<String> = emptyList(),
    val doctorCode: String? = null,
    val hospitalName: String? = null,
    val specialty: String? = null,
    val profileCompleted: Boolean = false
) {
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "id" to id,
            "phoneNumber" to phoneNumber,
            "role" to role.name,
            "name" to name,
            "email" to email,
            "profilePhotoUrl" to profilePhotoUrl,
            "createdAt" to createdAt,
            "lastLoginAt" to lastLoginAt,
            "fcmToken" to fcmToken,
            "linkedPatientIds" to linkedPatientIds,
            "doctorCode" to doctorCode,
            "hospitalName" to hospitalName,
            "specialty" to specialty,
            "profileCompleted" to profileCompleted
        )
    }

    companion object {
        fun fromMap(map: Map<String, Any?>): User {
            @Suppress("UNCHECKED_CAST")
            return User(
                id = map["id"] as? String ?: "",
                phoneNumber = map["phoneNumber"] as? String ?: "",
                role = try {
                    UserRole.valueOf(map["role"] as? String ?: "FAMILY")
                } catch (e: Exception) {
                    UserRole.FAMILY
                },
                name = map["name"] as? String ?: "",
                email = map["email"] as? String,
                profilePhotoUrl = map["profilePhotoUrl"] as? String,
                createdAt = map["createdAt"] as? Date ?: Date(),
                lastLoginAt = map["lastLoginAt"] as? Date ?: Date(),
                fcmToken = map["fcmToken"] as? String,
                linkedPatientIds = map["linkedPatientIds"] as? List<String> ?: emptyList(),
                doctorCode = map["doctorCode"] as? String,
                hospitalName = map["hospitalName"] as? String,
                specialty = map["specialty"] as? String,
                profileCompleted = map["profileCompleted"] as? Boolean ?: false
            )
        }
    }
}
