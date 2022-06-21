package com.example.task.ui.main.compose

import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.example.network.model.MatchModel
import com.example.task.R
import com.example.task.base.BaseComposeActivity
import com.example.task.ui.main.MainViewModel
import com.example.task.ui.main.model.MainUiModel
import com.example.task.ui.result.ResultActivity
import com.example.task.utils.extension.showToast
import com.example.task.utils.sharedHelper.SharedHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainScreen : BaseComposeActivity<MainScreenViewModel>() {

    override val viewModel: MainScreenViewModel by viewModels()

    override fun onCreated() {
        setContent {
            Scaffold(topBar = { mainCompose() }) {
                matchesCompose()
            }
        }
        checkPage()
    }

    @OptIn(ExperimentalUnitApi::class)
    @Composable
    fun mainCompose() {
        val appRadius = dimensionResource(id = R.dimen.app_radius)
        TopAppBar(
            modifier = Modifier.clip(RoundedCornerShape(bottomEnd = appRadius, bottomStart = appRadius)),
            title = {
            },
            backgroundColor = colorResource(id = R.color.primary500),
            actions = {
                IconButton(modifier = Modifier.padding(horizontal = appRadius), onClick = {
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
        )
    }

    @Composable
    private fun matchesCompose() {
        val flow by viewModel.getStateUi.collectAsState(listOf(), context = lifecycleScope.coroutineContext)
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            itemsIndexed(flow) { idx, match ->
                matchItem(MainUiModel(
                    match = MatchModel(match.team1, match.team2),
                    prediction1 = match.prediction1,
                    prediction2 = match.prediction2
                ))
            }
        }
    }

    @OptIn(ExperimentalUnitApi::class)
    @Composable
    private fun matchItem(match: MainUiModel, modifier: Modifier = Modifier) {
        Surface(
            modifier = modifier
        ) {
            Card(
                elevation = 4.dp,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                shape = RoundedCornerShape(dimensionResource(id = R.dimen.app_radius))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp, vertical = 20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = match.match.team1,
                        modifier = Modifier
                            .wrapContentHeight()
                            .weight(1f),
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        style = TextStyle(
                            fontSize = TextUnit(
                                value = dimensionResource(id = R.dimen.normal_size).value,
                                type = TextUnitType.Sp
                            ), fontWeight = FontWeight.Bold
                        )
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Column(
                        modifier = Modifier.weight(0.5f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Button(
                            onClick = { viewModel.addPrediction(this@MainScreen, match) },
                            modifier = Modifier,
                            shape = RoundedCornerShape(dimensionResource(id = R.dimen.app_radius)),
                            colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.primary500))
                        ) {
                            Text(
                                text = stringResource(id = R.string.bet),
                                color = Color.White
                            )
                        }
                        if (match.prediction1 != null && match.prediction2 != null) {
                            Text(
                                text = "${match.prediction1} : ${match.prediction2}",
                                modifier = Modifier
                                    .wrapContentWidth(),
                                textAlign = TextAlign.Center,
                                style = TextStyle(
                                    fontSize = TextUnit(
                                        value = dimensionResource(id = R.dimen.small_size).value,
                                        type = TextUnitType.Sp
                                    )
                                )
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = match.match.team2,
                        modifier = Modifier
                            .wrapContentHeight()
                            .weight(1f),
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        style = TextStyle(
                            fontSize = TextUnit(
                                value = dimensionResource(id = R.dimen.normal_size).value,
                                type = TextUnitType.Sp
                            ), fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
        }
    }

    @Preview
    @Composable
    fun previewCard() {
        matchItem(MainUiModel(MatchModel("sadf", "fgnjhkgnfhokf"), 2, 3), Modifier.height(100.dp))
    }

    private fun openResult(isCheckPage: Boolean = false) {
        val list = viewModel.getStateUi.value
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
