package com.drs.dseller.feature_onboarding.usecases

import com.drs.dseller.BaseTest
import com.drs.dseller.core.domain.model.AppUserWithPassword
import com.drs.dseller.feature_onboarding.domain.repository.OnBoardingWithConfirmCodeRepository
import com.drs.dseller.feature_onboarding.domain.usecases.RegisterUser
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

class RegisterUserShould:BaseTest() {

    private lateinit var repo:OnBoardingWithConfirmCodeRepository<AppUserWithPassword, OnBoardingResponse<Unit>>
    private lateinit var userCase : RegisterUser

    @Before
    fun init(){
        repo = mock()
        userCase = RegisterUser(repo)
    }

    @Test
    fun `register a new user`() = runTest{
        val appuser = AppUserWithPassword("Raaja","test@gmail.com","354676")
        whenever(repo.registerUser(appuser)).
                thenReturn(OnBoardingResponse.Success(Unit))
        val result = userCase.invoke(appuser)
        verify(repo, times(1)).registerUser(appuser)
        assertTrue(result is OnBoardingResponse.Success)
    }

    @Test
    fun `send error when user cannot be registered`() = runTest{
        val appuser = AppUserWithPassword("Raaja","test@gmail.com","354676")
        whenever(repo.registerUser(appuser)).
        thenReturn(OnBoardingResponse.Error("Invalid"))
        val result = userCase.invoke(appuser)
        assertTrue(result is OnBoardingResponse.Error)
        assertEquals((result as OnBoardingResponse.Error).error,"Invalid")
    }

}