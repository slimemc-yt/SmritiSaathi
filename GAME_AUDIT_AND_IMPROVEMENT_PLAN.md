# Game Audit & Improvement Plan

**Date:** 2026-08-29  
**Status:** Critical Overhaul Required

---

## EXECUTIVE SUMMARY

**Current State:** All 9 games are 5-30 second prototypes with minimal content, no progression, and high repetition.

**Target State:** Polished, multi-level cognitive games with adaptive difficulty, Gemini-powered content variety, and meaningful 2-5 minute sessions.

**Estimated Work:** 10-15 hours for complete overhaul

---

## GAME-BY-GAME AUDIT

### Game 1: Card Matching (Memory Match)

**Current Implementation:**
- **Gameplay:** Flip cards to find matching pairs
- **Cognitive Skill:** Short-term memory, visual recognition
- **Current Problems:**
  - Only 2-4 pairs (4-8 cards) - completes in 10-30 seconds
  - Fixed 6 icons (Heart, Star, Sun, Leaf, Flower, Cat) - highly repetitive
  - No multiple rounds or levels
  - No adaptive difficulty
  - No content categories
  - Card flip animation may have text rendering issues
- **Session Duration:** 10-30 seconds ❌
- **Replayability:** Very Low ❌

**Proposed Improvements:**
1. **Multi-Level Structure:**
   - Level 1: 4 pairs (8 cards) - familiar objects
   - Level 2: 6 pairs (12 cards) - mixed categories
   - Level 3: 8 pairs (16 cards) - increased complexity
   - Level 4+: Adaptive based on performance
   
2. **Content Categories (8+ categories):**
   - Fruits (Apple, Banana, Mango, Orange, Grapes, etc.)
   - Vegetables (Potato, Tomato, Onion, Carrot, etc.)
   - Animals (Cat, Dog, Cow, Elephant, Lion, etc.)
   - Household items (Cup, Plate, Spoon, Chair, etc.)
   - Vehicles (Car, Bus, Bicycle, Train, etc.)
   - Flowers (Rose, Lotus, Sunflower, etc.)
   - Family members (Mother, Father, Child, etc.)
   - Daily objects (Phone, Clock, Key, etc.)

3. **Session Flow:**
   - Complete Level 1 → Encouragement → Level 2 → ...
   - 3-5 levels per session (2-5 minutes total)
   - Gentle hints after 3 consecutive mistakes
   - Category changes between levels

4. **Card Flip Fix:**
   - Use proper front/back rendering
   - Ensure text is always readable
   - Smooth animation without visual glitches

5. **Hint System:**
   - After 3 mistakes: Briefly highlight a matching pair
   - After 5 mistakes: Reveal one pair for 2 seconds
   - Never punish, always encourage

**Difficulty System:**
- Start at Level 1
- 2 consecutive successful levels → Increase difficulty
- 3+ mistakes in a level → Maintain or reduce difficulty
- Track best level achieved per patient

**Content Source:**
- Local content bank (8 categories × 10+ items each)
- Gemini can generate additional category items
- Track recently used categories to avoid repetition

---

### Game 2: Daily Routine Ordering

**Current Implementation:**
- **Gameplay:** Arrange daily routine steps in correct order
- **Cognitive Skill:** Sequencing, procedural memory
- **Current Problems:**
  - Fixed 3-4 routines (Morning, Evening, etc.)
  - Hardcoded steps - highly repetitive
  - No difficulty progression
  - No variety in routine types
- **Session Duration:** 20-40 seconds ❌
- **Replayability:** Very Low ❌

**Proposed Improvements:**
1. **Routine Categories (10+ types):**
   - Morning routine (wake up, brush, bath, breakfast)
   - Meal preparation (make tea, cook rice, etc.)
   - Getting ready to go out
   - Bedtime routine
   - Household chores
   - Personal care
   - Social visits
   - Religious/cultural practices
   - Seasonal activities
   - Festival preparations

2. **Multi-Round Sessions:**
   - Round 1: 4 steps (simple routine)
   - Round 2: 5 steps (moderate)
   - Round 3: 6 steps (complex)
   - 3-5 rounds per session

3. **Gemini Integration:**
   - Generate routine steps based on:
     - Selected language
     - Cultural context
     - Difficulty level
     - Patient preferences
   - Validate structured JSON output
   - Cache unused generated content

4. **Adaptive Difficulty:**
   - Start with 4 steps
   - Increase steps based on accuracy
   - Reduce if struggling
   - Provide hints (show first/last step)

**Content Source:**
- Local bank: 20+ pre-defined routines
- Gemini: Generate new routines weekly
- Track used routines to avoid repetition

---

### Game 3: Face Recognition

**Current Implementation:**
- **Gameplay:** Recognize family members from descriptions
- **Cognitive Skill:** Face recognition, memory recall
- **Current Problems:**
  - Limited to pre-loaded family photos
  - No actual photo upload system
  - Hardcoded questions
  - Not usable without setup
- **Session Duration:** N/A (not functional) ❌
- **Replayability:** None ❌

**Proposed Improvements:**
1. **Alternative Approach (No Photos Required):**
   - Use descriptive clues instead of photos
   - "Who cooks delicious food for you?" → Mother/Grandmother
   - "Who reads you stories?" → Father/Grandfather
   - Cultural/familiar relationships

2. **Multi-Round Structure:**
   - Round 1: 3 questions (immediate family)
   - Round 2: 5 questions (extended family)
   - Round 3: 7 questions (friends, neighbors)

3. **Content Variation:**
   - 50+ relationship questions
   - Rotate through different sets
   - Gemini can generate culturally appropriate questions

4. **Gentle Feedback:**
   - Correct: "Yes! [Name] loves you very much."
   - Incorrect: "Let's think... [hint]"

**Content Source:**
- Local bank: 50+ relationship questions
- Gemini: Generate additional questions
- Language-aware content

---

### Game 4: Music Memory

**Current Implementation:**
- **Gameplay:** Recognize songs/music clips
- **Cognitive Skill:** Auditory memory, recognition
- **Current Problems:**
  - No actual audio files
  - Placeholder implementation
  - Not functional
- **Session Duration:** N/A ❌
- **Replayability:** None ❌

**Proposed Improvements:**
1. **Simplified Approach (No Audio Files):**
   - Show song titles/lyrics snippets
   - "Complete the lyric: 'Tum ___ ho'"
   - "Which festival song: 'Jai ho...'"
   - Use text-based music recognition

2. **Categories:**
   - Bollywood classics
   - Regional songs (Assamese, Bengali, etc.)
   - Devotional songs
   - Festival songs
   - Children's songs

3. **Multi-Round:**
   - Round 1: 3 songs (very famous)
   - Round 2: 5 songs (moderate)
   - Round 3: 7 songs (challenging)

**Content Source:**
- Local bank: 100+ song questions
- Gemini: Generate additional questions
- Language-specific songs

---

### Game 5: Shopping Basket (Object Memory)

**Current Implementation:**
- **Gameplay:** Remember items in a shopping list
- **Cognitive Skill:** Short-term memory, recall
- **Current Problems:**
  - Limited item variety
  - No difficulty progression
  - Repetitive content
- **Session Duration:** 30-60 seconds ⚠️
- **Replayability:** Low ❌

**Proposed Improvements:**
1. **Item Categories:**
   - Groceries (rice, dal, oil, etc.)
   - Vegetables
   - Fruits
   - Household items
   - Personal care items

2. **Progressive Difficulty:**
   - Level 1: Remember 3 items
   - Level 2: Remember 5 items
   - Level 3: Remember 7 items
   - Level 4: Remember 9 items

3. **Multi-Round Sessions:**
   - 3-5 rounds per session
   - Increasing difficulty
   - Category variation

4. **Recall Methods:**
   - Show list for 10 seconds
   - Hide list
   - Ask "What was item #3?"
   - Or "Which of these was on the list?" (multiple choice)

**Content Source:**
- Local bank: 100+ items across categories
- Gemini: Generate additional items
- Track used items

---

### Game 6: Life Stage Memory

**Current Implementation:**
- **Gameplay:** Match life stages with activities
- **Cognitive Skill:** Semantic memory, life knowledge
- **Current Problems:**
  - Limited content
  - No progression
  - Repetitive
- **Session Duration:** 30-60 seconds ⚠️
- **Replayability:** Low ❌

**Proposed Improvements:**
1. **Life Stages:**
   - Childhood (school, playing, learning)
   - Youth (college, first job, marriage)
   - Adulthood (career, family, home)
   - Elderly (retirement, grandchildren, wisdom)

2. **Question Types:**
   - "What do children do?" → Go to school
   - "What do adults do?" → Go to work
   - "What do grandparents do?" → Tell stories

3. **Multi-Round:**
   - 3-5 rounds
   - Different life stages each round
   - Increasing complexity

**Content Source:**
- Local bank: 50+ life stage questions
- Gemini: Generate additional questions
- Cultural relevance

---

### Game 7: Guess Who's Speaking

**Current Implementation:**
- **Gameplay:** Identify speaker from voice/description
- **Cognitive Skill:** Auditory recognition, inference
- **Current Problems:**
  - No actual audio
  - Placeholder implementation
  - Not functional
- **Session Duration:** N/A ❌
- **Replayability:** None ❌

**Proposed Improvements:**
1. **Text-Based Approach:**
   - Show characteristic phrases
   - "Who says 'Eat your vegetables'?" → Mother
   - "Who says 'Let me tell you a story'?" → Grandmother

2. **Relationship Categories:**
   - Family members
   - Friends
   - Neighbors
   - Community members

3. **Multi-Round:**
   - 3-5 rounds
   - Different relationships
   - Cultural context

**Content Source:**
- Local bank: 50+ speaker clues
- Gemini: Generate additional clues
- Language-aware

---

### Game 8: NER Familiar Images

**Current Implementation:**
- **Gameplay:** Named Entity Recognition with images
- **Cognitive Skill:** Object recognition, categorization
- **Current Problems:**
  - Limited content
  - No progression
  - Unclear gameplay
- **Session Duration:** 30-60 seconds ⚠️
- **Replayability:** Low ❌

**Proposed Improvements:**
1. **Clear Gameplay:**
   - Show image/icon
   - Ask "What category does this belong to?"
   - Options: Fruit/Vegetable/Animal/etc.

2. **Categories:**
   - Fruits vs Vegetables
   - Wild vs Domestic animals
   - Indoor vs Outdoor objects
   - Hot vs Cold items

3. **Multi-Round:**
   - 5-7 questions per round
   - 3-5 rounds per session
   - Increasing difficulty (more similar categories)

**Content Source:**
- Local bank: 100+ categorization questions
- Gemini: Generate additional questions
- Use icons/emojis instead of images

---

### Game 9: Quick Recall

**Current Implementation:**
- **Gameplay:** Quick memory recall
- **Cognitive Skill:** Short-term memory, attention
- **Current Problems:**
  - Limited content
  - No clear structure
  - Repetitive
- **Session Duration:** 30-60 seconds ⚠️
- **Replayability:** Low ❌

**Proposed Improvements:**
1. **Gameplay Types:**
   - Number sequence recall
   - Color pattern recall
   - Word list recall
   - Shape pattern recall

2. **Progressive Difficulty:**
   - Level 1: 3 items
   - Level 2: 5 items
   - Level 3: 7 items
   - Level 4: 9 items

3. **Multi-Round:**
   - 3-5 rounds per session
   - Different recall types
   - Increasing difficulty

**Content Source:**
- Procedurally generated (numbers, colors, shapes)
- Gemini: Generate word lists
- No repetition within session

---

## CROSS-CUTTING IMPROVEMENTS

### 1. Reusable Game Engine Components

**Create:**
- `GameSessionManager` - Track rounds, scores, progression
- `DifficultyController` - Adaptive difficulty logic
- `ContentSelector` - Non-repetitive content selection
- `PerformanceTracker` - Save patient progress
- `HintSystem` - Gentle hints when struggling
- `SessionSummary` - End-of-session feedback

### 2. Gemini Integration Strategy

**When to Call Gemini:**
- Before session starts: Generate content package
- Cache unused content for future sessions
- Never call during gameplay (too slow)
- Fallback to local content if API fails

**Content Generation:**
- Structured JSON output
- Validate all generated content
- Language-aware generation
- Cultural relevance
- Track recently used content IDs

**Fallback Strategy:**
- Local content bank (100+ items per game)
- Least-recently-used selection
- Never repeat within 10 sessions
- Mark fallback content clearly in logs

### 3. Progress Tracking

**Persist Per Patient:**
- Current level per game
- Highest level achieved
- Recent accuracy (last 5 sessions)
- Recently used content IDs
- Preferred categories
- Total play time

**Storage:**
- Firestore: `/patients/{patientId}/gameProgress/{gameType}`
- Keep lightweight (only essential data)
- Sync across devices

### 4. Localization

**All Games Must:**
- Use selected language for instructions
- Generate content in selected language
- Provide feedback in selected language
- Use culturally appropriate content

**Implementation:**
- Pass language code to Gemini
- Use localized strings from AppStrings
- Validate translated content

### 5. Accessibility

**All Games Must:**
- Large touch targets (min 48dp)
- Clear, readable text (min 16sp)
- High contrast colors
- Simple instructions
- Gentle animations
- No time pressure by default
- Voice instructions available
- Clear success/failure feedback

---

## IMPLEMENTATION PRIORITY

### Phase 1: Core Infrastructure (2-3 hours)
1. Create reusable game engine components
2. Implement progress tracking system
3. Set up Gemini content generation framework
4. Create local content banks

### Phase 2: Card Matching Overhaul (2-3 hours)
1. Multi-level structure
2. 8 content categories
3. Adaptive difficulty
4. Hint system
5. Session flow (3-5 levels)
6. Card flip animation fix

### Phase 3: Routine Game Overhaul (2 hours)
1. 10 routine categories
2. Gemini integration for variety
3. Multi-round structure
4. Progressive difficulty

### Phase 4: Other Games (3-4 hours)
1. Face Recognition (text-based)
2. Music Memory (text-based)
3. Shopping Basket
4. Life Stage Memory
5. Guess Who's Speaking
6. NER Familiar Images
7. Quick Recall

### Phase 5: Polish & Testing (2 hours)
1. Test all games thoroughly
2. Verify Gemini integration
3. Test fallback system
4. Test localization
5. Test progress tracking
6. Fix any bugs

**Total Estimated Time:** 11-14 hours

---

## TESTING CHECKLIST

For Each Game:
- [ ] Start new session
- [ ] Complete 3+ rounds
- [ ] Intentionally make mistakes
- [ ] Test hint system
- [ ] Reach higher difficulty
- [ ] Exit and restart
- [ ] Test different language
- [ ] Test with Gemini available
- [ ] Test fallback (Gemini unavailable)
- [ ] Verify content changes between sessions
- [ ] Check progress is saved
- [ ] Verify no crashes

---

## SUCCESS CRITERIA

**Each Game Must:**
- ✅ Have 3+ levels/rounds
- ✅ Session lasts 2-5 minutes
- ✅ Content varies between sessions
- ✅ Difficulty adapts to performance
- ✅ Works in all 9 languages
- ✅ Gemini generates content (when available)
- ✅ Fallback works (when Gemini unavailable)
- ✅ Progress is saved per patient
- ✅ No repetitive content within 10 sessions
- ✅ Gentle, encouraging feedback
- ✅ No crashes or major bugs

---

## RECOMMENDATION

**For Hackathon (Limited Time):**

**Option A: Quick Win (3-4 hours)**
- Overhaul Card Matching (most visible)
- Overhaul Daily Routine (Gemini demo)
- Basic improvements to 2-3 other games
- Skip full overhaul of all 9 games

**Option B: Complete Overhaul (11-14 hours)**
- Overhaul all 9 games
- Full Gemini integration
- Complete progress tracking
- Full localization

**Option C: Strategic Selection (5-6 hours)**
- Overhaul top 4 games (Card Matching, Routine, Shopping Basket, Face Recognition)
- Basic improvements to other 5 games
- Focus on quality over quantity

**My Recommendation:** Option C for hackathon - impressive demo with 4 polished games + 5 functional games.

---

**Last Updated:** 2026-08-29  
**Next Step:** Begin Phase 1 (Core Infrastructure) or start with Card Matching overhaul
