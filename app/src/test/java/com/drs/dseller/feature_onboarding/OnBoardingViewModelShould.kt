package com.drs.dseller.feature_onboarding

import com.drs.dseller.BaseTest
import com.drs.dseller.feature_onboarding.domain.usecases.OnBoardingUseCases
import com.drs.dseller.feature_onboarding.presentation.states.LoginUserState
import com.drs.dseller.feature_onboarding.presentation.states.RegisterUserState
import com.drs.dseller.feature_onboarding.presentation.states.getAppUser
import com.drs.dseller.feature_onboarding.presentation.states.toAppUser
import com.drs.dseller.feature_onboarding.presentation.viewmodels.ConfirmCodeEvent
import com.drs.dseller.feature_onboarding.presentation.viewmodels.LoginEvent
import com.drs.dseller.feature_onboarding.presentation.viewmodels.RegisterEvent
import com.drs.dseller.feature_onboarding.presentation.viewmodels.OnBoardingViewModel
import com.drs.dseller.feature_onboarding.response.OnBoardingResponse
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.kotlin.spy
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever


@RunWith(JUnit4::class)
class OnBoardingViewModelShould : BaseTest(){

    private lateinit var vm:OnBoardingViewModel
    private lateinit var onBoarding:OnBoardingUseCases

    @Before
    fun init(){
        onBoarding = spy(OnBoardingMocks().getUseCase())
        vm = OnBoardingViewModel(onBoarding)
    }

    @Test
    fun `register a new user`() = runTest{
        val newUser = RegisterUserState("TestUser","test@gmail.com")
        val appUser = newUser.toAppUser()
        whenever(onBoarding.registerUser.invoke(appUser)).thenReturn(OnBoardingResponse.Success(Unit))
        vm.onRegisterEvent(RegisterEvent.FullNameChanged(newUser.name))
        vm.onRegisterEvent(RegisterEvent.EmailChanged(newUser.email))
        vm.onRegisterEvent(RegisterEvent.RegisterNewUser)
        advanceUntilIdle()
        verify(onBoarding.registerUser, times(1)).invoke(appUser)
        assertTrue(vm.registerUserState.value.isRegistrationComplete)
    }

    @Test
    fun `send error when user cannot be registered`() = runTest{
        val newUser = RegisterUserState("TestUser","test@gmail.com")
        val appUser = newUser.toAppUser()
        whenever(onBoarding.registerUser.invoke(appUser)).thenReturn(OnBoardingResponse.Error("Email Empty"))
        vm.onRegisterEvent(RegisterEvent.FullNameChanged(newUser.name))
        vm.onRegisterEvent(RegisterEvent.EmailChanged(newUser.email))
        vm.onRegisterEvent(RegisterEvent.RegisterNewUser)
        advanceUntilIdle()
        assertEquals(vm.registerUserState.value.errorState.message,"Email Empty")
        assertTrue(!vm.registerUserState.value.isRegistrationComplete)
    }

    @Test
    fun `confirm verification code for registration`() = runTest {
        val code ="15875"
        val state = RegisterUserState("TestUser","test@gmail.com")
        val newUser = state.toAppUser()
        whenever(onBoarding.confirmRegistrationCode.invoke(newUser,code)).thenReturn(
            OnBoardingResponse.Success(Unit)
        )
        vm.onRegisterEvent(RegisterEvent.FullNameChanged(state.name))
        vm.onRegisterEvent(RegisterEvent.EmailChanged(state.email))
        vm.onConfirmCodeEvent(ConfirmCodeEvent.ConfirmCodeChanged(code))
        vm.onConfirmCodeEvent(ConfirmCodeEvent.ConfirmRegistrationCode)
        advanceUntilIdle()
        verify(onBoarding.confirmRegistrationCode, times(1)).invoke(newUser,code)
        assertTrue(vm.confirmUserState.value.isCodeConfirmationComplete)
    }

    @Test
    fun `send error when registration code cannot be confirmed`() = runTest {
        val code ="15875"
        val state = RegisterUserState("TestUser","test@gmail.com")
        val newUser = state.toAppUser()
        whenever(onBoarding.confirmRegistrationCode.invoke(newUser,code)).thenReturn(
            OnBoardingResponse.Error("Code invalid")
        )
        vm.onRegisterEvent(RegisterEvent.FullNameChanged(state.name))
        vm.onRegisterEvent(RegisterEvent.EmailChanged(state.email))
        vm.onConfirmCodeEvent(ConfirmCodeEvent.ConfirmCodeChanged(code))
        vm.onConfirmCodeEvent(ConfirmCodeEvent.ConfirmRegistrationCode)
        advanceUntilIdle()
        assertEquals(vm.confirmUserState.value.errorState.message,"Code invalid")
        assertTrue(!vm.confirmUserState.value.isCodeConfirmationComplete)
    }

    @Test
    fun `log a user`() = runTest {
        val loginState = LoginUserState("test@gmail.com","8546859")
        whenever(onBoarding.loginUser.invoke(loginState.getAppUser())).thenReturn(
            OnBoardingResponse.Success(Unit)
        )
        vm.onLoginEvent(LoginEvent.LoginEmailChanged(loginState.email))
        vm.onLoginEvent(LoginEvent.LoginPasswordChanged(loginState.password))
        vm.onLoginEvent(LoginEvent.LoginUser)
        advanceUntilIdle()
        verify(onBoarding.loginUser, times(1)).invoke(loginState.getAppUser())
        assertTrue(vm.loginState.value.isLoginComplete)
    }

    @Test
    fun `send error when user cannot be logged in`() = runTest {
        val loginState = LoginUserState("test@gmail.com","8546859")
        whenever(onBoarding.loginUser.invoke(loginState.getAppUser())).thenReturn(
            OnBoardingResponse.Error("Password invalid")
        )
        vm.onLoginEvent(LoginEvent.LoginEmailChanged(loginState.email))
        vm.onLoginEvent(LoginEvent.LoginPasswordChanged(loginState.password))
        vm.onLoginEvent(LoginEvent.LoginUser)
        advanceUntilIdle()
        assertEquals(vm.loginState.value.errorState.message,"Password invalid")
        assertTrue(!vm.loginState.value.isLoginComplete)
    }
}