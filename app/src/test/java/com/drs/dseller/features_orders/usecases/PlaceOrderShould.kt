package com.drs.dseller.features_orders.usecases

import com.drs.dseller.BaseTest
import com.drs.dseller.cart.CartMock
import com.drs.dseller.feature_orders.domain.repository.ShoppingOrderRepository
import com.drs.dseller.feature_orders.domain.usecases.PlaceOrder
import com.drs.dseller.feature_orders.response.ShoppingOrderResponse
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class PlaceOrderShould :BaseTest() {

    private lateinit var repo:ShoppingOrderRepository<ShoppingOrderResponse<Unit>>
    private lateinit var useCase:PlaceOrder
    private lateinit var mock:CartMock

    @Before
    fun init(){
        mock = CartMock()
        repo = mock()
        useCase = PlaceOrder(repo)
    }

    @Test
    fun`place an order`() = runTest {
        val products = mock.getMockCartProducts()
        whenever(repo.placeOrder(products)).thenReturn(
            ShoppingOrderResponse.Success(Unit)
        )
        val r = useCase.invoke(products)
        verify(repo,times(1)).placeOrder(products)
        assertEquals((r as ShoppingOrderResponse.Success).data,Unit)
    }

    @Test
    fun`send error when order cannot be placed`() = runTest {
        val products = mock.getMockCartProducts()
        whenever(repo.placeOrder(products)).thenReturn(
            ShoppingOrderResponse.Error("Error")
        )
        val r = useCase.invoke(products)
        assertEquals((r as ShoppingOrderResponse.Error).message,"Error")
    }

}