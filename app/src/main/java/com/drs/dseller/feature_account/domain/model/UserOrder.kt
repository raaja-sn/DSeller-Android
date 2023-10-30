package com.drs.dseller.feature_account.domain.model

import com.google.gson.annotations.SerializedName

data class UserOrder(
    @SerializedName("_id") val orderId:String = "",
    val invoiceNo:String = "",
    val billValue:Double = 0.0,
    val shippingCost:Double = 0.0,
    val purchaseDate: Long = 0
)
