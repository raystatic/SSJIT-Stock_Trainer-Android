package com.ssjit.papertrading.ui.fragments

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ssjit.papertrading.R
import com.ssjit.papertrading.data.models.stockdetail.StockData
import com.ssjit.papertrading.databinding.BuySellDialogBinding
import com.ssjit.papertrading.other.Constants
import com.ssjit.papertrading.other.Utility
import timber.log.Timber

class BuySellDialogFragment: BottomSheetDialogFragment() {

    private var _binding: BuySellDialogBinding?=null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = BuySellDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()


        binding.swipeBtn.setOnStateChangeListener {
            val lp = LinearLayout.LayoutParams(Utility.getInDP(90),Utility.getInDP(90))
            lp.gravity = Gravity.CENTER_HORIZONTAL
            binding.swipeBtn.layoutParams = lp
            binding.swipeBtn.setButtonBackground(ContextCompat.getDrawable(requireContext(),R.drawable.transaparent))
            if (type == Constants.BUY){
                binding.swipeBtn.setSlidingButtonBackground(ContextCompat.getDrawable(requireContext(),R.drawable.ic_checked_green))
            }else{
                binding.swipeBtn.setSlidingButtonBackground(ContextCompat.getDrawable(requireContext(),R.drawable.ic_checked_red))
            }
        }


    }

    private fun initUI() {
        binding.apply {
            if (type == Constants.BUY){
                relTop.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.green))
                imgFlash.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_flash_green))

               // toggleIntraday.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.green))

                swipeBtn.setSlidingButtonBackground(ContextCompat.getDrawable(requireContext(), R.drawable.shape_round_green))
                swipeBtn.setButtonBackground(ContextCompat.getDrawable(requireContext(), R.drawable.shape_button_green))

            }else{
                relTop.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.primary_red))
                imgFlash.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_flash_red))

               // toggleIntraday.drawabl(ContextCompat.getColor(requireContext(), R.color.primary_red))

                swipeBtn.setSlidingButtonBackground(ContextCompat.getDrawable(requireContext(), R.drawable.shape_round_red))
                swipeBtn.setButtonBackground(ContextCompat.getDrawable(requireContext(), R.drawable.shape_button_red))
            }

            toggleIntraday.apply {
                toggle()
                setShadowEffect(false)
            }

            swipeBtn.setEnabledDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.transaparent))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object{
        var stockData:StockData?=null
        var type = Constants.BUY
    }

}