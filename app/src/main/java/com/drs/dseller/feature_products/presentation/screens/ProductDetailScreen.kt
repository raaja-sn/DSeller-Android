package com.drs.dseller.feature_products.presentation.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavHostController
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

    val addToBasket = remember {
        addToBasket@{quantity:Int ->
            if(quantity == 0)return@addToBasket
            vm.onProductDetailEvent(ProductsDetailEvent.AddToBasket(quantity))
        }
    }

    ProductDetailBody(
        state = state,
        addToCart = addToBasket
    )

}