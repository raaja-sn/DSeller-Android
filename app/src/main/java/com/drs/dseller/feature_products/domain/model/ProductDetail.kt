package com.drs.dseller.feature_products.domain.model

data class ProductDetail(
    val productId:String = "",
    val name:String = "",
    val category:String = "",
    val subCategory:String = "",
    val descriptionLong:String = "",
    val price:Double = 0.0,
    val productStock:Int = 0,
    val productPictures:List<String> = listOf()
)
