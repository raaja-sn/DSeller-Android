package com.drs.dseller.core.domain.usecases.shopping_cart_use_cases

import javax.inject.Inject


class ShoppingCartUseCases @Inject constructor(
    val hasProduct: CartHasProduct,
    val cartFull: CartFull,
    val addProduct: CartAddProduct,
    val removeProduct: CartRemoveProduct,
    val incrementQuantity: CartIncrementQuantity,
    val decrementQuantity: CartDecrementQuantity,
    val getProduct: CartGetProduct,
    val getAllProducts: CartGetAllProducts,
    val clearCart: ClearCart
) {
}