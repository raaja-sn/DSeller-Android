package com.drs.dseller.feature_products.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.drs.dseller.core.network.ServiceGenerator
import com.drs.dseller.feature_products.data.network.ProductListPagingSource
import com.drs.dseller.feature_products.data.network.ProductPagingKey
import com.drs.dseller.feature_products.data.network.ProductsService
import com.drs.dseller.feature_products.domain.model.Product
import com.drs.dseller.feature_products.domain.model.ProductSearchFilter
import com.drs.dseller.feature_products.domain.repository.ProductListRepository
import kotlinx.coroutines.flow.Flow

class ProductListRepositoryImpl(
    private val serviceGenerator: ServiceGenerator
) : ProductListRepository<PagingData<Product>,ProductSearchFilter>{

    private val productService = serviceGenerator.generateService(ProductsService::class.java)
    private lateinit var productPagingSource:ProductListPagingSource
    override fun getProductsList(key: ProductSearchFilter): Flow<PagingData<Product>> {
        return Pager(
            PagingConfig(pageSize = 30, maxSize = 90)
        ){
            productPagingSource = ProductListPagingSource(productService,ProductPagingKey.fromSearchFilter(key))
            productPagingSource
        }.flow
    }

    override fun changeKeyAndInvalidate(key: ProductSearchFilter) {
        TODO("Not yet implemented")
    }
}