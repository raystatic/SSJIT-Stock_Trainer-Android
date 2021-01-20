package com.ssjit.papertrading.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssjit.papertrading.R
import com.ssjit.papertrading.databinding.FragmentWatchlistBinding
import com.ssjit.papertrading.other.Constants
import com.ssjit.papertrading.ui.activities.StockDetailsActivity
import com.ssjit.papertrading.ui.adapters.WatchlistAdapter
import com.ssjit.papertrading.ui.viewmodels.WatchlistViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WatchlistFragment: Fragment() {

    private var _binding:FragmentWatchlistBinding?=null

    private val binding get() = _binding!!

    private val viewModel by viewModels<WatchlistViewModel>()

    private lateinit var watchlistAdapter:WatchlistAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentWatchlistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.cardSearch.setOnClickListener {v->
            v.findNavController().navigate(R.id.action_watchlistFragment_to_searchFragment)
        }

        watchlistAdapter = WatchlistAdapter {
            it?.let {
                val intent = Intent(requireContext(), StockDetailsActivity::class.java)
                intent.putExtra(Constants.STOCK_SYMBOL,it)
                startActivity(intent)
            }
        }

        binding.rvWatchlist.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = watchlistAdapter
        }

        viewModel.watchList.observe(viewLifecycleOwner,{
            it.let {
                watchlistAdapter.submitData(it)
            }
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}