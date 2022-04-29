package com.example.task.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Tarik MANKAOGLU on 29.04.2022.
 */
abstract class BaseRecyclerAdapter<T, DB : ViewDataBinding>(diffUtil: DiffUtil.ItemCallback<T>) :
    ListAdapter<T, BaseRecyclerAdapter.ViewHolder>(diffUtil) {

    abstract val layoutRes: Int
    abstract fun bindView(binding: DB, currentData: T, position: Int)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: DB =
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), layoutRes, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        try {
            val currentData: T = getItem(position)
            bindView(holder.currentBinding as DB, currentData, position)
        } catch (e: Exception) {
            println()
        }
    }

    class ViewHolder(binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
        val currentBinding = binding
    }
}