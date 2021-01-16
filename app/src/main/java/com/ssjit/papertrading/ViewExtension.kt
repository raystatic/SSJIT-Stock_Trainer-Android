package com.ssjit.papertrading

import android.view.View
import com.google.android.material.snackbar.Snackbar

object ViewExtension {

    fun View.showSnack(message:String){
        Snackbar.make(this,message, Snackbar.LENGTH_SHORT).show()
    }

}