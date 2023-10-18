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
import com.drs.dseller.feature_products.presentation.states.ProductDetailState
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

    private val _productDetailState = mutableStateOf(ProductDetailState())
    val productDetailState:State<ProductDetailState> = _productDetailState

    fun onProductListEvent(event:ProductsEvent){
        when(event){
            ProductsEvent.ListProducts -> listProducts()
            is ProductsEvent.ListProductsForNewFilter -> listProductsForNewFilter(event.filter)
            is ProductsEvent.FilterClicked -> {
                _productScreenState.value = _productScreenState.value.copy(
                    showFilterOptions = !_productScreenState.value.showFilterOptions
                )
            }

            is ProductsEvent.SetProductsCategory -> {
                _productScreenState.value = _productScreenState.value.copy(
                    category = event.category
                )
            }
        }
    }

    fun onProductDetailEvent(event:ProductsDetailEvent){
        when(event){
            is ProductsDetailEvent.GetDetailForProduct -> getProductDetail(event.productId)
            is ProductsDetailEvent.AddToBasket ->{

            }

            is ProductsDetailEvent.SetProductId -> {
                _productDetailState.value = _productDetailState.value.copy(
                    productId = event.productId
                )
            }
        }
    }

    private fun getProductDetail(productId:String){
        viewModelScope.launch {
            _productDetailState.value = _productDetailState.value.copy(productDetailLoading = true)
            when(val r = productsUseCases.getProductDetail(productId)){
                is ProductResponse.Error -> {
                    _productDetailState.value = _productDetailState.value.copy(
                        productDetailLoading = false,
                        productDetailErrorState = ProductDetailErrorState(true,r.message)
                    )
                }
                is ProductResponse.Success -> {
                    _productDetailState.value = _productDetailState.value.copy(
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
            sortBy = AppConstants.API_QUERY_KEY_NAME,
            sortOrder = AppConstants.SORT_ORDER_DESCENDING,
        )
        val listFlow = productsUseCases.listProducts(filter).cachedIn(viewModelScope)
        _productScreenState.value = _productScreenState.value.copy(productsFlow = listFlow)
    }

    private fun listProductsForNewFilter(filter:ProductScreenFilter){
        _productScreenState.value = _productScreenState.value.copy(
            filter = filter,
            showFilterOptions = !_productScreenState.value.showFilterOptions
        )
        _productScreenState.value = _productScreenState.value.copy(filter = filter)
        val listFlow = productsUseCases.listProducts(getSearchFilter(filter)).cachedIn(viewModelScope)
        _productScreenState.value = _productScreenState.value.copy(productsFlow = listFlow)
    }

    private fun getSearchFilter(productScreenFilter:ProductScreenFilter):ProductSearchFilter{
        return when(productScreenFilter){
            is ProductScreenFilter.ByName -> {
                ProductSearchFilter(
                    category = productScreenState.value.category,
                    sortBy = AppConstants.API_QUERY_KEY_NAME,
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

/**
 * Events in Products list screen
 */
sealed class ProductsEvent{
    data class SetProductsCategory(val category:String):ProductsEvent()
    data object ListProducts:ProductsEvent()
    data class ListProductsForNewFilter(val filter:ProductScreenFilter):ProductsEvent()

    data object FilterClicked:ProductsEvent()
}

/**
 * Sealed class used to list products according to the filter
 */
sealed class ProductScreenFilter{
    data class ByPrice(val sortOrder:ProductSortOrder):ProductScreenFilter()
    data class ByName(val sortOrder:ProductSortOrder):ProductScreenFilter()

}

/**
 * The sort order for the filter
 */
sealed class ProductSortOrder{
    data object DESCENDING:ProductSortOrder()
    data object ASCENDING:ProductSortOrder()
}

/**
 * Events from Product Detail screen
 */
sealed class ProductsDetailEvent{
    data class SetProductId(val productId:String):ProductsDetailEvent()
    data class GetDetailForProduct(val productId:String):ProductsDetailEvent()
    data class AddToBasket(val quantity:Int):ProductsDetailEvent()
}