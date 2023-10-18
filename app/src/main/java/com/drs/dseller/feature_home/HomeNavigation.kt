package com.drs.dseller.feature_home

import androidx.navigation.NavHostController

fun NavHostController.toHome(){
    popBackStack()
    navigate("home")
}