package com.drs.dseller.feature_products.presentation.states

data class ProductDetailErrorState(
    val isError:Boolean = false,
    val message:String = ""
) {
}