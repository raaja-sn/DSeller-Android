package com.drs.dseller.feature_products.presentation.states

import com.drs.dseller.core.domain.model.shopping_cart.CartProduct
import com.drs.dseller.feature_products.domain.model.ProductDetail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class ProductDetailState(
    val productId:String? = null,
    val productDetail: ProductDetail? = null,
    val productDetailLoading:Boolean = false,
    val productDetailErrorState:ProductDetailErrorState = ProductDetailErrorState(),
    val productInfo:ProductInfoState = ProductInfoState(),
    val cartFlow: StateFlow<List<CartProduct>> = MutableStateFlow(emptyList())
) {
}