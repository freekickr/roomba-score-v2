package com.freekickr.roombahighscore.entities

import android.util.Log
import androidx.databinding.ObservableBoolean

data class Round(
    var roundNumber: Int = 1,
    var player1: Player,
    var player2: Player,
    var player3: Player,
    var player4: Player,
    var player5: Player,
    var player6: Player,
) {
    companion object {
        fun newInstance(numberOfPlayers: Int): Round? {
            return when (numberOfPlayers) {
                2 -> {
                    Round(
                        1,
                        Player("Игрок 1", isInitialized = true),
                        Player("Игрок 2", isInitialized = true),
                        Player(
                            "Игрок 3",
                            isInitialized = false,
                            isInGame = ObservableBoolean(false)
                        ),
                        Player(
                            "Игрок 4",
                            isInitialized = false,
                            isInGame = ObservableBoolean(false)
                        ),
                        Player(
                            "Игрок 5",
                            isInitialized = false,
                            isInGame = ObservableBoolean(false)
                        ),
                        Player(
                            "Игрок 6",
                            isInitialized = false,
                            isInGame = ObservableBoolean(false)
                        )
                    )
                }
                3 -> {
                    Round(
                        1,
                        Player("Игрок 1", isInitialized = true),
                        Player("Игрок 2", isInitialized = true),
                        Player("Игрок 3", isInitialized = true),
                        Player(
                            "Игрок 4",
                            isInitialized = false,
                            isInGame = ObservableBoolean(false)
                        ),
                        Player(
                            "Игрок 5",
                            isInitialized = false,
                            isInGame = ObservableBoolean(false)
                        ),
                        Player(
                            "Игрок 6",
                            isInitialized = false,
                            isInGame = ObservableBoolean(false)
                        )
                    )
                }
                4 -> {
                    Round(
                        1,
                        Player("Игрок 1", isInitialized = true),
                        Player("Игрок 2", isInitialized = true),
                        Player("Игрок 3", isInitialized = true),
                        Player("Игрок 4", isInitialized = true),
                        Player(
                            "Игрок 5",
                            isInitialized = false,
                            isInGame = ObservableBoolean(false)
                        ),
                        Player(
                            "Игрок 6",
                            isInitialized = false,
                            isInGame = ObservableBoolean(false)
                        )
                    )
                }
                5 -> {
                    Round(
                        1,
                        Player("Игрок 1", isInitialized = true),
                        Player("Игрок 2", isInitialized = true),
                        Player("Игрок 3", isInitialized = true),
                        Player("Игрок 4", isInitialized = true),
                        Player("Игрок 5", isInitialized = true),
                        Player(
                            "Игрок 6",
                            isInitialized = false,
                            isInGame = ObservableBoolean(false)
                        )
                    )
                }
                6 -> {
                    Round(
                        1,
                        Player("Игрок 1", isInitialized = true),
                        Player("Игрок 2", isInitialized = true),
                        Player("Игрок 3", isInitialized = true),
                        Player("Игрок 4", isInitialized = true),
                        Player("Игрок 5", isInitialized = true),
                        Player("Игрок 6", isInitialized = true)
                    )
                }
                else -> null
            }
        }
    }

    fun addScore(scores: CurrentScores) {
        player1.score.set(player1.score.get() + scores.score1)
        player2.score.set(player2.score.get() + scores.score2)
        player3.score.set(player3.score.get() + scores.score3)
        player4.score.set(player4.score.get() + scores.score4)
        player5.score.set(player5.score.get() + scores.score5)
        player6.score.set(player6.score.get() + scores.score6)
    }

    fun checkScores(): List<String> {
        val stillInGame = mutableListOf<String>()
        if (player1.isLoose()) {
            player1.isInGame.set(false)
        } else if (player1.isInitialized) {
            stillInGame.add(player1.name)
        }
        if (player2.isLoose()) {
            player2.isInGame.set(false)
        } else if (player2.isInitialized) {
            stillInGame.add(player2.name)
        }
        if (player3.isLoose()) {
            player3.isInGame.set(false)
        } else if (player3.isInitialized) {
            stillInGame.add(player3.name)
        }
        if (player4.isLoose()) {
            player4.isInGame.set(false)
        } else if (player4.isInitialized) {
            stillInGame.add(player4.name)
        }
        if (player5.isLoose()) {
            player5.isInGame.set(false)
        } else if (player5.isInitialized) {
            stillInGame.add(player5.name)
        }
        if (player6.isLoose()) {
            player6.isInGame.set(false)
        } else if (player6.isInitialized) {
            stillInGame.add(player6.name)
        }
        return stillInGame
    }

}