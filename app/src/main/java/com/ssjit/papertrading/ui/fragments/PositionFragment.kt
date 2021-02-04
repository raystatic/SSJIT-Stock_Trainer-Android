package com.ssjit.papertrading.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssjit.papertrading.databinding.FragmentPositionBinding
import com.ssjit.papertrading.ui.adapters.PortfolioItemAdapter
import com.ssjit.papertrading.ui.viewmodels.OrdersViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_position.*

@AndroidEntryPoint
class PositionFragment: Fragment() {

    private var _binding: FragmentPositionBinding?=null
    private val binding get() = _binding!!
    private val viewmodel by activityViewModels<OrdersViewModel>()
    private lateinit var portfolioAdapter: PortfolioItemAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentPositionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        portfolioAdapter = PortfolioItemAdapter(requireContext()) {

        }

        binding.rvPositions.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = portfolioAdapter
            val dividerItemDecoration = DividerItemDecoration(requireContext(),LinearLayoutManager.VERTICAL)
            addItemDecoration(dividerItemDecoration)
        }

        subscribeToObservers()
    }

    private fun subscribeToObservers() {
        viewmodel.positions.observe(viewLifecycleOwner,{
            it?.let {
                binding.rvPositions.isVisible = it.isNotEmpty()
                binding.tvEmpty.isVisible = it.isEmpty()
                portfolioAdapter.submitData(it)
                binding.rvPositions.scrollToPosition(0)
            }
        })

        viewmodel.dataLoading.observe(viewLifecycleOwner,{
            it?.let {
                binding.rvPositions.isVisible = !it
                binding.progressBar.isVisible = it
            }
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}