package com.drs.dseller.cart.usecases

import com.drs.dseller.BaseTest
import com.drs.dseller.core.data.repository.shopping_cart.CartRepositoryImpl
import com.drs.dseller.core.domain.model.shopping_cart.CartProduct
import com.drs.dseller.core.domain.repository.shopping_cart.CartRepository
import com.drs.dseller.core.domain.usecases.shopping_cart_use_cases.CartDecrementQuantity
import junit.framework.TestCase.assertEquals
import org.junit.BeforeClass
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runners.MethodSorters

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class CartDecrementQuantityShould {

    companion object{
        private lateinit var repo: CartRepository<CartProduct>
        private lateinit var useCase:CartDecrementQuantity

        @BeforeClass
        @JvmStatic
        fun init() {
            repo = CartRepositoryImpl()
            useCase = CartDecrementQuantity(repo)
            repo.addProductToCart(CartProduct(productId = "5", quantity = 5))
        }
    }

    @Test
    fun`decrement a product quantity`(){
        useCase.invoke(2,"5")
        assertEquals(repo.getProductFromCart("5").quantity,3)
    }

    @Test
    fun`not decrement quantity if product cannot be found`(){
        useCase.invoke(2,"6")
        assertEquals(repo.getProductFromCart("5").quantity,3)
    }

}