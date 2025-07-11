# Liskov Substitution Principle (LSP) — YouTube Playback Example

## 📖 Story

In a media player system (like YouTube), we have two types of content:
- **Recorded videos** (e.g., movie trailers)
- **Live streams** (e.g., sports matches)

Both support **play**, but only recorded videos support **rewind**.  
Live streams **cannot** be rewound while playing.

### 🚫 Problem

If we design both `Video` and `LiveStream` to extend a common class with `play()` and `rewind()`,  
we might be tempted to override `rewind()` in `LiveStream` to **throw an exception**.

This violates the **Liskov Substitution Principle (LSP)**:
> Subtypes must be usable in place of their base types without breaking expected behavior.

### ✅ Solution

We should separate the behaviors using **interfaces**:
- A common interface for `Playable`
- A separate interface for `Rewindable`

This allows us to use polymorphism safely — and avoids surprise errors or behavior changes.

