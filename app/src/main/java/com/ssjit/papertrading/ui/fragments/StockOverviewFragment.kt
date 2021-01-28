package com.ssjit.papertrading.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.ssjit.papertrading.databinding.FragmentHoldingBinding
import com.ssjit.papertrading.databinding.FragmentStockOverviewBinding
import com.ssjit.papertrading.other.Constants

class StockOverviewFragment: Fragment() {

    private var _binding: FragmentStockOverviewBinding?=null
    private val binding get() = _binding!!

    private lateinit var buySellDialogFragment: BuySellDialogFragment

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentStockOverviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buySellDialogFragment = BuySellDialogFragment()

        binding.btnBuy.setOnClickListener {
            BuySellDialogFragment.type = Constants.BUY
            buySellDialogFragment.show(childFragmentManager,buySellDialogFragment.tag)
        }

        binding.btnSell.setOnClickListener {
            BuySellDialogFragment.type = Constants.SELL
            buySellDialogFragment.show(childFragmentManager,buySellDialogFragment.tag)
        }

        binding.lowHighSeekbar.setOnTouchListener { v, event ->
            return@setOnTouchListener true
        }

        binding.seekbar52.setOnTouchListener { v, event ->
            return@setOnTouchListener true
        }



    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}