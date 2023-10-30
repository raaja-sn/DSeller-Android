package com.drs.dseller.feature_account.di

import androidx.paging.PagingData
import com.drs.dseller.core.domain.model.AppUser
import com.drs.dseller.core.domain.repository.SessionRepository
import com.drs.dseller.core.network.ServiceGenerator
import com.drs.dseller.core.response.SessionResponse
import com.drs.dseller.feature_account.data.repository.AccountRepositoryImpl
import com.drs.dseller.feature_account.data.repository.UserOrderListRepositoryImpl
import com.drs.dseller.feature_account.data.repository.UserOrderRepositoryImpl
import com.drs.dseller.feature_account.domain.model.AccountUser
import com.drs.dseller.feature_account.domain.model.FullInvoice
import com.drs.dseller.feature_account.domain.model.UserOrder
import com.drs.dseller.feature_account.domain.model.UserOrderFilter
import com.drs.dseller.feature_account.domain.repository.AccountRepository
import com.drs.dseller.feature_account.domain.repository.UserOrderListRepository
import com.drs.dseller.feature_account.domain.repository.UserOrderRepository
import com.drs.dseller.feature_account.response.AccountResponse
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class AccountModule {

    @ViewModelScoped
    @Provides
    fun getAccountRepository(
        sessionRepository: SessionRepository<SessionResponse<AppUser>>,
    ):AccountRepository<AccountUser,AccountResponse<AccountUser>>{
        return AccountRepositoryImpl(sessionRepository)
    }

    @ViewModelScoped
    @Provides
    fun getUserOrderListRepository(
        serviceGenerator: ServiceGenerator
    ):UserOrderListRepository<PagingData<UserOrder>,UserOrderFilter>{
        return UserOrderListRepositoryImpl(serviceGenerator)
    }

    @ViewModelScoped
    @Provides
    fun getUserOrderRepository(
        serviceGenerator: ServiceGenerator
    ):UserOrderRepository<AccountResponse<FullInvoice>>{
        return UserOrderRepositoryImpl(serviceGenerator)
    }

}