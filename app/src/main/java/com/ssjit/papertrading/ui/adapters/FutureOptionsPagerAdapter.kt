package com.ssjit.papertrading.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.ssjit.papertrading.ui.fragments.FuturesFragment
import com.ssjit.papertrading.ui.fragments.OptionsFragment

class FutureOptionsPagerAdapter(fm:FragmentManager): FragmentPagerAdapter(fm) {

    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> FuturesFragment()
            1 -> OptionsFragment()
            else -> FuturesFragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0 -> "Futures"
            1-> "Options"
            else -> "Futures"
        }
    }
}