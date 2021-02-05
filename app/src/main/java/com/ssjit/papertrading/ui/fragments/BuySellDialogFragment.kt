package com.ssjit.papertrading.ui.fragments

import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ssjit.papertrading.R
import com.ssjit.papertrading.data.models.stockdetail.StockData
import com.ssjit.papertrading.data.models.transaction.CreateOrderRequest
import com.ssjit.papertrading.data.models.transaction.CreateOrderResponse
import com.ssjit.papertrading.data.models.transaction.Order
import com.ssjit.papertrading.databinding.BuySellDialogBinding
import com.ssjit.papertrading.other.Constants
import com.ssjit.papertrading.other.Extensions.showToast
import com.ssjit.papertrading.other.Resource
import com.ssjit.papertrading.other.Status
import com.ssjit.papertrading.other.Utility
import com.ssjit.papertrading.ui.activities.HomeActivity
import com.ssjit.papertrading.ui.viewmodels.StockInfoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.buy_sell_dialog.*
import timber.log.Timber
import kotlin.math.roundToInt

@AndroidEntryPoint
class BuySellDialogFragment: BottomSheetDialogFragment() {

    private var _binding: BuySellDialogBinding?=null
    private val binding get() = _binding!!
    private val viewmodel by activityViewModels<StockInfoViewModel>()
    private lateinit var createOrderObserver:Observer<Resource<CreateOrderResponse>>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = BuySellDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()

        binding.etStockQty.doAfterTextChanged {
            val query = it.toString()
            price?.let { p->
                val quantity = if (query.isEmpty()) 0 else query.toInt()
                amount = quantity * p
                binding.tvPrice.text = Utility.formatAmount(amount = amount.toString())
            }

            toSellOrder?.let { s->
                val quantity = if (query.isEmpty()) 0 else query.toInt()
                amount = s.currentPrice?.let {
                    quantity * it
                }
                binding.tvPrice.text = Utility.formatAmount(amount = amount.toString())
            }

        }

        binding.loadingButton.setOnClickListener {
            val qty = binding.etStockQty.text.toString()
            if (qty.isEmpty()){
                requireContext().showToast("Stock quantity cannot be empty")
                return@setOnClickListener
            }

            maxCapacity?.let { max->
                if (qty.toInt() > max){
                    requireContext().showToast("Stock limit exceeded")
                    return@setOnClickListener
                }

                if (symbol.isNotEmpty() && price!=null && amount!=null && userId.isNotEmpty()){
                    var orderType = Constants.BUY
                    if (type == Constants.SELL_STOCK){
                        orderType = Constants.SELL
                    }
                    val createOrderRequest = CreateOrderRequest(
                            symbol = symbol,
                            noOfShares = qty,
                            orderCreatedAt = "${System.currentTimeMillis()}",
                            userId = userId,
                            orderAmount = amount.toString(),
                            intraday = toggleIntraday.isChecked,
                            type = orderType
                    )

                    viewmodel.createOrderRequest(createOrderRequest)

                }
            }

            toSellOrder?.let { order->
                if (qty.toInt() > order.no_shares.toInt()){
                    requireContext().showToast("Stock limit exceeded")
                    return@setOnClickListener
                }

                val createOrderRequest = CreateOrderRequest(
                        symbol = order.symbol,
                        noOfShares = qty,
                        orderCreatedAt = "${System.currentTimeMillis()}",
                        userId = order.user_id,
                        orderAmount = amount.toString(),
                        intraday = false,
                        type = Constants.SELL
                )

                viewmodel.createOrderRequest(createOrderRequest)

            }

        }


        subscribeToObservers()

        Timber.d("dialog: onviewcreated")
    }

    private fun subscribeToObservers() {

        createOrderObserver = Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let { res ->
                        Timber.d("dialog order: ${res}")
                        if (!res.error) {
                            binding.imgSuccess.isVisible = true
                            binding.loadingButton.isVisible = false
                            this.dismiss()
                            val intent = Intent(requireContext(),HomeActivity::class.java)
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
                    binding.loadingButton.hideLoading()
                    if (type == Constants.BUY_STOCK) {
                        binding.loadingButton.text = "BUY"
                    } else {
                        binding.loadingButton.text = "SELL"
                    }

                }
                Status.LOADING -> {
                    binding.loadingButton.showLoading()
                    binding.imgSuccess.isVisible = false
                    binding.loadingButton.isVisible = true

                }
                Status.ERROR -> {
                    Timber.d("Error creating order: ${it.message}")
                    requireContext().showToast(Constants.SOMETHING_WENT_WRONG)
                    binding.loadingButton.hideLoading()
                    binding.imgSuccess.isVisible = false
                    binding.loadingButton.isVisible = true
                    if (type == Constants.BUY_STOCK) {
                        binding.loadingButton.text = "BUY"
                    } else {
                        binding.loadingButton.text = "SELL"
                    }
                }
            }
        }

        viewmodel.createOrderResponse.observe(viewLifecycleOwner,createOrderObserver)

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
                            tvStockBuyQuantity.text = "Maximum Buy Capacity $capacity"
                            price = stockData.lastPrice.replace(",", "").replace("-", "").toFloat()
                        }
                    })
                }
            }
        })
    }

    private fun initUI() {
        binding.apply {

            binding.loadingButton.isVisible = true
            binding.imgSuccess.isVisible = false

            if (type == Constants.BUY_STOCK){
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

                binding.toggleIntraday.isVisible = false
                binding.linIntraday.isVisible = false

                toSellOrder?.let { order->
                    tvStockSymbol.text = order.symbol
                    tvStockBuyQuantity.text = "Maximum sell quantity ${order.no_shares}"

                    binding.loadingButton.setOnClickListener {
                        val qty = binding.etStockQty.text.toString()
                        if (qty.isEmpty()){
                            requireContext().showToast("Stock quantity cannot be empty")
                            return@setOnClickListener
                        }

                    }

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

//    override fun onResume() {
//        super.onResume()
//        dialog?.setOnKeyListener { dialog, keyCode, event ->
//            if (keyCode == KeyEvent.KEYCODE_BACK){
//                onCancel(dialog)
//            }
//            return@setOnKeyListener false
//        }
//    }

    override fun onDestroyView() {
        this.dismiss()
        super.onDestroyView()
        _binding = null
        Timber.d("dialog: ondestroy")
    }

    companion object{
        var userId = ""
        var amount:Float?=null
        var symbol = ""
        var price:Float?=null
        var maxCapacity:Int?=null
        var type = Constants.BUY_STOCK
        var toSellOrder:Order?=null
    }

}