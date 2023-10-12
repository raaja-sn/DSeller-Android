package com.drs.dseller.feature_onboarding.usecases

import com.drs.dseller.BaseTest
import com.drs.dseller.core.domain.model.AppUserWithPassword
import com.drs.dseller.feature_onboarding.domain.repository.OnBoardingRepository
import com.drs.dseller.feature_onboarding.domain.usecases.LoginUser
import com.drs.dseller.feature_onboarding.response.OnBoardingResponse
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class LoginUserShould:BaseTest() {

    private lateinit var repo:OnBoardingRepository<AppUserWithPassword, OnBoardingResponse<Unit>>
    private lateinit var useCase:LoginUser

    @Before
    fun init(){
        repo = mock()
        useCase = LoginUser(repo)
    }

    @Test
    fun `login a user`() = runTest{
        val appUser = AppUserWithPassword("Raaja","test@gmail.com", "34546464")
        whenever(repo.loginUser(appUser)).thenReturn(
            OnBoardingResponse.Success(Unit)
        )
        val result = useCase.invoke(appUser)
        verify(repo,times(1)).loginUser(appUser)
        assertTrue(result is OnBoardingResponse.Success)
    }

    @Test
    fun `send error when user cannot be logged in`() = runTest{
        val appUser = AppUserWithPassword("Raaja","test@gmail.com", "34546464")
        whenever(repo.loginUser(appUser)).thenReturn(
            OnBoardingResponse.Error("Invalid")
        )
        val result = useCase.invoke(appUser)
        assertTrue(result is OnBoardingResponse.Error)
        assertEquals((result as OnBoardingResponse.Error).error,"Invalid")
    }

}