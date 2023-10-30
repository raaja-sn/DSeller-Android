package com.drs.dseller.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController

@Composable
inline fun <reified V: ViewModel> NavBackStackEntry.getViewModel(navController: NavHostController):V{
    val parentRoute = destination.parent?.route?:return hiltViewModel()
    val pEntry = remember(this){
        navController.getBackStackEntry(parentRoute)
    }
    return hiltViewModel(pEntry)
}