import SwiftUI

// MARK: - 1. Card Matching Game View
public struct CardMatchingGameView: View {
    public let onComplete: (Int, Int) -> Void // score, mistakes
    public let onDistressTriggered: () -> Void

    @State private var cards = [
        ("Rhino", "🦏"), ("Rhino", "🦏"),
        ("Tea Leaf", "🍵"), ("Tea Leaf", "🍵"),
        ("Flower", "🌸"), ("Flower", "🌸")
    ].shuffled()

    @State private var flippedIndices: [Int] = []
    @State private var matchedIndices: Set<Int> = []
    @State private var mistakesCount: Int = 0

    public var body: some View {
        VStack(spacing: 20) {
            Text("Find the Matching Pairs")
                .font(.system(size: 24, weight: .bold))
                .foregroundColor(Color(red: 0.12, green: 0.23, blue: 0.54))

            LazyVGrid(columns: [GridItem(.flexible()), GridItem(.flexible())], spacing: 16) {
                ForEach(0..<cards.count, id: \.self) { index in
                    let isFlipped = flippedIndices.contains(index) || matchedIndices.contains(index)
                    Button(action: { handleCardTap(index) }) {
                        ZStack {
                            RoundedRectangle(cornerRadius: 16)
                                .fill(isFlipped ? Color.white : Color(red: 0.12, green: 0.23, blue: 0.54))
                                .shadow(radius: 4)

                            if isFlipped {
                                Text(cards[index].1)
                                    .font(.system(size: 48))
                            } else {
                                Image(systemName: "questionmark")
                                    .font(.system(size: 32))
                                    .foregroundColor(.white)
                            }
                        }
                        .frame(height: 120)
                    }
                    .disabled(matchedIndices.contains(index))
                }
            }
            .padding(.horizontal, 20)

            Spacer()
        }
        .padding(.top, 20)
        .onAppear {
            VoiceAssistantManager.shared.speak("Tap two cards to find matching pairs")
        }
    }

    private func handleCardTap(_ index: Int) {
        if flippedIndices.count == 1 {
            flippedIndices.append(index)
            if cards[flippedIndices[0]].0 == cards[flippedIndices[1]].0 {
                matchedIndices.insert(flippedIndices[0])
                matchedIndices.insert(flippedIndices[1])
                flippedIndices.removeAll()
                if matchedIndices.count == cards.count {
                    DispatchQueue.main.asyncAfter(deadline: .now() + 0.8) {
                        onComplete(100, mistakesCount)
                    }
                }
            } else {
                mistakesCount += 1
                if mistakesCount >= 3 {
                    onDistressTriggered()
                    return
                }
                DispatchQueue.main.asyncAfter(deadline: .now() + 1.0) {
                    flippedIndices.removeAll()
                }
            }
        } else if flippedIndices.isEmpty {
            flippedIndices.append(index)
        }
    }
}

// MARK: - 2. Face Recognition Game View
public struct FaceRecognitionGameView: View {
    public let onComplete: (Int, Int) -> Void
    public let onDistressTriggered: () -> Void

    let familyMembers = ["Aarav (Grandson)", "Ramesh (Son)", "Meera (Daughter)"]
    @State private var mistakesCount: Int = 0

    public var body: some View {
        VStack(spacing: 24) {
            Text("Who is this loved one?")
                .font(.system(size: 26, weight: .bold))

            Circle()
                .fill(Color(red: 0.12, green: 0.23, blue: 0.54).opacity(0.15))
                .frame(width: 140, height: 140)
                .overlay(Image(systemName: "person.crop.circle.fill").font(.system(size: 80)).foregroundColor(Color(red: 0.12, green: 0.23, blue: 0.54)))

            VStack(spacing: 12) {
                ForEach(familyMembers, id: \.self) { name in
                    Button(action: {
                        if name.contains("Aarav") {
                            onComplete(100, mistakesCount)
                        } else {
                            mistakesCount += 1
                            if mistakesCount >= 3 { onDistressTriggered() }
                        }
                    }) {
                        Text(name)
                            .font(.system(size: 20, weight: .semibold))
                            .frame(maxWidth: .infinity)
                            .frame(height: 60)
                            .background(Color(UIColor.secondarySystemBackground))
                            .cornerRadius(16)
                    }
                }
            }
            .padding(.horizontal, 24)
            Spacer()
        }
        .padding(.top, 24)
        .onAppear {
            VoiceAssistantManager.shared.speak("Look at the photo. Who is this in your family?")
        }
    }
}

// MARK: - 3. Daily Routine Ordering Game View
public struct DailyRoutineGameView: View {
    public let onComplete: (Int, Int) -> Void
    public let onDistressTriggered: () -> Void

    let routineItems = ["Morning Tea 🍵", "Take Medicine 💊", "Evening Walk 🌳"]
    @State private var selectedIndex: Int = 0

    public var body: some View {
        VStack(spacing: 24) {
            Text("What happens first in the day?")
                .font(.system(size: 24, weight: .bold))

            VStack(spacing: 14) {
                ForEach(0..<routineItems.count, id: \.self) { index in
                    Button(action: {
                        if index == 0 {
                            onComplete(100, 0)
                        } else {
                            onDistressTriggered()
                        }
                    }) {
                        Text(routineItems[index])
                            .font(.system(size: 20, weight: .semibold))
                            .frame(maxWidth: .infinity)
                            .frame(height: 64)
                            .background(Color(UIColor.secondarySystemBackground))
                            .cornerRadius(16)
                    }
                }
            }
            .padding(.horizontal, 24)
            Spacer()
        }
        .padding(.top, 24)
    }
}

// MARK: - 4. NER Familiar Images Game View
public struct NERFamiliarImagesGameView: View {
    public let onComplete: (Int, Int) -> Void
    public let onDistressTriggered: () -> Void

    let landmarks = ["Kaziranga National Park 🦏", "Majuli River Island 🏝️", "Kamakhya Temple 🛕"]

    public var body: some View {
        VStack(spacing: 24) {
            Text("Which famous Assam sanctuary is home to the one-horned rhino?")
                .font(.system(size: 22, weight: .bold))
                .multilineTextAlignment(.center)
                .padding(.horizontal, 20)

            VStack(spacing: 14) {
                ForEach(landmarks, id: \.self) { landmark in
                    Button(action: {
                        if landmark.contains("Kaziranga") {
                            onComplete(100, 0)
                        } else {
                            onDistressTriggered()
                        }
                    }) {
                        Text(landmark)
                            .font(.system(size: 20, weight: .semibold))
                            .frame(maxWidth: .infinity)
                            .frame(height: 64)
                            .background(Color(UIColor.secondarySystemBackground))
                            .cornerRadius(16)
                    }
                }
            }
            .padding(.horizontal, 24)
            Spacer()
        }
        .padding(.top, 24)
    }
}

// MARK: - 5. Shopping Basket Recall View
public struct ShoppingBasketGameView: View {
    public let onComplete: (Int, Int) -> Void
    public let onDistressTriggered: () -> Void

    let items = ["Assam Tea Leaf 🍵", "Fresh Ginger 🫚", "Sweet Mango 🥭"]

    public var body: some View {
        VStack(spacing: 24) {
            Text("Remember items to buy at the market:")
                .font(.system(size: 22, weight: .bold))

            VStack(spacing: 12) {
                ForEach(items, id: \.self) { item in
                    Text(item)
                        .font(.system(size: 20, weight: .medium))
                        .padding()
                        .frame(maxWidth: .infinity)
                        .background(Color(red: 0.02, green: 0.47, blue: 0.34).opacity(0.1))
                        .cornerRadius(12)
                }
            }
            .padding(.horizontal, 24)

            Button(action: { onComplete(100, 0) }) {
                Text("I Remember! ✅")
                    .font(.system(size: 20, weight: .bold))
                    .foregroundColor(.white)
                    .frame(maxWidth: .infinity)
                    .frame(height: 56)
                    .background(Color(red: 0.02, green: 0.47, blue: 0.34))
                    .cornerRadius(16)
            }
            .padding(.horizontal, 24)

            Spacer()
        }
        .padding(.top, 24)
    }
}

// MARK: - 6. Quick Recall Story View
public struct QuickRecallGameView: View {
    public let onComplete: (Int, Int) -> Void
    public let onDistressTriggered: () -> Void

    public var body: some View {
        VStack(spacing: 24) {
            Text("Short Memory Story")
                .font(.system(size: 24, weight: .bold))

            Text("Grandfather planted sweet yellow marigolds in the Guwahati garden yesterday.")
                .font(.system(size: 20))
                .foregroundColor(.secondary)
                .multilineTextAlignment(.center)
                .padding(.horizontal, 24)

            Text("What color flowers were planted?")
                .font(.system(size: 22, weight: .bold))

            VStack(spacing: 12) {
                Button(action: { onComplete(100, 0) }) {
                    Text("Yellow Marigolds 🌼")
                        .font(.system(size: 20, weight: .semibold))
                        .frame(maxWidth: .infinity)
                        .frame(height: 60)
                        .background(Color(UIColor.secondarySystemBackground))
                        .cornerRadius(16)
                }

                Button(action: { onDistressTriggered() }) {
                    Text("Red Roses 🌹")
                        .font(.system(size: 20, weight: .semibold))
                        .frame(maxWidth: .infinity)
                        .frame(height: 60)
                        .background(Color(UIColor.secondarySystemBackground))
                        .cornerRadius(16)
                }
            }
            .padding(.horizontal, 24)
            Spacer()
        }
        .padding(.top, 24)
    }
}

// MARK: - 7. Life Stage Memory View
public struct LifeStageMemoryGameView: View {
    public let onComplete: (Int, Int) -> Void
    let stages = ["Childhood Days in Nagaon", "College Days in Cotton University", "Retirement in Guwahati"]

    public var body: some View {
        VStack(spacing: 24) {
            Text("Your Beautiful Life Journey")
                .font(.system(size: 24, weight: .bold))

            ScrollView {
                VStack(spacing: 16) {
                    ForEach(stages, id: \.self) { stage in
                        HStack {
                            Image(systemName: "photo.stack.fill")
                                .foregroundColor(Color(red: 0.12, green: 0.23, blue: 0.54))
                            Text(stage)
                                .font(.system(size: 18, weight: .medium))
                            Spacer()
                        }
                        .padding()
                        .background(Color(UIColor.secondarySystemBackground))
                        .cornerRadius(16)
                    }
                }
                .padding(.horizontal, 24)
            }

            Button(action: { onComplete(100, 0) }) {
                Text("Complete Album Recall ✨")
                    .font(.system(size: 20, weight: .bold))
                    .foregroundColor(.white)
                    .frame(maxWidth: .infinity)
                    .frame(height: 56)
                    .background(Color(red: 0.02, green: 0.47, blue: 0.34))
                    .cornerRadius(16)
            }
            .padding(.horizontal, 24)
            Spacer()
        }
        .padding(.top, 24)
    }
}

// MARK: - 8. Guess Who's Speaking View
public struct GuessWhosSpeakingGameView: View {
    public let onComplete: (Int, Int) -> Void
    public let onDistressTriggered: () -> Void

    public var body: some View {
        VStack(spacing: 24) {
            Text("Listen to the Voice Note")
                .font(.system(size: 24, weight: .bold))

            Button(action: {
                VoiceAssistantManager.shared.speak("নমস্কাৰ ককা, মই আৰভ, সোনকালে খেল খেলিম আহক")
            }) {
                Circle()
                    .fill(Color(red: 0.12, green: 0.23, blue: 0.54))
                    .frame(width: 90, height: 90)
                    .overlay(Image(systemName: "play.fill").font(.system(size: 36)).foregroundColor(.white))
            }

            Text("Who is speaking in this message?")
                .font(.system(size: 20, weight: .semibold))

            Button(action: { onComplete(100, 0) }) {
                Text("Grandson Aarav 👦")
                    .font(.system(size: 20, weight: .semibold))
                    .frame(maxWidth: .infinity)
                    .frame(height: 60)
                    .background(Color(UIColor.secondarySystemBackground))
                    .cornerRadius(16)
            }
            .padding(.horizontal, 24)

            Spacer()
        }
        .padding(.top, 24)
    }
}

// MARK: - 9. Music & Folk Song Memory View
public struct MusicMemoryGameView: View {
    public let onComplete: (Int, Int) -> Void

    let songs = ["Bihu Bodo Folk Tune", "Dr. Bhupen Hazarika Classic", "Goalparia Folk Melody"]

    public var body: some View {
        VStack(spacing: 24) {
            Text("Folk Melody Resonance")
                .font(.system(size: 24, weight: .bold))

            ForEach(songs, id: \.self) { song in
                Button(action: { onComplete(100, 0) }) {
                    HStack {
                        Image(systemName: "music.note")
                            .foregroundColor(Color(red: 0.12, green: 0.23, blue: 0.54))
                        Text(song)
                            .font(.system(size: 18, weight: .semibold))
                        Spacer()
                    }
                    .padding()
                    .background(Color(UIColor.secondarySystemBackground))
                    .cornerRadius(16)
                }
                .padding(.horizontal, 24)
            }
            Spacer()
        }
        .padding(.top, 24)
    }
}
