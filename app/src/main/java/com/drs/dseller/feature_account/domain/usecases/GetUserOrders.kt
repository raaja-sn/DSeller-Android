package com.drs.dseller.feature_account.domain.usecases

import androidx.paging.PagingData
import com.drs.dseller.feature_account.domain.model.UserOrder
import com.drs.dseller.feature_account.domain.model.UserOrderFilter
import com.drs.dseller.feature_account.domain.repository.UserOrderListRepository
import javax.inject.Inject

/**
 * Get list of user orders
 *
 * @param repo The list repository, to get orders list
 */
class GetUserOrders @Inject constructor(
    private val repo:UserOrderListRepository<PagingData<UserOrder>,UserOrderFilter>
) {

    suspend operator fun invoke(filter:UserOrderFilter) =
        repo.getUserOrders(filter)
}