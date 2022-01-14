package com.freekickr.roombahighscore.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.freekickr.roombahighscore.databinding.FragmentRulesBinding

class RulesFragment: Fragment() {

    private lateinit var binding: FragmentRulesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRulesBinding.inflate(layoutInflater)

        binding.btnClose.setOnClickListener {
            findNavController().navigateUp()
        }

        return binding.root
    }

}