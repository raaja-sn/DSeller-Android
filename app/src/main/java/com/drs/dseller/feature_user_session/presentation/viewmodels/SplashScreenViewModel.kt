package com.drs.dseller.feature_user_session.presentation.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drs.dseller.core.domain.usecases.UserSessionUseCases
import com.drs.dseller.feature_user_session.presentation.states.SplashScreenErrorState
import com.drs.dseller.feature_user_session.presentation.states.SplashScreenState
import com.drs.dseller.core.response.SessionResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val userSessionUseCases: UserSessionUseCases
):ViewModel(){

    val splashState = mutableStateOf(SplashScreenState())

    fun onEvent(event:SplashEvent){
        when(event){
            SplashEvent.GetUser -> getCurrentUserSession()


            is SplashEvent.ChangeErrorState -> {
                splashState.value = splashState.value.copy(
                    errorState = SplashScreenErrorState(
                        event.message,
                        event.isError
                    )
                )
            }

            SplashEvent.VerifySession -> {
                splashState.value = splashState.value.copy(
                    shouldVerifySession = true,
                    errorState = SplashScreenErrorState(
                        "",
                        false
                    )
                )
            }
        }
    }

    private fun getCurrentUserSession(){
        if(splashState.value.isVerifyingSession){
            return
        }
        viewModelScope.launch {
            splashState.value = splashState.value.copy(isVerifyingSession = true, shouldVerifySession = false)
            delay(500)
            when(val result = userSessionUseCases.getUser()){
                is SessionResponse.Error -> {
                    splashState.value =
                        splashState.value.copy(
                            isVerifyingSession = false,
                            errorState = SplashScreenErrorState(result.message,true)
                        )
                }
                is SessionResponse.SignInError ->{
                    splashState.value = splashState.value.copy(
                        isVerifyingSession = false,
                        shouldSignIn = true
                    )
                }
                is SessionResponse.Success -> {
                    splashState.value =
                        splashState.value.copy(
                            isVerifyingSession = false,
                            user = result.data
                        )
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        println("Splash View model cleared -----------------")
    }
}

sealed class SplashEvent{
    data object GetUser:SplashEvent()
    data object VerifySession:SplashEvent()
    data class ChangeErrorState(val isError:Boolean,val message:String = ""):SplashEvent()
}