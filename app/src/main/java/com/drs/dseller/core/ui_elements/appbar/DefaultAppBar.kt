@file:OptIn(ExperimentalMaterial3Api::class)

package com.drs.dseller.core.ui_elements.appbar

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.TopAppBarState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.drs.dseller.R
import com.drs.dseller.ui.theme.AppTypography
import com.drs.dseller.ui.theme.Black80

@Composable
fun DefaultAppBar(
    scrollBehavior:TopAppBarScrollBehavior,
    iconResId:Int = R.drawable.ic_arrow_left,
    title:String,
    navIconClicked:()->Unit,
    actionResId:Int? = null,
    actionClicked:(()->Unit) = {  },
){
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title,
                style = AppTypography.headlineSmall,
                overflow = TextOverflow.Ellipsis,
                color = Black80
            )
        },
        navigationIcon = {
            IconButton(onClick = navIconClicked) {
                Icon(
                    modifier = Modifier
                        .size(dimensionResource(id = R.dimen.fourty_dp))
                        .padding(dimensionResource(id = R.dimen.ten_dp)),
                    painter = painterResource(id = iconResId),
                    contentDescription = stringResource(id = R.string.description_app_bar_back),
                    tint = Color.Unspecified
                    )
            }
        },
        actions = {
            actionResId?.let{
                IconButton(onClick = actionClicked) {
                    Icon(
                        modifier = Modifier
                            .size(dimensionResource(id = R.dimen.fourty_dp))
                            .padding(dimensionResource(id = R.dimen.ten_dp)),
                        painter = painterResource(id = actionResId),
                        contentDescription = stringResource(id = R.string.description_app_bar_filter),
                        tint = Color.Unspecified
                    )
                }
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
          containerColor = Color.White
        ),
        scrollBehavior = scrollBehavior

    )
}

@Preview
@Composable
private fun AppBarPreview(){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    DefaultAppBar(scrollBehavior = scrollBehavior, title = "Electronics",
        actionResId = R.drawable.ic_filter,
        navIconClicked = { /*TODO*/ }) {

    }
}