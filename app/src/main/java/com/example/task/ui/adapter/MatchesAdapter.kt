package com.example.task.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.task.R
import com.example.task.base.BaseRecyclerAdapter
import com.example.task.databinding.RowMatchBinding
import com.example.task.ui.main.model.MainUiModel
import com.example.task.utils.createBetDialog

/**
 * Created by Tarik MANKAOGLU on 30.04.2022.
 */
class MatchesAdapter : BaseRecyclerAdapter<MainUiModel, RowMatchBinding>(diffCallback()) {

    companion object {
        fun diffCallback() = object :
            DiffUtil.ItemCallback<MainUiModel>() {
            override fun areItemsTheSame(
                oldItem: MainUiModel,
                newItem: MainUiModel,
            ) = oldItem == newItem

            override fun areContentsTheSame(
                oldItem: MainUiModel,
                newItem: MainUiModel,
            ) = oldItem == newItem
        }
    }

    override val layoutRes: Int
        get() = R.layout.row_match

    override fun bindView(
        binding: RowMatchBinding,
        currentData: MainUiModel,
        position: Int
    ) {
        binding.model = currentData

        binding.btnBet.setOnClickListener {
            binding.root.context.createBetDialog(
                team1 = currentData.match.team1,
                team2 = currentData.match.team2,
                prediction1 = currentData.prediction1 ?: 0,
                prediction2 = currentData.prediction2 ?: 0,
                limitLower = 0
            )
            { prediction1, prediction2 ->
                currentData.prediction1 = prediction1
                currentData.prediction2 = prediction2
                this.notifyItemChanged(position)
            }
        }
    }
}