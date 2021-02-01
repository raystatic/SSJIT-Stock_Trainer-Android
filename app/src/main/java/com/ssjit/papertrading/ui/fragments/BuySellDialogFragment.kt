package com.ssjit.papertrading.ui.fragments

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ssjit.papertrading.R
import com.ssjit.papertrading.data.models.stockdetail.StockData
import com.ssjit.papertrading.databinding.BuySellDialogBinding
import com.ssjit.papertrading.other.Constants
import com.ssjit.papertrading.other.Extensions.showToast
import com.ssjit.papertrading.other.Utility
import com.ssjit.papertrading.ui.viewmodels.StockInfoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.max
import kotlin.math.roundToInt

@AndroidEntryPoint
class BuySellDialogFragment: BottomSheetDialogFragment() {

    private var _binding: BuySellDialogBinding?=null
    private val binding get() = _binding!!
    private val viewmodel by activityViewModels<StockInfoViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = BuySellDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()

        binding.loadingButton.setOnClickListener {
            val qty = binding.etStockQty.text.toString()
            if (qty.isEmpty()){
                requireContext().showToast("Stock quantity cannot be empty")
                return@setOnClickListener
            }

            maxCapacity?.let { max->
                if (qty.toInt() > max){
                    requireContext().showToast("Stock limit exceeded")
                }
            } ?: kotlin.run {
                return@setOnClickListener
            }
        }

        subscribeToObservers()


    }

    private fun subscribeToObservers() {
        viewmodel.currentStockData.observe(viewLifecycleOwner,{
            it?.let {stockData ->
                binding.apply {
                    tvStockSymbol.text = stockData.symbol
                    viewmodel.user.observe(viewLifecycleOwner,{u->
                        u?.let { user ->
                            val balance = user.balance
                            val maximumByCapacity = balance.toFloat() / Utility.evaluatePrice(stockData.buyPrice1,stockData.buyPrice2, stockData.buyPrice3, stockData.buyPrice4, stockData.buyPrice5).toFloat()
                            val capacity = maximumByCapacity.roundToInt()
                            maxCapacity = capacity
                            tvStockBuyQuantity.text = "Maximum Buy Capacity $capacity"
                            tvPrice.text = Utility.formatAmount(
                                Utility.evaluatePrice(stockData.buyPrice1,stockData.buyPrice2, stockData.buyPrice3, stockData.buyPrice4, stockData.buyPrice5)
                            )

                        }
                    })
                }
            }
        })
    }

    private fun initUI() {
        binding.apply {
            if (type == Constants.BUY){
                relTop.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.green))
                imgFlash.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_flash_green))

               // toggleIntraday.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.green))

                loadingButton.apply {
                    text = "BUY"
                    buttonColor = ContextCompat.getColor(requireContext(), R.color.green)
                    shadowColor = ContextCompat.getColor(requireContext(), R.color.green)
                }

            }else{
                relTop.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.primary_red))
                imgFlash.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_flash_red))

                loadingButton.apply {
                    text = "SELL"
                    buttonColor = ContextCompat.getColor(requireContext(), R.color.primary_red)
                    shadowColor = ContextCompat.getColor(requireContext(), R.color.primary_red)
                }


                // toggleIntraday.drawabl(ContextCompat.getColor(requireContext(), R.color.primary_red))

            }

            toggleIntraday.apply {
                toggle()
                setShadowEffect(false)
            }

           // swipeBtn.setEnabledDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.transaparent))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object{
        var maxCapacity:Int?=null
        var type = Constants.BUY
    }

}