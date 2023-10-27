package com.drs.dseller.feature_products.domain.model

import com.google.gson.annotations.SerializedName

data class ProductPicture(
    @SerializedName("_id") val pictureId:String ="",
    @SerializedName("imgUrl") val pictureUrl:String =""
)
