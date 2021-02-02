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
import com.ssjit.papertrading.databinding.FragmentOrdersExecutedBinding
import com.ssjit.papertrading.databinding.FragmentOrdersPendingBinding
import com.ssjit.papertrading.ui.adapters.OrderItemAdapter
import com.ssjit.papertrading.ui.viewmodels.OrdersViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrdersExecutedFragment: Fragment() {

    private var _binding: FragmentOrdersExecutedBinding?=null
    private val binding get() = _binding!!
    private val viewmodel by activityViewModels<OrdersViewModel>()
    private lateinit var orderItemAdapter: OrderItemAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentOrdersExecutedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        orderItemAdapter = OrderItemAdapter {

        }

        binding.rvExecutedOrders.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = orderItemAdapter
            val dividerItemDecoration = DividerItemDecoration(requireContext(),LinearLayoutManager.VERTICAL)
            addItemDecoration(dividerItemDecoration)
        }

        subscribeToObservers()

    }

    private fun subscribeToObservers() {
        viewmodel.executedOrders.observe(viewLifecycleOwner, {
            it?.let { orders->
                binding.apply {
                    if (orders.isEmpty()){
                        tvEmpty.isVisible = true
                        rvExecutedOrders.isVisible = false
                    }else{
                        tvEmpty.isVisible = false
                        rvExecutedOrders.isVisible = true
                        orderItemAdapter.submitData(orders)
                    }
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}