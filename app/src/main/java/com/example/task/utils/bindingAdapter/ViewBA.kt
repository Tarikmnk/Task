package com.example.task.utils.bindingAdapter

import android.view.View
import androidx.databinding.BindingAdapter

/**
 * Created by Tarik MANKAOGLU on 30.04.2022.
 */
@BindingAdapter("showIf")
fun showIf(view: View, showIf: Boolean?)
{
    view.visibility = if (showIf == true) View.VISIBLE else View.GONE
}