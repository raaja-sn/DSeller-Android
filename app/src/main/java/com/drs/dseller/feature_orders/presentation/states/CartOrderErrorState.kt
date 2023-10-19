package com.drs.dseller.feature_orders.presentation.states

data class CartOrderErrorState(
    val isError:Boolean = false,
    val message:String = ""
)
