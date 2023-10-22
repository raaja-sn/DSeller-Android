package com.drs.dseller.feature_products

import androidx.paging.PagingData
import com.drs.dseller.BaseTest
import com.drs.dseller.cart.CartMock
import com.drs.dseller.core.constants.AppConstants
import com.drs.dseller.core.domain.model.shopping_cart.CartProduct
import com.drs.dseller.core.domain.usecases.shopping_cart_use_cases.ShoppingCartUseCases
import com.drs.dseller.feature_products.domain.model.ProductDetail
import com.drs.dseller.feature_products.domain.model.ProductSearchFilter
import com.drs.dseller.feature_products.domain.usecases.ProductsUseCases
import com.drs.dseller.feature_products.presentation.ProductsDetailEvent
import com.drs.dseller.feature_products.presentation.ProductsEvent
import com.drs.dseller.feature_products.presentation.ProductsViewModel
import com.drs.dseller.feature_products.response.ProductResponse
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class ProductsViewModelShould : BaseTest() {

    private lateinit var productUseCases:ProductsUseCases
    private lateinit var cartUseCases:ShoppingCartUseCases
    private lateinit var vm:ProductsViewModel
    private lateinit var productMock:ProductMocks
    private lateinit var cartMock:CartMock
    private lateinit var products:List<CartProduct>

    @Before
    fun init(){
        productMock = ProductMocks()
        productUseCases = productMock.getUseCases()
        cartMock = CartMock()
        cartUseCases = cartMock.getUseCases()
        products = cartMock.getMockCartProducts()
        whenever(cartUseCases.getAllProducts.invoke()).thenReturn(
            MutableStateFlow(products)
        )
        vm = ProductsViewModel(productUseCases,cartUseCases)
    }

    @Test
    fun `get products list from backend`() = runTest {
        val products = productMock.getProductList()
        val filter = ProductSearchFilter(
            category = "",
            sortBy = AppConstants.API_QUERY_KEY_NAME,
            sortOrder = AppConstants.SORT_ORDER_DESCENDING,
        )
        val job = launch {
            vm.productScreenState.value.productsFlow.collect{ data ->
                verify(productUseCases.listProducts, times(1)).invoke(filter)
            }
        }
        whenever(productUseCases.listProducts.invoke(filter)).thenReturn(
            flow{
               emit(PagingData.from(products))
            }
        )
        vm.onProductListEvent(ProductsEvent.ListProducts)
        advanceUntilIdle()
        job.cancel()
    }

    @Test
    fun `get product detail`() = runTest{
        val id = "sdf5sdf"
        val product = ProductDetail(name = "Indian Terrain T-Shirt Blue")
        whenever(productUseCases.getProductDetail.invoke(id)).thenReturn(
            ProductResponse.Success(product)
        )
        vm.onProductDetailEvent(ProductsDetailEvent.GetDetailForProduct(id))
        advanceUntilIdle()
        verify(productUseCases.getProductDetail, times(1)).invoke(id)
        assertEquals(vm.productDetailState.value.productDetail?.name , "Indian Terrain T-Shirt Blue")
    }

    @Test
    fun `send error when product detail cannot be fetched`() = runTest{
        val id = "sdf5sdf"
        val product = ProductDetail(name = "Indian Terrain T-Shirt Blue")
        whenever(productUseCases.getProductDetail.invoke(id)).thenReturn(
            ProductResponse.Error("Error")
        )
        vm.onProductDetailEvent(ProductsDetailEvent.GetDetailForProduct(id))
        advanceUntilIdle()
        assertEquals(vm.productDetailState.value.productDetailErrorState.message , "Error")
    }

}