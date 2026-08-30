package com.socklet.smritisaathi.domain.model

import java.util.Date

enum class DoctorRequestStatus(val displayName: String) {
    PENDING("Pending Approval"),
    ACCEPTED("Connected"),
    DECLINED("Declined"),
    CANCELLED("Cancelled")
}

data class DoctorPatientRequest(
    val id: String = "",
    val patientId: String = "",
    val patientName: String = "",
    val patientAge: Int = 0,
    val dementiaStage: DementiaStage = DementiaStage.MILD,
    val familyId: String = "",
    val familyName: String = "",
    val familyRelationship: String = "",
    val doctorId: String = "",
    val doctorName: String = "",
    val doctorCode: String = "",
    val hospitalName: String = "",
    val status: DoctorRequestStatus = DoctorRequestStatus.PENDING,
    val requestedAt: Date = Date(),
    val respondedAt: Date? = null,
    val declineReason: String? = null
) {
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "id" to id,
            "patientId" to patientId,
            "patientName" to patientName,
            "patientAge" to patientAge,
            "dementiaStage" to dementiaStage.name,
            "familyId" to familyId,
            "familyName" to familyName,
            "familyRelationship" to familyRelationship,
            "doctorId" to doctorId,
            "doctorName" to doctorName,
            "doctorCode" to doctorCode,
            "hospitalName" to hospitalName,
            "status" to status.name,
            "requestedAt" to requestedAt,
            "respondedAt" to respondedAt,
            "declineReason" to declineReason
        )
    }

    companion object {
        fun fromMap(map: Map<String, Any?>): DoctorPatientRequest {
            return DoctorPatientRequest(
                id = map["id"] as? String ?: "",
                patientId = map["patientId"] as? String ?: "",
                patientName = map["patientName"] as? String ?: "",
                patientAge = (map["patientAge"] as? Number)?.toInt() ?: 0,
                dementiaStage = try {
                    DementiaStage.valueOf(map["dementiaStage"] as? String ?: "MILD")
                } catch (_: Exception) {
                    DementiaStage.MILD
                },
                familyId = map["familyId"] as? String ?: "",
                familyName = map["familyName"] as? String ?: "",
                familyRelationship = map["familyRelationship"] as? String ?: "",
                doctorId = map["doctorId"] as? String ?: "",
                doctorName = map["doctorName"] as? String ?: "",
                doctorCode = map["doctorCode"] as? String ?: "",
                hospitalName = map["hospitalName"] as? String ?: "",
                status = try {
                    DoctorRequestStatus.valueOf(map["status"] as? String ?: "PENDING")
                } catch (_: Exception) {
                    DoctorRequestStatus.PENDING
                },
                requestedAt = (map["requestedAt"] as? com.google.firebase.Timestamp)?.toDate()
                    ?: (map["requestedAt"] as? Date) ?: Date(),
                respondedAt = (map["respondedAt"] as? com.google.firebase.Timestamp)?.toDate()
                    ?: (map["respondedAt"] as? Date),
                declineReason = map["declineReason"] as? String
            )
        }
    }
}
