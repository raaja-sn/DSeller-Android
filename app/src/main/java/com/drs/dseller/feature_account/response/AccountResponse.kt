package com.drs.dseller.feature_account.response

/**
 * Sealed class used as event responses for account screens
 */
sealed class AccountResponse<out U>{

    /**
     * The event is success
     *
     * @param data The result of the event
     */
    data class Success<U>(val data:U):AccountResponse<U>()

    /**
     * the event failed
     *
     * @param message The error message
     */
    data class Error(val message:String):AccountResponse<Nothing>()

}
