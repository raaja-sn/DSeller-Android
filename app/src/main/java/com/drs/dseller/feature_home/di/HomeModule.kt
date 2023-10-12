package com.drs.dseller.feature_home.di

import com.drs.dseller.core.network.ServiceGenerator
import com.drs.dseller.feature_home.data.CategoryRepositoryImpl
import com.drs.dseller.feature_home.data.OffersRepositoryImpl
import com.drs.dseller.feature_home.domain.model.Category
import com.drs.dseller.feature_home.domain.model.HomeOffer
import com.drs.dseller.feature_home.domain.repository.CategoryRepository
import com.drs.dseller.feature_home.domain.repository.OffersRepository
import com.drs.dseller.feature_home.response.HomeResponse
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class HomeModule {

    @ViewModelScoped
    @Provides
    fun getCategoryRepository(serviceGenerator:ServiceGenerator):CategoryRepository<HomeResponse<List<Category>>>{
        return CategoryRepositoryImpl(serviceGenerator)
    }

    @ViewModelScoped
    @Provides
    fun getOffersRepository():OffersRepository<HomeResponse<List<HomeOffer>>>{
        return OffersRepositoryImpl()
    }

}