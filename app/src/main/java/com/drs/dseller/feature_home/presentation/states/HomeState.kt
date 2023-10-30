package com.drs.dseller.feature_home.presentation.states

import com.drs.dseller.core.domain.model.shopping_cart.CartProduct
import com.drs.dseller.feature_home.domain.model.Category
import com.drs.dseller.feature_home.domain.model.HomeOffer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class HomeState(
    val categories:List<Category> = listOf(
        Category("Electronics"),
        Category("Fashion"),
        Category("Home Appliances"),
        Category("Groceries"),
        Category("Toys"),
        Category("Sports & Fitness"),
        Category("Kitchen & Decors"),
        Category("Movies & Games")
    ),
    val offers:List<HomeOffer> = listOf(
        HomeOffer("1"),
        HomeOffer("2"),
        HomeOffer("3")
    ),
    val refreshOffers:Boolean = true,
    val refreshCategories:Boolean = true,
    val searchQuery:String = "",
    val errorState: HomeErrorState = HomeErrorState(),
    val cartFlow:StateFlow<List<CartProduct>> = MutableStateFlow(emptyList())
)
