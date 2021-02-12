package com.ssjit.papertrading.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ssjit.papertrading.R
import com.ssjit.papertrading.data.models.orders.Order
import com.ssjit.papertrading.data.models.orders.OrderRequest
import com.ssjit.papertrading.databinding.OrderLayoutBinding
import com.ssjit.papertrading.other.Constants
import com.ssjit.papertrading.other.Extensions.enable
import com.ssjit.papertrading.other.Extensions.showToast
import com.ssjit.papertrading.other.Extensions.toast
import com.ssjit.papertrading.other.Status
import com.ssjit.papertrading.ui.activities.HomeActivity
import com.ssjit.papertrading.ui.viewmodels.StockInfoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.order_layout.*
import timber.log.Timber
import kotlin.math.roundToInt

@AndroidEntryPoint
class OrderFragment: BottomSheetDialogFragment() {

    private var _binding: OrderLayoutBinding?=null
    private val binding get() = _binding!!
    private val viewmodel by activityViewModels<StockInfoViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = OrderLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        subscribeToObservers()

    }

    private fun subscribeToObservers() {

        viewmodel.orderResponse.observe(viewLifecycleOwner){
            when(it.status){
                Status.SUCCESS -> {
                    it.data?.let { res->
                        if (!res.error) {
                            binding.imgSuccess.isVisible = true
                            binding.loadingButton.isVisible = false
                            this.dismiss()
                            val intent = Intent(requireContext(), HomeActivity::class.java)
                            intent.putExtra("TRANSACTION",true)
                            requireContext().startActivity(intent)
                        } else {
                            Timber.d("Error creating order: ${res.message}")
                            requireContext().showToast(res.message
                                    ?: Constants.SOMETHING_WENT_WRONG)
                            binding.imgSuccess.isVisible = false
                            binding.loadingButton.isVisible = true
                        }
                    }
                }
                Status.LOADING -> {
                    binding.loadingButton.showLoading()
                }
                Status.ERROR -> {
                    binding.loadingButton.showLoading()
                    requireContext().toast(it.message.toString())
                }

            }
        }

        viewmodel.currentProduct.observe(viewLifecycleOwner){product ->
            viewmodel.currentOrder.observe(viewLifecycleOwner){order->

                binding.btnCO.isVisible = !(order == Constants.SL || order == Constants.SLM)

                binding.linTrigger.isVisible = (order == Constants.SL || order == Constants.SLM)


                viewmodel.currentVariety.observe(viewLifecycleOwner){variety->

                    binding.apply {
                        if (product == Constants.CNC || product ==  Constants.MIS){
                            if (order == Constants.MARKET){
                                if (variety == Constants.AMO || variety ==  Constants.RGLR){
                                    linValidity.isVisible =true
                                    linStoplossTrigger.isVisible = false
                                    linBO.isVisible = false

                                    linSetToggle.isVisible = false

                                    btnSL.isVisible = true
                                    btnSLM.isVisible = true


                                }else if (variety ==  Constants.BO){
                                    linSetToggle.isVisible = false

                                    linValidity.isVisible =false
                                    linStoplossTrigger.isVisible = false
                                    linBO.isVisible = true

                                    btnSL.isVisible = true
                                    btnSLM.isVisible = true

                                }else if(variety ==  Constants.CO){
                                    linSetToggle.isVisible = false

                                    linValidity.isVisible =false
                                    linStoplossTrigger.isVisible = true
                                    linBO.isVisible = false

                                    btnSL.isVisible = false
                                    btnSLM.isVisible = false

                                }
                            }else if (order == Constants.LIMIT){
                                if (variety == Constants.AMO){
                                    linValidity.isVisible =true
                                    linStoplossTrigger.isVisible = false
                                    linBO.isVisible = false

                                    linSetToggle.isVisible = false

                                    btnSL.isVisible = true
                                    btnSLM.isVisible = true

                                }else if (variety == Constants.RGLR){
                                   linSetToggle.isVisible = true

                                    linValidity.isVisible =true
                                    linStoplossTrigger.isVisible = false
                                    linBO.isVisible = false

                                    btnSL.isVisible = true
                                    btnSLM.isVisible = true

                                    btnCO.isVisible = true


                                }else if (variety == Constants.BO){
                                    linSetToggle.isVisible = false

                                    linValidity.isVisible =false
                                    linStoplossTrigger.isVisible = false
                                    linBO.isVisible = true

                                    btnSL.isVisible = true
                                    btnSLM.isVisible = true

                                    btnCO.isVisible = true

                                }else if (variety ==  Constants.CO){
                                    linSetToggle.isVisible = false

                                    linValidity.isVisible =false
                                    linStoplossTrigger.isVisible = true
                                    linBO.isVisible = false

                                    btnSL.isVisible = false
                                    btnSLM.isVisible = false

                                    btnCO.isVisible = true

                                }
                            }else if (order == Constants.SL){
//                                linTrigger.isVisible = true
                                etPrice.isFocusable = true
                                etPrice.isClickable = true
                                etPrice.setText(price.toString())

                                btnCO.isVisible = false

                                if (variety == Constants.AMO || variety ==  Constants.RGLR){
                                    linValidity.isVisible =true
                                    linStoplossTrigger.isVisible = false
                                    linBO.isVisible = false

                                    linSetToggle.isVisible = false

                                    btnSL.isVisible = true
                                    btnSLM.isVisible = true


                                }else if (variety ==  Constants.BO){
                                    linSetToggle.isVisible = false

                                    linValidity.isVisible =false
                                    linStoplossTrigger.isVisible = false
                                    linBO.isVisible = true

                                    btnSL.isVisible = true
                                    btnSLM.isVisible = true

                                }else if(variety ==  Constants.CO){
                                    linSetToggle.isVisible = false

                                    linValidity.isVisible =false
                                    linStoplossTrigger.isVisible = true
                                    linBO.isVisible = false

                                    btnSL.isVisible = false
                                    btnSLM.isVisible = false

                                }

                            }else if (order == Constants.SLM){
//                                linTrigger.isVisible = true
                                etPrice.isFocusable = false
                                etPrice.isClickable = false
                                etPrice.setText(price.toString())

                                btnCO.isVisible = false

                                if (variety == Constants.AMO || variety ==  Constants.RGLR){
                                    linValidity.isVisible =true
                                    linStoplossTrigger.isVisible = false
                                    linBO.isVisible = false

                                    linSetToggle.isVisible = false

                                    btnSL.isVisible = true
                                    btnSLM.isVisible = true


                                }else if (variety ==  Constants.BO){
                                    linSetToggle.isVisible = false

                                    linValidity.isVisible =false
                                    linStoplossTrigger.isVisible = false
                                    linBO.isVisible = true

                                    btnSL.isVisible = true
                                    btnSLM.isVisible = true

                                }else if(variety ==  Constants.CO){
                                    linSetToggle.isVisible = false

                                    linValidity.isVisible =false
                                    linStoplossTrigger.isVisible = true
                                    linBO.isVisible = false

                                    btnSL.isVisible = false
                                    btnSLM.isVisible = false

                                }

                            }

                        }
                    }

                }
            }
        }

        viewmodel.currentStockData.observe(viewLifecycleOwner, {
            it?.let { stockData ->
                binding.apply {
                    symbol = stockData.symbol
                    tvStockSymbol.text = stockData.symbol
                    viewmodel.user.observe(viewLifecycleOwner, { u ->
                        u?.let { user ->
                            userId = user.id
                            val balance = user.balance
                            val maximumByCapacity = balance.toFloat() / stockData.lastPrice.replace(",", "").replace("-", "").toFloat()
                            val capacity = maximumByCapacity.roundToInt()
                            maxCapacity = capacity
                            price = stockData.lastPrice.replace(",", "").replace("-", "").toFloat()
                            //tvStockBuyQuantity.text = Utility.formatAmount(price.toString())
                            tvStockSymbol.text = symbol
                            etPrice.setText(price.toString())
                            etStockQty.setText("1")
                        }
                    })
                }
            }
        })
    }

    private fun initUI() {

        currentOrder = ""
        currentProduct = ""
        currentVariety = ""

        binding.apply {
            linQty.isVisible = true
            btnCNC.enable(false)
            btnMIS.enable(false)
            btnMarket.enable(false)
            btnLIMIT.enable(false)
            btnSL.enable(false)
            btnSLM.enable(false)
            btnRGLR.enable(false)
            btnBO.enable(false)
            btnCO.enable(false)
            btnAMO.enable(false)
            btnDAY.enable(false)
            btnIOC.enable(false)

            if (OrderFragment.type == Constants.SELL_STOCK){
                relTop.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.primary_red))
                loadingButton.buttonColor = ContextCompat.getColor(requireContext(), R.color.primary_red)
                loadingButton.shadowColor = ContextCompat.getColor(requireContext(), R.color.primary_red)
                loadingButton.text = "SELL"
            }else{
                relTop.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.logo_green))
                loadingButton.buttonColor = ContextCompat.getColor(requireContext(), R.color.logo_green)
                loadingButton.shadowColor = ContextCompat.getColor(requireContext(), R.color.logo_green)
                loadingButton.text = "BUY"
            }

            btnDAY.setOnClickListener {
                btnDAY.enable(true)
                currentVal = Constants.DAY
                btnIOC.enable(false)
            }

            btnIOC.setOnClickListener {
                btnIOC.enable(true)
                currentVal = Constants.IOC
                btnDAY.enable(false)
            }

            btnCNC.setOnClickListener {
                btnCNC.enable(true)
                currentProduct = Constants.CNC
                btnMIS.enable(false)
                viewmodel.setCurrentProduct(currentProduct)
            }

            btnMIS.setOnClickListener {
                btnMIS.enable(true)
                currentProduct = Constants.MIS
                btnCNC.enable(false)
                viewmodel.setCurrentProduct(currentProduct)
            }


            btnMarket.setOnClickListener {

                if (currentProduct.isEmpty()){
                    requireContext().toast("Please select product type")
                    return@setOnClickListener
                }

                btnMarket.enable(true)
                btnLIMIT.enable(false)
                btnSL.enable(false)
                btnSLM.enable(false)
                currentOrder = Constants.MARKET

                viewmodel.setCurrentOrder(currentOrder)

            }

            btnLIMIT.setOnClickListener {
                if (currentProduct.isEmpty()){
                    requireContext().toast("Please select product type")
                    return@setOnClickListener
                }
                btnMarket.enable(false)
                btnLIMIT.enable(true)
                btnSL.enable(false)
                btnSLM.enable(false)
                currentOrder = Constants.LIMIT

                viewmodel.setCurrentOrder(currentOrder)

            }

            btnSL.setOnClickListener {
                if (currentProduct.isEmpty()){
                    requireContext().toast("Please select product type")
                    return@setOnClickListener
                }
                btnMarket.enable(false)
                btnLIMIT.enable(false)
                btnSL.enable(true)
                btnSLM.enable(false)
                currentOrder = Constants.SL



                viewmodel.setCurrentOrder(currentOrder)


            }

            btnSLM.setOnClickListener {
                if (currentProduct.isEmpty()){
                    requireContext().toast("Please select product type")
                    return@setOnClickListener
                }
                btnMarket.enable(false)
                btnLIMIT.enable(false)
                btnSL.enable(false)
                btnSLM.enable(true)
                currentOrder = Constants.SLM

                viewmodel.setCurrentOrder(currentOrder)


            }

            btnRGLR.setOnClickListener {
                if (currentProduct.isEmpty()){
                    requireContext().toast("Please select product type")
                    return@setOnClickListener
                }
                if (currentOrder.isEmpty()){
                    requireContext().toast("Please select order type")
                    return@setOnClickListener
                }
                btnRGLR.enable(true)
                btnBO.enable(false)
                btnCO.enable(false)
                btnAMO.enable(false)
                currentVariety = Constants.RGLR


                viewmodel.setCurrentVariety(currentVariety)

            }

            btnBO.setOnClickListener {
                if (currentProduct.isEmpty()){
                    requireContext().toast("Please select product type")
                    return@setOnClickListener
                }
                if (currentOrder.isEmpty()){
                    requireContext().toast("Please select order type")
                    return@setOnClickListener
                }
                btnRGLR.enable(false)
                btnBO.enable(true)
                btnCO.enable(false)
                btnAMO.enable(false)
                currentVariety = Constants.BO

                viewmodel.setCurrentVariety(currentVariety)


            }

            btnCO.setOnClickListener {
                if (currentProduct.isEmpty()){
                    requireContext().toast("Please select product type")
                    return@setOnClickListener
                }
                if (currentOrder.isEmpty()){
                    requireContext().toast("Please select order type")
                    return@setOnClickListener
                }
                btnRGLR.enable(false)
                btnBO.enable(false)
                btnCO.enable(true)
                btnAMO.enable(false)
                currentVariety = Constants.CO

                viewmodel.setCurrentVariety(currentVariety)


            }

            btnAMO.setOnClickListener {
                if (currentProduct.isEmpty()){
                    requireContext().toast("Please select product type")
                    return@setOnClickListener
                }
                if (currentOrder.isEmpty()){
                    requireContext().toast("Please select order type")
                    return@setOnClickListener
                }
                btnRGLR.enable(false)
                btnBO.enable(false)
                btnCO.enable(false)
                btnAMO.enable(true)
                currentVariety = Constants.AMO

                viewmodel.setCurrentVariety(currentVariety)

            }


            loadingButton.setOnClickListener {
                val quantity = etStockQty.text.toString()
                if (quantity.isEmpty()){
                    requireContext().toast("Quantity cannot be empty")
                    return@setOnClickListener
                }

                val amount = etPrice.text.toString()
                if (amount.isEmpty()){
                    requireContext().toast("Price cannot be empty")
                    return@setOnClickListener
                }

                if (currentProduct.isEmpty()){
                    requireContext().toast("Please select a product type")
                    return@setOnClickListener
                }

                if (currentOrder.isEmpty()){
                    requireContext().toast("Please select a order type")
                    return@setOnClickListener
                }

                if (currentVariety.isEmpty()){
                    requireContext().toast("Please select a variety type")
                    return@setOnClickListener
                }

                if (currentVariety == Constants.RGLR || currentVariety == Constants.AMO){
                    if (currentVal.isEmpty()){
                        requireContext().toast("Please select a validity")
                        return@setOnClickListener
                    }
                }

                var trigger = etTrigger.text.toString()

                if (currentOrder == Constants.SL || currentOrder == Constants.SLM){
                    if (trigger.isEmpty()){
                        requireContext().toast("Please enter trigger")
                        return@setOnClickListener
                    }
                }else{
                    if (trigger.isEmpty()){
                       trigger = "0"
                    }
                }

                var stoploss = etStopless.text.toString()
                var target = etTarget.text.toString()
                var trailingStoploss = etTarget.text.toString()

                if (currentVariety == Constants.BO){
                    if (stoploss.isEmpty()){
                        requireContext().toast("Please enter stoploss")
                        return@setOnClickListener
                    }

                    if (target.isEmpty()){
                        requireContext().toast("Please enter target")
                        return@setOnClickListener
                    }

                    if (trailingStoploss.isEmpty()){
                        requireContext().toast("Please enter Trailing Stoploss")
                        return@setOnClickListener
                    }
                }else {
                    if (stoploss.isEmpty()){
                        stoploss = "0"
                    }

                    if (target.isEmpty()){
                        target = "0"
                    }

                    if (trailingStoploss.isEmpty()){
                        trailingStoploss = "0"
                    }
                }

                var stoplossTrigger = etStoplossTrigger.text.toString()

                if (currentVariety == Constants.CO){
                    if (stoplossTrigger.isEmpty()){
                        requireContext().toast("Please enter Stoploss trigger")
                        return@setOnClickListener
                    }
                }else{
                    stoplossTrigger = "0"
                }

                val disc = etDiscQty.text.toString()

                if (disc.isEmpty()){
                    requireContext().toast("Please enter Disclosed Quantity")
                    return@setOnClickListener
                }

                var orderType = "BUY"
                orderType = if (type == Constants.SELL_STOCK){
                    "SELL"
                }else{
                    "BUY"
                }

                val orderRequest = OrderRequest(
                        eachPrice = price.toString(),
                        noOfShares = quantity,
                        orderType = currentOrder,
                        price = amount,
                        product = currentProduct,
                        stopLoss = stoploss,
                        stoplossTrigger = stoplossTrigger,
                        symbol = symbol,
                        target = target,
                        trailingStoploss = trailingStoploss,
                        triggeredPrice = trigger,
                        type = orderType,
                        userId = userId,
                        validity = currentVal,
                        variety = currentVariety,

                )

                viewmodel.order(orderRequest)


            }

            toSellOrder?.let {
                userId = it.userId
                price = it.eachPrice.replace(",", "").replace("-", "").toFloat()
                //tvStockBuyQuantity.text = Utility.formatAmount(price.toString())
                tvStockSymbol.text = it.symbol
                etPrice.setText(price.toString())
                etStockQty.setText("1")
            }

        }
    }


    companion object{
        var userId = ""
        var amount:Float?=null
        var symbol = ""
        var price:Float?=null
        var maxCapacity:Int?=null
        var type = Constants.BUY_STOCK
        var toSellOrder: Order?=null

        private var currentOrder = ""
        private var currentProduct = ""
        private var currentVariety = ""
        private var currentVal = ""
    }

}

