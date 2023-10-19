package com.drs.dseller.feature_orders.di

import com.drs.dseller.core.domain.model.AppUser
import com.drs.dseller.core.domain.repository.SessionRepository
import com.drs.dseller.core.network.ServiceGenerator
import com.drs.dseller.core.response.SessionResponse
import com.drs.dseller.feature_orders.data.repository.ShoppingOrderRepositoryImpl
import com.drs.dseller.feature_orders.domain.repository.ShoppingOrderRepository
import com.drs.dseller.feature_orders.response.ShoppingOrderResponse
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class OrdersModule {

    @ViewModelScoped
    @Provides
    fun getOrderRepository(
        userSessionRepository: SessionRepository<SessionResponse<AppUser>>,
        serviceGenerator: ServiceGenerator
    ):ShoppingOrderRepository<ShoppingOrderResponse<Unit>>{
        return ShoppingOrderRepositoryImpl(userSessionRepository,serviceGenerator)
    }
}