package com.drs.dseller.feature_onboarding.data.network

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Query

interface OnBoardingService {

    @POST("user/")
    suspend fun createNewUser(
        @Query("fullname") fullname:String,
        @Query("email") email:String,
        @Query("phoneNumber") phoneNumber:String
    ):Response<OnBoardingUser>
}