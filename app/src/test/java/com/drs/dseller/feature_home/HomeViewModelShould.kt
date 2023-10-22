package com.drs.dseller.feature_home

import com.drs.dseller.BaseTest
import com.drs.dseller.cart.CartMock
import com.drs.dseller.core.domain.model.shopping_cart.CartProduct
import com.drs.dseller.core.domain.usecases.shopping_cart_use_cases.ShoppingCartUseCases
import com.drs.dseller.feature_home.domain.usecases.HomeUseCases
import com.drs.dseller.feature_home.presentation.HomeEvent
import com.drs.dseller.feature_home.presentation.HomeViewModel
import com.drs.dseller.feature_home.response.HomeResponse
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class HomeViewModelShould : BaseTest() {

    private lateinit var useCases: HomeUseCases
    private lateinit var cartUseCases:ShoppingCartUseCases
    private lateinit var vm:HomeViewModel
    private lateinit var cartMocks: CartMock
    private lateinit var products:List<CartProduct>

    @Before
    fun init(){
        useCases = HomeMocks.getUseCases()
        cartUseCases = CartMock().getUseCases()
        cartMocks = CartMock()
        products = cartMocks.getMockCartProducts()
        whenever(cartUseCases.getAllProducts.invoke()).thenReturn(
            MutableStateFlow(products)
        )
        vm = HomeViewModel(useCases,cartUseCases)
    }

    @Test
    fun `get product categories`() = runTest {
        val categories= HomeMocks.getCategories()
        whenever(useCases.getCategories.invoke()).thenReturn(
            HomeResponse.Success(categories)
        )
        vm.onEvent(HomeEvent.GetCategories)
        advanceUntilIdle()
        verify(useCases.getCategories, times(1)).invoke()
        assertEquals(vm.homeState.value.categories,categories)
    }

    @Test
    fun `send error when product categories cannot be fetched`() = runTest {
        whenever(useCases.getCategories.invoke()).thenReturn(
            HomeResponse.Error("Error")
        )
        vm.onEvent(HomeEvent.GetCategories)
        advanceUntilIdle()
        assertEquals(vm.homeState.value.errorState.isError,true)
        assertEquals(vm.homeState.value.errorState.message,"Error")
    }

    @Test
    fun`get offers for home screen`() = runTest {
        val offers = HomeMocks.getOffers()
        whenever(useCases.getHomeOffers.invoke()).thenReturn(
            HomeResponse.Success(offers)
        )
        vm.onEvent(HomeEvent.GetOffers)
        advanceUntilIdle()
        verify(useCases.getHomeOffers, times(1)).invoke()
        assertEquals(vm.homeState.value.offers,offers)
    }


}