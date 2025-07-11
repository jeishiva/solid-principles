## Single Responsibility Principle (SRP) — Game Settings Management

### 📖 Story

In a game application, we handle:
- **Fetching feature flags** (e.g., multiplayer enabled?)
- **Fetching player game state** (e.g., level, score)
- **Caching game state**

Initially, all responsibilities were handled by one class.

### 🚫 Problem

When a class handles **multiple unrelated responsibilities**, like:
- Downloading feature flags
- Fetching game state
- Caching data

...you violate the **Single Responsibility Principle**.

> One class should have **only one reason to change**.

### ✅ Solution

We refactor the logic into focused components:
- `GameFeatureFlagFetcher` — fetches feature flags only
- `GameStateFetcher` — fetches game state only
- `GameStateCache` — handles caching only

This improves:
- **Maintainability** → Each class is easier to modify and understand
- **Extensibility** → You can add cloud sync or disk caching without touching unrelated code
- **Testability** → Each class can be unit tested in isolation