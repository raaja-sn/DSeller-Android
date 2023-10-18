@file:OptIn(ExperimentalMaterial3Api::class)

package com.drs.dseller.feature_products.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavHostController
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
    lifeCycleOwner:LifecycleOwner = LocalLifecycleOwner.current
){

    DisposableEffect(key1 = lifeCycleOwner){
        val observer = object:DefaultLifecycleObserver{
            override fun onResume(owner: LifecycleOwner) {
                super.onResume(owner)
                state.productId?.let{
                    if(state.productDetail == null && !state.productDetailLoading){
                        vm.onProductDetailEvent(ProductsDetailEvent.GetDetailForProduct(it))
                    }
                }
            }
        }
        lifeCycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifeCycleOwner.lifecycle.removeObserver(observer)
        }
    }

    val navClicked:() -> Unit = remember{
        {
            navHostController.popBackStack()
        }
    }

    val addToBasket = remember {
        addToBasket@{quantity:Int ->
            if(quantity == 0)return@addToBasket
            vm.onProductDetailEvent(ProductsDetailEvent.AddToBasket(quantity))
        }
    }

    val scrollBehavior  = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        topBar = {
            DefaultAppBar(
                scrollBehavior = scrollBehavior,
                title = state.productDetail?.name?:"",
                navIconClicked = navClicked
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            ProductDetailBody(
                state = state,
                addToCart = addToBasket
            )
        }

    }

}