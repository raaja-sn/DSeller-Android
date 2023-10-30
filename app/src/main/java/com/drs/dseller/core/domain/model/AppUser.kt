package com.drs.dseller.core.domain.model

import com.drs.dseller.core.constants.AppConstants
import com.drs.dseller.feature_account.domain.model.AccountUser

data class AppUser(
    val fullname:String = "no name",
    val email:String = "NIL",
    val phoneNumber:String = "",
    val userId:String = "",
    val type:Int = AppConstants.USER_TYPE_SHOPPER
){
    fun toAccountUser() = AccountUser(
        userId = this.userId,
        name = this.fullname,
        email = this.email,
        phoneNumber = this.phoneNumber
    )
}
