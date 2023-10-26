package com.drs.dseller.feature_account.presentation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.drs.dseller.core.navigation.getViewModel
import com.drs.dseller.feature_account.presentation.screens.account.AccountScreen
import com.drs.dseller.feature_account.presentation.screens.account_detail.AccountDetailScreen
import com.drs.dseller.feature_account.presentation.screens.invoice.InvoiceScreen
import com.drs.dseller.feature_account.presentation.screens.user_orders.UserOrdersScreen


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
            route = "$DESTINATION_INVOICE/{invoiceId}"
        ){
            val vm:UserAccountViewModel = it.getViewModel(navController = navHostController)
            vm.onUserInvoiceEvent(UserInvoiceEvent.SetOrderId(it.arguments?.getString("invoiceId")?:""))
            InvoiceScreen(
                state = vm.userInvoiceState.value,
                bottomNavState = vm.bottomNavigationBarState.value,
                vm = vm,
                navHostController = navHostController
            )
        }

        composable(
            route = DESTINATION_ACCOUNT_DETAIL
        ){
            val vm:UserAccountViewModel = it.getViewModel(navController = navHostController)
            AccountDetailScreen(
                state = vm.userAccountDetailState.value,
                bottomNavState = vm.bottomNavigationBarState.value,
                vm = vm,
                navHostController = navHostController)
        }
    }

}

fun NavHostController.toAccount(){
    navigate(DESTINATION_ACCOUNT)
}

fun NavHostController.toAccountDetail(){
    navigate(DESTINATION_ACCOUNT_DETAIL)
}

fun NavHostController.toUserOrders(){
    navigate(DESTINATION_USER_ORDERS)
}

fun NavHostController.toInvoice(invoiceId:String){
    navigate("$DESTINATION_INVOICE/$invoiceId")
}


const val ROUTE_ACCOUNT = "account"
const val DESTINATION_ACCOUNT = "user_profile"
const val DESTINATION_ACCOUNT_DETAIL = "user_profile_detail"
const val DESTINATION_USER_ORDERS = "user_orders"
const val DESTINATION_INVOICE= "invoice"
