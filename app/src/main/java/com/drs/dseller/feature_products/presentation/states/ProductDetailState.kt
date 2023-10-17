package com.drs.dseller.feature_products.presentation.states

import com.drs.dseller.feature_products.domain.model.ProductDetail

data class ProductDetailState(
    val productId:String? = null,
    val productDetail: ProductDetail? = null,
    val productDetailLoading:Boolean = false,
    val productDetailErrorState:ProductDetailErrorState = ProductDetailErrorState()
) {
}