package com.drs.dseller.feature_home.domain.usecases

import javax.inject.Inject

data class HomeUseCases @Inject constructor(
    val getCategories: GetCategories,
    val getHomeOffers: GetHomeOffers,
    val homeSearchProduct: HomeSearchProduct,
) {
}