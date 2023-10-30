package com.drs.dseller.feature_account.data.network

import com.drs.dseller.feature_account.domain.model.UserOrder
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UserOrderService {

    @GET("listorders/{userId}")
    suspend fun listOrders(
        @Path("userId") userId:String,
        @Query("sortBy") sortBy:String,
        @Query("sortOrder") sortOrder:String,
        @Query("pageNumber") pageNumber:Int,
        @Query("pageSize") pageSize:Int
    ):Response<List<UserOrder>>

}