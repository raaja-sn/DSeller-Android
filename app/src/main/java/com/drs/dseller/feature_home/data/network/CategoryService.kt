package com.drs.dseller.feature_home.data.network

import com.drs.dseller.feature_home.domain.model.Category
import retrofit2.Response
import retrofit2.http.GET

interface CategoryService {

    @GET("category")
    suspend fun getCategories():Response<List<Category>>

}