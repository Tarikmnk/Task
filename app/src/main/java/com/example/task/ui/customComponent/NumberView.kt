package com.example.task.ui.customComponent

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.task.databinding.ViewNumberBinding

/**
 * Created by Tarik MANKAOGLU on 30.04.2022.
 */
class NumberView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    val binding = ViewNumberBinding.inflate(LayoutInflater.from(context), this, true)

    private var limitUpper: Int? = null
    private var limitLower: Int? = null
    private var number = 0
        set(value) {
            field = value
            if (field > (limitUpper ?: (field + 1))) {
                field--
            }
            if (field < (limitLower ?: (field - 1))) {
                field++
            }
            binding.tvNumber.text = field.toString()
        }

    init {
        binding.btnPlus.setOnClickListener {
            increase()
        }

        binding.btnMinus.setOnClickListener {
            decrease()
        }
    }

    fun setLowerLimit(limitLower: Int?) {
        this.limitLower = limitLower
    }

    fun setUpperLimit(limitUpper: Int?) {
        this.limitUpper = limitUpper
    }

    fun increase() {
        number++
    }

    fun decrease() {
        number--
    }

    fun setRate(number: Int) {
        this.number = number
    }

    fun getRate() = number
}