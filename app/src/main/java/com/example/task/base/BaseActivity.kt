package com.example.task.base

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.task.R
import com.example.task.utils.createWarningDialog

/**
 * Created by Tarik MANKAOGLU on 29.04.2022.
 */
abstract class BaseActivity<VM : BaseViewModel, DB : ViewDataBinding> : AppCompatActivity() {

    private var dialogProgress: Dialog? = null

    //Abstract
    abstract val viewModel: VM
    abstract val layoutRes: Int
    abstract fun onCreated()
    abstract fun observers()

    lateinit var binding: DB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutRes)

        supportActionBar?.hide()

        binding = DataBindingUtil.setContentView(this, layoutRes)
        binding.lifecycleOwner = this
        observe()

        onCreated()
        observers()
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