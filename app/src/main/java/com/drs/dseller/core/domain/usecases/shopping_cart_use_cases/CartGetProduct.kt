package com.drs.dseller.core.domain.usecases.shopping_cart_use_cases

import com.drs.dseller.core.domain.model.shopping_cart.CartProduct
import com.drs.dseller.core.domain.repository.shopping_cart.CartRepository
import javax.inject.Inject

/**
 * Get a product present in the cart
 *
 */
class CartGetProduct @Inject constructor(
    private val repo:CartRepository<CartProduct>
) {

    operator fun invoke(productId:String) =
        repo.getProductFromCart(productId)

}