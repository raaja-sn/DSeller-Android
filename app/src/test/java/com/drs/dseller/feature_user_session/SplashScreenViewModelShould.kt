package com.drs.dseller.feature_user_session

import com.drs.dseller.BaseTest
import com.drs.dseller.core.domain.model.AppUser
import com.drs.dseller.core.domain.usecases.UserSessionUseCases
import com.drs.dseller.feature_user_session.presentation.viewmodels.SplashEvent
import com.drs.dseller.feature_user_session.presentation.viewmodels.SplashScreenViewModel
import com.drs.dseller.core.response.SessionResponse
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.spy
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class SplashScreenViewModelShould:BaseTest() {

    private lateinit var vm:SplashScreenViewModel
    private lateinit var userSession: UserSessionUseCases

    @Before
    fun init(){
        userSession = spy(SessionMocks().getUseCases())
        vm = SplashScreenViewModel(userSession)
    }

    @Test
    fun `get the current user session`()= runTest {
        val appuser = AppUser("Raaja","test@gmail.com")
        whenever(userSession.getUser.invoke()).thenReturn(
            SessionResponse.Success(appuser)
        )
        vm.onEvent(SplashEvent.GetUser)
        advanceUntilIdle()
        verify(userSession.getUser,times(1)).invoke()
        assertEquals(vm.splashState.value.user?.fullname,appuser.fullname)
    }

    @Test
    fun `send eroor if current user session is unavailable`()= runTest {
        whenever(userSession.getUser.invoke()).thenReturn(
            SessionResponse.Error("Unavailable")
        )
        vm.onEvent(SplashEvent.GetUser)
        advanceUntilIdle()
        assertTrue(vm.splashState.value.user == null)
        assertEquals(vm.splashState.value.errorState.message,"Unavailable")
    }



}