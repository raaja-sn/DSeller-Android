package com.drs.dseller.core.domain.model

import com.drs.dseller.core.constants.AppConstants

data class AppUser(
    val fullname:String = "no name",
    val email:String = "NIL",
    val phoneNumber:String = "",
    val userId:String = "",
    val type:Int = AppConstants.USER_TYPE_SHOPPER
)
