package com.example.task.utils

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import com.example.task.databinding.DialogWarningBinding

/**
 * Created by Tarik MANKAOGLU on 29.04.2022.
 */

fun Context.createWarningDialog(clickBtn: () -> Unit,
    text: String,
    cancelable:Boolean = true
) {
    val binding: DialogWarningBinding =
        DialogWarningBinding.inflate(LayoutInflater.from(this))
    val dialog = AlertDialog.Builder(this).create()
    dialog.setView(binding.root)
    binding.tvWarningDialog.text = text
    dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)

    dialog.setCancelable(cancelable)

    binding.btnOkay.setOnClickListener {

        clickBtn.invoke()
        dialog.dismiss()
    }
    dialog.show()
}