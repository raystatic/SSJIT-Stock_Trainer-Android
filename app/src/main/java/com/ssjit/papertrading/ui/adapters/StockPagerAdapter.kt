package com.ssjit.papertrading.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.ssjit.papertrading.ui.fragments.OrdersExecutedFragment
import com.ssjit.papertrading.ui.fragments.OrdersPendingFragment
import com.ssjit.papertrading.ui.fragments.StockChartFragment
import com.ssjit.papertrading.ui.fragments.StockOverviewFragment

class StockPagerAdapter(fm:FragmentManager): FragmentPagerAdapter(fm) {

    override fun getCount(): Int = 2

    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> StockOverviewFragment()
            1 -> StockChartFragment()
            else -> StockOverviewFragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0 -> "Overview"
            1-> "Chart"
            else -> "Overview"
        }
    }
}