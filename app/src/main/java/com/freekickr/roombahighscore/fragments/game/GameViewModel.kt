package com.freekickr.roombahighscore.fragments.game

import android.content.Context
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.freekickr.roombahighscore.domain.SharedPreferencesHandler
import com.freekickr.roombahighscore.entities.CurrentScores
import com.freekickr.roombahighscore.entities.Player
import com.freekickr.roombahighscore.entities.Round
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val sharedPreferencesHandler: SharedPreferencesHandler
) : ViewModel() {

    val isRoombaPressed = ObservableBoolean(false)
    val mRound = ObservableField<Round>()

    val mScores = ObservableField<Scores>(Scores())

    val isGameFinished = ObservableBoolean(false)

    private val _winner: MutableLiveData<String> = MutableLiveData(null)
    val winner: LiveData<String>
        get() = _winner

    fun getUnfinishedGame(context: Context) = sharedPreferencesHandler.getUnfinishedGame(context)

    fun setRound(round: Round) {
        mRound.set(round)
    }

    fun setRoomba() {
        isRoombaPressed.set(true)
    }

    fun setPlaying() {
        isRoombaPressed.set(false)
        mScores.get()?.let {
            addScoresToRound(it.getScore())
            it.clearFields()
            checkScores()
        }
    }

    private fun addScoresToRound(score: CurrentScores) {
        mRound.get()?.addScore(score)
    }

    private fun checkScores() {
        mRound.get()?.let {
            val inGame = it.checkScores()
            if (inGame.size == 1) {
                _winner.postValue(inGame.first())
            }
        }
    }

    fun winnerReceived() {
        _winner.postValue(null)
    }

    fun saveGame(context: Context) {
        mRound.get()?.let {
            sharedPreferencesHandler.saveGame(context, it)
        }
    }

    fun clearSharedData(context: Context) {
        sharedPreferencesHandler.clearData(context)
    }

}

data class Scores(
    var score1: ObservableField<String> = ObservableField(""),
    var score2: ObservableField<String> = ObservableField(""),
    var score3: ObservableField<String> = ObservableField(""),
    var score4: ObservableField<String> = ObservableField(""),
    var score5: ObservableField<String> = ObservableField(""),
    var score6: ObservableField<String> = ObservableField(""),
) {
    fun clearFields() {
        score1.set("")
        score2.set("")
        score3.set("")
        score4.set("")
        score5.set("")
        score6.set("")
    }

    fun getScore(): CurrentScores {
        val readyScore = CurrentScores()
        score1.get()?.let {
            readyScore.score1 = it.toIntOrNull() ?: 0
        }
        score2.get()?.let {
            readyScore.score2 = it.toIntOrNull() ?: 0
        }
        score3.get()?.let {
            readyScore.score3 = it.toIntOrNull() ?: 0
        }
        score4.get()?.let {
            readyScore.score4 = it.toIntOrNull() ?: 0
        }
        score5.get()?.let {
            readyScore.score5 = it.toIntOrNull() ?: 0
        }
        score6.get()?.let {
            readyScore.score6 = it.toIntOrNull() ?: 0
        }

        return readyScore
    }
}