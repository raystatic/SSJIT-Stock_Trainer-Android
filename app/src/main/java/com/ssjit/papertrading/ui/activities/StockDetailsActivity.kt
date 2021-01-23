package com.ssjit.papertrading.ui.activities

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import app.futured.donut.DonutSection
import com.ssjit.papertrading.R
import com.ssjit.papertrading.databinding.ActivityStockDetailsBinding
import com.ssjit.papertrading.other.Constants
import com.ssjit.papertrading.other.Status
import com.ssjit.papertrading.other.ViewExtension.showSnack
import com.ssjit.papertrading.ui.viewmodels.StockInfoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_stock_details.*
import timber.log.Timber
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class StockDetailsActivity : AppCompatActivity() {

    private lateinit var binding:ActivityStockDetailsBinding

    private var stockSymbol = ""

    private val viewModel by viewModels<StockInfoViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStockDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = ContextCompat.getColor(this,R.color.green)

        stockSymbol = intent.getStringExtra(Constants.STOCK_SYMBOL) ?: ""

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
        viewModel.stockInfoResponse.observe(this, {
            when(it.status){
                Status.SUCCESS -> {
                    it.data?.let { res->
                        if (!res.error){
                            val stock = res.data?.data?.get(0)

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

                                imgAddToWatchlist.setOnClickListener {
                                    stock?.addedToWatchList=1
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