package com.ssjit.papertrading.other

import android.content.Context
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

object Extensions {

    fun View.showSnack(message:String){
        Snackbar.make(this,message, Snackbar.LENGTH_SHORT).show()
    }

    fun Context.showToast(message: String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}