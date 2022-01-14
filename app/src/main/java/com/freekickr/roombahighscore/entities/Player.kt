package com.freekickr.roombahighscore.entities

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableInt

data class Player(
    val name: String = "Player",
    val isInitialized: Boolean = false,
    var isInGame: ObservableBoolean = ObservableBoolean(true),
    var score: ObservableInt = ObservableInt(0)
) {
    fun isLoose(): Boolean {
        return score.get() >= 100
    }
}
