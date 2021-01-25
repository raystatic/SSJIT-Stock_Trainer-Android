package com.ssjit.papertrading.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import app.futured.donut.DonutSection
import com.ssjit.papertrading.R
import com.ssjit.papertrading.data.models.stockdetail.StockData
import com.ssjit.papertrading.databinding.ActivityStockDetailsBinding
import com.ssjit.papertrading.other.Constants
import com.ssjit.papertrading.other.Status
import com.ssjit.papertrading.other.ViewExtension.showSnack
import com.ssjit.papertrading.ui.viewmodels.StockInfoViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class StockDetailsActivity : AppCompatActivity() {

    private lateinit var binding:ActivityStockDetailsBinding

    private var stockSymbol = ""
    private var isLocal = false

    private val viewModel by viewModels<StockInfoViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStockDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = ContextCompat.getColor(this,R.color.green)

        stockSymbol = intent.getStringExtra(Constants.STOCK_SYMBOL) ?: ""
        isLocal = intent.getBooleanExtra(Constants.ISLOCAL, false)

        if (stockSymbol.isEmpty()) finish()

        binding.imgBack.setOnClickListener {
            finish()
        }

        viewModel.getStockInfo(symbol = stockSymbol)


        subscribeToObservers()

        val section1 = DonutSection(
            name = "section_1",
            color = resources.getColor(R.color.primary_green),
            amount = 0.81f
        )

        binding.donutView.cap = 1f
        binding.donutView.submitData(listOf(section1))

    }

    private fun subscribeToObservers() {

//        viewModel.currentStockData.observe(this,{
//            it?.let { stock ->
//                binding.apply {
//                    tvCompanyName.text = stock?.companyName
//                    tvSymbol.text = stock?.symbol
//                    tvPrice.text = "₹${stock?.closePrice}"
//                    tvOpen.text = stock?.open
//                    tvHigh.text = stock?.dayHigh
//                    tv52WKHigh.text = stock?.high52
//                    tvPrevClose.text = stock?.previousClose
//                    tvLow.text = stock?.dayLow
//                    tv52WKLow.text = stock?.low52
//                    tvVol.text = stock?.totalTradedVolume
////                                tvMKTCap.text = ""
////                                tvCapType.text = ""
////                                tvPE.text = ""
//
//
//                    imgAddToWatchlist.setOnClickListener {
//                        if (stock?.addedToWatchList == 1){
//                            stock.addedToWatchList=0
//                            imgAddToWatchlist.setImageResource(R.drawable.ic_add_white)
//                        }else{
//                            stock?.addedToWatchList = 1
//                            imgAddToWatchlist.setImageResource(R.drawable.ic_check)
//                        }
//                        stock?.let { it1 -> viewModel.upsertStockData(it1) }
//                    }
//
//                }
//            }
//        })

        viewModel.stockInfoResponse.observe(this, {
            when(it.status){
                Status.SUCCESS -> {
                    it.data?.let { res->
                        if (!res.error){
                            val stock = res.data?.data?.get(0)
                            stock?.let { it1 -> viewModel.upsertStockData(it1) }

                            binding.apply {
                                tvCompanyName.text = stock?.companyName
                                tvSymbol.text = stock?.symbol
                                tvPrice.text = "₹${stock?.closePrice}"
                                tvOpen.text = stock?.open
                                tvHigh.text = stock?.dayHigh
                                tv52WKHigh.text = stock?.high52
                                tvPrevClose.text = stock?.previousClose
                                tvLow.text = stock?.dayLow
                                tv52WKLow.text = stock?.low52
                                tvVol.text = stock?.totalTradedVolume
//                                tvMKTCap.text = ""
//                                tvCapType.text = ""
//                                tvPE.text = ""

                                viewModel.watchList.observe(this@StockDetailsActivity,{list->
                                    list?.let {d->
                                       val s:List<StockData> =  d.filter {
                                            it.symbol == stock?.symbol
                                        }
                                        Timber.d("stock_debug $s")
                                        if (s.isEmpty()){
                                            stock?.addedToWatchList = 0
                                        }else{
                                            stock?.addedToWatchList = 1
                                        }

                                        if (stock?.addedToWatchList == 1){
                                            imgAddToWatchlist.setImageResource(R.drawable.ic_check)
                                        }else{
                                            imgAddToWatchlist.setImageResource(R.drawable.ic_add_white)
                                        }

                                    } ?: kotlin.run {
                                        Timber.d("stock_debug local stocks null")
                                    }
                                })

                                imgAddToWatchlist.setOnClickListener {
                                    if (stock?.addedToWatchList == 1){
                                        stock.addedToWatchList=0
                                        imgAddToWatchlist.setImageResource(R.drawable.ic_add_white)
                                    }else{
                                        stock?.addedToWatchList = 1
                                        imgAddToWatchlist.setImageResource(R.drawable.ic_check)
                                    }
                                    stock?.let { it1 -> viewModel.upsertStockData(it1) }
                                }

                            }
                        }else{
                            Timber.e("Error: ${it.message}")
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
    }
}