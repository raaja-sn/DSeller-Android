package com.drs.dseller.feature_account.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.drs.dseller.core.network.ServiceGenerator
import com.drs.dseller.feature_account.data.network.UserOrderPagingSource
import com.drs.dseller.feature_account.data.network.UserOrderService
import com.drs.dseller.feature_account.domain.model.UserOrder
import com.drs.dseller.feature_account.domain.model.UserOrderFilter
import com.drs.dseller.feature_account.domain.repository.UserOrderListRepository
import kotlinx.coroutines.flow.Flow

class UserOrderListRepositoryImpl(
    private val serviceGenerator:ServiceGenerator
):UserOrderListRepository<PagingData<UserOrder>,UserOrderFilter> {

    private val userOrderService = serviceGenerator.generateService(UserOrderService::class.java)

    override suspend fun getUserOrders(filter: UserOrderFilter): Flow<PagingData<UserOrder>> {
        return Pager(
            PagingConfig(20, maxSize = 80)
        ){
            UserOrderPagingSource(filter,userOrderService)
        }.flow
    }
}