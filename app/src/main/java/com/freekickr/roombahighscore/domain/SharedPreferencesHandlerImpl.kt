package com.freekickr.roombahighscore.domain

import android.content.Context
import com.freekickr.roombahighscore.entities.Round
import com.google.gson.Gson
import javax.inject.Inject
import javax.inject.Singleton

private const val APP_PREF_NAME = "roomba_highscore_pref"
private const val PREF_ROUND = "round"

@Singleton
class SharedPreferencesHandlerImpl @Inject constructor(): SharedPreferencesHandler {

    override fun checkUnfinishedGame(context: Context): Boolean {
        val preferences = context.getSharedPreferences(APP_PREF_NAME, Context.MODE_PRIVATE)
        return preferences.contains(PREF_ROUND)
    }

    override fun getUnfinishedGame(context: Context): Round? {
        val preferences = context.getSharedPreferences(APP_PREF_NAME, Context.MODE_PRIVATE)
        val roundString = preferences.getString(PREF_ROUND, null) ?: return null

        return Gson().fromJson(roundString, Round::class.java)
    }

    override fun clearData(context: Context) {
        val editor = context.getSharedPreferences(APP_PREF_NAME, Context.MODE_PRIVATE).edit()
        editor.remove(PREF_ROUND)
        editor.apply()
    }

    override fun saveGame(context: Context, round: Round) {
        val editor = context.getSharedPreferences(APP_PREF_NAME, Context.MODE_PRIVATE).edit()
        editor.putString(PREF_ROUND, Gson().toJson(round))

        editor.apply()
    }
}