package com.drs.dseller.feature_products.response

/**
 *  Sealed class used as event responses for product screens
 */
sealed class ProductResponse<out P>{

    /**
     * The event is success
     *
     * @param result The result of the event
     */
    data class Success<P>(val result:P):ProductResponse<P>()

    /**
     * The event failed
     *
     * @param message The error message
     */
    data class Error(val message:String):ProductResponse<Nothing>()
}