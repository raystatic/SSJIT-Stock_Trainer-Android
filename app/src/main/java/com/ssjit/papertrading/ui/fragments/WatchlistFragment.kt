package com.ssjit.papertrading.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.ssjit.papertrading.R
import com.ssjit.papertrading.databinding.FragmentWatchlistBinding

class WatchlistFragment: Fragment() {

    private var _binding:FragmentWatchlistBinding?=null

    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentWatchlistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.cardSearch.setOnClickListener {v->
            v.findNavController().navigate(R.id.action_watchlistFragment_to_searchFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}