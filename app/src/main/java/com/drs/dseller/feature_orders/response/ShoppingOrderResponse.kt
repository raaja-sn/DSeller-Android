package com.drs.dseller.feature_orders.response

/**
 *  Sealed class used as event responses for shopping cart screen
 */
sealed class ShoppingOrderResponse<out O> {

    /**
     * The event is success
     *
     * @param data The result of the event
     */
    data class Success<O>(val data:O):ShoppingOrderResponse<O>()


    /**
     * The event failed
     *
     * @param message The error message
     */
    data class Error(val message:String):ShoppingOrderResponse<Nothing>()
}