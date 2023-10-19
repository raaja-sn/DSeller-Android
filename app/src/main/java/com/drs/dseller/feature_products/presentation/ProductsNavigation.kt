package com.drs.dseller.feature_products.presentation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.drs.dseller.feature_products.presentation.screens.ProductDetailScreen
import com.drs.dseller.feature_products.presentation.screens.ProductListScreen
import com.drs.dseller.getViewModel



fun NavGraphBuilder.Products(navController:NavHostController){
    navigation(
        startDestination = "products_list/{category}",
        route = "products"
    ){
        composable(route = "products_list/{category}"){
            val vm:ProductsViewModel = it.getViewModel(navController = navController)

            vm.onProductListEvent(ProductsEvent.SetProductsCategory(it.arguments?.getString("category","")?:""))
            ProductListScreen(state = vm.productScreenState.value, vm = vm, navHostController = navController)
        }

        composable(route = "product_detail/{productId}"){
            val vm:ProductsViewModel = it.getViewModel(navController = navController)
            vm.onProductDetailEvent(ProductsDetailEvent.SetProductId(it.arguments?.getString("productId","")?:""))
            ProductDetailScreen(state = vm.productDetailState.value, vm = vm, navHostController = navController)
        }

    }
}

fun NavHostController.toProductDetail(productId:String){
    navigate("product_detail/$productId")
}

fun NavHostController.toProducts(category:String){
    navigate("products_list/$category")
}