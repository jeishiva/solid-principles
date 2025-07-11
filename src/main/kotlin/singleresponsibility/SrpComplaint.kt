package com.jeishiva.singleresponsibility

import com.jeishiva.common.GameState

/**
 * This implementation follows the Single Responsibility Principle (SRP).
 *
 * How it’s compliant:
 * --------------------------------------------------
 * 1. Each class and interface has a single responsibility:
 *    - GameFeatureFlagFetcher → fetches game feature flags only
 *    - GameStateFetcher → fetches game state only
 *    - GameStateCache → handles caching game state only
 *
 * 2. Changes in one responsibility affect only one class:
 *    - You can modify feature flag retrieval without affecting game state or caching logic.
 *    - You can switch caching (e.g., from memory to disk) independently.
 *
 * 3. Improves maintainability:
 *    - Focused classes are easier to understand and modify.
 *
 * 4. Enhances extensibility:
 *    - Easy to add features (e.g., cloud sync, new game modes) without impacting unrelated parts.
 *
 * 5. Promotes focused unit testing:
 *    - Each class can be tested in isolation with mocks/stubs.
 *    - No hidden side effects or tight coupling.
 *
 * This separation of concerns makes the codebase robust, scalable, and demo-friendly.
 */

/**
 * Responsible only for fetching game feature flags.
 * Can be extended or swapped (e.g., remote, local, mock).
 * Easy to test with stubs/mocks.
 */
interface GameFeatureFlagFetcher {
    fun downloadGameFeatureFlags(): Map<String, Boolean>
}

/**
 * Responsible only for fetching game state.
 * Not concerned with caching or feature flags.
 * Independent and easy to unit test.
 */
interface GameStateFetcher {
    fun downloadGameState(playerId: Int): GameState
}

/**
 * Responsible only for caching game state.
 * Separated from data fetching logic.
 * Can be replaced with memory, disk, or DB-based implementation.
 */
interface GameStateCache {
    fun cacheGameState(gameState: GameState): Boolean
    fun getGameState(playerId: Int): GameState?
}

/**
 * Concrete implementation of GameFeatureFlagFetcher.
 * Responsible only for game feature flag retrieval.
 */
class RemoteGameFeatureFlagFetcher : GameFeatureFlagFetcher {
    override fun downloadGameFeatureFlags(): Map<String, Boolean> {
        // Simulate remote call for game feature flags
        return mapOf(
            "isMultiplayerEnabled" to true,    // Clear, boolean-friendly name
            "isLeaderboardEnabled" to false    // Consistent naming
        )
    }
}

/**
 * Concrete implementation of GameStateFetcher.
 * Handles only the game state fetching logic.
 */
class RemoteGameStateFetcher : GameStateFetcher {
    override fun downloadGameState(playerId: Int): GameState {
        // Simulate remote game state fetch with sample achievements
        return GameState(playerId, level = 5, score = 1000)
    }
}

/**
 * Concrete implementation of GameStateCache.
 * Handles only storing and retrieving game state.
 */
class InMemoryGameStateCache : GameStateCache {
    private val cache = mutableMapOf<Int, GameState>()

    override fun cacheGameState(gameState: GameState): Boolean {
        return try {
            cache[gameState.playerId] = gameState
            true
        } catch (e: Exception) {
            println("Failed to cache game state for player ID: ${gameState.playerId}")
            false
        }
    }

    override fun getGameState(playerId: Int): GameState? {
        return cache[playerId]
    }
}

/**
 * GameSettingsCoordinator orchestrates high-level game settings operations.
 *
 * SRP Compliance:
 * --------------------------------------------------
 * - Delegates responsibilities to specialized collaborators:
 *     - GameFeatureFlagFetcher handles game feature flag retrieval
 *     - GameStateFetcher handles fetching game state
 *     - GameStateCache handles caching
 *
 * Promotes loose coupling and high cohesion.
 */
class GameSettingsCoordinator(
    private val featureFlagFetcher: GameFeatureFlagFetcher,
    private val gameStateFetcher: GameStateFetcher,
    private val gameStateCache: GameStateCache
) {
    fun fetchGameState(playerId: Int): GameState {
        return gameStateFetcher.downloadGameState(playerId)
    }

    fun cacheGameState(gameState: GameState): Boolean {
        return gameStateCache.cacheGameState(gameState)
    }

    fun getCachedGameState(playerId: Int): GameState? {
        return gameStateCache.getGameState(playerId)
    }

    fun getGameFeatureFlags(): Map<String, Boolean> {
        return featureFlagFetcher.downloadGameFeatureFlags()
    }
}