package com.drs.dseller.core.utils

import com.drs.dseller.core.network.ErrorResponse
import com.drs.dseller.core.network.toNetworkErrorResponse
import okhttp3.ResponseBody

class NetworkUtils {

    companion object{

        fun parseErrorResponse(body:ResponseBody?):ErrorResponse{
            return body?.string()?.toNetworkErrorResponse()?:
            ErrorResponse("2","Unknown Error")
        }
    }

}