package com.drs.dseller.feature_account.presentation.states

import com.drs.dseller.feature_account.domain.model.AccountUser

data class AccountDetailScreenState(
    val user: AccountUser? = null,
    val name:String = "",
    val phoneNumber:String = "",
    val errorState:AccountErrorState = AccountErrorState(),
    val updatingUser:Boolean = false
)
