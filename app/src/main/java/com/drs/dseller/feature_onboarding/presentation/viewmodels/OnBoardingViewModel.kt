package com.drs.dseller.feature_onboarding.presentation.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drs.dseller.feature_onboarding.domain.usecases.OnBoardingUseCases
import com.drs.dseller.feature_onboarding.presentation.states.ConfirmUserState
import com.drs.dseller.feature_onboarding.presentation.states.LoginUserState
import com.drs.dseller.feature_onboarding.presentation.states.OnBoardingErrorState
import com.drs.dseller.feature_onboarding.presentation.states.RegisterUserState
import com.drs.dseller.feature_onboarding.presentation.states.getAppUser
import com.drs.dseller.feature_onboarding.presentation.states.toAppUser
import com.drs.dseller.feature_onboarding.response.OnBoardingResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val onBoarding:OnBoardingUseCases
):ViewModel(){

    private val _registerUserState = mutableStateOf(RegisterUserState())
    val registerUserState: State<RegisterUserState> = _registerUserState

    private val _loginState = mutableStateOf(LoginUserState())
    val loginState:State<LoginUserState> = _loginState

    private val _confirmUserState = mutableStateOf(ConfirmUserState())
    val confirmUserState:State<ConfirmUserState> = _confirmUserState

    fun onRegisterEvent(event:RegisterEvent){
        when(event){
            RegisterEvent.RegisterNewUser -> registerUser()
            is RegisterEvent.EmailChanged -> {
                _registerUserState.value = _registerUserState.value.copy(email = event.email)
            }
            is RegisterEvent.FullNameChanged -> {
                _registerUserState.value = _registerUserState.value.copy(name = event.name)
            }
            is RegisterEvent.PasswordChanged -> {
                _registerUserState.value = _registerUserState.value.copy(password = event.password)
            }
            is RegisterEvent.PhoneChanged ->{
                _registerUserState.value = _registerUserState.value.copy(phoneNumber = event.phoneNumber)
            }

            is RegisterEvent.ValidateAndRegister -> {
                _registerUserState.value = _registerUserState.value.copy(shouldVerify = event.shouldVerify)
            }

            is RegisterEvent.VerifyCodeScreenDisplayed -> {
                _registerUserState.value = _registerUserState.value.copy(isRegistrationComplete = false)
            }

            is RegisterEvent.ChangeErrorState ->{
                _registerUserState.value = _registerUserState.value.copy(
                    errorState = OnBoardingErrorState(event.message,event.isError)
                )
            }

        }
    }

    fun onConfirmCodeEvent(event:ConfirmCodeEvent){
        when(event){
            is ConfirmCodeEvent.ConfirmCodeChanged -> {
                _confirmUserState.value = _confirmUserState.value.copy(confirmationCode = event.code)
            }
            is ConfirmCodeEvent.ChangeErrorState ->{
                _confirmUserState.value = _confirmUserState.value.copy(
                    errorState = OnBoardingErrorState(event.message,event.isError)
                )
            }
            ConfirmCodeEvent.ConfirmRegistrationCode -> confirmCode()
        }
    }

    fun onLoginEvent(event:LoginEvent){
        when(event){
            is LoginEvent.ChangeErrorState -> {
                _loginState.value = _loginState.value.copy(
                    errorState = OnBoardingErrorState(event.message,event.isError)
                )
            }
            is LoginEvent.LoginEmailChanged -> {
                _loginState.value = _loginState.value.copy(email = event.email)
            }
            is LoginEvent.LoginPasswordChanged -> {
                _loginState.value = _loginState.value.copy(password = event.password)
            }
            LoginEvent.LoginUser -> login()
        }
    }

    private fun registerUser(){
        viewModelScope.launch {
            _registerUserState.value = _registerUserState.value.copy(isRegistering = true)
            when(val result = onBoarding.registerUser(_registerUserState.value.toAppUser())){
                is OnBoardingResponse.Error -> {
                    _registerUserState.value = _registerUserState.value.copy(
                        isRegistering = false,
                        errorState = OnBoardingErrorState(result.error,true)
                    )
                }
                is OnBoardingResponse.Success -> {
                    _registerUserState.value = _registerUserState.value.copy(
                        isRegistering = false,
                        isRegistrationComplete = true
                    )
                }
            }
        }
    }



    private fun confirmCode(){
        viewModelScope.launch {
            _confirmUserState.value = _confirmUserState.value.copy(isConfirming = true)
            when(val result = onBoarding.confirmRegistrationCode(
                _registerUserState.value.toAppUser(),
                _confirmUserState.value.confirmationCode
            )){
                is OnBoardingResponse.Error ->{
                    _confirmUserState.value = _confirmUserState.value.copy(
                        isConfirming = false,
                        errorState = OnBoardingErrorState(result.error,true)
                    )
                }
                is OnBoardingResponse.Success ->{
                    _confirmUserState.value = _confirmUserState.value.copy(
                        isConfirming = false,
                        isCodeConfirmationComplete = true
                    )
                }
            }
        }
    }

    private fun login(){
        viewModelScope.launch {
            _loginState.value = _loginState.value.copy(isLoggingIn = true)
            when(val result = onBoarding.loginUser(_loginState.value.getAppUser())){
                is OnBoardingResponse.Error -> {
                    _loginState.value = _loginState.value.copy(
                        isLoggingIn = false,
                        errorState = OnBoardingErrorState(message = result.error,true)
                    )
                }
                is OnBoardingResponse.Success -> {
                    _loginState.value = _loginState.value.copy(
                        isLoggingIn = false,
                        isLoginComplete = true
                    )
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        println("Cleared OnBoarding --------------------")
    }
}

sealed class RegisterEvent{

    data object RegisterNewUser :RegisterEvent()

    data class FullNameChanged(val name:String):RegisterEvent()
    data class PhoneChanged(val phoneNumber:String):RegisterEvent()
    data class EmailChanged(val email:String):RegisterEvent()
    data class PasswordChanged(val password:String):RegisterEvent()
    data class ValidateAndRegister(val shouldVerify:Boolean):RegisterEvent()

    data object VerifyCodeScreenDisplayed:RegisterEvent()

    data class ChangeErrorState(val isError:Boolean, val message:String = ""):RegisterEvent()
}

sealed class ConfirmCodeEvent{
    data class ConfirmCodeChanged(val code:String):ConfirmCodeEvent()
    data object ConfirmRegistrationCode:ConfirmCodeEvent()
    data class ChangeErrorState(val isError:Boolean, val message:String = ""):ConfirmCodeEvent()
}

sealed class LoginEvent{
    data object LoginUser:LoginEvent()

    data class LoginEmailChanged(val email:String):LoginEvent()
    data class LoginPasswordChanged(val password:String):LoginEvent()
    data class ChangeErrorState(val isError:Boolean, val message:String = ""):LoginEvent()
}

