package com.drs.dseller.feature_orders.presentation.states

import com.drs.dseller.core.domain.model.shopping_cart.CartProduct

data class CartState(
    val cartItems:List<CartProduct> = listOf(),
    val totalCartValue:Double = 0.0,
    val isPlacingOrder:Boolean = false,
    val isOrderComplete:Boolean = false,
    val errorState:CartOrderErrorState = CartOrderErrorState()
)
