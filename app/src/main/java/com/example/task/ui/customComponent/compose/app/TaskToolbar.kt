package com.example.task.ui.customComponent.compose.app

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.ExperimentalUnitApi
import com.example.task.R

@OptIn(ExperimentalUnitApi::class)
@Composable
fun TaskToolbar(
    navigationIcon: @Composable (() -> Unit)? = null,
    title: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
) {
    val appRadius = dimensionResource(id = R.dimen.app_radius)
    Surface(
        modifier = Modifier
            .clip(RoundedCornerShape(bottomEnd = appRadius, bottomStart = appRadius))
            .background(colorResource(id = R.color.primary500))
            .padding(horizontal = appRadius)
    ) {
        TopAppBar(
            modifier = Modifier,
            title = title,
            navigationIcon = navigationIcon,
            backgroundColor =colorResource(id = R.color.primary500),
            actions = actions
        )
    }
}