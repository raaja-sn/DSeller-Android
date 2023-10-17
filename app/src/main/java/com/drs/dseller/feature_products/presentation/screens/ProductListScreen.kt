package com.drs.dseller.feature_products.presentation.screens

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.drs.dseller.feature_products.presentation.ProductsEvent
import com.drs.dseller.feature_products.presentation.ProductsViewModel
import com.drs.dseller.feature_products.presentation.screens.components.ProductElement
import com.drs.dseller.feature_products.presentation.states.ProductScreenState

@Composable
fun ProductListScreen(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    state:ProductScreenState,
    vm:ProductsViewModel
) {
    val gridState  = remember{
        LazyGridState()
    }

    val list = state.productsFlow.collectAsLazyPagingItems()

    DisposableEffect(key1 = lifecycleOwner){
        val observer = object:DefaultLifecycleObserver{
            override fun onResume(owner: LifecycleOwner) {
                super.onResume(owner)
                if(list.itemCount > 0) return
                vm.onProductListEvent(ProductsEvent.ListProducts)
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    val callback = remember{
        {idx:String ->

        }
    }
    val cart = remember{
        {idx:String ->

        }
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        state = gridState
    ){
        items(list.itemCount,
            key = list.itemKey {
                it.productId
            },
            contentType = list.itemContentType { "Products" }
        ){ index:Int->
            list[index]?.let{
                ProductElement(
                   product = it, productClicked = callback, addToCart = cart)
            }

        }

    }
}