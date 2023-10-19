package com.drs.dseller.core.data.repository.shopping_cart

import com.drs.dseller.core.domain.model.shopping_cart.CartProduct
import com.drs.dseller.core.domain.repository.shopping_cart.CartRepository
import kotlin.IllegalStateException

class CartRepositoryImpl:CartRepository<CartProduct> {

    private val cart = mutableMapOf<String,CartProduct>()

    override fun hasProductInCart(productId: String): Boolean {
        return cart[productId] != null
    }

    override fun isCartFull(): Boolean = cart.size == 20

    override fun addProductToCart(product: CartProduct) {
        if(cart[product.productId] != null) throw IllegalStateException("Product is already in cart")
        if(isCartFull()) throw IllegalStateException("Cart is full")
        cart[product.productId] = product
    }

    override fun removeProductFromCart(productId: String) {
        if(cart[productId] == null) throw IllegalStateException("Product not present in cart")
        cart.remove(productId)
    }

    override fun getProductFromCart(productId: String): CartProduct {
        return cart[productId]
            ?: throw IllegalArgumentException("No product found for the given product Id")
    }

    override fun incrementProductQuantity(quantity: Int, productId: String) {
        cart[productId]?.let{
            val newQuantity = if(it.quantity + quantity > 10){
                10
            }else{
                it.quantity + quantity
            }
            val cProduct = it.copy(
                quantity = newQuantity
            )
            cart[productId] = cProduct
        }
    }

    override fun decrementProductQuantity(quantity: Int, productId: String) {
        cart[productId]?.let{
            val newQuantity = if(it.quantity - quantity < 0){
                0
            }else{
                it.quantity - quantity
            }
            val cProduct = it.copy(
                quantity = newQuantity
            )
            cart[productId] = cProduct
        }
    }

    override fun getCartProducts(): List<CartProduct> {
        return cart.values.toList()
    }
}