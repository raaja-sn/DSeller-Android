package com.drs.dseller.feature_products.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.drs.dseller.core.constants.AppConstants
import com.drs.dseller.feature_products.domain.model.ProductSearchFilter
import com.drs.dseller.feature_products.domain.usecases.ProductsUseCases
import com.drs.dseller.feature_products.presentation.states.ProductDetailErrorState
import com.drs.dseller.feature_products.presentation.states.ProductScreenState
import com.drs.dseller.feature_products.response.ProductResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val productsUseCases: ProductsUseCases
):ViewModel(){

    private val _productScreenState = mutableStateOf(ProductScreenState())
    val productScreenState:State<ProductScreenState> = _productScreenState

    fun onEvent(event:ProductsEvent){
        when(event){
            is ProductsEvent.GetDetailForProduct -> getProductDetail(event.productId)
            ProductsEvent.ListProducts -> listProducts()
            is ProductsEvent.ListProductsForNewFilter -> listProductsForNewFilter(event.filter)
        }
    }

    private fun getProductDetail(productId:String){
        viewModelScope.launch {
            _productScreenState.value = _productScreenState.value.copy(productDetailLoading = true)
            when(val r = productsUseCases.getProductDetail(productId)){
                is ProductResponse.Error -> {
                    _productScreenState.value = _productScreenState.value.copy(
                        productDetailLoading = false,
                        productDetailErrorState = ProductDetailErrorState(true,r.message)
                    )
                }
                is ProductResponse.Success -> {
                    _productScreenState.value = _productScreenState.value.copy(
                        productDetailLoading = false,
                        productDetail = r.result
                    )

                }
            }
        }
    }

    private fun listProducts(){
        val filter = ProductSearchFilter(
            category = productScreenState.value.category,
            sortBy = AppConstants.API_QUERY_KEY_DATE,
            sortOrder = AppConstants.SORT_ORDER_DESCENDING,
        )
        val listFlow = productsUseCases.listProducts(filter).cachedIn(viewModelScope)
        _productScreenState.value = _productScreenState.value.copy(productsFlow = listFlow)
    }

    private fun listProductsForNewFilter(filter:ProductScreenFilter){
        _productScreenState.value = _productScreenState.value.copy(
            filter = filter
        )
        //productsUseCases.changeFilterAndListProducts(getSearchFilter(filter))
        val listFlow = productsUseCases.listProducts(getSearchFilter(filter)).cachedIn(viewModelScope)
        _productScreenState.value = _productScreenState.value.copy(productsFlow = listFlow)
    }

    private fun getSearchFilter(productScreenFilter:ProductScreenFilter):ProductSearchFilter{
        return when(productScreenFilter){
            is ProductScreenFilter.ByDate -> {
                ProductSearchFilter(
                    category = productScreenState.value.category,
                    sortBy = AppConstants.API_QUERY_KEY_DATE,
                    sortOrder = getSortOrderString(productScreenFilter.sortOrder),
                )
            }
            is ProductScreenFilter.ByPrice -> {
                ProductSearchFilter(
                    category = productScreenState.value.category,
                    sortBy = AppConstants.API_QUERY_KEY_PRICE,
                    sortOrder = getSortOrderString(productScreenFilter.sortOrder),
                )
            }
        }
    }

    private fun getSortOrderString(sortOrder:ProductSortOrder):String{
        return when(sortOrder){
            ProductSortOrder.ASCENDING -> AppConstants.SORT_ORDER_ASCENDING
            ProductSortOrder.DESCENDING -> AppConstants.SORT_ORDER_DESCENDING
        }
    }

}

sealed class ProductsEvent{

    data class GetDetailForProduct(val productId:String):ProductsEvent()
    data object ListProducts:ProductsEvent()
    data class ListProductsForNewFilter(val filter:ProductScreenFilter):ProductsEvent()
}

sealed class ProductScreenFilter{
    data class ByPrice(val sortOrder:ProductSortOrder):ProductScreenFilter()
    data class ByDate(val sortOrder:ProductSortOrder):ProductScreenFilter()
}

sealed class ProductSortOrder{
    data object DESCENDING:ProductSortOrder()
    data object ASCENDING:ProductSortOrder()
}