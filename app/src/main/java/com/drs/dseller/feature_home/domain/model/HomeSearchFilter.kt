package com.drs.dseller.feature_home.domain.model

import com.drs.dseller.core.constants.AppConstants.Companion.SORT_ORDER_ASCENDING

data class HomeSearchFilter(
    val searchTerm:String ="",
    val categories:String = "",
    val subCategory:String = "",
    val sortBy:String = "",
    val sortOrder:String = SORT_ORDER_ASCENDING,
    val pageNumber: Int = 1,
    val pageSize:Int = 8
){

}

