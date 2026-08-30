import SwiftUI

// MARK: - Doctor Dashboard View
public struct DoctorDashboardView: View {
    public let onSelectPatient: (Patient) -> Void

    @State private var patients = [
        Patient(name: "Hemlata Devi", age: 72, state: "Assam", dementiaStage: .mild),
        Patient(name: "Biren Das", age: 78, state: "Assam", dementiaStage: .moderate),
        Patient(name: "Mina Begum", age: 81, state: "Meghalaya", dementiaStage: .severe, enhancedSupportEnabled: true)
    ]

    public var body: some View {
        VStack(spacing: 16) {
            HStack {
                VStack(alignment: .leading, spacing: 2) {
                    Text("Clinical Triage Portal")
                        .font(.system(size: 14, weight: .bold))
                        .foregroundColor(Color(red: 0.12, green: 0.23, blue: 0.54))
                    Text("NER Healthcare Network")
                        .font(.system(size: 24, weight: .bold))
                }
                Spacer()
                Image(systemName: "cross.case.fill")
                    .foregroundColor(Color(red: 0.70, green: 0.15, blue: 0.15))
                    .font(.system(size: 28))
            }
            .padding(.horizontal, 20)
            .padding(.top, 16)

            ScrollView {
                VStack(spacing: 12) {
                    ForEach(patients) { patient in
                        Button(action: { onSelectPatient(patient) }) {
                            HStack(spacing: 16) {
                                Circle()
                                    .fill(Color(red: 0.12, green: 0.23, blue: 0.54).opacity(0.12))
                                    .frame(width: 48, height: 48)
                                    .overlay(Text(String(patient.name.prefix(1))).font(.system(size: 20, weight: .bold)))

                                VStack(alignment: .leading, spacing: 4) {
                                    Text(patient.name)
                                        .font(.system(size: 18, weight: .bold))
                                        .foregroundColor(.primary)
                                    Text("Age: \(patient.age) • Tier \(patient.dementiaStage.tier) (\(patient.dementiaStage.displayName))")
                                        .font(.system(size: 13))
                                        .foregroundColor(.secondary)
                                }

                                Spacer()

                                Text(patient.dementiaStage == .mild ? "Stable" : "Monitor")
                                    .font(.system(size: 12, weight: .bold))
                                    .padding(.horizontal, 8)
                                    .padding(.vertical, 4)
                                    .background(patient.dementiaStage == .mild ? Color.green.opacity(0.15) : Color.orange.opacity(0.15))
                                    .foregroundColor(patient.dementiaStage == .mild ? .green : .orange)
                                    .cornerRadius(8)
                            }
                            .padding(16)
                            .background(Color(UIColor.secondarySystemBackground))
                            .cornerRadius(16)
                        }
                    }
                }
                .padding(.horizontal, 20)
            }
        }
    }
}

// MARK: - Doctor Patient Detail View
public struct DoctorPatientDetailView: View {
    public let patient: Patient
    public let onBack: () -> Void

    @State private var clinicalObservation = ""
    @State private var familyGuidance = ""
    @State private var noteSaved = false

    public var body: some View {
        ScrollView {
            VStack(spacing: 20) {
                HStack {
                    Button(action: onBack) { Image(systemName: "arrow.left") }
                    Text(patient.name)
                        .font(.system(size: 22, weight: .bold))
                    Spacer()
                }
                .padding(.horizontal, 20)
                .padding(.top, 16)

                // Stats Overview
                HStack(spacing: 12) {
                    VStack(alignment: .leading, spacing: 4) {
                        Text("Cognitive Rolling Avg")
                            .font(.system(size: 12))
                            .foregroundColor(.secondary)
                        Text("84%")
                            .font(.system(size: 26, weight: .bold))
                            .foregroundColor(Color(red: 0.12, green: 0.23, blue: 0.54))
                    }
                    .padding(16)
                    .frame(maxWidth: .infinity, alignment: .leading)
                    .background(Color(red: 0.12, green: 0.23, blue: 0.54).opacity(0.08))
                    .cornerRadius(16)

                    VStack(alignment: .leading, spacing: 4) {
                        Text("Med Adherence")
                            .font(.system(size: 12))
                            .foregroundColor(.secondary)
                        Text("92%")
                            .font(.system(size: 26, weight: .bold))
                            .foregroundColor(Color(red: 0.02, green: 0.47, blue: 0.34))
                    }
                    .padding(16)
                    .frame(maxWidth: .infinity, alignment: .leading)
                    .background(Color(red: 0.02, green: 0.47, blue: 0.34).opacity(0.08))
                    .cornerRadius(16)
                }
                .padding(.horizontal, 20)

                // Clinical Observation Input
                VStack(alignment: .leading, spacing: 8) {
                    Text("Clinical Notes & Diagnosis")
                        .font(.system(size: 16, weight: .bold))
                    TextField("Record clinical notes...", text: $clinicalObservation)
                        .padding()
                        .background(Color(UIColor.secondarySystemBackground))
                        .cornerRadius(12)

                    Text("Guidance to Push to Family App")
                        .font(.system(size: 16, weight: .bold))
                    TextField("Enter official family guidance...", text: $familyGuidance)
                        .padding()
                        .background(Color(UIColor.secondarySystemBackground))
                        .cornerRadius(12)

                    Button(action: { noteSaved = true }) {
                        Text("Save & Push to Family App")
                            .font(.system(size: 16, weight: .bold))
                            .foregroundColor(.white)
                            .frame(maxWidth: .infinity)
                            .frame(height: 50)
                            .background(Color(red: 0.12, green: 0.23, blue: 0.54))
                            .cornerRadius(14)
                    }
                    .padding(.top, 8)
                }
                .padding(.horizontal, 20)
            }
        }
        .alert("Clinical Guidance Pushed! 🩺", isPresented: $noteSaved) {
            Button("OK", role: .cancel) { }
        } message: {
            Text("Notes saved to patient's clinical file and dispatched directly to caregiver's family dashboard.")
        }
    }
}
