@file:OptIn(ExperimentalMaterial3Api::class)

package com.drs.dseller.feature_account.presentation.screens.user_orders

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
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
import com.drs.dseller.feature_account.presentation.states.AccountBottomNavigationBarState
import com.drs.dseller.feature_account.presentation.states.UserOrdersState
import com.drs.dseller.feature_account.presentation.toInvoice
import com.drs.dseller.ui.theme.AppTypography
import com.drs.dseller.ui.theme.Black80

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
                if(ordersList.itemCount > 0)return
                vm.onOrdersEvent(UserOrdersEvent.ListOrders)
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    val orderClicked = remember {
        {orderId:String ->
            navHostController.toInvoice(orderId)
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
        if(ordersList.itemCount > 0){
            UserOrdersBody(
                innerPadding = innerPadding,
                ordersPagingItems = ordersList,
                orderClicked = orderClicked
            )
        }else{
            EmptyOrderInfo()
        }

    }
}

@Composable
private fun EmptyOrderInfo(){
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .size(dimensionResource(id = R.dimen.three_hundred_dp))
                .aspectRatio(1f / 0.7f)
                .padding(horizontal = dimensionResource(id = R.dimen.thirty_dp)),
            painter = painterResource(id = R.drawable.img_empty_order),
            contentDescription = stringResource(id = R.string.description_user_order_empty),
            contentScale = ContentScale.Crop
        )
        Text(
            text = stringResource(id = R.string.user_orders_empty),
            style = AppTypography.headlineMedium,
            color = Black80,
            textAlign = TextAlign.Center
        )
    }
}