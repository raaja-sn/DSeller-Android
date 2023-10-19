package com.drs.dseller.feature_orders.data.network

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ShoppingOrderService {

    @POST("order")
    suspend fun placeOrder(
        @Body order:ShoppingOrder
    ):Response<Nothing>

}