package com.drs.dseller.feature_home.domain.repository

/**
 * Repository to search products from home
 *
 * @param K The key or filter for the search
 * @param R The result of operations. Preferably sealed class with success and error results.
 */
interface HomeSearchRepository<K,R>{

    /**
     * Search for product matching the key or filter
     *
     * @param key The key or filter for searching
     * @return [R] The result of the search
     */
    suspend fun searchProduct(key:K):R

}