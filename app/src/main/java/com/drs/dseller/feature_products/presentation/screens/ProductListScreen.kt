@file:OptIn(ExperimentalMaterial3Api::class)

package com.drs.dseller.feature_products.presentation.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.dimensionResource
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.drs.dseller.R
import com.drs.dseller.core.ui_elements.appbar.DefaultAppBar
import com.drs.dseller.feature_products.domain.model.Product
import com.drs.dseller.feature_products.presentation.ProductScreenFilter
import com.drs.dseller.feature_products.presentation.ProductsEvent
import com.drs.dseller.feature_products.presentation.ProductsViewModel
import com.drs.dseller.feature_products.presentation.screens.components.ProductFilter
import com.drs.dseller.feature_products.presentation.screens.components.ProductListBody
import com.drs.dseller.feature_products.presentation.states.ProductScreenState
import com.drs.dseller.feature_products.presentation.toProductDetail

@Composable
fun ProductListScreen(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    state:ProductScreenState,
    vm:ProductsViewModel,
    navHostController: NavHostController
) {

    val products = state.productsFlow.collectAsLazyPagingItems()

    DisposableEffect(key1 = lifecycleOwner){
        val observer = object:DefaultLifecycleObserver{
            override fun onResume(owner: LifecycleOwner) {
                super.onResume(owner)
                if(products.itemCount > 0) return
                vm.onProductListEvent(ProductsEvent.ListProducts)
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    val appBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    val navClicked:() -> Unit = remember{
        {
            navHostController.popBackStack()
        }
    }
    val filterIconClicked = remember{
        {
            vm.onProductListEvent(ProductsEvent.FilterClicked)
        }
    }

    val productItemClicked = remember {
        { productId:String ->
            navHostController.toProductDetail(productId)
        }
    }
    val addToCart = remember {
        { product:Product ->
            vm.onProductListEvent(ProductsEvent.AddToCart(product))
        }
    }

    val filterSelected = remember {
        { filter:ProductScreenFilter ->
            vm.onProductListEvent(ProductsEvent.ListProductsForNewFilter(filter))
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(appBarScrollBehavior.nestedScrollConnection),
        topBar = {
            DefaultAppBar(
                scrollBehavior = appBarScrollBehavior,
                title = state.category,
                navIconClicked = navClicked,
                actionResId = R.drawable.ic_filter,
                actionClicked = filterIconClicked
            )
        }
    ){ innerPadding ->

        ProductListBody(
            modifier = Modifier
                .background(Color.White)
                .padding(innerPadding)
                .padding(horizontal = dimensionResource(id = R.dimen.five_dp)),
            products = products,
            itemClicked = productItemClicked,
            addToCart = addToCart
        )

    }

    Box(
    ){
        AnimatedVisibility(
            modifier = Modifier
                .align(Alignment.BottomCenter),
            visible = state.showFilterOptions,
            enter = slideInVertically { it },
            exit = slideOutVertically { it }
        ) {
            ProductFilter(
                state.filter,
                filterSelected
            )
        }
    }

}