package com.drs.dseller.feature_home.presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drs.dseller.core.domain.usecases.shopping_cart_use_cases.ShoppingCartUseCases
import com.drs.dseller.feature_home.domain.usecases.HomeUseCases
import com.drs.dseller.feature_home.presentation.states.HomeErrorState
import com.drs.dseller.feature_home.presentation.states.HomeState
import com.drs.dseller.feature_home.response.HomeResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeUseCases:HomeUseCases,
    cartUseCases: ShoppingCartUseCases
):ViewModel(){

    private val _homeState = mutableStateOf(
        HomeState(
            cartFlow = cartUseCases.getAllProducts.invoke()
        )
    )
    val homeState: State<HomeState> = _homeState

    fun onEvent(event: HomeEvent){
        when(event){
            HomeEvent.GetCategories -> getCategories()
            HomeEvent.GetOffers -> getOffers()
            is HomeEvent.SearchChanged ->{
                _homeState.value = _homeState.value.copy(
                    searchQuery = event.query
                )
            }
        }
    }

    private fun getCategories(){
        viewModelScope.launch {
            when(val response = homeUseCases.getCategories()){
                is HomeResponse.Error -> {
                    _homeState.value = _homeState.value.copy(
                        errorState = HomeErrorState(true,response.message)
                    )
                }
                is HomeResponse.Success -> {
                    _homeState.value = _homeState.value.copy(categories = response.result)
                }
            }
        }
    }

    private fun getOffers(){
        viewModelScope.launch {
            when(val response = homeUseCases.getHomeOffers()){
                is HomeResponse.Error -> {

                }
                is HomeResponse.Success -> {
                    _homeState.value = _homeState.value.copy(offers = response.result)
                }
            }
        }
    }

}

sealed class HomeEvent{
    data object GetCategories:HomeEvent()
    data object GetOffers:HomeEvent()
    data class SearchChanged(val query:String):HomeEvent()
}