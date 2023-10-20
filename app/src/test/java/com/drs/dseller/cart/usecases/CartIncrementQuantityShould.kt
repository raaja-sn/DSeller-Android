package com.drs.dseller.cart.usecases

import com.drs.dseller.BaseTest
import com.drs.dseller.core.data.repository.shopping_cart.CartRepositoryImpl
import com.drs.dseller.core.domain.model.shopping_cart.CartProduct
import com.drs.dseller.core.domain.repository.shopping_cart.CartRepository
import com.drs.dseller.core.domain.usecases.shopping_cart_use_cases.CartIncrementQuantity
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.BeforeClass
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runners.MethodSorters

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class CartIncrementQuantityShould {

    companion object{
        private lateinit var repo: CartRepository<CartProduct>
        private lateinit var useCase:CartIncrementQuantity

        @BeforeClass
        @JvmStatic
        fun init(){
            repo = CartRepositoryImpl()
            useCase = CartIncrementQuantity(repo)
        }
    }


    @Test
    fun`increment a product quantity`(){
        val product = CartProduct(productId = "5", quantity = 5)
        repo.addProductToCart(product)
        useCase.invoke(2, product.productId)
        assertEquals(repo.getProductFromCart(product.productId).quantity,7)
    }

    @Test
    fun`not increment quantity if product is not found`(){
        val product = CartProduct(productId = "5", quantity = 5)
        useCase.invoke(2, "")
        assertEquals(repo.getProductFromCart(product.productId).quantity,7)
    }

}