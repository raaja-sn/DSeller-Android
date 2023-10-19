package com.drs.dseller.feature_orders.domain.repository

import com.drs.dseller.core.domain.model.shopping_cart.CartProduct

/**
 * Repository Interface for Shopping Order
 *
 * @param R The result of the operation. Preferably sealed class with success and error results.
 */
interface ShoppingOrderRepository<R> {

    suspend fun placeOrder(cartProducts:List<CartProduct>):R

}