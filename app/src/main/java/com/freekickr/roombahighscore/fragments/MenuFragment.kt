package com.freekickr.roombahighscore.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.fragment.findNavController
import com.freekickr.roombahighscore.R
import com.freekickr.roombahighscore.databinding.FragmentMenuBinding
import com.freekickr.roombahighscore.domain.SharedPreferencesHandler
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MenuFragment : Fragment() {

    private lateinit var binding: FragmentMenuBinding

    @Inject lateinit var sharedPreferencesHandler: SharedPreferencesHandler

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMenuBinding.inflate(layoutInflater)

        binding.btnRules.setOnClickListener {
            findNavController().navigate(MenuFragmentDirections.actionMenuFragmentToRulesFragment())
        }

        binding.ibAbout.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle(resources.getString(R.string.about))
                .setIcon(ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_info_24))
                .setMessage("Credits: \n www.freepik.com")
                .show()
        }

        binding.btnStartGame.setOnClickListener {
            if (sharedPreferencesHandler.checkUnfinishedGame(requireContext())) {
                showLoadGameDialog()
            } else {
                findNavController().navigate(MenuFragmentDirections.actionMenuFragmentToNewGameFragment())
            }
        }

        return binding.root
    }

    private fun showLoadGameDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Обнаружена незаконченная игра")
            .setMessage("Загрузить?")
            .setIcon(ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_sports_score_24))
            .setPositiveButton(resources.getString(R.string.yes)) { dialog, which ->
                findNavController().navigate(MenuFragmentDirections.actionMenuFragmentToGameFragment(loadGame = true))
            }
            .setNegativeButton(resources.getString(R.string.no)) { dialog, which ->
                sharedPreferencesHandler.clearData(requireContext())
                findNavController().navigate(MenuFragmentDirections.actionMenuFragmentToNewGameFragment())
            }
            .setNeutralButton(resources.getString(R.string.cancel)) { dialog, which ->
                dialog.dismiss()
            }
            .show()
    }
}