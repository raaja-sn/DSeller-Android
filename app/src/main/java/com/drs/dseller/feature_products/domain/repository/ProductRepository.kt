package com.drs.dseller.feature_products.domain.repository

/**
 * Repository for Shopper Product
 *
 * @param R The result of operations. Preferably sealed class with success and error results.
 */
interface ProductRepository<R> {

    /**
     *  Get the product's detail
     *
     *  @param productId product Id to of the product
     *
     *  @return [R] The detail of the product
     */
    suspend fun getProductDetail(productId:String):R


}