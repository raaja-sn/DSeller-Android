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
import androidx.paging.compose.collectAsLazyPagingItems
import com.drs.dseller.R
import com.drs.dseller.core.ui_elements.appbar.AppBottomNavigationBar
import com.drs.dseller.core.ui_elements.appbar.DefaultAppBar
import com.drs.dseller.feature_account.presentation.UserAccountViewModel
import com.drs.dseller.feature_account.presentation.UserOrdersEvent
import com.drs.dseller.feature_account.presentation.screens.components.UserOrdersBody
import com.drs.dseller.feature_account.presentation.states.AccountBottomNavigationBarState
import com.drs.dseller.feature_account.presentation.states.UserOrdersState

@Composable
fun UserOrdersScreen(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    state:UserOrdersState,
    bottomNavState:AccountBottomNavigationBarState,
    vm:UserAccountViewModel,
    navHostController: NavHostController
){

    val ordersList = state.userOrders.collectAsLazyPagingItems()

    DisposableEffect(key1 = lifecycleOwner){
        val observer = object:DefaultLifecycleObserver{
            override fun onResume(owner: LifecycleOwner) {
                super.onResume(owner)
                if(ordersList.itemCount == 0){
                    vm.onOrdersEvent(UserOrdersEvent.ListOrders)
                }
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    val orderClicked = remember {
        {orderId:String ->

        }
    }

    val backPressed:() -> Unit = remember{
        {
            navHostController.popBackStack()
        }
    }

    val cartItems = bottomNavState.cartFlow.collectAsStateWithLifecycle().value
    val appScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier
            .nestedScroll(appScrollBehavior.nestedScrollConnection),
        topBar = {
            DefaultAppBar(
                scrollBehavior = appScrollBehavior,
                title = stringResource(id = R.string.account_order),
                navIconClicked = backPressed)
        },
        bottomBar = {
            AppBottomNavigationBar(
                cartQuantity = cartItems.size,
                navHostController = navHostController
            )
        },
        containerColor = Color.White
    ) { innerPadding ->
        UserOrdersBody(
            innerPadding = innerPadding,
            orders = ordersList,
            orderClicked = orderClicked)
    }


}