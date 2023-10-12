package com.drs.dseller.feature_home.domain.model

data class Category(
    val name:String ="",
    val image:String ="",
    val subCategories:List<String> = listOf()
)
