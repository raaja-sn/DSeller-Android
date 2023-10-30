package com.drs.dseller.feature_account.domain.repository

/**
 * Repository interface to work with user orders
 *
 * @param R The result of operation. Preferably sealed class with success and error results.
 */
interface UserOrderRepository<R> {

    /**
     * Get order detail for the given order id
     *
     * @param orderId The order Id of the order
     * @return [R] The result of fetching the order
     */
    suspend fun getOrderDetail(orderId:String):R

}