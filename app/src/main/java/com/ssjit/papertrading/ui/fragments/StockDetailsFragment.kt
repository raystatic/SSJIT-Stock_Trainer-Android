package com.ssjit.papertrading.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.ssjit.papertrading.R
import com.ssjit.papertrading.databinding.ActivityStockDetailsBinding
import com.ssjit.papertrading.ui.adapters.StockPagerAdapter
import com.ssjit.papertrading.ui.viewmodels.StockInfoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StockDetailsFragment: Fragment() {

    private var _binding: ActivityStockDetailsBinding?=null
    private val binding get() = _binding!!

    private val viewModel by activityViewModels<StockInfoViewModel>()

    private lateinit var pagerAdapter: StockPagerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = ActivityStockDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.primaryblue)

        binding.imgBack.setOnClickListener {
//            finish()
            it.findNavController().navigate(R.id.action_stockDetailsFragment_to_watchlistFragment)
        }

        binding.stockTabLayout.setupWithViewPager(binding.stockPager)
        pagerAdapter = StockPagerAdapter(childFragmentManager)
        binding.stockPager.adapter = pagerAdapter


//
//        subscribeToObservers()
//
//        val section1 = DonutSection(
//                name = "section_1",
//                color = resources.getColor(R.color.primary_green),
//                amount = 0.81f
//        )
//
//        binding.donutView.cap = 1f
//        binding.donutView.submitData(listOf(section1))

    }

    private fun subscribeToObservers() {

//        viewModel.stockInfoResponse.observe(viewLifecycleOwner, {
//            when(it.status){
//                Status.SUCCESS -> {
//                    it.data?.let { res->
//                        if (!res.error){
//                            if (res.data?.data?.isEmpty() ==  true) return@observe
//                            val stock = res.data?.data?.get(0)
//                            //stock?.let { it1 -> viewModel.upsertStockData(it1) }
//
//                            binding.apply {
//                                tvCompanyName.text = stock?.companyName
//                                tvSymbol.text = stock?.symbol
//                                tvPrice.text = "₹${stock?.closePrice}"
//                                tvOpen.text = stock?.open
//                                tvHigh.text = stock?.dayHigh
//                                tv52WKHigh.text = stock?.high52
//                                tvPrevClose.text = stock?.previousClose
//                                tvLow.text = stock?.dayLow
//                                tv52WKLow.text = stock?.low52
//                                tvVol.text = stock?.totalTradedVolume
////                                tvMKTCap.text = ""
////                                tvCapType.text = ""
////                                tvPE.text = ""
//
//                                viewModel.addedStocks(stock!!).observe(viewLifecycleOwner,{list->
//                                    list?.let {s->
//                                        if (s.addedToWatchList == 1){
//                                            imgAddToWatchlist.setImageResource(R.drawable.ic_check)
//                                        }else{
//                                            imgAddToWatchlist.setImageResource(R.drawable.ic_add_white)
//                                        }
//                                    } ?: kotlin.run {
//                                        Timber.d("stock_debug local stocks null")
//                                        imgAddToWatchlist.setImageResource(R.drawable.ic_add_white)
//                                    }
//                                })
//
//                                imgAddToWatchlist.setOnClickListener {
//                                    if (stock.addedToWatchList == 1){
//                                        stock.addedToWatchList=0
//                                        imgAddToWatchlist.setImageResource(R.drawable.ic_add_white)
//                                    }else{
//                                        stock.addedToWatchList = 1
//                                        imgAddToWatchlist.setImageResource(R.drawable.ic_check)
//                                    }
//                                    stock.let { it1 -> viewModel.upsertStockData(it1) }
//                                }
//
//                            }
//                        }else{
//                            Timber.e("Error: ${it.message}")
//                            binding.root.showSnack(Constants.SOMETHING_WENT_WRONG)
//                        }
//                    }
//                }
//                Status.LOADING -> {
//
//                }
//
//                Status.ERROR -> {
//                    binding.root.showSnack(it.message.toString())
//                }
//
//            }
//        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object{
        var stockSymbol = ""
    }

}