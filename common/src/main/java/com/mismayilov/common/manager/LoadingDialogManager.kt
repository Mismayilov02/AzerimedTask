package com.mismayilov.common.manager

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

object LoadingDialogManager {
    private lateinit var dialog: Dialog
    fun showLoadingDialog(context: Context) {
        if (!::dialog.isInitialized) {
            dialog = Dialog(context).apply {
                val inflater = LayoutInflater.from(context)
                val view: View = inflater.inflate(com.mismayilov.uikit.R.layout.loading_dialog, null)
                setCancelable(false)
                setContentView(view)
                window?.apply {
                    setBackgroundDrawableResource(android.R.color.transparent)
                    setLayout(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                }
            }
        }
        if (!dialog.isShowing) {
            dialog.show()
        }
    }

    fun hideLoadingDialog() {
        if (::dialog.isInitialized && dialog.isShowing) {
            dialog.dismiss()
        }
    }
}