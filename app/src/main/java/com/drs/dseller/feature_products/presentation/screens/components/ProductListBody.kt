package com.drs.dseller.feature_products.presentation.screens.components

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.drs.dseller.feature_products.domain.model.Product
import com.drs.dseller.feature_products.presentation.states.ProductScreenState

@Composable
fun ProductListBody(
    state: ProductScreenState,
    itemClicked:(String) -> Unit,
    addToCart:(String) -> Unit
){
    val gridState = remember{
        LazyGridState()
    }
    val productItems = state.productsFlow.collectAsLazyPagingItems()

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        state = gridState
    ){
        items(
            count = productItems.itemCount,
            key = productItems.itemKey { product: Product ->
                product.productId
            },
            contentType = {"Products"}
        ){ idx ->
            productItems[idx]?.let{
                ProductElement(
                    product = it,
                    productClicked = itemClicked,
                    addToCart = addToCart
                )
            }

        }
    }
}