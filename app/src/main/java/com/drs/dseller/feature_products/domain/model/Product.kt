package com.drs.dseller.feature_products.domain.model

import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("_id") val productId:String = "",
    val name:String ="",
    val productPictures:List<ProductPicture> = listOf(),
    val price:Double = 0.0,
)
