package com.drs.dseller.feature_products.domain.usecases

import javax.inject.Inject

data class ProductsUseCases @Inject constructor(
    val getProductDetail: GetProductDetail,
    val listProducts: ListProducts,
    val changeFilterAndListProducts: ChangeFilterAndListProducts
){
}