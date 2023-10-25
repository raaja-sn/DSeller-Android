package com.drs.dseller.feature_account.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.drs.dseller.feature_account.presentation.screens.AccountScreen
import com.drs.dseller.feature_account.presentation.screens.UserOrdersScreen
import com.drs.dseller.getViewModel


fun NavGraphBuilder.Account(navHostController: NavHostController){

    navigation(
        startDestination = DESTINATION_ACCOUNT,
        route = ROUTE_ACCOUNT
    ){

        composable(
            route = DESTINATION_ACCOUNT
        ){
            val vm:UserAccountViewModel = it.getViewModel(navController = navHostController)
            AccountScreen(
                state = vm.userAccountState.value,
                bottomNavState = vm.bottomNavigationBarState.value,
                vm = vm,
                navHostController = navHostController
            )
        }

        composable(
            route = DESTINATION_USER_ORDERS
        ){
            val vm:UserAccountViewModel = it.getViewModel(navController = navHostController)
            UserOrdersScreen(
                state = vm.userOrdersState.value,
                bottomNavState = vm.bottomNavigationBarState.value,
                vm = vm,
                navHostController = navHostController
            )
        }

        composable(
            route = DESTINATION_INVOICE
        ){
            val vm:UserAccountViewModel = it.getViewModel(navController = navHostController)
        }

        composable(
            route = DESTINATION_ACCOUNT_DETAIL
        ){
            val vm:UserAccountViewModel = it.getViewModel(navController = navHostController)
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
const val DESTINATION_ACCOUNT_DETAIL = "user_profile_detail"
const val DESTINATION_USER_ORDERS = "user_orders"
const val DESTINATION_INVOICE= "invoice/{invoiceId}"
