package com.drs.dseller.feature_orders.presentation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.drs.dseller.feature_orders.presentation.screens.OrderSuccessScreen
import com.drs.dseller.feature_orders.presentation.screens.ShoppingCartScreen
import com.drs.dseller.getViewModel


fun NavGraphBuilder.Cart(navHostController: NavHostController){
    
    navigation(
        startDestination = DESTINATION_SHOPPING_CART,
        route = ROUTE_CART
    ){
        composable(
            route = DESTINATION_SHOPPING_CART
        ){
            val vm:ShoppingOrderViewModel = it.getViewModel(navController = navHostController)
            
            ShoppingCartScreen(
                state = vm.cartScreenState.value,
                vm = vm,
                navHostController = navHostController)
        }
        
        composable(
            route = DESTINATION_ORDER_COMPLETE
        ){
            OrderSuccessScreen(navHostController = navHostController)
        }
        
    }
}

fun NavHostController.toShoppingCart(){
    navigate(DESTINATION_SHOPPING_CART)
}

fun NavHostController.toOrderSuccess(){
    navigate(DESTINATION_ORDER_COMPLETE)
}

const val ROUTE_CART = "cart"
const val DESTINATION_SHOPPING_CART = "shopping_cart"
const val DESTINATION_ORDER_COMPLETE = "order_complete"