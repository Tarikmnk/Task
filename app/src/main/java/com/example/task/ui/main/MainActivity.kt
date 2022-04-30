package com.example.task.ui.main

import androidx.activity.viewModels
import com.example.task.R
import com.example.task.base.BaseActivity
import com.example.task.databinding.ActivityMainBinding
import com.example.task.ui.main.model.MainUiModel
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

                }
            }
            return@setOnMenuItemClickListener true
        }
    }

    private fun setAdapter() {
        binding.rvMatches.adapter = viewModel.adapter
    }
}