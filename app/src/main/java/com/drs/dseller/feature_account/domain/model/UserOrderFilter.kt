package com.drs.dseller.feature_account.domain.model

import com.google.gson.annotations.SerializedName

data class UserOrderFilter(
    val userId:String ="",
    val pageNumber:Int = 1,
    val pageSize:Int = 10,
    val sortBy:String = "purchaseDate",
    val sortOrder:String = "desc"
)
