package com.drs.dseller.feature_account.data.network

import retrofit2.http.PATCH
import retrofit2.http.Path
import retrofit2.http.Query

interface AccountsService {

    @PATCH("user/{userId}")
    suspend fun updateUser(
        @Path("userId") userID:String,
        @Query("fullname") name:String,
        @Query("phoneNumber") phoneNumber:String
    )

}