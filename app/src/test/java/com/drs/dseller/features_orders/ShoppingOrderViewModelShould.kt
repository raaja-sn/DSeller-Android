package com.drs.dseller.features_orders

import com.drs.dseller.BaseTest
import com.drs.dseller.cart.CartMock
import com.drs.dseller.core.domain.model.shopping_cart.CartProduct
import com.drs.dseller.core.domain.usecases.shopping_cart_use_cases.ShoppingCartUseCases
import com.drs.dseller.feature_orders.domain.usecases.OrdersUseCases
import com.drs.dseller.feature_orders.presentation.CartEvent
import com.drs.dseller.feature_orders.presentation.OrderEvent
import com.drs.dseller.feature_orders.presentation.ShoppingOrderViewModel
import com.drs.dseller.feature_orders.response.ShoppingOrderResponse
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class ShoppingOrderViewModelShould :BaseTest() {


    private lateinit var orderMocks:OrderMocks
    private lateinit var orderUseCases:OrdersUseCases
    private lateinit var cartMocks: CartMock
    private lateinit var cartUseCases:ShoppingCartUseCases
    private lateinit var vm:ShoppingOrderViewModel
    private lateinit var products:List<CartProduct>


    @Before
    fun init(){
        orderMocks = OrderMocks()
        orderUseCases = orderMocks.getUseCases()
        cartMocks = CartMock()
        cartUseCases = cartMocks.getUseCases()
        products = cartMocks.getMockCartProducts()
        whenever(cartUseCases.getAllProducts.invoke()).thenReturn(
            MutableStateFlow(products)
        )
        vm = ShoppingOrderViewModel(cartUseCases,orderUseCases)
    }

    @Test
    fun `get products in cart`(){
        vm.onCartEvent(CartEvent.GetCartProducts)
        verify(cartUseCases.getAllProducts, times(2)).invoke()
        assertEquals(vm.cartScreenState.value.cartItems,products)
    }

    @Test
    fun `place order`() = runTest {
        val products = cartMocks.getMockCartProducts()
        whenever(orderUseCases.placeOrder.invoke(products)).thenReturn(
            ShoppingOrderResponse.Success(Unit)
        )
        vm.onOrderEvent(OrderEvent.PlaceOrder)
        advanceUntilIdle()
        verify(orderUseCases.placeOrder, times(1)).invoke(products)
        assertEquals(vm.cartScreenState.value.isOrderComplete,true)
    }

    @Test
    fun `send error if order cannot be placed`() = runTest {
        val products = cartMocks.getMockCartProducts()
        whenever(orderUseCases.placeOrder.invoke(products)).thenReturn(
            ShoppingOrderResponse.Error("Error")
        )
        vm.onOrderEvent(OrderEvent.PlaceOrder)
        advanceUntilIdle()
        assertEquals(vm.cartScreenState.value.errorState.message,"Error")
    }

}