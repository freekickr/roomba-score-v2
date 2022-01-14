package com.freekickr.roombahighscore.fragments.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.freekickr.roombahighscore.R
import com.freekickr.roombahighscore.databinding.FragmentGameBinding
import com.freekickr.roombahighscore.domain.SharedPreferencesHandler
import com.freekickr.roombahighscore.entities.Round
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class GameFragment : Fragment() {

    @Inject lateinit var sharedPreferencesHandler: SharedPreferencesHandler
    private val gameViewModel: GameViewModel by hiltNavGraphViewModels(R.id.nav_graph)

    private lateinit var binding: FragmentGameBinding
    private val args: GameFragmentArgs by navArgs()

//    private lateinit var currentRound: Round

    private val backButtonCallback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            showLeaveGameDialog()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGameBinding.inflate(layoutInflater)

        binding.viewModel = gameViewModel

        val numberOfPlayers = args.playerNum
        val isLoad = args.loadGame

        if (isLoad) {
            val savedGame = gameViewModel.getUnfinishedGame(requireContext())
            if (savedGame == null) {
                showCreationError()
            } else {
                initializeLoadedGame(savedGame)
            }
        } else {
            if (numberOfPlayers !in 2..6) {
                showCreationError()
            } else {
                setupGame(numberOfPlayers)
            }
        }

        gameViewModel.winner.observe(viewLifecycleOwner, {
            if (it != null) {
                gameViewModel.isGameFinished.set(true)
                gameViewModel.winnerReceived()
                showWinDialog(it)
            }
        })

        binding.btnRoomba.setOnClickListener {
            gameViewModel.setRoomba()
        }

        binding.btnEndRound.setOnClickListener {
            gameViewModel.setPlaying()
        }

        registerBackButtonCallback()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        unregisterBackButtonCallback()
    }

    private fun registerBackButtonCallback() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            backButtonCallback
        )
    }

    private fun unregisterBackButtonCallback() {
        backButtonCallback.remove()
    }

    private fun showWinDialog(winner: String) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Победа")
            .setMessage("$winner победил")
            .setIcon(ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_sports_score_24))
            .setCancelable(false)
            .setPositiveButton("Завершить") { dialog, which ->
                gameViewModel.clearSharedData(requireContext())
                findNavController().navigateUp()
            }
            .show()
    }

    private fun showLeaveGameDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(resources.getString(R.string.game_exit))
            .setMessage(resources.getString(R.string.do_save_game))
            .setIcon(ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_sports_score_24))
            .setPositiveButton(resources.getString(R.string.yes)) { dialog, which ->
                gameViewModel.saveGame(requireContext())
                findNavController().navigateUp()
            }
            .setNegativeButton(resources.getString(R.string.no)) { dialog, which ->
                gameViewModel.clearSharedData(requireContext())
                findNavController().navigateUp()
            }
            .setNeutralButton(resources.getString(R.string.cancel)) { dialog, which ->
                dialog.dismiss()
            }
            .show()
    }

    private fun setupGame(numberOfPlayers: Int) {
        val initRound = Round.newInstance(numberOfPlayers)
        if (initRound == null) {
            showCreationError()
        } else {
            gameViewModel.setRound(initRound)
            gameViewModel.isGameFinished.set(false)
            gameViewModel.isRoombaPressed.set(false)
        }
    }

    private fun initializeLoadedGame(round: Round) {
        gameViewModel.setRound(round)
        gameViewModel.isGameFinished.set(false)
        gameViewModel.isRoombaPressed.set(false)
    }

    private fun showCreationError() {
        Toast.makeText(
            requireContext(),
            resources.getString(R.string.creationError),
            Toast.LENGTH_SHORT
        ).show()
        findNavController().navigateUp()
    }

}