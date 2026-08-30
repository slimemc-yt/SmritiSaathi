import SwiftUI

public enum UserRole: String {
    case patient
    case family
    case doctor
}

// MARK: - Language Selection View
public struct LanguageSelectionView: View {
    public let onLanguageSelected: (String) -> Void

    let languages = [
        ("Assamese (অসমীয়া)", "as"),
        ("Bodo (बर')", "brx"),
        ("Khasi", "kha"),
        ("Garo (A·chik)", "grt"),
        ("Mizo (Mizo ṭawng)", "lus"),
        ("Manipuri (মৈতৈলোন্)", "mni"),
        ("Nagamese", "nga"),
        ("Hindi (हिन्दी)", "hi"),
        ("English", "en")
    ]

    public var body: some View {
        VStack(spacing: 20) {
            VStack(spacing: 8) {
                Text("স্মৃতিসাথী / SmritiSaathi")
                    .font(.system(size: 28, weight: .bold))
                    .foregroundColor(Color(red: 0.12, green: 0.23, blue: 0.54))

                Text("Select your preferred regional language")
                    .font(.system(size: 16))
                    .foregroundColor(.secondary)
            }
            .padding(.top, 24)

            ScrollView {
                VStack(spacing: 12) {
                    ForEach(languages, id: \.1) { name, code in
                        Button(action: {
                            VoiceAssistantManager.shared.setLanguage(code: code)
                            VoiceAssistantManager.shared.speak("নমস্কাৰ, স্মৃতিসাথীলৈ স্বাগতম")
                            onLanguageSelected(code)
                        }) {
                            HStack {
                                Text(name)
                                    .font(.system(size: 18, weight: .semibold))
                                    .foregroundColor(.primary)
                                Spacer()
                                Image(systemName: "speaker.wave.2.fill")
                                    .foregroundColor(Color(red: 0.12, green: 0.23, blue: 0.54))
                            }
                            .padding(.horizontal, 20)
                            .frame(height: 60)
                            .background(Color(UIColor.secondarySystemBackground))
                            .cornerRadius(12)
                        }
                    }
                }
                .padding(.horizontal, 20)
            }
        }
    }
}

// MARK: - Role Selection View
public struct RoleSelectionView: View {
    public let onRoleSelected: (UserRole) -> Void

    public var body: some View {
        VStack(spacing: 24) {
            VStack(spacing: 8) {
                Text("Who is using this device?")
                    .font(.system(size: 26, weight: .bold))
                Text("Choose an experience tailored for your role")
                    .font(.system(size: 16))
                    .foregroundColor(.secondary)
            }
            .padding(.top, 32)

            VStack(spacing: 16) {
                RoleCard(
                    title: "Elderly Patient",
                    subtitle: "Zero navigation, voice-first companion & large touch games",
                    icon: "heart.fill",
                    color: Color(red: 0.02, green: 0.47, blue: 0.34)
                ) {
                    onRoleSelected(.patient)
                }

                RoleCard(
                    title: "Family Member / Caregiver",
                    subtitle: "Routine setup, live score analytics & remote nudges",
                    icon: "person.2.fill",
                    color: Color(red: 0.12, green: 0.23, blue: 0.54)
                ) {
                    onRoleSelected(.family)
                }

                RoleCard(
                    title: "Doctor / ASHA Worker",
                    subtitle: "Multi-patient district triage & clinical observations",
                    icon: "cross.case.fill",
                    color: Color(red: 0.70, green: 0.15, blue: 0.15)
                ) {
                    onRoleSelected(.doctor)
                }
            }
            .padding(.horizontal, 20)

            Spacer()
        }
    }
}

struct RoleCard: View {
    let title: String
    let subtitle: String
    let icon: String
    let color: Color
    let action: () -> Void

    var body: some View {
        Button(action: action) {
            HStack(spacing: 16) {
                Circle()
                    .fill(color.opacity(0.15))
                    .frame(width: 56, height: 56)
                    .overlay(
                        Image(systemName: icon)
                            .font(.system(size: 24))
                            .foregroundColor(color)
                    )

                VStack(alignment: .leading, spacing: 4) {
                    Text(title)
                        .font(.system(size: 18, weight: .bold))
                        .foregroundColor(.primary)
                    Text(subtitle)
                        .font(.system(size: 13))
                        .foregroundColor(.secondary)
                        .multilineTextAlignment(.leading)
                }
                Spacer()
                Image(systemName: "chevron.right")
                    .foregroundColor(.secondary)
            }
            .padding(16)
            .background(Color(UIColor.secondarySystemBackground))
            .cornerRadius(16)
        }
    }
}
