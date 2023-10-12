package com.drs.dseller.feature_onboarding.usecases

import com.drs.dseller.BaseTest
import com.drs.dseller.core.domain.model.AppUserWithPassword
import com.drs.dseller.feature_onboarding.domain.repository.OnBoardingWithConfirmCodeRepository
import com.drs.dseller.feature_onboarding.domain.usecases.ConfirmRegistrationCode
import com.drs.dseller.feature_onboarding.presentation.states.RegisterUserState
import com.drs.dseller.feature_onboarding.presentation.states.toAppUser
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


class ConfirmRegistrationCodeShould : BaseTest() {

    private lateinit var repo: OnBoardingWithConfirmCodeRepository<AppUserWithPassword, OnBoardingResponse<Unit>>
    private lateinit var useCase:ConfirmRegistrationCode

    @Before
    fun init(){
        repo = mock()
        useCase = ConfirmRegistrationCode(repo)
    }

    @Test
    fun `confirm code for new registration`() = runTest{
        val code ="4556"
        val newUser = RegisterUserState("TestUser","test@gmail.com").toAppUser()
        whenever(repo.confirmCodeForRegistration(newUser,code)).thenReturn(
            OnBoardingResponse.Success(Unit)
        )
        val r = useCase.invoke(newUser,code)
        verify(repo, times(1)).confirmCodeForRegistration(newUser,code)
        assertTrue(r is OnBoardingResponse.Success)
    }

    @Test
    fun `send error if code cannot be confirmed`() = runTest{
        val code ="4556"
        val newUser = RegisterUserState("TestUser","test@gmail.com").toAppUser()
        whenever(repo.confirmCodeForRegistration(newUser,code)).thenReturn(
            OnBoardingResponse.Error("Invalid")
        )
        val r = useCase.invoke(newUser,code)
        assertEquals((r as OnBoardingResponse.Error).error,"Invalid")
    }

}