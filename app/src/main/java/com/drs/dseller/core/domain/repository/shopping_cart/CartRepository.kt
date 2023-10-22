package com.drs.dseller.core.domain.repository.shopping_cart

import kotlinx.coroutines.flow.StateFlow


/**
 * Repository interface fo the shopping cart
 * @param P The cart product
 */
interface CartRepository<P> {

    /**
     * Checks if the product is present in the cart
     *
     * @param productId the product id
     * @return true if the product is present in cart
     */
    fun hasProductInCart(productId:String):Boolean

    /**
     * Returns true if cart is full
     */
    fun isCartFull():Boolean

    /**
     * Add the product to cart. Always check if the product is present in cart using [hasProductInCart] method, and if the cart is full using [isCartFull] method,
     * before adding the product.
     *
     * @param product Product to add
     * @throws IllegalStateException when the cart is full and product cannot be added and when the same product is added twice
     */
    fun addProductToCart(product:P)

    /**
     * Remove product from cart
     *
     * @param productId Product Id of the product. Always check if the product is present in cart using [hasProductInCart] method, before removing the product.
     * @throws IllegalStateException when the product is not present in cart
     */
    fun removeProductFromCart(productId:String)

    /**
     * Get product from cart
     *
     * @param productId Product Id of the product
     * @throws IllegalStateException when the product is not present in cart
     */
    fun getProductFromCart(productId:String):P

    /**
     * Increase the quantity of the product in the cart
     *
     * @param quantity The no of quantity to increment
     * @param productId The product Id of the product
     */
    fun incrementProductQuantity(quantity:Int, productId:String)

    /**
     * Decrease the quantity of the product in the cart
     *
     * @param quantity The no of quantity to decrement
     * @param productId The product Id of the product
     */
    fun decrementProductQuantity(quantity:Int, productId:String)

    /**
     * Get all the products from the cart
     *
     * @return List of products [P]
     */
    fun getCartProducts():StateFlow<List<P>>

    /**
     * Clear the shopping cart
     */
    fun clearCart()

}