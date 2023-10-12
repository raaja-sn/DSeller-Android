package com.drs.dseller.core.domain.model

import com.drs.dseller.core.constants.AppConstants

data class AppUserWithPassword(
    val fullname: String = "no name",
    val email: String = "NIL",
    val password: String,
    val phoneNumber: String = "",
    val type: Int = AppConstants.USER_TYPE_SHOPPER
)
