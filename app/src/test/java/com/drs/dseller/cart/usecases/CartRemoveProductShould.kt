package com.drs.dseller.cart.usecases

import com.drs.dseller.core.data.repository.shopping_cart.CartRepositoryImpl
import com.drs.dseller.core.domain.model.shopping_cart.CartProduct
import com.drs.dseller.core.domain.repository.shopping_cart.CartRepository
import com.drs.dseller.core.domain.usecases.shopping_cart_use_cases.CartRemoveProduct
import junit.framework.TestCase.assertEquals
import org.junit.BeforeClass
import org.junit.Test

class CartRemoveProductShould {

    companion object{
        private lateinit var repo:CartRepository<CartProduct>
        private lateinit var useCase:CartRemoveProduct

        @BeforeClass
        @JvmStatic
        fun init(){
            repo = CartRepositoryImpl()
            useCase = CartRemoveProduct(repo)
            repo.addProductToCart(CartProduct(productId = "5"))
        }
    }

    @Test
    fun`remove product from the cart`(){
        assertEquals(repo.getCartProducts(), listOf(CartProduct(productId = "5")))
        useCase.invoke("5")
        assertEquals(repo.getCartProducts(), listOf<CartProduct>())
    }

    @Test
    fun`throw exception if product is not found in cart`(){
        try{
            useCase.invoke("6")
        }catch(e:Exception){
            assertEquals(e.message, "Product not present in cart")
        }

    }
}