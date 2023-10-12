package com.drs.dseller.feature_home.presentation.states

import com.drs.dseller.feature_home.domain.model.Category
import com.drs.dseller.feature_home.domain.model.HomeOffer

data class HomeState(
    val categories:List<Category> = listOf(
        Category("Electronics"),
        Category("Fashion"),
        Category("Home Appliances"),
        Category("Books"),
        Category("Toys"),
        Category("Sports & Fitness"),
        Category("Home & Kitchen"),
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
    val errorState: HomeErrorState = HomeErrorState()
)
