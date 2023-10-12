package com.drs.dseller.feature_home.data

import com.drs.dseller.core.network.ErrorResponse
import com.drs.dseller.core.network.ServiceGenerator
import com.drs.dseller.core.utils.NetworkUtils
import com.drs.dseller.feature_home.data.network.CategoryService
import com.drs.dseller.feature_home.domain.model.Category
import com.drs.dseller.feature_home.domain.repository.CategoryRepository
import com.drs.dseller.feature_home.response.HomeResponse

class CategoryRepositoryImpl(
    private val serviceGenerator:ServiceGenerator
):CategoryRepository<HomeResponse<List<Category>>> {

    private val categoryService = serviceGenerator.generateService(CategoryService::class.java)

    override suspend fun getCategories(): HomeResponse<List<Category>> {
        return try{
            val categoryResponse = categoryService.getCategories()
            if(categoryResponse.code() == 200){
                val categories = categoryResponse.body()?.let{
                    it
                }?:listOf()
                HomeResponse.Success(categories)
            }else{
                HomeResponse.Error(
                    NetworkUtils.parseErrorResponse(categoryResponse.errorBody()).message
                )
            }
        }catch(e:Exception){
            HomeResponse.Error(e.message?:"")
        }
    }
}