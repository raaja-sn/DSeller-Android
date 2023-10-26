package com.drs.dseller.feature_onboarding.presentation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.drs.dseller.core.navigation.getViewModel
import com.drs.dseller.feature_home.ROUTE_HOME
import com.drs.dseller.feature_onboarding.presentation.screens.confirm_code.ConfirmationCodeScreen
import com.drs.dseller.feature_onboarding.presentation.screens.login.SignInScreen
import com.drs.dseller.feature_onboarding.presentation.screens.signup.SignUpScreen
import com.drs.dseller.feature_onboarding.presentation.viewmodels.OnBoardingViewModel


fun NavHostController.toSignUp(){
    navigate(DESTINATION_SIGNUP)
}

fun NavHostController.toConfirmCode(){
    navigate(DESTINATION_VERIFY_CODE)
}

fun NavHostController.toLogIn(){
    popBackStack()
}

fun NavHostController.toLogInAfterLogout(){
    navigate(DESTINATION_LOGIN){
        popUpTo(ROUTE_HOME)
    }
}

fun NavHostController.toSplash(){
    popBackStack(ROUTE_ONBOARD,true)
    navigate("splash")
}

fun NavGraphBuilder.Onboard(navController:NavHostController){
    navigation(DESTINATION_LOGIN, ROUTE_ONBOARD){
        composable(DESTINATION_LOGIN){
            val vm: OnBoardingViewModel = it.getViewModel(navController = navController)

            SignInScreen(state = vm.loginState.value, vm = vm, navController = navController )
        }
        composable(DESTINATION_SIGNUP){
            val vm: OnBoardingViewModel = it.getViewModel(navController = navController)

            SignUpScreen(state = vm.registerUserState.value, vm = vm,navController)
        }
        composable(DESTINATION_VERIFY_CODE){
            val vm: OnBoardingViewModel = it.getViewModel(navController = navController)

            ConfirmationCodeScreen(state = vm.confirmUserState.value, vm = vm,navController)
        }
    }
}

const val ROUTE_ONBOARD = "onboard"
const val DESTINATION_LOGIN = "login"
const val DESTINATION_SIGNUP = "sign_up"
const val DESTINATION_VERIFY_CODE = "verify_code"