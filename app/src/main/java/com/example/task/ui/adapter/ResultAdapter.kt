package com.example.task.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.task.R
import com.example.task.base.BaseRecyclerAdapter
import com.example.task.databinding.RowMatchBinding
import com.example.task.databinding.RowResultBinding
import com.example.task.ui.main.model.MainUiModel
import com.example.task.ui.result.model.ResultUiModel
import com.example.task.utils.createBetDialog

/**
 * Created by Tarik MANKAOGLU on 30.04.2022.
 */
class ResultAdapter : BaseRecyclerAdapter<ResultUiModel, RowResultBinding>(diffCallback()) {

    companion object {
        fun diffCallback() = object :
            DiffUtil.ItemCallback<ResultUiModel>() {
            override fun areItemsTheSame(
                oldItem: ResultUiModel,
                newItem: ResultUiModel,
            ) = oldItem == newItem

            override fun areContentsTheSame(
                oldItem: ResultUiModel,
                newItem: ResultUiModel,
            ) = oldItem == newItem
        }
    }

    override val layoutRes: Int
        get() = R.layout.row_result

    override fun bindView(
        binding: RowResultBinding,
        currentData: ResultUiModel,
        position: Int
    ) {
        binding.model = currentData
    }
}