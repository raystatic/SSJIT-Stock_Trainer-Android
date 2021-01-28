package com.ssjit.papertrading.other

import android.content.res.Resources
import java.text.NumberFormat
import java.util.*

object Utility {

    fun getInDP(px: Int): Int {
        return  ((px * Resources.getSystem().displayMetrics.density).toInt())
    }

    fun formatAmount(amount:String):String{
        val s = amount.toFloat()
        return NumberFormat.getNumberInstance().format(s)
    }

}