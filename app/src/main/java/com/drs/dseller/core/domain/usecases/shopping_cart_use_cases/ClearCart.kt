package com.drs.dseller.core.domain.usecases.shopping_cart_use_cases

import com.drs.dseller.core.domain.model.shopping_cart.CartProduct
import com.drs.dseller.core.domain.repository.shopping_cart.CartRepository
import javax.inject.Inject

/**
 * Clear the shopping cart
 *
 */
class ClearCart @Inject constructor(
    private val repo:CartRepository<CartProduct>
) {

    operator fun invoke() = repo.clearCart()
}