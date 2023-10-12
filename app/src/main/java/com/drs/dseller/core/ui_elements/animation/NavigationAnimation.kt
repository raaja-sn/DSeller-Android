package com.drs.dseller.core.ui_elements.animation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.navigation.NavHostController

fun NavHostController.getEnterAnimation():EnterTransition{
    return slideInHorizontally { it }
}

fun NavHostController.getExitAnimation(): ExitTransition {
    return slideOutHorizontally { it }
}