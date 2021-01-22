package com.ssjit.papertrading.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import com.ssjit.papertrading.ui.fragments.OrdersExecutedFragment
import com.ssjit.papertrading.ui.fragments.OrdersPendingFragment

class OrdersPagerAdapter(fm:FragmentManager): FragmentPagerAdapter(fm) {

    override fun getCount(): Int = 2

    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> OrdersPendingFragment()
            1 -> OrdersExecutedFragment()
            else -> OrdersPendingFragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0 -> "Pending"
            1-> "Executed"
            else -> "Pending"
        }
    }
}