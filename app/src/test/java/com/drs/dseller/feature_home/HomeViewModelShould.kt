package com.drs.dseller.feature_home

import com.drs.dseller.BaseTest
import com.drs.dseller.feature_home.domain.usecases.HomeUseCases
import com.drs.dseller.feature_home.presentation.HomeEvent
import com.drs.dseller.feature_home.presentation.HomeViewModel
import com.drs.dseller.feature_home.response.HomeResponse
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class HomeViewModelShould : BaseTest() {

    private lateinit var useCases: HomeUseCases
    private lateinit var vm:HomeViewModel

    @Before
    fun init(){
        useCases = HomeMocks.getUseCases()
        vm = HomeViewModel(useCases)
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