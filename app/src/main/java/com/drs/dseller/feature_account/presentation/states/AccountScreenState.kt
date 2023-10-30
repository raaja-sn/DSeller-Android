package com.drs.dseller.feature_account.presentation.states

import com.drs.dseller.feature_account.domain.model.AccountUser

data class AccountScreenState(
    val user:AccountUser? = null,
    val errorState:AccountErrorState = AccountErrorState(),
    val logoutComplete:Boolean = false
)
