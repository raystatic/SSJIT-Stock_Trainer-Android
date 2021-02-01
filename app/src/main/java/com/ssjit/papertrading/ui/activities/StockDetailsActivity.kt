package com.ssjit.papertrading.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import com.ssjit.papertrading.R
import com.ssjit.papertrading.data.models.stockdetail.StockData
import com.ssjit.papertrading.databinding.ActivityStockDetailsBinding
import com.ssjit.papertrading.other.Constants
import com.ssjit.papertrading.other.Status
import com.ssjit.papertrading.other.Utility
import com.ssjit.papertrading.other.ViewExtension.showSnack
import com.ssjit.papertrading.ui.adapters.StockPagerAdapter
import com.ssjit.papertrading.ui.viewmodels.StockInfoViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class StockDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStockDetailsBinding
    private var stockSymbol = ""
    private lateinit var pagerAdapter: StockPagerAdapter

    private val viewmodel by viewModels<StockInfoViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStockDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = ContextCompat.getColor(this, R.color.primaryblue)

        stockSymbol = intent.getStringExtra(Constants.STOCK_SYMBOL) ?: ""

        if (stockSymbol.isEmpty()){
            finish()
        }

        binding.imgBack.setOnClickListener {
            finish()
        }

        binding.stockTabLayout.setupWithViewPager(binding.stockPager)
        pagerAdapter = StockPagerAdapter(supportFragmentManager)
        binding.stockPager.adapter = pagerAdapter

        viewmodel.getStockInfo(stockSymbol)

        subscribeToObservers()

        binding.imgWatchlist.setOnClickListener {
            selectedStock?.let {
                if (it.addedToWatchList == 0)
                    it.addedToWatchList = 1
                else
                    it.addedToWatchList = 0
                viewmodel.upsertStockData(it)
            }
        }

    }

    private fun subscribeToObservers() {

        viewmodel.watchList.observe(this,{
            it.let { watchlist->
                viewmodel.currentStockData.observe(this,{current->
                    current?.let { currentStock->
                        selectedStock = currentStock
                        if (watchlist.filter { it.symbol == currentStock.symbol }.isNullOrEmpty()){
                            binding.imgWatchlist.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_add_white))
                        }else{
                            binding.imgWatchlist.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_check))
                        }
                    }
                })
            }
        })

        viewmodel.stockInfoResponse.observe(this, {
            when(it.status){
                Status.SUCCESS ->{
                    it.data?.let { res->
                        if (!res.error){
                            res.data?.let { quote ->
                                quote.data?.get(0)?.let { stock->
                                    viewmodel.setCurrentStock(stock)
                                    binding.apply {
                                        tvSymbol.text = stock.symbol
                                        tvPrice.text = Utility.evaluatePrice(stock.buyPrice1, stock.buyPrice2, stock.buyPrice3, stock.buyPrice4, stock.buyPrice5)
                                    }
                                }
                            }
                        }else{
                            Timber.d("Error in fetching stock details: ${res.message}")
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

    companion object{
        var selectedStock:StockData?=null
    }

}