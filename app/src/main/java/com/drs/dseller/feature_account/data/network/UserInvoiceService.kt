package com.drs.dseller.feature_account.data.network

import com.drs.dseller.feature_account.domain.model.FullInvoice
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface UserInvoiceService {

    @GET("order/{orderId}")
    suspend fun getInvoice(
        @Path("orderId") orderId:String
    ):Response<FullInvoice>

}