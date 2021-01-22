package com.ssjit.papertrading.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import com.ssjit.papertrading.ui.fragments.HoldingFragment
import com.ssjit.papertrading.ui.fragments.OrdersExecutedFragment
import com.ssjit.papertrading.ui.fragments.OrdersPendingFragment
import com.ssjit.papertrading.ui.fragments.PositionFragment

class PortfolioPagerAdapter(fm:FragmentManager): FragmentPagerAdapter(fm) {

    override fun getCount(): Int = 2

    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> HoldingFragment()
            1 -> PositionFragment()
            else -> HoldingFragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0 -> "Holding"
            1-> "Position"
            else -> "Holding"
        }
    }
}