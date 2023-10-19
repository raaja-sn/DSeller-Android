package com.drs.dseller.feature_products.domain.usecases

import com.drs.dseller.feature_products.domain.model.ProductDetail
import com.drs.dseller.feature_products.domain.repository.ProductRepository
import com.drs.dseller.feature_products.response.ProductResponse
import javax.inject.Inject

/**
 * Get product Details
 *
 * @param repo Repository class for fetching product details
 */
class GetProductDetail @Inject constructor(
    private val repo:ProductRepository<ProductResponse<ProductDetail>>
) {

    suspend operator fun invoke(productId:String) =
        repo.getProductDetail(productId)

}