package com.drs.dseller.feature_home.domain.usecases

import com.drs.dseller.feature_home.domain.model.HomeSearchFilter
import com.drs.dseller.feature_products.domain.model.Product
import com.drs.dseller.feature_home.domain.repository.HomeSearchRepository
import com.drs.dseller.feature_home.response.HomeResponse
import javax.inject.Inject

/**
 * Search product
 *
 * @param repo Repository object to search products
 */
class HomeSearchProduct @Inject constructor(
    private val repo: HomeSearchRepository<HomeSearchFilter, HomeResponse<List<Product>>>
) {

    suspend operator fun invoke(productSearchFilter: HomeSearchFilter) =
        repo.searchProduct(productSearchFilter)

}