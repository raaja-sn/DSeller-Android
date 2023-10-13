package com.drs.dseller.feature_products.data.network

import com.drs.dseller.core.constants.AppConstants
import com.drs.dseller.feature_products.domain.model.ProductSearchFilter

/**
 *  Object used as Key for the Product Paging Source
 */
data class ProductPagingKey(
    val filter:ProductSearchFilter,
    val isPrepend:Boolean = false
) {

    companion object{

        fun fromSearchFilter(searchFilter: ProductSearchFilter):ProductPagingKey{
            return ProductPagingKey(
                searchFilter,
                false
            )
        }
    }

}