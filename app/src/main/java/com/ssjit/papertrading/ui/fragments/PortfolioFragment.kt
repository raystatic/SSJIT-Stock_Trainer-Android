package com.ssjit.papertrading.ui.fragments

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.ssjit.papertrading.R
import com.ssjit.papertrading.databinding.FragmentPortfolioBinding
import com.ssjit.papertrading.other.Constants
import com.ssjit.papertrading.other.Extensions.showSnack
import com.ssjit.papertrading.other.Status
import com.ssjit.papertrading.other.Utility
import com.ssjit.papertrading.ui.adapters.OrdersPagerAdapter
import com.ssjit.papertrading.ui.adapters.PortfolioPagerAdapter
import com.ssjit.papertrading.ui.viewmodels.OrdersViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class PortfolioFragment: Fragment() {

    private var _binding:FragmentPortfolioBinding?=null
    private val binding get() = _binding!!
    private lateinit var portfolioPagerAdapter: PortfolioPagerAdapter
    private val viewmodel by activityViewModels<OrdersViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentPortfolioBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.window?.statusBarColor = ContextCompat.getColor(requireContext(), R.color.brown)
        binding.portfolioTabLayout.setupWithViewPager(binding.portfolioPager)
        portfolioPagerAdapter = PortfolioPagerAdapter(childFragmentManager)
        binding.portfolioPager.adapter = portfolioPagerAdapter


        subscribeToObservers()

    }

    private fun subscribeToObservers() {

        viewmodel.ordersResponse.observe(viewLifecycleOwner,{
            when(it.status){
                Status.SUCCESS -> {
                    it.data?.let { res->
                        if (!res.error){
                            res.orders?.let { orders->
                                viewmodel.deleteAllOrders()
                                orders.forEach { order->
                                    viewmodel.insertOrder(order)
                                }

                            }
                        }else{
                            Timber.d("Error in getting orders: ${it.message}")
                            binding.root.showSnack(Constants.SOMETHING_WENT_WRONG)
                        }

                        viewmodel.isDataLoading(false)
                    }
                }
                Status.LOADING -> {
                    viewmodel.isDataLoading(true)
                }
                Status.ERROR -> {
                    binding.root.showSnack(it.message.toString())
                    viewmodel.isDataLoading(false)
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