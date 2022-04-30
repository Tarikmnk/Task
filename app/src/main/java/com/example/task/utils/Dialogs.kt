package com.example.task.utils

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import com.example.task.databinding.DialogBetBinding
import com.example.task.databinding.DialogWarningBinding

/**
 * Created by Tarik MANKAOGLU on 29.04.2022.
 */

fun Context.createWarningDialog(
    clickBtn: () -> Unit,
    text: String,
    cancelable: Boolean = true
) {
    val binding: DialogWarningBinding =
        DialogWarningBinding.inflate(LayoutInflater.from(this))
    val dialog = AlertDialog.Builder(this).create()
    dialog.setView(binding.root)
    binding.tvWarningDialog.text = text
    dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

    dialog.setCancelable(cancelable)

    binding.btnOkay.setOnClickListener {

        clickBtn.invoke()
        dialog.dismiss()
    }
    dialog.show()
}

fun Context.createBetDialog(
    team1: String,
    team2: String,
    prediction1: Int = 0,
    prediction2: Int = 0,
    limitUpper: Int? = null,
    limitLower: Int? = null,
    returnBet: (prediction1: Int, prediction2: Int) -> Unit,
) {
    val binding: DialogBetBinding =
        DialogBetBinding.inflate(LayoutInflater.from(this))
    val dialog = AlertDialog.Builder(this).create()
    dialog.setView(binding.root)

    dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

    binding.tvPrediction1.setUpperLimit(limitUpper)
    binding.tvPrediction1.setLowerLimit(limitLower)

    binding.tvPrediction2.setUpperLimit(limitUpper)
    binding.tvPrediction2.setLowerLimit(limitLower)

    binding.tvPrediction1.setRate(prediction1)
    binding.tvPrediction2.setRate(prediction2)

    binding.tvTeam1.text = team1
    binding.tvTeam2.text = team2
    binding.btnOkay.setOnClickListener {

        returnBet.invoke(binding.tvPrediction1.getRate(), binding.tvPrediction2.getRate())
        dialog.dismiss()
    }
    dialog.show()
}