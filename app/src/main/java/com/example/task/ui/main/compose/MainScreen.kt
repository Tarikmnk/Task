package com.example.task.ui.main.compose

import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.lifecycle.lifecycleScope
import com.example.network.model.MatchModel
import com.example.task.R
import com.example.task.base.BaseComposeActivity
import com.example.task.ui.customComponent.compose.app.TaskToolbar
import com.example.task.ui.customComponent.compose.match.MatchUiModel
import com.example.task.ui.customComponent.compose.match.matchItem
import com.example.task.ui.result.ResultActivity
import com.example.task.ui.result.compose.ResultScreen
import com.example.task.utils.extension.showToast
import com.example.task.utils.sharedHelper.SharedHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainScreen : BaseComposeActivity<MainScreenViewModel>() {

    override val viewModel: MainScreenViewModel by viewModels()

    override fun onCreated() {
        setContent {
            Scaffold(topBar = { TaskToolbar(actions = toolbarActions()) }) {
                matchesCompose()
            }
        }
        checkPage()
    }

    @OptIn(ExperimentalUnitApi::class)
    @Composable
    fun toolbarActions() : @Composable RowScope.() -> Unit {

        return {
            IconButton(modifier = Modifier, onClick = {
                openResult()
            }) {
                Text(
                    text = getString(R.string.get_results),
                    color = Color.White,
                    fontSize = TextUnit(
                        value = dimensionResource(id = R.dimen.normal_size).value,
                        type = TextUnitType.Sp
                    )
                )
            }
        }
    }

    @Composable
    private fun matchesCompose() {
        val flow by viewModel.getStateUi.collectAsState(listOf(), context = lifecycleScope.coroutineContext)
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            itemsIndexed(flow) { idx, match ->
                matchItem(match = MatchUiModel(
                    match = MatchModel(match.team1, match.team2),
                    prediction1 = match.prediction1,
                    prediction2 = match.prediction2
                ), isClickable = true, onButtonClick = { item -> viewModel.addPrediction(this@MainScreen, item) })
            }
        }
    }

    private fun openResult(isCheckPage: Boolean = false) {
        val list = viewModel.getStateUi.value
        if (!list.any { it.prediction1 != null && it.prediction2 != null } && !isCheckPage) {
            showToast(getString(R.string.warning_at_least_one_bet))
            return
        }
        startActivity(ResultScreen.create(this))
    }


    private fun checkPage() {
        if (viewModel.localStorage.getString(SharedHelper.keyLastScreenTag) == ResultActivity.TAG) {
            openResult(true)
        }
    }
}
