package com.drs.dseller.feature_products.presentation.states

/**
 * State used to notify UI about cart operation
 */
data class ProductInfoState(
    val info:String = "",
    val showInfoState:Boolean = false
)
