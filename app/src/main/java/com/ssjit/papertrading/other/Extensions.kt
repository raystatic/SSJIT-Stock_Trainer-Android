package com.ssjit.papertrading.other

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.ssjit.papertrading.R

object Extensions {

    fun View.showSnack(message:String){
        val snackbar = Snackbar.make(this,message,Snackbar.LENGTH_SHORT)
        snackbar.view.setBackgroundColor(ContextCompat.getColor(context, R.color.black))
        snackbar.setTextColor(ContextCompat.getColor(context, R.color.white))
        snackbar.show()
    }

    fun Context.showToast(message: String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}