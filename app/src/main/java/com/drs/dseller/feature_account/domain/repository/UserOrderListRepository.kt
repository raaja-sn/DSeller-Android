package com.drs.dseller.feature_account.domain.repository

import kotlinx.coroutines.flow.Flow

/**
 * Repository interface to ger user orders list.
 *
 * @param O List of user orders
 * @param Q The key or filter to fetch list of Orders
 */
interface UserOrderListRepository<O,Q> {

    /**
     * Get list of user orders
     *
     * @param filter The filter used to match and return list of orders
     * @return Flow to collect list of user orders
     */
    suspend fun getUserOrders(filter:Q):Flow<O>

}