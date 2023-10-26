package com.drs.dseller.feature_account.presentation.states

import com.drs.dseller.feature_account.domain.model.AccountUser

data class AccountDetailScreenState(
    val user: AccountUser? = null,
    val name:String = "",
    val phoneNumber:String = "",
    val errorState:AccountErrorState = AccountErrorState(),
    val shouldValidate:Boolean = false,
    val updatingUser:Boolean = false,
    val shouldDisplaySuccessInfo:Boolean = false
)


fun AccountDetailScreenState.hasValidUserName():Boolean{
    return name.isNotEmpty() || name.length >= 4
}

fun AccountDetailScreenState.hasValidPhoneNumber():Boolean{
    return phoneNumber.length == 10
}