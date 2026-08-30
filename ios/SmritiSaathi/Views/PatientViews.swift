import SwiftUI

// MARK: - Patient Today View (Core Single-Task UX)
public struct PatientTodayView: View {
    public let patient: Patient
    public let onStartActivity: () -> Void
    public let onTriggerSOS: () -> Void

    @State private var showingIncomingCall: Bool = false
    @State private var showingDistressCalm: Bool = false
    @State private var showingMedicationPrompt: Bool = false

    public var body: some View {
        ZStack {
            Color(red: 0.98, green: 0.97, blue: 0.94) // Warm soothing background
                .ignoresSafeArea()

            VStack(spacing: 24) {
                // Top Header with Time and Name
                VStack(spacing: 6) {
                    Text(greetingText())
                        .font(.system(size: 22, weight: .medium))
                        .foregroundColor(.secondary)

                    Text(patient.name)
                        .font(.system(size: 34, weight: .bold))
                        .foregroundColor(Color(red: 0.12, green: 0.23, blue: 0.54))

                    Text("Guwahati, Assam • " + DateFormatter.localizedString(from: Date(), dateStyle: .full, timeStyle: .none))
                        .font(.system(size: 16))
                        .foregroundColor(.secondary)
                }
                .padding(.top, 32)

                Spacer()

                // Big 10-Minute Daily Activity Button
                Button(action: onStartActivity) {
                    VStack(spacing: 16) {
                        Circle()
                            .fill(Color.white.opacity(0.2))
                            .frame(width: 84, height: 84)
                            .overlay(
                                Image(systemName: "sparkles")
                                    .font(.system(size: 40))
                                    .foregroundColor(.white)
                            )

                        VStack(spacing: 4) {
                            Text("Start Your Activity")
                                .font(.system(size: 26, weight: .bold))
                                .foregroundColor(.white)

                            Text("10 minutes of fun daily memory games")
                                .font(.system(size: 16))
                                .foregroundColor(.white.opacity(0.9))
                        }
                    }
                    .frame(maxWidth: .infinity)
                    .frame(height: 220)
                    .background(Color(red: 0.02, green: 0.47, blue: 0.34))
                    .cornerRadius(28)
                    .shadow(color: Color.black.opacity(0.1), radius: 10, y: 6)
                }
                .padding(.horizontal, 24)

                // Next Scheduled Routine Item Pill
                HStack(spacing: 12) {
                    Image(systemName: "pill.fill")
                        .foregroundColor(Color(red: 0.12, green: 0.23, blue: 0.54))
                        .font(.system(size: 22))

                    VStack(alignment: .leading, spacing: 2) {
                        Text("Next: Evening Medicine (8:00 PM)")
                            .font(.system(size: 16, weight: .bold))
                            .foregroundColor(.primary)

                        Text("Scheduled with water after dinner")
                            .font(.system(size: 13))
                            .foregroundColor(.secondary)
                    }
                    Spacer()
                }
                .padding(16)
                .background(Color.white)
                .cornerRadius(16)
                .padding(.horizontal, 24)

                Spacer()

                // Persistent SOS Emergency Button
                Button(action: onTriggerSOS) {
                    HStack(spacing: 12) {
                        Image(systemName: "phone.fill")
                            .font(.system(size: 22))
                        Text("SOS Emergency Help")
                            .font(.system(size: 20, weight: .bold))
                    }
                    .foregroundColor(.white)
                    .frame(maxWidth: .infinity)
                    .frame(height: 64)
                    .background(Color(red: 0.85, green: 0.15, blue: 0.15))
                    .cornerRadius(20)
                }
                .padding(.horizontal, 24)
                .padding(.bottom, 24)
            }
        }
    }

    private func greetingText() -> String {
        let hour = Calendar.current.component(.hour, from: Date())
        if hour < 12 { return "Good Morning ☀️" }
        if hour < 17 { return "Good Afternoon 🌤️" }
        return "Good Evening 🌙"
    }
}

// MARK: - Incoming Call Reminder Overlay
public struct IncomingCallReminderOverlayView: View {
    public let title: String
    public let subtitle: String
    public let onAccept: () -> Void
    public let onDecline: () -> Void

    @State private var isPulsing = false

    public var body: some View {
        ZStack {
            Color(red: 0.05, green: 0.10, blue: 0.25)
                .ignoresSafeArea()

            VStack(spacing: 32) {
                Spacer()

                ZStack {
                    Circle()
                        .stroke(Color.white.opacity(0.2), lineWidth: 4)
                        .frame(width: 140, height: 140)
                        .scaleEffect(isPulsing ? 1.3 : 1.0)
                        .opacity(isPulsing ? 0.0 : 1.0)
                        .animation(Animation.easeOut(duration: 1.5).repeatForever(autoreverses: false), value: isPulsing)

                    Circle()
                        .fill(Color(red: 0.02, green: 0.47, blue: 0.34))
                        .frame(width: 110, height: 110)

                    Image(systemName: "phone.fill")
                        .font(.system(size: 44))
                        .foregroundColor(.white)
                }
                .onAppear { isPulsing = true }

                VStack(spacing: 8) {
                    Text(title)
                        .font(.system(size: 28, weight: .bold))
                        .foregroundColor(.white)
                        .multilineTextAlignment(.center)

                    Text(subtitle)
                        .font(.system(size: 18))
                        .foregroundColor(.white.opacity(0.8))
                        .multilineTextAlignment(.center)
                }
                .padding(.horizontal, 24)

                Spacer()

                HStack(spacing: 40) {
                    Button(action: onDecline) {
                        VStack(spacing: 8) {
                            Circle()
                                .fill(Color(red: 0.85, green: 0.15, blue: 0.15))
                                .frame(width: 72, height: 72)
                                .overlay(Image(systemName: "xmark").font(.system(size: 28)).foregroundColor(.white))
                            Text("Later")
                                .font(.system(size: 16, weight: .medium))
                                .foregroundColor(.white)
                        }
                    }

                    Button(action: onAccept) {
                        VStack(spacing: 8) {
                            Circle()
                                .fill(Color(red: 0.02, green: 0.47, blue: 0.34))
                                .frame(width: 72, height: 72)
                                .overlay(Image(systemName: "checkmark").font(.system(size: 28)).foregroundColor(.white))
                            Text("Start Now")
                                .font(.system(size: 16, weight: .medium))
                                .foregroundColor(.white)
                        }
                    }
                }
                .padding(.bottom, 48)
            }
        }
    }
}

// MARK: - Medication Prompt View
public struct MedicationPromptView: View {
    public let reminder: Reminder
    public let onTaken: () -> Void
    public let onRemindLater: () -> Void
    public let onNeedHelp: () -> Void

    public var body: some View {
        VStack(spacing: 24) {
            Spacer()

            Circle()
                .fill(Color(red: 0.12, green: 0.23, blue: 0.54).opacity(0.12))
                .frame(width: 100, height: 100)
                .overlay(
                    Image(systemName: "pills.fill")
                        .font(.system(size: 48))
                        .foregroundColor(Color(red: 0.12, green: 0.23, blue: 0.54))
                )

            VStack(spacing: 8) {
                Text(reminder.title)
                    .font(.system(size: 28, weight: .bold))
                    .multilineTextAlignment(.center)

                if let dosage = reminder.dosage {
                    Text(dosage)
                        .font(.system(size: 18))
                        .foregroundColor(.secondary)
                }
            }

            Spacer()

            VStack(spacing: 14) {
                Button(action: onTaken) {
                    Text("Taken ✅")
                        .font(.system(size: 22, weight: .bold))
                        .foregroundColor(.white)
                        .frame(maxWidth: .infinity)
                        .frame(height: 64)
                        .background(Color(red: 0.02, green: 0.47, blue: 0.34))
                        .cornerRadius(18)
                }

                Button(action: onRemindLater) {
                    Text("Remind me in 15 mins ⏰")
                        .font(.system(size: 18, weight: .semibold))
                        .foregroundColor(.primary)
                        .frame(maxWidth: .infinity)
                        .frame(height: 56)
                        .background(Color(UIColor.secondarySystemBackground))
                        .cornerRadius(18)
                }

                Button(action: onNeedHelp) {
                    Text("I need help 🆘")
                        .font(.system(size: 18, weight: .semibold))
                        .foregroundColor(Color(red: 0.85, green: 0.15, blue: 0.15))
                        .frame(maxWidth: .infinity)
                        .frame(height: 56)
                        .background(Color(red: 0.85, green: 0.15, blue: 0.15).opacity(0.12))
                        .cornerRadius(18)
                }
            }
            .padding(.horizontal, 24)
            .padding(.bottom, 32)
        }
    }
}

// MARK: - Distress Calming View
public struct DistressCalmingView: View {
    public let onDismiss: () -> Void

    public var body: some View {
        ZStack {
            Color(red: 0.94, green: 0.97, blue: 0.98)
                .ignoresSafeArea()

            VStack(spacing: 24) {
                Spacer()

                Text("Everything is alright 🌸")
                    .font(.system(size: 28, weight: .bold))
                    .foregroundColor(Color(red: 0.12, green: 0.23, blue: 0.54))

                // Comforting Family Photo Placeholder
                RoundedRectangle(cornerRadius: 24)
                    .fill(Color.white)
                    .frame(height: 240)
                    .overlay(
                        VStack(spacing: 12) {
                            Image(systemName: "photo.artframe")
                                .font(.system(size: 64))
                                .foregroundColor(Color(red: 0.12, green: 0.23, blue: 0.54).opacity(0.6))
                            Text("Your family loves you very much")
                                .font(.system(size: 18, weight: .medium))
                                .foregroundColor(.secondary)
                        }
                    )
                    .padding(.horizontal, 24)

                Text("Take a gentle breath. We are here with you.")
                    .font(.system(size: 18))
                    .foregroundColor(.secondary)
                    .multilineTextAlignment(.center)
                    .padding(.horizontal, 32)

                Spacer()

                Button(action: onDismiss) {
                    Text("I Feel Calm Now 🌿")
                        .font(.system(size: 20, weight: .bold))
                        .foregroundColor(.white)
                        .frame(maxWidth: .infinity)
                        .frame(height: 60)
                        .background(Color(red: 0.02, green: 0.47, blue: 0.34))
                        .cornerRadius(18)
                }
                .padding(.horizontal, 24)
                .padding(.bottom, 32)
            }
        }
    }
}
