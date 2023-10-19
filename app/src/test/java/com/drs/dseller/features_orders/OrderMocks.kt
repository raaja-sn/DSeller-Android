package com.drs.dseller.features_orders

import com.drs.dseller.core.domain.model.shopping_cart.CartProduct
import com.drs.dseller.core.domain.usecases.shopping_cart_use_cases.ShoppingCartUseCases
import com.drs.dseller.feature_orders.domain.usecases.OrdersUseCases
import org.mockito.kotlin.mock

class OrderMocks {


    fun getUseCases():OrdersUseCases{
        return OrdersUseCases(mock())
    }

}