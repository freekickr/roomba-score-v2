package com.freekickr.roombahighscore.domain

import android.content.Context
import com.freekickr.roombahighscore.entities.Round

interface SharedPreferencesHandler {
    fun checkUnfinishedGame(context: Context): Boolean

    fun getUnfinishedGame(context: Context): Round?

    fun clearData(context: Context)

    fun saveGame(context: Context, round: Round)
}