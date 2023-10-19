package com.drs.dseller.feature_products.domain.model

import com.drs.dseller.core.constants.AppConstants.Companion.SORT_ORDER_ASCENDING

/**
 *  Search filter used to search products matching the filter
 */
data class ProductSearchFilter(
    val searchTerm:String ="",
    val category:String = "",
    val subCategory:String = "",
    val sortBy:String = "",
    val sortOrder:String = SORT_ORDER_ASCENDING,
    val pageNumber:Int = 1
){
}

