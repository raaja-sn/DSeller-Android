package com.drs.dseller.core.domain.model.shopping_cart

data class CartProduct(
    val productName:String = "",
    val productId:String = "",
    val quantity:Int = 0,
    val price:Double = 0.0,
    val productImage:String = ""
)
