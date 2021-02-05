package com.ssjit.papertrading.other

import android.content.res.Resources
import timber.log.Timber
import java.text.NumberFormat
import java.util.*

object Utility {

    fun getInDP(px: Int): Int {
        return  ((px * Resources.getSystem().displayMetrics.density).toInt())
    }

    fun formatAmount(amount:String):String{
        val s = amount.replace(",","").toFloat()
        return "${Constants.RUPEE_SYMBOL} ${NumberFormat.getNumberInstance().format(s)}"
    }

    fun format(amount: String):String{
        return "${Constants.RUPEE_SYMBOL} $amount"
    }

    fun evaluatePrice(price1:String, price2:String, price3:String, price4:String, price5:String):String{
        var price = 0f

        price1.replace(",","").replace("-","0").toFloat().also {
            if (!it.isNaN()) price += it
        }
        price2.replace(",","").replace("-","0").toFloat().also {
            if (!it.isNaN()) price += it
        }
        price3.replace(",","").replace("-","0").toFloat().also {
            if (!it.isNaN()) price += it
        }
        price4.replace(",","").replace("-","0").toFloat().also {
            if (!it.isNaN()) price += it
        }
        price5.replace(",","").replace("-","0").toFloat().also {
            if (!it.isNaN()) price += it
        }

        return price.toString()

    }

}