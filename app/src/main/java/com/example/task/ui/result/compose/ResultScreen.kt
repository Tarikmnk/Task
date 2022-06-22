package com.example.task.ui.result.compose

import android.content.Context
import android.content.Intent
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.example.task.R
import com.example.task.base.BaseComposeActivity
import com.example.task.ui.customComponent.compose.app.TaskToolbar
import com.example.task.ui.customComponent.compose.match.matchItem
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResultScreen : BaseComposeActivity<ResultScreenViewModel>() {

    companion object {
        const val TAG = "ResultActivity"
        fun create(context: Context): Intent {
            return Intent(context, ResultScreen::class.java)
        }
    }

    override val viewModel: ResultScreenViewModel by viewModels()

    override fun onCreated() {
        setContent {
            Scaffold(topBar = { TaskToolbar(navigationIcon = toolbarIcon()) }) {
                ListResults()
            }
        }
    }

    @Composable
    fun toolbarIcon(): @Composable () -> Unit {
        return {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Arrow Back",
                tint = Color.White,
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable {
                    onBackPressed()
                })
        }
    }

    @Composable
    fun ListResults() {
        val list by viewModel.resultLiveData.observeAsState()

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            list?.let {
                itemsIndexed(it) { index, item ->
                    matchItem(match = item)
                }
            }
        }
    }

}