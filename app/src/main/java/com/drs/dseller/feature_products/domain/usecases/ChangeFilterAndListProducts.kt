package com.drs.dseller.feature_products.domain.usecases

import androidx.paging.PagingData
import com.drs.dseller.feature_products.domain.model.Product
import com.drs.dseller.feature_products.domain.model.ProductSearchFilter
import com.drs.dseller.feature_products.domain.repository.ProductListRepository
import javax.inject.Inject

/**
 * List products with the updated filter and invalidate the old one
 *
 * @param repo Repository object to get invalidated list
 */
class ChangeFilterAndListProducts @Inject constructor(
    private val repo:ProductListRepository<PagingData<Product>,ProductSearchFilter>
){

    operator fun invoke(searchFilter: ProductSearchFilter) =
        repo.changeKeyAndInvalidate(searchFilter)

}