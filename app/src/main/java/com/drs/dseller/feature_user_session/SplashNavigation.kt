package com.drs.dseller.feature_user_session

import androidx.navigation.NavHostController

fun NavHostController.onBoardUser(){
    popBackStack()
    this.navigate("onboard")
}

fun NavHostController.toHome(){
    popBackStack()
    navigate("home")
}