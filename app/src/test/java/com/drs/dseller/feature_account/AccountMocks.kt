package com.drs.dseller.feature_account

import com.drs.dseller.feature_account.domain.model.AccountUser
import com.drs.dseller.feature_account.domain.model.UserOrder
import com.drs.dseller.feature_account.domain.usecases.AccountUseCases
import org.mockito.kotlin.mock
import org.mockito.kotlin.spy

class AccountMocks {

    fun getUser() = AccountUser(name = "Raaja")

    fun getOrdersList() = listOf(
        UserOrder(orderId = "5"),
        UserOrder(orderId = "6"),
        UserOrder(orderId = "7"),
        UserOrder(orderId = "8"),
        UserOrder(orderId = "9")
    )

    fun getUseCases() = spy(AccountUseCases(mock(), mock(), mock()))

}