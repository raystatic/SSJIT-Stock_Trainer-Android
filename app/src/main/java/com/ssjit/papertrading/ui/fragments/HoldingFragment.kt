package com.ssjit.papertrading.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssjit.papertrading.databinding.FragmentHoldingBinding
import com.ssjit.papertrading.other.Constants
import com.ssjit.papertrading.ui.adapters.PortfolioItemAdapter
import com.ssjit.papertrading.ui.viewmodels.OrdersViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HoldingFragment: Fragment() {

    private var _binding: FragmentHoldingBinding?=null
    private val binding get() = _binding!!
    private val viewmodel by activityViewModels<OrdersViewModel>()
    private lateinit var portfolioAdapter:PortfolioItemAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentHoldingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        portfolioAdapter = PortfolioItemAdapter(requireContext()) {
            val buySellDialogFragment = BuySellDialogFragment()
            BuySellDialogFragment.type = Constants.SELL_STOCK
            BuySellDialogFragment.toSellOrder = it
            buySellDialogFragment.show(childFragmentManager,buySellDialogFragment.tag)
        }

        binding.rvHoldings.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = portfolioAdapter
            val dividerItemDecoration = DividerItemDecoration(requireContext(),LinearLayoutManager.VERTICAL)
            addItemDecoration(dividerItemDecoration)
        }

        subscribeToObservers()

    }

    private fun subscribeToObservers() {
        viewmodel.holdings.observe(viewLifecycleOwner,{
            it?.let {
                binding.rvHoldings.isVisible = it.isNotEmpty()
                binding.tvEmpty.isVisible = it.isEmpty()
                portfolioAdapter.submitData(it)
                binding.rvHoldings.scrollToPosition(0)
            }
        })

        viewmodel.dataLoading.observe(viewLifecycleOwner,{
            it?.let {
                binding.rvHoldings.isVisible = !it
                binding.progressBar.isVisible = it
            }
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}