package com.drs.dseller.feature_orders.data.network

import com.drs.dseller.feature_orders.domain.model.OrderProduct

data class ShoppingOrder(
    val userId:String = "",
    val products:List<OrderProduct> = listOf()
)
