package com.drs.dseller.feature_products.presentation.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.LifecycleOwner
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import com.drs.dseller.feature_products.domain.model.Product

@Composable
fun ProductListBody(
    modifier: Modifier = Modifier,
    products:LazyPagingItems<Product>,
    itemClicked:(String) -> Unit,
    addToCart:(String) -> Unit
){
    val gridState = remember{
        LazyGridState()
    }

    LazyVerticalGrid(
        modifier = modifier
            .background(Color.White),
        columns = GridCells.Fixed(2),
        state = gridState
    ){
        items(
            count = products.itemCount,
            key = products.itemKey { product: Product ->
                product.productId
            },
            contentType = {"Products"}
        ){ idx ->
            products[idx]?.let{
                ProductElement(
                    product = it,
                    productClicked = itemClicked,
                    addToCart = addToCart
                )
            }

        }
    }
}