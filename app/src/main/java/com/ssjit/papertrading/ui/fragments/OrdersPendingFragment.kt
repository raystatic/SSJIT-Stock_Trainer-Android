package com.ssjit.papertrading.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssjit.papertrading.databinding.FragmentOrdersPendingBinding
import com.ssjit.papertrading.databinding.FragmentPortfolioBinding
import com.ssjit.papertrading.ui.adapters.OrderItemAdapter
import com.ssjit.papertrading.ui.viewmodels.OrdersViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class OrdersPendingFragment: Fragment() {

    private var _binding: FragmentOrdersPendingBinding?=null
    private val binding get() = _binding!!
    private val viewmodel by activityViewModels<OrdersViewModel>()
    private lateinit var orderItemAdapter:OrderItemAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentOrdersPendingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        orderItemAdapter = OrderItemAdapter(
                requireContext(),
            onClick = {

            }
        )

        binding.rvPendingOrders.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = orderItemAdapter
            val dividerItemDecoration = DividerItemDecoration(requireContext(),LinearLayoutManager.VERTICAL)
            addItemDecoration(dividerItemDecoration)
        }

        subscribeToObservers()
    }

    private fun subscribeToObservers() {
        viewmodel.pendingOrders.observe(viewLifecycleOwner, {
            it?.let { orders->
                binding.apply {

                    tvEmpty.isVisible = orders.isEmpty()
                    rvPendingOrders.isVisible = orders.isNotEmpty()

                    orderItemAdapter.submitData(orders)
                    rvPendingOrders.scrollToPosition(0)

                }
            }
        })

        viewmodel.dataLoading.observe(viewLifecycleOwner,{
            it?.let {
                binding.rvPendingOrders.isVisible = !it
                binding.progressBar.isVisible = it
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}