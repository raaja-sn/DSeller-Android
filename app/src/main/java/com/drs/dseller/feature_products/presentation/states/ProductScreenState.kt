package com.drs.dseller.feature_products.presentation.states

import androidx.paging.PagingData
import com.drs.dseller.feature_products.domain.model.Product
import com.drs.dseller.feature_products.domain.model.ProductDetail
import com.drs.dseller.feature_products.presentation.ProductScreenFilter
import com.drs.dseller.feature_products.presentation.ProductSortOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

data class ProductScreenState (
    val category:String = "",
    val subCategories:List<String> = listOf(),
    val filter:ProductScreenFilter = ProductScreenFilter.ByName(ProductSortOrder.DESCENDING),
    val showFilterOptions:Boolean = false,
    val productsFlow: Flow<PagingData<Product>> = flow {
    }
){
}