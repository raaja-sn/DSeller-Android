package com.drs.dseller.feature_account.presentation.states

data class AccountErrorState(
    val isError:Boolean = false,
    val message:String = ""
)
