package com.drs.dseller.cart.usecases

import com.drs.dseller.BaseTest
import com.drs.dseller.core.data.repository.shopping_cart.CartRepositoryImpl
import com.drs.dseller.core.domain.model.shopping_cart.CartProduct
import com.drs.dseller.core.domain.repository.shopping_cart.CartRepository
import com.drs.dseller.core.domain.usecases.shopping_cart_use_cases.CartGetProduct
import junit.framework.TestCase.assertEquals
import org.junit.BeforeClass
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runners.MethodSorters

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class CartGetProductShould {



    companion object{
        private lateinit var repo: CartRepository<CartProduct>
        private lateinit var useCase:CartGetProduct
        @BeforeClass
        @JvmStatic
        fun init() {
            repo = CartRepositoryImpl()
            useCase =  CartGetProduct(repo)
        }
    }

    @Test
    fun`get a product from cart`(){
        val product = CartProduct(productId = "5")
        repo.addProductToCart(product)
        assertEquals(useCase.invoke("5"),product)
    }

    @Test
    fun`throw exception when product cannot be found`(){
        try{
            useCase.invoke("6")
        }catch(e:Exception){
            assertEquals(e.message,"No product found for the given product Id")
        }
    }
}