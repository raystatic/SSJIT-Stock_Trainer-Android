package com.ssjit.papertrading.other

import android.content.res.Resources

object Utility {

    fun getInDP(px: Int): Int {
        return  ((px * Resources.getSystem().displayMetrics.density).toInt())
    }

}