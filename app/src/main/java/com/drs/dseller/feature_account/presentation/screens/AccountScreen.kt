@file:OptIn(ExperimentalMaterial3Api::class)

package com.drs.dseller.feature_account.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.drs.dseller.R
import com.drs.dseller.core.ui_elements.appbar.AppBottomNavigationBar
import com.drs.dseller.core.ui_elements.appbar.DefaultAppBar
import com.drs.dseller.feature_account.presentation.UserAccountEvent
import com.drs.dseller.feature_account.presentation.UserAccountViewModel
import com.drs.dseller.feature_account.presentation.UserOrdersEvent
import com.drs.dseller.feature_account.presentation.screens.components.AccountScreenBody
import com.drs.dseller.feature_account.presentation.screens.components.UserOrdersBody
import com.drs.dseller.feature_account.presentation.states.AccountBottomNavigationBarState
import com.drs.dseller.feature_account.presentation.states.AccountScreenState
import com.drs.dseller.feature_account.presentation.toUserOrders
import com.drs.dseller.feature_onboarding.presentation.toLogInAfterLogout

@Composable
fun AccountScreen(
    lifecycleOwner:LifecycleOwner = LocalLifecycleOwner.current,
    state:AccountScreenState,
    bottomNavState:AccountBottomNavigationBarState,
    vm:UserAccountViewModel,
    navHostController: NavHostController
){

    DisposableEffect(key1 = lifecycleOwner){
        val observer = object: DefaultLifecycleObserver {
            override fun onResume(owner: LifecycleOwner) {
                super.onResume(owner)
                if(state.user != null) return
                vm.onUserAccountEvent(UserAccountEvent.GetAccountUser)
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    val myOrdersClicked = remember {
        {
            navHostController.toUserOrders(navHostController)
        }
    }

    val myDetailsClicked = remember {
        {

        }
    }

    val aboutClicked = remember {
        {

        }
    }

    val logout = remember {
        {
            vm.onUserAccountEvent(UserAccountEvent.LogOutUser)
        }
    }

    val displayLogin = remember{
        {
            navHostController.toLogInAfterLogout()
        }
    }

    val cartItems = bottomNavState.cartFlow.collectAsStateWithLifecycle().value
    val appScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = androidx.compose.ui.Modifier
            .nestedScroll(appScrollBehavior.nestedScrollConnection),
        bottomBar = {
            AppBottomNavigationBar(
                cartQuantity = cartItems.size,
                navHostController = navHostController
            )
        },
        containerColor = Color.White
    ) { innerPadding ->

        AccountScreenBody(
            innerPadding = innerPadding,
            state = state,
            myDetailClicked = myDetailsClicked,
            myOrdersClicked = myOrdersClicked,
            aboutClicked = aboutClicked,
            logoutClicked = logout
            )
    }

    DisplayLogin(state.logoutComplete,displayLogin)
}

@Composable
private fun DisplayLogin(
    logoutComplete:Boolean,
    displayLogin:() -> Unit
){
    if(!logoutComplete) return
    displayLogin()
}