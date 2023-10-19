package com.drs.dseller.feature_products.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.drs.dseller.core.constants.AppConstants
import com.drs.dseller.core.domain.model.shopping_cart.CartProduct
import com.drs.dseller.core.domain.usecases.shopping_cart_use_cases.ShoppingCartUseCases
import com.drs.dseller.feature_products.domain.model.Product
import com.drs.dseller.feature_products.domain.model.ProductDetail
import com.drs.dseller.feature_products.domain.model.ProductSearchFilter
import com.drs.dseller.feature_products.domain.usecases.ProductsUseCases
import com.drs.dseller.feature_products.presentation.states.ProductDetailErrorState
import com.drs.dseller.feature_products.presentation.states.ProductDetailState
import com.drs.dseller.feature_products.presentation.states.ProductInfoState
import com.drs.dseller.feature_products.presentation.states.ProductScreenState
import com.drs.dseller.feature_products.response.ProductResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val productsUseCases: ProductsUseCases,
    private val cartUseCases: ShoppingCartUseCases
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

            is ProductsEvent.AddToCart -> {
               addListProductToCart(event.product)
            }

            ProductsEvent.HideProductInfo ->{
                _productScreenState.value = _productScreenState.value.copy(
                    productInfo = ProductInfoState(
                        showInfoState = false
                    )
                )
            }
        }
    }

    fun onProductDetailEvent(event:ProductsDetailEvent){
        when(event){
            is ProductsDetailEvent.GetDetailForProduct -> getProductDetail(event.productId)
            is ProductsDetailEvent.AddToCart ->{
                productDetailState.value.productDetail?.let{
                    addProductToCart(event.quantity,it)
                }
            }

            is ProductsDetailEvent.SetProductId -> {
                _productDetailState.value = _productDetailState.value.copy(
                    productId = event.productId
                )
            }

            is ProductsDetailEvent.UpdateProductQuantity -> {
                productDetailState.value.productDetail?.let{
                    updateQuantityOfProductInCart(event.quantity,it)
                }
            }

            ProductsDetailEvent.HideProductInfo -> {
                _productDetailState.value = _productDetailState.value.copy(
                    productInfo = ProductInfoState(
                        showInfoState = false
                    )
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

    /**
     * Add product to cart when the user adds it from the products list
     */
    private fun addListProductToCart(listProduct:Product){
        if(cartUseCases.hasProduct(listProduct.productId)){
            cartUseCases.incrementQuantity(
                cartUseCases.getProduct(listProduct.productId).quantity+1,
                listProduct.productId
            )
        }else{
            cartUseCases.addProduct(CartProduct(
                productName = listProduct.name,
                productId = listProduct.productId,
                quantity = 1,
                price = listProduct.price
            ))
        }
        _productScreenState.value = _productScreenState.value.copy(
            productInfo = ProductInfoState(
                info = "${listProduct.name} added to cart"
            )
        )
    }

    /**
     * Add product to cart when the user adds it from the products detail screen
     */
    private fun addProductToCart(quantity: Int,product: ProductDetail){
        cartUseCases.addProduct(
            CartProduct(
                productName = product.name,
                productId = product.productId,
                quantity = 1,
                price = product.price
            )
        )
        _productDetailState.value = _productDetailState.value.copy(
            productInfo = ProductInfoState(
                info = "${product.name} added to cart"
            )
        )
    }

    /**
     * Update quantity of the product, if user adds the same product to cart from the product detail screen
     */
    private fun updateQuantityOfProductInCart(quantity:Int,product: ProductDetail){
        cartUseCases.incrementQuantity(
            cartUseCases.getProduct(product.productId).quantity+1,
            product.productId
        )
        _productDetailState.value = _productDetailState.value.copy(
            productInfo = ProductInfoState(
                info = "${product.name} added to cart"
            )
        )
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
    /**
     *  Set the [ProductScreenState.category], which is used to fetch the products related to the category
     */
    data class SetProductsCategory(val category:String):ProductsEvent()

    data object ListProducts:ProductsEvent()

    /**
     * List products according to the new filter
     */
    data class ListProductsForNewFilter(val filter:ProductScreenFilter):ProductsEvent()

    /**
     * UI event when a filter icon is clicked. To display or hide filter UI
     */
    data object FilterClicked:ProductsEvent()

    data class AddToCart(val product:Product):ProductsEvent()

    data object HideProductInfo:ProductsEvent()
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

    /**
     *  Set the [ProductDetailState.productId], which is used to fetch the product associated with the ID
     */
    data class SetProductId(val productId:String):ProductsDetailEvent()

    data class GetDetailForProduct(val productId:String):ProductsDetailEvent()

    data class AddToCart(val quantity:Int):ProductsDetailEvent()

    /**
     * If a product is already in the cart, update its quantity
     */
    data class UpdateProductQuantity(val quantity: Int):ProductsDetailEvent()

    data object HideProductInfo:ProductsDetailEvent()
}