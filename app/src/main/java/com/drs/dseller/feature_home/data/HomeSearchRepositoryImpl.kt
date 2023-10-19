package com.drs.dseller.feature_home.data

import com.drs.dseller.core.network.ServiceGenerator
import com.drs.dseller.feature_home.domain.model.HomeSearchFilter
import com.drs.dseller.feature_home.domain.repository.HomeSearchRepository
import com.drs.dseller.feature_home.response.HomeResponse
import com.drs.dseller.feature_products.domain.model.Product

class HomeSearchRepositoryImpl(
    private val serviceGenerator: ServiceGenerator
):HomeSearchRepository<HomeSearchFilter, HomeResponse<List<Product>>> {

    override suspend fun searchProduct(key: HomeSearchFilter): HomeResponse<List<Product>> {
        TODO("Not yet implemented")
    }
}