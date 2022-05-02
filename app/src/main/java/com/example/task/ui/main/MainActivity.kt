package com.example.task.ui.main

import androidx.activity.viewModels
import com.example.task.R
import com.example.task.base.BaseActivity
import com.example.task.databinding.ActivityMainBinding
import com.example.task.ui.main.model.MainUiModel
import com.example.task.ui.result.ResultActivity
import com.example.task.utils.extension.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {

    override val viewModel: MainViewModel by viewModels()

    override val layoutRes: Int
        get() = R.layout.activity_main

    override fun onCreated() {
        setToolbar()
        setAdapter()
    }

    override fun observers() {
        viewModel.matchLiveData.observe(this) { res ->
            viewModel.adapter.submitList(res.matches.map {
                MainUiModel(match = it)
            })
        }
    }

    private fun setToolbar() {
        binding.tbMain.setOnMenuItemClickListener {

            when (it.itemId) {
                R.id.btnResult -> {
                    openResult()
                }
            }
            return@setOnMenuItemClickListener true
        }
    }

    private fun setAdapter() {
        binding.rvMatches.adapter = viewModel.adapter
    }

    private fun openResult() {
        val list = viewModel.adapter.currentList.toList()
        if (!list.any { it.prediction1 != null && it.prediction2 != null }) {
            showToast(getString(R.string.warning_at_least_one_bet))
            return
        }
        startActivity(ResultActivity.create(this, viewModel.adapter.currentList.toList()))
    }
}