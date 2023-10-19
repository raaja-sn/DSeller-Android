package com.drs.dseller.feature_products.data.network

import com.drs.dseller.feature_products.domain.model.Product
import com.drs.dseller.feature_products.domain.model.ProductDetail
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductsService {

    @GET("product/{productId}")
    suspend fun getProductDetail(
        @Path("productId") productId:String,
    ):Response<ProductDetail>


    @GET("listproducts/")
    suspend fun listProducts(
        @Query("category") category:String,
        @Query("sortBy") sortBy:String,
        @Query("sortOrder") sortOrder:String,
        @Query("pageNumber") pageNumber:Int,
        @Query("pageSize") pageSize:Int
    ):Response<List<Product>>
}