package com.drs.dseller.feature_orders.domain.usecases

import javax.inject.Inject


data class OrdersUseCases @Inject constructor(
    val placeOrder: PlaceOrder
){
}