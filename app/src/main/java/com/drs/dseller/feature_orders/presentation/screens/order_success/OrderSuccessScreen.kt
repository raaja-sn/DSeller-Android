package com.drs.dseller.feature_orders.presentation.screens.order_success

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController

@Composable
fun OrderSuccessScreen(
    navHostController: NavHostController
){

    val backToHome = remember {
        {

        }
    }

    val continueShopping = remember {
        {

        }
    }

    OrderSuccessBody(
        backToHome = backToHome,
        continueShopping = continueShopping
    )

}