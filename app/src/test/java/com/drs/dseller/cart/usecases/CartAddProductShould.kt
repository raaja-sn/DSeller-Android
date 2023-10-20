package com.drs.dseller.cart.usecases

import com.drs.dseller.BaseTest
import com.drs.dseller.core.data.repository.shopping_cart.CartRepositoryImpl
import com.drs.dseller.core.domain.model.shopping_cart.CartProduct
import com.drs.dseller.core.domain.repository.shopping_cart.CartRepository
import com.drs.dseller.core.domain.usecases.shopping_cart_use_cases.CartAddProduct
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.BeforeClass
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runners.MethodSorters

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class CartAddProductShould {

    companion object{
        private lateinit var repo:CartRepository<CartProduct>
        private lateinit var useCase:CartAddProduct

        @BeforeClass
        @JvmStatic
        fun init(){
            repo = CartRepositoryImpl()
            useCase = CartAddProduct(repo)
        }
    }



    @Test
    fun`add products to cart`(){
        val p = CartProduct(productId = "5")
        useCase.invoke(p)
        assertEquals(repo.getCartProducts(),listOf<CartProduct>(p))
    }

    @Test
    fun`throw exception when adding a product twice`(){
        try{
            val p = CartProduct(productId = "5")
            useCase.invoke(p)
        }catch(e:Exception){
            assertEquals(e.message,"Product is already in cart")
        }
    }

    @Test
    fun`throw exception when cart is full and product cannot be added`(){
        for(i in 0 until 19){
            useCase.invoke(CartProduct(productId =" ${30+i}"))
        }
        println(repo.getCartProducts())
        try{
            useCase.invoke(CartProduct(productId = "26"))
            useCase.invoke(CartProduct(productId = "27"))
        }catch(e:Exception){
            assertEquals(e.message,"Cart is full")
        }
    }
}