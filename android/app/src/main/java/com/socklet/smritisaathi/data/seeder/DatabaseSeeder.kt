package com.socklet.smritisaathi.data.seeder

import com.google.firebase.firestore.FirebaseFirestore
import com.socklet.smritisaathi.domain.model.Doctor
import com.socklet.smritisaathi.domain.model.Hospital
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Utility class to seed initial data for doctors and hospitals
 * This should be called once when the app is first set up
 */
@Singleton
class DatabaseSeeder @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    suspend fun seedInitialData() {
        try {
            seedHospitals()
            seedDoctors()
        } catch (e: Exception) {
            // Silently fail seeding in case of permission issues during hackathon/demo
            // This prevents app crashes on first login
            e.printStackTrace()
        }
    }

    private suspend fun seedHospitals() {
        val hospitalsCollection = firestore.collection("hospitals")

        // Check if already seeded
        val existing = hospitalsCollection.limit(1).get().await()
        if (!existing.isEmpty) return

        val hospitals = listOf(
            Hospital(
                id = "",
                name = "Guwahati Medical College & Hospital",
                address = "Bhangagarh, Guwahati, Assam 781032",
                phone = "+91-361-252-8289",
                doctors = emptyList()
            ),
            Hospital(
                id = "",
                name = "Assam Medical College & Hospital",
                address = "Dibrugarh, Assam 786002",
                phone = "+91-373-230-0441",
                doctors = emptyList()
            ),
            Hospital(
                id = "",
                name = "Silchar Medical College & Hospital",
                address = "Silchar, Assam 788014",
                phone = "+91-3842-240-502",
                doctors = emptyList()
            ),
            Hospital(
                id = "",
                name = "Jorhat Medical College & Hospital",
                address = "Jorhat, Assam 785001",
                phone = "+91-376-230-0155",
                doctors = emptyList()
            ),
            Hospital(
                id = "",
                name = "Barpeta Medical College & Hospital",
                address = "Barpeta, Assam 781301",
                phone = "+91-3666-230-001",
                doctors = emptyList()
            ),
            Hospital(
                id = "",
                name = "Tezpur Medical College & Hospital",
                address = "Tezpur, Assam 784001",
                phone = "+91-3712-230-004",
                doctors = emptyList()
            ),
            Hospital(
                id = "",
                name = "Diphu Medical College & Hospital",
                address = "Diphu, Karbi Anglong, Assam 782460",
                phone = "+91-3671-230-001",
                doctors = emptyList()
            ),
            Hospital(
                id = "",
                name = "Lakhimpur Medical College & Hospital",
                address = "North Lakhimpur, Assam 787001",
                phone = "+91-3752-230-001",
                doctors = emptyList()
            ),
            Hospital(
                id = "",
                name = "Fakhruddin Ali Ahmed Medical College",
                address = "Barpeta Road, Assam 781315",
                phone = "+91-3666-230-002",
                doctors = emptyList()
            ),
            Hospital(
                id = "",
                name = "Mahendra Mohan Choudhury Hospital",
                address = "R.G. Baruah Road, Guwahati, Assam 781003",
                phone = "+91-361-260-4016",
                doctors = emptyList()
            ),
            Hospital(
                id = "",
                name = "Down Town Hospital",
                address = "Zoo Road, Guwahati, Assam 781003",
                phone = "+91-361-233-0088",
                doctors = emptyList()
            ),
            Hospital(
                id = "",
                name = "Apollo Hospitals Guwahati",
                address = "Bhetapara, Guwahati, Assam 781035",
                phone = "+91-361-444-4000",
                doctors = emptyList()
            ),
            Hospital(
                id = "",
                name = "Nemcare Hospital",
                address = "Ganeshguri, Guwahati, Assam 781005",
                phone = "+91-361-233-2222",
                doctors = emptyList()
            ),
            Hospital(
                id = "",
                name = "Hayat Hospital",
                address = "Basistha, Guwahati, Assam 781029",
                phone = "+91-361-230-3333",
                doctors = emptyList()
            ),
            Hospital(
                id = "",
                name = "Marwari Maternity Hospital",
                address = "AT Road, Guwahati, Assam 781001",
                phone = "+91-361-254-3456",
                doctors = emptyList()
            )
        )

        hospitals.forEach { hospital ->
            val docRef = hospitalsCollection.document()
            docRef.set(hospital.copy(id = docRef.id)).await()
        }
    }

    private suspend fun seedDoctors() {
        val doctorsCollection = firestore.collection("doctors")

        // Check if already seeded
        val existing = doctorsCollection.limit(1).get().await()
        if (!existing.isEmpty) return

        // First get hospital IDs
        val hospitalsSnapshot = firestore.collection("hospitals").get().await()
        val hospitals = hospitalsSnapshot.documents.mapNotNull { doc ->
            doc.toObject(Hospital::class.java)?.copy(id = doc.id)
        }

        val doctors = mutableListOf<Doctor>()

        // Create sample doctors for each specialty
        val specialties = listOf(
            "Neurologist",
            "Psychiatrist",
            "Geriatrician",
            "General Physician",
            "Psychologist"
        )

        hospitals.forEach { hospital ->
            specialties.forEachIndexed { index, specialty ->
                doctors.add(
                    Doctor(
                        id = "",
                        name = "Dr. ${getDoctorName(index, specialty)}",
                        specialization = specialty,
                        hospitalId = hospital.id,
                        hospitalName = hospital.name,
                        phone = "+91-98765-43${(100 + doctors.size).toString().takeLast(2)}",
                        email = "doctor${doctors.size + 1}@example.com"
                    )
                )
            }
        }

        // Add some independent doctors
        repeat(10) { index ->
            doctors.add(
                Doctor(
                    id = "",
                    name = "Dr. Independent Doctor ${index + 1}",
                    specialization = specialties[index % specialties.size],
                    hospitalId = "",
                    hospitalName = "Independent Practice",
                    phone = "+91-98765-43${(200 + index).toString().takeLast(2)}",
                    email = "independent.doc${index + 1}@example.com"
                )
            )
        }

        doctors.forEach { doctor ->
            val docRef = doctorsCollection.document()
            docRef.set(doctor.copy(id = docRef.id)).await()
        }
    }

    private fun getDoctorName(index: Int, specialty: String): String {
        val names = when (specialty) {
            "Neurologist" -> listOf("Rajesh Sharma", "Priya Das", "Amit Bora")
            "Psychiatrist" -> listOf("Suman Barua", "Anjali Devi", "Rahul Ahmed")
            "Geriatrician" -> listOf("Gopal Hazarika", "Meera Kalita", "Vikram Singh")
            "General Physician" -> listOf("Ramesh Gogoi", "Sunita Baishya", "Deepak Das")
            "Psychologist" -> listOf("Kavita Nath", "Sanjay Paul", "Neha Bora")
            else -> listOf("John Doe", "Jane Smith", "Robert Brown")
        }
        return names[index % names.size]
    }
}
