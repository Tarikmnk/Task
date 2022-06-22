package com.example.task.ui.customComponent.compose.match

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.example.network.model.MatchModel
import com.example.task.R

@OptIn(ExperimentalUnitApi::class)
@Composable
fun matchItem(
    modifier: Modifier = Modifier,
    match: MatchUiModel,
    isClickable: Boolean = false,
    onButtonClick: (item: MatchUiModel) -> Unit = {}
) {
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
                    modifier = Modifier,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        onClick = { onButtonClick.invoke(match) },
                        modifier = Modifier.focusable(isClickable),
                        shape = RoundedCornerShape(dimensionResource(id = R.dimen.app_radius)),
                        colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.primary500))
                    ) {
                        val btnText = if (match.team1_points != null && match.team2_points != null) {
                            "${match.team1_points} : ${match.team2_points}"
                        } else {
                            stringResource(id = R.string.bet)
                        }
                        Text(
                            text = btnText,
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
    matchItem(
        match = MatchUiModel(MatchModel("sadf", "fgnjhkgnfhokf"), 2, 3),
        modifier = Modifier.height(100.dp),
        isClickable = true,
        onButtonClick = {})
}

@Preview
@Composable
fun previewCardWithPoints() {
    matchItem(
        match = MatchUiModel(MatchModel("sadf", "fgnjhkgnfhokf"), 2, 3,1,5),
        modifier = Modifier.height(100.dp),
        isClickable = true,
        onButtonClick = {})
}