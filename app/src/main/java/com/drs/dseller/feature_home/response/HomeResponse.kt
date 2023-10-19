package com.drs.dseller.feature_home.response

/**
 *  Sealed class used as event responses for home screen related operations
 */
sealed class HomeResponse<out H> {

    /**
     * The event is success
     *
     * @param result The result of the success event
     */
    data class Success<H>(val result:H):HomeResponse<H>()

    /**
     * The event failed
     *
     * @param message The error message of failed event
     */
    data class Error(val message:String):HomeResponse<Nothing>()

}