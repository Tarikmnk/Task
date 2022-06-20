package com.example.task.ui.main

import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.example.network.model.MatchModel
import com.example.task.R
import com.example.task.base.BaseComposeActivity
import com.example.task.ui.result.ResultActivity
import com.example.task.utils.extension.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainComposeActivity : BaseComposeActivity<MainViewModel>() {

    override val viewModel: MainViewModel by viewModels()

    override val layoutRes: Int
        get() = R.layout.activity_main

    override fun onCreated() {
        setContent {
            Scaffold(topBar = { mainCompose() }) {
                matchesCompose()
            }
        }
    }


    @OptIn(ExperimentalUnitApi::class)
    @Composable
    fun mainCompose() {
        val appRadius = dimensionResource(id = R.dimen.app_radius)
        TopAppBar(
            modifier = Modifier
                .clip(RoundedCornerShape(bottomEnd = appRadius, bottomStart = appRadius)),
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
                        fontSize = TextUnit(value = dimensionResource(id = R.dimen.normal_size).value, type = TextUnitType.Sp)
                    )
                }
            }
        )
    }

    @Composable
    private fun matchesCompose() {
        val data by viewModel.matchLiveData.observeAsState()
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(data?.matches ?: listOf()) { match ->
                matchItem(match)
            }
        }
    }

    @Composable
    private fun matchItem(match: MatchModel) {
        Surface(
            modifier = Modifier

        ) {
            Card(
                elevation = 4.dp,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                shape = RoundedCornerShape(dimensionResource(id = R.dimen.app_radius))
            ) {
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp, vertical = 20.dp),
                ) {
                    Text(
                        text = match.team1, modifier = Modifier
                            .wrapContentHeight()
                            .weight(1f), textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Button(
                        onClick = { },
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(0.5f),
                        shape = RoundedCornerShape(dimensionResource(id = R.dimen.app_radius)),
                        colors = ButtonDefaults.buttonColors(backgroundColor =colorResource(id = R.color.primary500))
                    ) {

                    }
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = match.team2, modifier = Modifier
                            .wrapContentHeight()
                            .weight(1f), textAlign = TextAlign.Center
                    )
                }
            }
        }
    }

    @Preview
    @Composable
    fun previewCard(){
        matchItem(MatchModel("sadf",""))
    }

    private fun openResult(isCheckPage: Boolean = false) {
        val list = viewModel.adapter.currentList.toList()
        if (!list.any { it.prediction1 != null && it.prediction2 != null } && !isCheckPage) {
            showToast(getString(R.string.warning_at_least_one_bet))
            return
        }
        startActivity(ResultActivity.create(this))
    }
}
