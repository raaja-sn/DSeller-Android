package com.drs.dseller.core.di

import com.drs.dseller.core.domain.model.AppUser
import com.drs.dseller.core.data.repository.AuthRepositoryImpl
import com.drs.dseller.core.data.repository.SessionRepositoryImpl
import com.drs.dseller.core.domain.repository.AuthRepository
import com.drs.dseller.core.domain.repository.SessionRepository
import com.drs.dseller.core.response.SessionResponse
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UserSessionModule {

    @Singleton
    @Provides
    fun createSessionRepository(): SessionRepository<SessionResponse<AppUser>> {
        return SessionRepositoryImpl()
    }

    @Singleton
    @Provides
    fun createAuthRepository(): AuthRepository {
        return AuthRepositoryImpl()
    }

}