package com.drs.dseller.feature_products.di

import androidx.paging.PagingData
import com.drs.dseller.core.network.ServiceGenerator
import com.drs.dseller.feature_products.data.repository.ProductListRepositoryImpl
import com.drs.dseller.feature_products.data.repository.ProductRepositoryImpl
import com.drs.dseller.feature_products.domain.model.Product
import com.drs.dseller.feature_products.domain.model.ProductDetail
import com.drs.dseller.feature_products.domain.model.ProductSearchFilter
import com.drs.dseller.feature_products.domain.repository.ProductListRepository
import com.drs.dseller.feature_products.domain.repository.ProductRepository
import com.drs.dseller.feature_products.response.ProductResponse
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class ProductsModule {

    @ViewModelScoped
    @Provides
    fun getProductListRepository(
        serviceGenerator: ServiceGenerator
    ):ProductListRepository<PagingData<Product>,ProductSearchFilter>{
        return ProductListRepositoryImpl(serviceGenerator)
    }

    @ViewModelScoped
    @Provides
    fun getProductRepository(
        serviceGenerator: ServiceGenerator
    ):ProductRepository<ProductResponse<ProductDetail>>{
        return ProductRepositoryImpl(serviceGenerator)
    }

}