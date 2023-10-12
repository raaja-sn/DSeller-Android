package com.drs.dseller.feature_home.domain.repository

/**
 * Repository for product categories and sub categories.
 *
 * @param R The result of operations. Preferably sealed class with success and error results.
 */
interface CategoryRepository<R> {

    /**
     * Get product categories
     *
     * @return [R] The result of the get categories event
     */
    suspend fun getCategories():R

}