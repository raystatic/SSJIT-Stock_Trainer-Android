package com.ssjit.papertrading.other

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import java.lang.StringBuilder

class ShowAlertDialog(
    private val context: Context,
    private val title:String,
    private val message:String,
    private val positive:String?,
    private val negative:String?,
    private val onPositiveButtonClicked:() -> Unit,
    private val onNegativeButtonClicked:() -> Unit
) {

    init {
        initDialog()
    }

    private fun initDialog() {
        val alertDialogBuilder = AlertDialog.Builder(context)
        alertDialogBuilder.apply {
            setTitle(title)
            setMessage(message)
            setCancelable(false)
            positive?.let {
                setPositiveButton(it) { dialog, which ->
                    dialog.cancel()
                    onPositiveButtonClicked()
                }
            }

            negative?.let {
                setNegativeButton(it){dialog, _ ->
                    dialog.cancel()
                    onNegativeButtonClicked()
                }
            }
        }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

}