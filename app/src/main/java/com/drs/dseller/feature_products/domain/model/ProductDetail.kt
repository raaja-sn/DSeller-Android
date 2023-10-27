package com.drs.dseller.feature_products.domain.model

import com.google.gson.annotations.SerializedName

data class ProductDetail(
    @SerializedName("_id")val productId:String = "",
    val name:String = "",
    val category:String = "",
    val subCategory:String = "",
    val descriptionLong:String = "",
    val descriptionShort:String = "",
    val price:Double = 0.0,
    val productStock:Int = 0,
    val productPictures:List<ProductPicture> = listOf(
        ProductPicture()
    )
)
