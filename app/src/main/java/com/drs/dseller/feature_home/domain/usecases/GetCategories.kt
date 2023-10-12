package com.drs.dseller.feature_home.domain.usecases

import com.drs.dseller.feature_home.domain.model.Category
import com.drs.dseller.feature_home.domain.repository.CategoryRepository
import com.drs.dseller.feature_home.response.HomeResponse
import javax.inject.Inject

/**
 * Get Product Categories
 *
 * @param repo Repository object to get product categories
 */
class GetCategories @Inject constructor(
    private val repo:CategoryRepository<HomeResponse<List<Category>>>
) {

    suspend operator fun invoke() =
        repo.getCategories()

}