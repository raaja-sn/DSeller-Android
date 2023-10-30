@file:OptIn(ExperimentalMaterial3Api::class)

package com.drs.dseller.feature_account.presentation.screens.invoice

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
import com.drs.dseller.feature_account.presentation.UserAccountViewModel
import com.drs.dseller.feature_account.presentation.UserInvoiceEvent
import com.drs.dseller.feature_account.presentation.screens.invoice.InvoiceBody
import com.drs.dseller.feature_account.presentation.states.AccountBottomNavigationBarState
import com.drs.dseller.feature_account.presentation.states.UserInvoiceState

@Composable
fun InvoiceScreen(
    state:UserInvoiceState,
    bottomNavState:AccountBottomNavigationBarState,
    vm:UserAccountViewModel,
    navHostController: NavHostController
){

    val navBackClicked:()->Unit = remember {
        {
            navHostController.popBackStack()
        }
    }

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val cartItems = bottomNavState.cartFlow.collectAsStateWithLifecycle().value

    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            DefaultAppBar(
                scrollBehavior = scrollBehavior ,
                title = stringResource(id = R.string.account_invoice),
                navIconClicked = navBackClicked
            )
        },
        bottomBar = {
            AppBottomNavigationBar(
                cartQuantity = cartItems.size,
                navHostController = navHostController
            )
        },
        containerColor = Color.White
    ) { innerPadding ->

        InvoiceBody(
            innerPadding = innerPadding,
            state = state
        )
    }

}