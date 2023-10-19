package com.drs.dseller.feature_products

import com.drs.dseller.feature_products.domain.model.Product
import com.drs.dseller.feature_products.domain.usecases.ProductsUseCases
import org.mockito.kotlin.mock
import org.mockito.kotlin.spy

class ProductMocks {

    fun getUseCases():ProductsUseCases{
        return spy(
            ProductsUseCases(mock(), mock(), mock())
        )
    }

    fun getProductList():List<Product>{
        return listOf(
            Product("IPhone"),
            Product("Samsung 4k Tv 43 inches"),
            Product("Oats Rolled"),
            Product("Philips Night light"),
            Product("Indian Terrain T-Shirt Blue"),
        )
    }
}