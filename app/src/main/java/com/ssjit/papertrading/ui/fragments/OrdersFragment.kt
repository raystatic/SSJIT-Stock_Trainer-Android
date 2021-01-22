package com.ssjit.papertrading.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import com.ssjit.papertrading.R
import com.ssjit.papertrading.databinding.FragmentOrdersBinding
import com.ssjit.papertrading.ui.adapters.OrdersPagerAdapter

class OrdersFragment: Fragment() {

    private var _binding:FragmentOrdersBinding?=null
    private val binding get() = _binding!!
    private lateinit var orderPagerAdapter: OrdersPagerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentOrdersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.window?.statusBarColor = ContextCompat.getColor(requireContext(), R.color.green)
        binding.ordersTabLayout.setupWithViewPager(binding.ordersPager)
        orderPagerAdapter = OrdersPagerAdapter(childFragmentManager)
        binding.ordersPager.adapter = orderPagerAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}