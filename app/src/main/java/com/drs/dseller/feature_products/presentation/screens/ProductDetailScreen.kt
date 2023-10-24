@file:OptIn(ExperimentalMaterial3Api::class)

package com.drs.dseller.feature_products.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.drs.dseller.core.ui_elements.appbar.AppBottomNavigationBar
import com.drs.dseller.core.ui_elements.appbar.DefaultAppBar
import com.drs.dseller.feature_products.presentation.ProductsDetailEvent
import com.drs.dseller.feature_products.presentation.ProductsViewModel
import com.drs.dseller.feature_products.presentation.screens.components.ProductDetailBody
import com.drs.dseller.feature_products.presentation.states.ProductDetailState

@Composable
fun ProductDetailScreen(
    state:ProductDetailState,
    vm:ProductsViewModel,
    navHostController: NavHostController,
){

    val navClicked:() -> Unit = remember{
        {
            navHostController.popBackStack()
        }
    }

    val addToCart = remember {
        addToBasket@{quantity:Int ->
            if(quantity == 0)return@addToBasket
            vm.onProductDetailEvent(ProductsDetailEvent.AddToCart(quantity))
        }
    }

    val scrollBehavior  = TopAppBarDefaults.pinnedScrollBehavior()
    val cartItems = state.cartFlow.collectAsStateWithLifecycle().value

    Scaffold(
        topBar = {
            DefaultAppBar(
                scrollBehavior = scrollBehavior,
                title = state.productDetail?.name?:"",
                navIconClicked = navClicked
            )
        },
        bottomBar = {
            AppBottomNavigationBar(
                cartQuantity = cartItems.size ,
                navHostController = navHostController
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .background(Color.White)
                .padding(innerPadding)
        ) {
            ProductDetailBody(
                state = state,
                addToCart = addToCart
            )
        }

    }

}