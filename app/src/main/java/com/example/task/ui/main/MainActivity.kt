package com.example.task.ui.main

import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.network.model.MatchModel
import com.example.task.R
import com.example.task.base.BaseActivity
import com.example.task.databinding.ActivityMainBinding
import com.example.task.db.entity.PredictionEntity
import com.example.task.ui.main.model.MainUiModel
import com.example.task.ui.result.ResultActivity
import com.example.task.utils.extension.showToast
import com.example.task.utils.sharedHelper.SharedHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {

    override val viewModel: MainViewModel by viewModels()

    override val layoutRes: Int
        get() = R.layout.activity_main

    override fun onCreated() {
        setToolbar()
        setAdapter()
        checkPage()
    }

    override fun observers() {
        viewModel.matchLiveData.observe(this) { res ->
            val currentMill = System.currentTimeMillis()
            res.matches.map {
                val prediction = viewModel.getPrediction(it.team1, it.team2)
                val entity = PredictionEntity(
                    team1 = it.team1,
                    team2 = it.team2,
                    prediction1 = prediction?.prediction1,
                    prediction2 = prediction?.prediction2,
                    addedCurrentMill = currentMill
                )
                viewModel.dbRepository.addOrUpdatePrediction(
                    entity
                )
                entity
            }
        }
        lifecycleScope.launch {
            viewModel.getPredictionFlow().collectLatest {
                if (it != null && it.isEmpty()) {
                    viewModel.getMatches()
                    return@collectLatest
                }
                viewModel.adapter.submitList(it?.map { entity ->
                    MainUiModel(
                        match = MatchModel(entity.team1, entity.team2),
                        prediction1 = entity.prediction1,
                        prediction2 = entity.prediction2
                    )
                })
            }
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

    private fun openResult(isCheckPage: Boolean = false) {
        val list = viewModel.adapter.currentList.toList()
        if (!list.any { it.prediction1 != null && it.prediction2 != null } && !isCheckPage) {
            showToast(getString(R.string.warning_at_least_one_bet))
            return
        }
        startActivity(ResultActivity.create(this))
    }

    private fun checkPage() {
        if (viewModel.localStorage.getString(SharedHelper.keyLastScreenTag) == ResultActivity.TAG) {
            openResult(true)
        }
    }
}