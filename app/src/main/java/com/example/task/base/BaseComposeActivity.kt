package com.example.task.base

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.task.R
import com.example.task.utils.createWarningDialog

/**
 * Created by Tarik MANKAOGLU on 29.04.2022.
 */
abstract class BaseComposeActivity<VM : BaseViewModel> : ComponentActivity() {

    private var dialogProgress: Dialog? = null

    //Abstract
    abstract val viewModel: VM
    abstract val layoutRes: Int
    abstract fun onCreated()
    //abstract fun observers()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onCreated()
        //observers()
        /*setContentView(layoutRes)

        binding = DataBindingUtil.setContentView(this, layoutRes)
        binding.lifecycleOwner = this




         */
        observe()
    }

    private fun observe() {
        viewModel.errorLiveData.observe(this, { error ->
            createWarningDialog(
                {
                    if (error.isFinishActivity)
                        finish()
                }, error.message
            )
        })
        viewModel.eventShowOrHideProgress.observe(
            this,
            { showOrHideProgress(it) })
    }

    private fun showOrHideProgress(
        isShow: Boolean
    ) {
        if (dialogProgress == null) {
            dialogProgress = Dialog(this)
        }

        dialogProgress?.setContentView(R.layout.item_loading)
        dialogProgress?.setCanceledOnTouchOutside(false)
        dialogProgress?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        if (isShow) {
            dialogProgress?.show()
        } else {
            dialogProgress?.dismiss()
        }
    }
}