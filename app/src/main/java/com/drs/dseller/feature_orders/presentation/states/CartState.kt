package com.drs.dseller.feature_orders.presentation.states

import com.drs.dseller.core.domain.model.shopping_cart.CartProduct
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class CartState(
    val cartItems:List<CartProduct> = listOf(),
    val totalCartValue:Double = 0.0,
    val isPlacingOrder:Boolean = false,
    val isOrderComplete:Boolean = false,
    val errorState:CartOrderErrorState = CartOrderErrorState(),
    val cartFlow: StateFlow<List<CartProduct>> = MutableStateFlow(listOf())
)

