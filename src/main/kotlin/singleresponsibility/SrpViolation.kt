package com.jeishiva.singleresponsibility

import com.jeishiva.entities.GameState

/**
 * This class violates the Single Responsibility Principle.
 *
 * Reasons:
 * 1. This class is responsible for multiple things:
 *    - Downloading game feature flags
 *    - Downloading game state
 *    - Caching game state
 *
 * 2. Multiple unrelated changes can affect this class.
 *    For example:
 *      - A change in how feature flags are fetched (e.g., from remote to local)
 *      - A change in the game state structure (e.g., adding new fields)
 *      - A change in caching strategy (e.g., from memory to database)
 *
 * 3. Maintainability, extensibility, and testing become difficult.
 *    - Too many reasons to modify the class
 *    - Hard to write focused unit tests due to coupled logic
 *    - Violates separation of concerns
 */
class GameSettingsManager {
    private val cache = mutableMapOf<Int, GameState>()

    fun downloadGameFeatureFlags(): Map<String, Boolean> {
        return mapOf(
            "isMultiplayerEnabled" to true,
            "isLeaderboardEnabled" to false
        )
    }

    fun downloadGameState(playerId: Int): GameState {
        return GameState(playerId, level = 5, score = 1000)
    }

    fun cacheGameState(gameState: GameState): Boolean {
        return try {
            cache[gameState.playerId] = gameState
            true
        } catch (e: Exception) {
            println("Failed to cache game state for player ID: ${gameState.playerId}")
            false
        }
    }

    fun getGameState(playerId: Int): GameState? {
        return cache[playerId]
    }
}