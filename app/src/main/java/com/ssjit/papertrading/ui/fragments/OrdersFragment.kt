package com.ssjit.papertrading.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.activityViewModels
import com.ssjit.papertrading.R
import com.ssjit.papertrading.databinding.FragmentOrdersBinding
import com.ssjit.papertrading.other.Constants
import com.ssjit.papertrading.other.Extensions.showSnack
import com.ssjit.papertrading.other.Status
import com.ssjit.papertrading.ui.adapters.OrdersPagerAdapter
import com.ssjit.papertrading.ui.viewmodels.OrdersViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class OrdersFragment: Fragment() {

    private var _binding:FragmentOrdersBinding?=null
    private val binding get() = _binding!!
    private lateinit var orderPagerAdapter: OrdersPagerAdapter

    private val viewmodel by activityViewModels<OrdersViewModel>()

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

        subscribeToObservers()

    }

    private fun subscribeToObservers() {

        viewmodel.ordersResponse.observe(viewLifecycleOwner,{
            when(it.status){
                Status.SUCCESS -> {
                    it.data?.let { res->
                        if (!res.error){
                            res.orders?.let { orders->
                                orders.forEach { order->
                                    viewmodel.insertOrder(order)
                                }
                            }
                        }else{
                            Timber.d("Error in getting orders: ${it.message}")
                            binding.root.showSnack(Constants.SOMETHING_WENT_WRONG)
                        }
                    }
                }
                Status.LOADING -> {

                }
                Status.ERROR -> {
                    binding.root.showSnack(it.message.toString())
                }
            }
        })

        viewmodel.user.observe(viewLifecycleOwner,{
            it?.let { user ->
                viewmodel.getOrders(user.id)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}