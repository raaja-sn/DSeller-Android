package com.drs.dseller.feature_onboarding.presentation.screens.login

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.drs.dseller.R
import com.drs.dseller.core.ui_elements.dialog.DefaultBottomDialog
import com.drs.dseller.feature_onboarding.presentation.states.LoginUserState
import com.drs.dseller.feature_onboarding.presentation.toSignUp
import com.drs.dseller.feature_onboarding.presentation.toSplash
import com.drs.dseller.feature_onboarding.presentation.viewmodels.LoginEvent
import com.drs.dseller.feature_onboarding.presentation.viewmodels.OnBoardingViewModel

@Composable
fun SignInScreen(
    state:LoginUserState,
    vm:OnBoardingViewModel,
    navController:NavHostController
){

    val emailChangeListener = remember{
        {text:String ->
            vm.onLoginEvent(LoginEvent.LoginEmailChanged(text))
        }
    }

    val passwordChangedListener = remember{
        {text:String ->
            vm.onLoginEvent(LoginEvent.LoginPasswordChanged(text))
        }
    }

    val loginListener = remember{
        login@{
            if(state.isLoggingIn) return@login
            vm.onLoginEvent(LoginEvent.LoginUser)
        }

    }
    val signUpListener = remember{
        signup@{
            if(state.isLoggingIn) return@signup
            navController.toSignUp()
        }
    }

    val navigateToSplash = remember{
        {
            navController.toSplash()
        }
    }

    Box(
        modifier = Modifier
            .background(Color.White)
    ) {
        SignInBody(
            state = state,
            emailChangeListener = emailChangeListener,
            passwordChangeListener = passwordChangedListener,
            loginListener = loginListener,
            signUpListener = signUpListener
        )

        AnimatedVisibility(
            modifier = Modifier.align(Alignment.BottomCenter),
            visible = state.errorState.isError,
            enter = slideInVertically{it},
            exit = slideOutVertically{it }
        ) {
            DefaultBottomDialog(
                title = stringResource(id = R.string.error_title_default),
                message = state.errorState.message,
                positiveText = null,
                negativeText = stringResource(id = R.string.dialog_close_text),
                positiveCallback = null,
                negativeCallback = {
                    vm.onLoginEvent(LoginEvent.ChangeErrorState(false,""))
                }
            )
        }

    }

    DisplaySplashScreen(shouldDisplay = state.isLoginComplete,navigateToSplash)

}

@Composable
private fun DisplaySplashScreen(shouldDisplay:Boolean, navigate:()->Unit){
    if(!shouldDisplay)return
    navigate()
}

