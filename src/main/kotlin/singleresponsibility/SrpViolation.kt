package singleresponsibility

import com.jeishiva.common.GameState

/**
 * This class violates the Single Responsibility Principle.
 *
 * It handles multiple responsibilities:
 * - Downloading feature flags
 * - Fetching game state
 * - Caching game state
 *
 * This makes the class hard to test, maintain, and extend.
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