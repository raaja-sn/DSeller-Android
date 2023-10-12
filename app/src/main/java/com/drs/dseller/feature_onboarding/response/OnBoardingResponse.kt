package com.drs.dseller.feature_onboarding.response

/**
 *  Sealed class used as event responses for shopper or seller related operations, like login, logout .etc
 */
sealed class OnBoardingResponse<out S>{

    /**
     * The event is success.
     * @param S The result of the event.
     */
    data class Success<S>(val data:S): OnBoardingResponse<S>()

    /**
     * The event failed
     * @param error Error message
     */
    data class Error(val error: String): OnBoardingResponse<Nothing>()

}
