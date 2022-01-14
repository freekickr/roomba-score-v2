package com.freekickr.roombahighscore.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.freekickr.roombahighscore.databinding.FragmentNewGameBinding

class NewGameFragment: Fragment() {

    private lateinit var binding: FragmentNewGameBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewGameBinding.inflate(layoutInflater)

        setPlayersButtonListener()

        return binding.root
    }

    private fun setPlayersButtonListener() {
        binding.btn2Players.setOnClickListener {
            goToGameFragment(2)
        }
        binding.btn3Players.setOnClickListener {
            goToGameFragment(3)
        }
        binding.btn4Players.setOnClickListener {
            goToGameFragment(4)
        }
        binding.btn5Players.setOnClickListener {
            goToGameFragment(5)
        }
        binding.btn6Players.setOnClickListener {
            goToGameFragment(6)
        }
    }

    private fun goToGameFragment(playerNum: Int) {
        findNavController().navigate(NewGameFragmentDirections.actionNewGameFragmentToGameFragment(playerNum))
    }
}