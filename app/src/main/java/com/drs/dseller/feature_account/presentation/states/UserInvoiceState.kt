package com.drs.dseller.feature_account.presentation.states

import com.drs.dseller.feature_account.domain.model.FullInvoice

data class UserInvoiceState(
    val orderId:String? = null,
    val fetchingOrder:Boolean = false,
    val invoice:FullInvoice? = null,
    val errorState:AccountErrorState = AccountErrorState()
)
