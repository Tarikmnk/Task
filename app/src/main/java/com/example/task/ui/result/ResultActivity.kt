package com.example.task.ui.result

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import com.example.task.R
import com.example.task.base.BaseActivity
import com.example.task.databinding.ActivityResultBinding
import com.example.task.ui.main.model.MainUiModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResultActivity : BaseActivity<ResultViewModel, ActivityResultBinding>() {

    companion object {
        const val MODEL = "MODEL"
        fun create(context: Context, model: List<MainUiModel>): Intent {
            return Intent(context, ResultActivity::class.java).putExtra(MODEL, ArrayList(model))
        }
    }

    override val viewModel: ResultViewModel by viewModels()

    override val layoutRes: Int
        get() = R.layout.activity_result

    override fun onCreated() {
        setToolbar()
        setAdapter()
    }

    override fun observers() {
        viewModel.resultLiveData.observe(this) { res ->
            viewModel.adapter.submitList(res)
        }
    }

    private fun setToolbar() {
        binding.tbResult.setOnMenuItemClickListener {

            when (it.itemId) {
                R.id.btnResult -> {

                }
            }
            return@setOnMenuItemClickListener true
        }
    }

    private fun setAdapter() {
        binding.rvResult.adapter = viewModel.adapter
    }
}