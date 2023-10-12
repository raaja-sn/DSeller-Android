package com.drs.dseller.feature_onboarding.presentation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.drs.dseller.feature_onboarding.presentation.screens.confirm_code.ConfirmationCodeScreen
import com.drs.dseller.feature_onboarding.presentation.screens.login.SignInScreen
import com.drs.dseller.feature_onboarding.presentation.screens.signup.SignUpScreen
import com.drs.dseller.feature_onboarding.presentation.viewmodels.OnBoardingViewModel
import com.drs.dseller.getViewModel


fun NavHostController.toSignUp(){
    navigate("sign_up")
}

fun NavHostController.toConfirmCode(){
    navigate("verify_code")
}

fun NavHostController.toLogIn(){
    popBackStack()
}

fun NavHostController.toSplash(){
    popBackStack("onboard",true)
    navigate("splash")
}

fun NavGraphBuilder.toOnboard(navController:NavHostController){
    navigation("login","onboard"){
        composable("login"){
            val vm: OnBoardingViewModel = it.getViewModel(navController = navController)

            SignInScreen(state = vm.loginState.value, vm = vm, navController = navController )
        }
        composable("sign_up"){
            val vm: OnBoardingViewModel = it.getViewModel(navController = navController)

            SignUpScreen(state = vm.registerUserState.value, vm = vm,navController)
        }
        composable("verify_code"){
            val vm: OnBoardingViewModel = it.getViewModel(navController = navController)

            ConfirmationCodeScreen(state = vm.confirmUserState.value, vm = vm,navController)
        }
    }
}