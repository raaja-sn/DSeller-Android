@file:OptIn(ExperimentalMaterial3Api::class)

package com.drs.dseller.feature_onboarding.presentation.screens.signup

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.drs.dseller.R
import com.drs.dseller.core.ui_elements.dialog.DefaultBottomDialog
import com.drs.dseller.feature_onboarding.presentation.states.OnBoardingErrorState
import com.drs.dseller.feature_onboarding.presentation.states.RegisterUserState
import com.drs.dseller.feature_onboarding.presentation.states.hasValidEmail
import com.drs.dseller.feature_onboarding.presentation.states.hasValidPassword
import com.drs.dseller.feature_onboarding.presentation.states.hasValidPhoneNumber
import com.drs.dseller.feature_onboarding.presentation.states.hasValidUserName
import com.drs.dseller.feature_onboarding.presentation.toConfirmCode
import com.drs.dseller.feature_onboarding.presentation.toLogIn
import com.drs.dseller.feature_onboarding.presentation.viewmodels.RegisterEvent
import com.drs.dseller.feature_onboarding.presentation.viewmodels.OnBoardingViewModel

@Composable
fun SignUpScreen(
    state:RegisterUserState,
    vm:OnBoardingViewModel,
    navController: NavHostController
){
    val usernameChangeListener = remember{
        { text:String ->
            vm.onRegisterEvent(RegisterEvent.FullNameChanged(text))
        }
    }

    val phoneChangedListener = remember{
        phoneChane@{ text:String ->
            if(text.length > 10) return@phoneChane
            vm.onRegisterEvent(RegisterEvent.PhoneChanged(text))
        }
    }

    val emailChangeListener = remember{
        { text:String ->
            vm.onRegisterEvent(RegisterEvent.EmailChanged(text))
        }
    }

    val passwordChangeListener = remember{
        { text:String ->
            vm.onRegisterEvent(RegisterEvent.PasswordChanged(text))
        }
    }

    val signUpListener:()->Unit = remember {
        signUp@{
            if(state.isRegistering) return@signUp
            vm.onRegisterEvent(RegisterEvent.ValidateAndRegister(true))
        }
    }

    val loginListener = remember {
        {
            goToLoginScreen(navController)
        }
    }

    val confirmCodeListener = remember {
        {
            navController.toConfirmCode()
            vm.onRegisterEvent(RegisterEvent.VerifyCodeScreenDisplayed)
        }
    }

    DisplayConfirmCodeScreen(shouldNavigate = state.isRegistrationComplete,confirmCodeListener)

    Box(
        modifier = Modifier
            .background(Color.White)
    ) {
        SignUpScreenBody(
            state = state,
            usernameChangeListener = usernameChangeListener,
            phoneChangedListener = phoneChangedListener,
            emailChangeListener = emailChangeListener,
            passwordChangeListener = passwordChangeListener,
            signUpListener = signUpListener,
            loginListener = loginListener,
        )

        AnimatedVisibility(
            modifier = Modifier.align(Alignment.BottomCenter),
            visible = state.errorState.isError,
            enter = slideInVertically(initialOffsetY = {it}),
            exit = slideOutVertically(targetOffsetY = {it})
        ) {
            DefaultBottomDialog(
                title = stringResource(id = R.string.error_title_error),
                message = state.errorState.message,
                positiveText = null,
                negativeText = stringResource(id = R.string.dialog_close_text),
                positiveCallback = null,
                negativeCallback = {
                    vm.onRegisterEvent(RegisterEvent.ChangeErrorState(false))
                },
            )
        }
    }

    if(state.shouldVerify){
        ValidateRegistration(state = state, vm = vm)
        vm.onRegisterEvent(RegisterEvent.ValidateAndRegister(false))
    }

}

@Composable
private fun DisplayConfirmCodeScreen(shouldNavigate:Boolean, navigate:()->Unit){
    if(!shouldNavigate) return
    navigate()
}

@Composable
fun ValidateRegistration(
    state:RegisterUserState,
    vm:OnBoardingViewModel
){
    if(!state.hasValidUserName()) {
        vm.onRegisterEvent(
            RegisterEvent.ChangeErrorState(
                true,
                stringResource(id = R.string.signup_error_username)
            )
        )
        return
    }
    if(!state.hasValidPhoneNumber()) {
        vm.onRegisterEvent(
            RegisterEvent.ChangeErrorState(
                true,
                stringResource(id = R.string.signup_error_phone)
            )
        )
        return
    }
    if(!state.hasValidEmail()) {
        vm.onRegisterEvent(
            RegisterEvent.ChangeErrorState(
                true,
                stringResource(id = R.string.signup_error_email)
            )
        )
        return
    }
    if(!state.hasValidPassword()) {
        vm.onRegisterEvent(
            RegisterEvent.ChangeErrorState(
                true,
                stringResource(id = R.string.signup_error_password)
            )
        )
        return
    }
    vm.onRegisterEvent(RegisterEvent.RegisterNewUser)
}

private fun goToLoginScreen(navController: NavHostController){
    navController.toLogIn()
}

@Preview
@Composable
fun SignUpPreview(){
    SignUpScreenBody(state =RegisterUserState(
        "Name",
        "test@gmail.com",
        errorState = OnBoardingErrorState("Some error",true)
    ), {},{},{},{},{},{})
}
