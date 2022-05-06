package com.example.task.ui.result

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.network.model.MatchModel
import com.example.network.model.MatchResultModel
import com.example.task.R
import com.example.task.base.BaseActivity
import com.example.task.databinding.ActivityResultBinding
import com.example.task.ui.main.model.MainUiModel
import com.example.task.ui.result.model.ResultUiModel
import com.example.task.utils.sharedHelper.SharedHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ResultActivity : BaseActivity<ResultViewModel, ActivityResultBinding>() {

    companion object {
        const val TAG = "ResultActivity"
        fun create(context: Context): Intent {
            return Intent(context, ResultActivity::class.java)
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
            lifecycleScope.launch {
                viewModel.getPredictionFlow().collectLatest { collect ->
                    viewModel.adapter.submitList(res.map { entity ->
                        val prediction =
                            collect?.firstOrNull { it.team1 == entity.team1 && it.team2 == entity.team2 }
                        ResultUiModel(
                            match = entity,
                            prediction1 = prediction?.prediction1,
                            prediction2 = prediction?.prediction2
                        )
                    })
                }
            }
        }
    }

    private fun setToolbar() {
        binding.tbResult.setOnMenuItemClickListener {

            when (it.itemId) {
                R.id.btnRestart -> {
                    restart()
                    finish()
                }
            }
            return@setOnMenuItemClickListener true
        }
    }

    private fun setAdapter() {
        binding.rvResult.adapter = viewModel.adapter
    }

    private fun restart() {
        viewModel.localStorage.putString(SharedHelper.keyLastScreenTag, "")
        viewModel.dbRepository.clearPrediction()
    }

    override fun onBackPressed() {
        restart()
        super.onBackPressed()
    }
}