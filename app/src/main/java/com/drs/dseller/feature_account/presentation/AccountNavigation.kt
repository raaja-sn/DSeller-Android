package com.drs.dseller.feature_account.presentation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation


fun NavGraphBuilder.Account(navHostController: NavHostController){

    navigation(
        startDestination = DESTINATION_ACCOUNT,
        route = ROUTE_ACCOUNT
    ){
        composable(
            route = DESTINATION_ACCOUNT
        ){

        }

        composable(
            route = DESTINATION_USER_ORDERS
        ){

        }
    }

}

fun NavHostController.toAccount(navHostController: NavHostController){
    navHostController.navigate(DESTINATION_ACCOUNT)
}

fun NavHostController.toUserOrders(navHostController: NavHostController){
    navHostController.navigate(DESTINATION_USER_ORDERS)
}

const val ROUTE_ACCOUNT = "account"
const val DESTINATION_ACCOUNT = "user_profile"
const val DESTINATION_USER_ORDERS = "user_orders"