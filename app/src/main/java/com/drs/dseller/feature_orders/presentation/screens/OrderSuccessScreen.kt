package com.drs.dseller.feature_orders.presentation.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import com.drs.dseller.feature_orders.presentation.screens.components.OrderSuccessBody

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