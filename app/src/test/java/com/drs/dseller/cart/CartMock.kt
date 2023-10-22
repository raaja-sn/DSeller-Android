package com.drs.dseller.cart

import com.drs.dseller.core.domain.model.shopping_cart.CartProduct
import com.drs.dseller.core.domain.repository.shopping_cart.CartRepository
import com.drs.dseller.core.domain.usecases.shopping_cart_use_cases.ShoppingCartUseCases
import org.mockito.kotlin.mock
import org.mockito.kotlin.spy

class CartMock{

    fun getUseCases(): ShoppingCartUseCases {
        return spy(
            ShoppingCartUseCases(
                mock(),
                mock(),
                mock(),
                mock(),
                mock(),
                mock(),
                mock(),
                mock(),
                mock()
            )
        )
    }

    fun getMockCartProducts():List<CartProduct>{
        return listOf(
            CartProduct(productId = "1"),
            CartProduct(productId = "2"),
            CartProduct(productId = "3"),
            CartProduct(productId = "4"),
            CartProduct(productId = "5"),
        )
    }
}