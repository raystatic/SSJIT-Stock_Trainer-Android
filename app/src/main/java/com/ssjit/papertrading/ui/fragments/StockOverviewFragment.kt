package com.ssjit.papertrading.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.ssjit.papertrading.databinding.FragmentStockOverviewBinding
import com.ssjit.papertrading.other.Constants
import com.ssjit.papertrading.other.Utility
import com.ssjit.papertrading.ui.viewmodels.StockInfoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StockOverviewFragment: Fragment() {

    private var _binding: FragmentStockOverviewBinding?=null
    private val binding get() = _binding!!

    private val viewmodel by activityViewModels<StockInfoViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentStockOverviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBuy.setOnClickListener {
            val buySellDialogFragment = BuySellDialogFragment()
            BuySellDialogFragment.type = Constants.BUY_STOCK
            buySellDialogFragment.show(childFragmentManager,buySellDialogFragment.tag)
        }

        binding.btnSell.setOnClickListener {
            val buySellDialogFragment = BuySellDialogFragment()
            BuySellDialogFragment.type = Constants.SELL_STOCK
            buySellDialogFragment.show(childFragmentManager,buySellDialogFragment.tag)
        }

        binding.lowHighSeekbar.setOnTouchListener { v, event ->
            return@setOnTouchListener true
        }

        binding.seekbar52.setOnTouchListener { v, event ->
            return@setOnTouchListener true
        }

        subscribeToObservers()

    }

    private fun subscribeToObservers() {
        viewmodel.currentStockData.observe(viewLifecycleOwner,{
            it?.let {
                binding.apply {
                    tvCompany.text = it.companyName
                    tvOpen.text = Utility.format(it.open)
                    tvUpperCircuit.text = Utility.format(it.pricebandupper)
                    tvVolume.text = Utility.format(it.totalTradedVolume)
                    tvClose.text = Utility.format(it.closePrice)
                    tvLowerCircuit.text = Utility.format(it.pricebandlower)
                    tvATP.text = Utility.format(it.averagePrice)
                    tvLow.text = Utility.format(it.dayLow)
                    tvHigh.text = Utility.format(it.dayHigh)
                    tv52Low.text = Utility.format(it.low52)
                    tv52High.text = Utility.format(it.high52)
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}