package com.drs.dseller.feature_products.presentation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.drs.dseller.core.navigation.getViewModel
import com.drs.dseller.feature_products.presentation.screens.product_detail.ProductDetailScreen
import com.drs.dseller.feature_products.presentation.screens.product_list.ProductListScreen


fun NavGraphBuilder.Products(navHostController:NavHostController){
    navigation(
        startDestination = "products_list/{category}",
        route = "products"
    ){
        composable(route = "products_list/{category}"){
            val vm:ProductsViewModel = it.getViewModel(navController = navHostController)

            vm.onProductListEvent(ProductsEvent.SetProductsCategory(it.arguments?.getString("category","")?:""))
            ProductListScreen(state = vm.productScreenState.value, vm = vm, navHostController = navHostController)
        }

        composable(route = "product_detail/{productId}"){
            val vm:ProductsViewModel = it.getViewModel(navController = navHostController)
            vm.onProductDetailEvent(ProductsDetailEvent.SetProductId(it.arguments?.getString("productId","")?:""))
            ProductDetailScreen(state = vm.productDetailState.value, vm = vm, navHostController = navHostController)
        }

    }
}

fun NavHostController.toProductDetail(productId:String){
    navigate("$DESTINATION_PRODUCT_DETAIL/$productId")
}

fun NavHostController.toProducts(category:String){
    navigate("$DESTINATION_PRODUCTS_LIST/$category")
}

const val ROUTE_PRODUCTS = "products"
const val DESTINATION_PRODUCTS_LIST = "products_list"
const val DESTINATION_PRODUCT_DETAIL = "product_detail"