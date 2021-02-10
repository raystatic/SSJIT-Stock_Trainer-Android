package com.ssjit.papertrading.other

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import com.ssjit.papertrading.R
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

    fun openUrl(context: Context, url: String){
        val intentBuilder: CustomTabsIntent.Builder = CustomTabsIntent.Builder()

        intentBuilder.setToolbarColor(ContextCompat.getColor(context,
            R.color.colorPrimary
        ))
        intentBuilder.setSecondaryToolbarColor(
            ContextCompat.getColor(
                context,
                R.color.colorPrimary
            )
        )

        val customTabsIntent: CustomTabsIntent = intentBuilder.build()
        customTabsIntent.intent.setPackage("com.android.chrome")
        customTabsIntent.intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        customTabsIntent.launchUrl(context, Uri.parse(url))
    }

}