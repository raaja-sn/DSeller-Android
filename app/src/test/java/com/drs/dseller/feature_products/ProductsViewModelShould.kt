package com.drs.dseller.feature_products

import androidx.paging.PagingData
import com.drs.dseller.BaseTest
import com.drs.dseller.core.constants.AppConstants
import com.drs.dseller.feature_products.domain.model.ProductDetail
import com.drs.dseller.feature_products.domain.model.ProductSearchFilter
import com.drs.dseller.feature_products.domain.usecases.ProductsUseCases
import com.drs.dseller.feature_products.presentation.ProductsEvent
import com.drs.dseller.feature_products.presentation.ProductsViewModel
import com.drs.dseller.feature_products.response.ProductResponse
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.collectLatest
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

    private lateinit var useCases:ProductsUseCases
    private lateinit var vm:ProductsViewModel
    private lateinit var mock:ProductMocks

    @Before
    fun init(){
        mock = ProductMocks()
        useCases = mock.getUseCases()
        vm = ProductsViewModel(useCases)
    }

    @Test
    fun `get products list from backend`() = runTest {
        val products = mock.getProductList()
        val filter = ProductSearchFilter(
            category = "",
            sortBy = AppConstants.API_QUERY_KEY_DATE,
            sortOrder = AppConstants.SORT_ORDER_DESCENDING,
        )
        val job = launch {
            vm.productScreenState.value.productsFlow.collect{ data ->
                verify(useCases.listProducts, times(1)).invoke(filter)
            }
        }
        whenever(useCases.listProducts.invoke(filter)).thenReturn(
            flow{
               emit(PagingData.from(products))
            }
        )
        vm.onEvent(ProductsEvent.ListProducts)
        advanceUntilIdle()
        job.cancel()
    }

    @Test
    fun `get product detail`() = runTest{
        val id = "sdf5sdf"
        val product = ProductDetail(name = "Indian Terrain T-Shirt Blue")
        whenever(useCases.getProductDetail.invoke(id)).thenReturn(
            ProductResponse.Success(product)
        )
        vm.onEvent(ProductsEvent.GetDetailForProduct(id))
        advanceUntilIdle()
        verify(useCases.getProductDetail, times(1)).invoke(id)
        assertEquals(vm.productScreenState.value.productDetail.name , "Indian Terrain T-Shirt Blue")
    }

    @Test
    fun `send error when product detail cannot be fetched`() = runTest{
        val id = "sdf5sdf"
        val product = ProductDetail(name = "Indian Terrain T-Shirt Blue")
        whenever(useCases.getProductDetail.invoke(id)).thenReturn(
            ProductResponse.Error("Error")
        )
        vm.onEvent(ProductsEvent.GetDetailForProduct(id))
        advanceUntilIdle()
        assertEquals(vm.productScreenState.value.productDetailErrorState.message , "Error")
    }

}