package com.ssjit.papertrading.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.ssjit.papertrading.databinding.FragmentStockChartBinding
import com.ssjit.papertrading.databinding.FragmentStockOverviewBinding

class StockChartFragment: Fragment() {

    private var _binding: FragmentStockChartBinding?=null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentStockChartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.cartWebView.apply {
            settings.javaScriptEnabled = true
            loadUrl("https://in.tradingview.com/chart/?symbol=NSE:TCS")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object{
        var stockSymbol = ""
    }

}