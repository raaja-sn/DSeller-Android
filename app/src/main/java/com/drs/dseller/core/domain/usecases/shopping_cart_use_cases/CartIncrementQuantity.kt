package com.drs.dseller.core.domain.usecases.shopping_cart_use_cases

import com.drs.dseller.core.domain.model.shopping_cart.CartProduct
import com.drs.dseller.core.domain.repository.shopping_cart.CartRepository
import javax.inject.Inject

/**
 * Increment the quantity of the product present in cart
 */
class CartIncrementQuantity @Inject constructor(
    private val repo:CartRepository<CartProduct>
) {
    operator fun invoke(quantity:Int,productId:String) =
        repo.incrementProductQuantity(quantity, productId)
}