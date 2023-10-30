package com.drs.dseller.feature_products.domain.repository

import kotlinx.coroutines.flow.Flow

/**
 * Repository for Shopper Products list
 *
 * @param P The List of Products
 * @param Q The key or filter for fetching products list
 */
interface ProductListRepository<P : Any,Q:Any> {

    /**
     * Get list of products
     *
     * @param key The key or filter for fetching products list
     * @return The flow to collect list of products
     */
     fun getProductsList(key:Q): Flow<P>

    /**
     * Invalidate the old list and fetch new items matching the current key or filter
     *
     * @param key The key or filter for fetching products list
     */
    fun changeKeyAndInvalidate(key:Q)

}