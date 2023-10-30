package com.drs.dseller.feature_orders.presentation.screens.order_success

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import com.drs.dseller.feature_home.toHomeFromOrderSuccess

@Composable
fun OrderSuccessScreen(
    navHostController: NavHostController
){

    val backToHome = remember {
        {
            navHostController.toHomeFromOrderSuccess()
        }
    }

    val continueShopping:() ->Unit = remember {
        {
            navHostController.popBackStack()
        }
    }

    OrderSuccessBody(
        backToHome = backToHome,
        continueShopping = continueShopping
    )

}