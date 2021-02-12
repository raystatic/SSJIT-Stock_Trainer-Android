package com.ssjit.papertrading.other

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.ssjit.papertrading.R
import com.ssjit.papertrading.ui.fragments.OrderFragment

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


    fun MaterialButton.enable(b:Boolean){
        if (b){
            if (OrderFragment.type == Constants.BUY_STOCK)
                this.setBackgroundColor(ContextCompat.getColor(context,R.color.logo_green))
            else if (OrderFragment.type == Constants.SELL_STOCK)
                this.setBackgroundColor(ContextCompat.getColor(context,R.color.primary_red))
        }else{
            this.setBackgroundColor(ContextCompat.getColor(context,R.color.gray))
        }
    }

    fun Context.toast(message:String){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show()
    }

}