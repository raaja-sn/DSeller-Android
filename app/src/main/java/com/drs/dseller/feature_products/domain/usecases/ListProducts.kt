package com.drs.dseller.feature_products.domain.usecases

import androidx.paging.PagingData
import com.drs.dseller.feature_products.domain.model.Product
import com.drs.dseller.feature_products.domain.model.ProductSearchFilter
import com.drs.dseller.feature_products.domain.repository.ProductListRepository
import javax.inject.Inject

/**
 * List products
 *
 * @param repo Repository object to get list of products and paginate
 */
class ListProducts @Inject constructor(
    private val repo:ProductListRepository<PagingData<Product>,ProductSearchFilter>
){

    operator fun invoke(searchFilter: ProductSearchFilter) =
        repo.getProductsList(searchFilter)
}