package com.drs.dseller.feature_products.data.repository

import com.drs.dseller.core.network.ServiceGenerator
import com.drs.dseller.core.utils.NetworkUtils
import com.drs.dseller.feature_products.data.network.ProductsService
import com.drs.dseller.feature_products.domain.model.ProductDetail
import com.drs.dseller.feature_products.domain.repository.ProductRepository
import com.drs.dseller.feature_products.response.ProductResponse

class ProductRepositoryImpl(
    private val serviceGenerator: ServiceGenerator
) : ProductRepository<ProductResponse<ProductDetail>> {

    private val productsService = serviceGenerator.generateService(ProductsService::class.java)

    override suspend fun getProductDetail(productId: String): ProductResponse<ProductDetail> {
        return try{
            val resp = productsService.getProductDetail(productId)
            if(resp.code() == 200){
                ProductResponse.Success(resp.body()?:ProductDetail())
            }else{
                throw (Exception(
                    NetworkUtils.parseErrorResponse(resp.errorBody()).message
                ))
            }
        }catch(e:Exception){
            ProductResponse.Error(e.message?:"")
        }
    }
}