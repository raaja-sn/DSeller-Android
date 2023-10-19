package com.drs.dseller.feature_orders.domain.usecases

import com.drs.dseller.core.domain.model.shopping_cart.CartProduct
import com.drs.dseller.feature_orders.domain.repository.ShoppingOrderRepository
import com.drs.dseller.feature_orders.response.ShoppingOrderResponse
import javax.inject.Inject

/**
 *  Place a shopping order
 *
 *  @param repo Repository object to place order
 */
class PlaceOrder @Inject constructor(
    private val repo:ShoppingOrderRepository<ShoppingOrderResponse<Unit>>
){

    suspend operator fun invoke(cartProducts:List<CartProduct>) =
        repo.placeOrder(cartProducts)

}