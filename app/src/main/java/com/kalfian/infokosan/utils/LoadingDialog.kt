package com.kalfian.infokosan.utils

import android.app.Activity
import android.app.AlertDialog
import com.kalfian.infokosan.R


internal class LoadingDialog(private var activity: Activity) {
    private var dialog: AlertDialog? = null

    fun start() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
        val inflater = activity.layoutInflater
        builder.setView(inflater.inflate(R.layout.loader, null))
        builder.setCancelable(false)

        dialog = builder.create()
        dialog?.show()
    }

    fun stop() {
        dialog?.dismiss()
    }
}