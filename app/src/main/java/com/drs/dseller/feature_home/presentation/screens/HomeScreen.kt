package com.drs.dseller.feature_home.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.drs.dseller.R
import com.drs.dseller.feature_home.presentation.HomeEvent
import com.drs.dseller.feature_home.presentation.HomeViewModel
import com.drs.dseller.feature_home.presentation.screens.components.OfferImage
import com.drs.dseller.feature_home.presentation.states.HomeState

@Composable
fun HomeScreen(
    state:HomeState,
    vm:HomeViewModel,
    navHostController: NavHostController
){
    println("Home Screen -----------------")

    val searchChanged = remember{
        { text:String ->
            vm.onEvent(HomeEvent.SearchChanged(text))
        }
    }

    val categoryClicked = remember{
        { idx:Int ->

        }
    }

    HomeBody(
        state = state ,
        searchChanged = searchChanged ,
        categoryClicked = categoryClicked 
    )

}
