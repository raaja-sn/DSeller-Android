package com.drs.dseller.core.response

/**
 * Sealed class used as event responses for user login and logout operations
 */
sealed class SessionResponse<out R> {

    /**
     * The event succeeded
     * @param R Result, of the event operation like login or logout
     */
    data class Success<R>(val data:R): SessionResponse<R>()

    /**
     * The event failed
     * @param message The error message
     */
    data class Error(val message:String): SessionResponse<Nothing>()

    object SignInError: SessionResponse<Nothing>()
}