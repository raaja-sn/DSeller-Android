package com.drs.dseller.feature_onboarding.data.network

import com.google.gson.annotations.SerializedName

data class OnBoardingUser(
    @SerializedName("_id") val Id:String = "",
    val fullname:String ="",
    val email:String= "",
    val phoneNumber:String =""
)
