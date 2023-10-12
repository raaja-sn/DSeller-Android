package com.drs.dseller.core.network

import com.google.gson.Gson

data class ErrorResponse(
    val code:String,
    val message:String,
) {
}

fun String.toNetworkErrorResponse():ErrorResponse{
    return Gson().fromJson<ErrorResponse>(
        this,ErrorResponse::class.java
    )
}