package com.drs.dseller.feature_account.presentation.states

import androidx.paging.PagingData
import com.drs.dseller.feature_account.domain.model.UserOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

data class UserOrdersState(
    val userOrders:Flow<PagingData<UserOrder>> = flow {  }
)
