package com.ssjit.papertrading.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.ssjit.papertrading.R
import com.ssjit.papertrading.databinding.FragmentPortfolioBinding
import com.ssjit.papertrading.ui.adapters.OrdersPagerAdapter
import com.ssjit.papertrading.ui.adapters.PortfolioPagerAdapter

class PortfolioFragment: Fragment() {

    private var _binding:FragmentPortfolioBinding?=null
    private val binding get() = _binding!!
    private lateinit var portfolioPagerAdapter: PortfolioPagerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentPortfolioBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.window?.statusBarColor = ContextCompat.getColor(requireContext(), R.color.brown)
        binding.portfolioTabLayout.setupWithViewPager(binding.portfolioPager)
        portfolioPagerAdapter = PortfolioPagerAdapter(childFragmentManager)
        binding.portfolioPager.adapter = portfolioPagerAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}