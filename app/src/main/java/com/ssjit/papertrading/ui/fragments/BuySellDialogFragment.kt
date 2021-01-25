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
import com.ssjit.papertrading.databinding.BuySellDialogBinding
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

        binding.toggleIntraday.apply {
            toggle()
            setShadowEffect(true)
        }

        binding.swipeBtn.setOnStateChangeListener {
            val lp = LinearLayout.LayoutParams(Utility.getInDP(90),Utility.getInDP(90))
            lp.gravity = Gravity.CENTER_HORIZONTAL
            binding.swipeBtn.layoutParams = lp
            binding.swipeBtn.setButtonBackground(ContextCompat.getDrawable(requireContext(),R.drawable.transaparent))
            binding.swipeBtn.setSlidingButtonBackground(ContextCompat.getDrawable(requireContext(),R.drawable.ic_checked_green))
        }

        binding.swipeBtn.setEnabledDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.transaparent))

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}