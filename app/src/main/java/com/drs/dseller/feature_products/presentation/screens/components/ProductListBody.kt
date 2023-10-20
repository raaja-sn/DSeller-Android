package com.drs.dseller.feature_products.presentation.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.booleanResource
import androidx.lifecycle.LifecycleOwner
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import com.drs.dseller.R
import com.drs.dseller.feature_products.domain.model.Product

@Composable
fun ProductListBody(
    modifier: Modifier = Modifier,
    isTablet:Boolean = booleanResource(id = R.bool.is_tablet),
    products:LazyPagingItems<Product>,
    itemClicked:(String) -> Unit,
    addToCart:(String) -> Unit
){
    val gridState = remember{
        LazyGridState()
    }

    val cells = remember{
        if(isTablet){
            3
        }else{
            2
        }
    }

    LazyVerticalGrid(
        modifier = modifier
            .background(Color.White),
        columns = GridCells.Fixed(cells),
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