package com.drs.dseller.feature_home

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.drs.dseller.core.navigation.getViewModel
import com.drs.dseller.feature_home.presentation.HomeViewModel
import com.drs.dseller.feature_home.presentation.screens.home.HomeScreen


fun NavGraphBuilder.Home(navHostController: NavHostController){
    navigation(
        startDestination = DESTINATION_HOME,
        route = ROUTE_HOME
    ){
        composable(
            route = DESTINATION_HOME
        ){
            val vm: HomeViewModel = it.getViewModel(navController = navHostController)

            HomeScreen(
                state = vm.homeState.value,
                vm = vm,
                navHostController = navHostController
            )
        }
    }
}

fun NavHostController.toHome(){
    popBackStack()
    navigate("home")
}

const val ROUTE_HOME = "home_route"
const val DESTINATION_HOME = "home"