package com.jeishiva.entities

/**
 * Represents a player's game state, including their progress in the game.
 * Used by GameStateFetcher and GameStateCache.
 *
 * @param playerId Unique identifier for the player.
 * @param level Current level the player has reached.
 * @param score Total score accumulated by the player.
 */
data class GameState(
    val playerId: Int,
    val level: Int,
    val score: Int,
)
