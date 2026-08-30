import SwiftUI

// MARK: - Family Dashboard View
public struct FamilyDashboardView: View {
    public let patient: Patient
    public let onNavigateToPlayInvite: () -> Void
    public let onNavigateToAlerts: () -> Void
    public let onNavigateToReminders: () -> Void
    public let onNavigateToReports: () -> Void

    @State private var showingNudgeSuccess = false

    public var body: some View {
        ScrollView {
            VStack(spacing: 20) {
                // Header
                HStack {
                    VStack(alignment: .leading, spacing: 4) {
                        Text("Caregiver Command Hub")
                            .font(.system(size: 14, weight: .bold))
                            .foregroundColor(Color(red: 0.12, green: 0.23, blue: 0.54))
                        Text(patient.name)
                            .font(.system(size: 26, weight: .bold))
                    }
                    Spacer()
                    Circle()
                        .fill(Color(red: 0.12, green: 0.23, blue: 0.54).opacity(0.12))
                        .frame(width: 48, height: 48)
                        .overlay(Image(systemName: "person.crop.circle").foregroundColor(Color(red: 0.12, green: 0.23, blue: 0.54)))
                }
                .padding(.horizontal, 20)
                .padding(.top, 16)

                // Quick Clinical Metric Cards
                HStack(spacing: 12) {
                    VStack(alignment: .leading, spacing: 6) {
                        Text("Cognitive Avg")
                            .font(.system(size: 13))
                            .foregroundColor(.secondary)
                        Text("84%")
                            .font(.system(size: 28, weight: .bold))
                            .foregroundColor(Color(red: 0.12, green: 0.23, blue: 0.54))
                        Text("Stable trend")
                            .font(.system(size: 12))
                            .foregroundColor(.secondary)
                    }
                    .padding(16)
                    .frame(maxWidth: .infinity, alignment: .leading)
                    .background(Color(red: 0.12, green: 0.23, blue: 0.54).opacity(0.08))
                    .cornerRadius(16)

                    VStack(alignment: .leading, spacing: 6) {
                        Text("Med Adherence")
                            .font(.system(size: 13))
                            .foregroundColor(.secondary)
                        Text("92%")
                            .font(.system(size: 28, weight: .bold))
                            .foregroundColor(Color(red: 0.02, green: 0.47, blue: 0.34))
                        Text("12/13 taken")
                            .font(.system(size: 12))
                            .foregroundColor(.secondary)
                    }
                    .padding(16)
                    .frame(maxWidth: .infinity, alignment: .leading)
                    .background(Color(red: 0.02, green: 0.47, blue: 0.34).opacity(0.08))
                    .cornerRadius(16)
                }
                .padding(.horizontal, 20)

                // Remote Caregiver Actions
                VStack(spacing: 12) {
                    Text("Remote Caregiver Controls")
                        .font(.system(size: 18, weight: .bold))
                        .frame(maxWidth: .infinity, alignment: .leading)
                        .padding(.horizontal, 20)

                    HStack(spacing: 12) {
                        Button(action: { showingNudgeSuccess = true }) {
                            VStack(spacing: 8) {
                                Image(systemName: "hand.wave.fill")
                                    .font(.system(size: 24))
                                    .foregroundColor(Color(red: 0.12, green: 0.23, blue: 0.54))
                                Text("Send Nudge")
                                    .font(.system(size: 14, weight: .bold))
                                    .foregroundColor(.primary)
                            }
                            .frame(maxWidth: .infinity)
                            .frame(height: 90)
                            .background(Color(UIColor.secondarySystemBackground))
                            .cornerRadius(16)
                        }

                        Button(action: onNavigateToPlayInvite) {
                            VStack(spacing: 8) {
                                Image(systemName: "gamecontroller.fill")
                                    .font(.system(size: 24))
                                    .foregroundColor(Color(red: 0.02, green: 0.47, blue: 0.34))
                                Text("Play With Me")
                                    .font(.system(size: 14, weight: .bold))
                                    .foregroundColor(.primary)
                            }
                            .frame(maxWidth: .infinity)
                            .frame(height: 90)
                            .background(Color(UIColor.secondarySystemBackground))
                            .cornerRadius(16)
                        }

                        Button(action: onNavigateToAlerts) {
                            VStack(spacing: 8) {
                                Image(systemName: "bell.badge.fill")
                                    .font(.system(size: 24))
                                    .foregroundColor(Color(red: 0.85, green: 0.15, blue: 0.15))
                                Text("Alerts (0)")
                                    .font(.system(size: 14, weight: .bold))
                                    .foregroundColor(.primary)
                            }
                            .frame(maxWidth: .infinity)
                            .frame(height: 90)
                            .background(Color(UIColor.secondarySystemBackground))
                            .cornerRadius(16)
                        }
                    }
                    .padding(.horizontal, 20)
                }

                // Navigational Menu
                VStack(spacing: 10) {
                    NavigationCard(title: "Schedule & Daily Routine", subtitle: "Manage medicine & meal alerts", icon: "calendar") {
                        onNavigateToReminders()
                    }

                    NavigationCard(title: "Progress & Doctor Reports", subtitle: "Export PDF clinical reports", icon: "doc.text.fill") {
                        onNavigateToReports()
                    }
                }
                .padding(.horizontal, 20)
            }
            .padding(.bottom, 32)
        }
        .alert("Nudge Sent! 🌸", isPresented: $showingNudgeSuccess) {
            Button("OK", role: .cancel) { }
        } message: {
            Text("Warm audio greeting and reminder popped up on \(patient.name)'s device in real-time.")
        }
    }
}

struct NavigationCard: View {
    let title: String
    let subtitle: String
    let icon: String
    let action: () -> Void

    var body: some View {
        Button(action: action) {
            HStack(spacing: 16) {
                Image(systemName: icon)
                    .font(.system(size: 22))
                    .foregroundColor(Color(red: 0.12, green: 0.23, blue: 0.54))

                VStack(alignment: .leading, spacing: 2) {
                    Text(title)
                        .font(.system(size: 16, weight: .semibold))
                        .foregroundColor(.primary)
                    Text(subtitle)
                        .font(.system(size: 13))
                        .foregroundColor(.secondary)
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

// MARK: - Play With Grandpa View
public struct PlayWithGrandpaView: View {
    public let patient: Patient
    public let onBack: () -> Void
    @State private var inviteSent = false

    public var body: some View {
        VStack(spacing: 24) {
            Text("Play With \(patient.name)")
                .font(.system(size: 26, weight: .bold))

            Text("Send an interactive game invitation that pops up on their screen right now!")
                .font(.system(size: 16))
                .foregroundColor(.secondary)
                .multilineTextAlignment(.center)
                .padding(.horizontal, 24)

            VStack(spacing: 16) {
                Button(action: { inviteSent = true }) {
                    HStack {
                        Image(systemName: "square.grid.2x2.fill")
                        Text("Invite to Card Matching")
                        Spacer()
                    }
                    .font(.system(size: 18, weight: .bold))
                    .foregroundColor(.white)
                    .padding()
                    .frame(height: 64)
                    .background(Color(red: 0.12, green: 0.23, blue: 0.54))
                    .cornerRadius(16)
                }

                Button(action: { inviteSent = true }) {
                    HStack {
                        Image(systemName: "person.crop.rectangle.stack.fill")
                        Text("Invite to Face Recognition")
                        Spacer()
                    }
                    .font(.system(size: 18, weight: .bold))
                    .foregroundColor(.white)
                    .padding()
                    .frame(height: 64)
                    .background(Color(red: 0.02, green: 0.47, blue: 0.34))
                    .cornerRadius(16)
                }
            }
            .padding(.horizontal, 24)

            Spacer()
        }
        .padding(.top, 24)
        .alert("Game Invite Dispatched! 🎮", isPresented: $inviteSent) {
            Button("Awesome", role: .cancel) { onBack() }
        } message: {
            Text("\(patient.name)'s device is now presenting the incoming game invite screen.")
        }
    }
}

// MARK: - Behavioral Alerts Inbox View
public struct BehavioralAlertsInboxView: View {
    public let patient: Patient
    public let onBack: () -> Void

    public var body: some View {
        VStack(spacing: 20) {
            HStack {
                Button(action: onBack) { Image(systemName: "arrow.left") }
                Text("Behavioral Alerts Inbox")
                    .font(.system(size: 20, weight: .bold))
                Spacer()
            }
            .padding(.horizontal, 20)
            .padding(.top, 16)

            VStack(spacing: 12) {
                Image(systemName: "checkmark.shield.fill")
                    .font(.system(size: 56))
                    .foregroundColor(Color(red: 0.02, green: 0.47, blue: 0.34))

                Text("All Clear & Normal")
                    .font(.system(size: 20, weight: .bold))

                Text("No cognitive drop or missed medication alerts detected.")
                    .font(.system(size: 15))
                    .foregroundColor(.secondary)
                    .multilineTextAlignment(.center)
            }
            .padding(.top, 48)

            Spacer()
        }
    }
}
