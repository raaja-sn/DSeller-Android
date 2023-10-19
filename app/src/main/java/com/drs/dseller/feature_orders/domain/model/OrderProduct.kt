package com.drs.dseller.feature_orders.domain.model

import com.drs.dseller.core.domain.model.shopping_cart.CartProduct

data class OrderProduct(
    val productName:String = "",
    val productId:String = "",
    val quantity:Int = 0,
){
}

fun List<CartProduct>.toOrderProducts():List<OrderProduct>{
    val orderList = mutableListOf<OrderProduct>()
    forEach {
        orderList.add(OrderProduct(
            productName = it.productName,
            productId = it.productId,
            quantity = it.quantity
        ))
    }
    return orderList
}

