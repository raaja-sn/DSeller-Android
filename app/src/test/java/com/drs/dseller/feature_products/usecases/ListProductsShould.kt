package com.drs.dseller.feature_products.usecases

import androidx.paging.PagingData
import com.drs.dseller.BaseTest
import com.drs.dseller.feature_products.ProductMocks
import com.drs.dseller.feature_products.domain.model.Product
import com.drs.dseller.feature_products.domain.model.ProductSearchFilter
import com.drs.dseller.feature_products.domain.repository.ProductListRepository
import com.drs.dseller.feature_products.domain.usecases.ListProducts
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class ListProductsShould:BaseTest() {

    private lateinit var repo:ProductListRepository<PagingData<Product>,ProductSearchFilter>
    private lateinit var useCase:ListProducts
    private lateinit var mock:ProductMocks

    @Before
    fun init(){
        mock = ProductMocks()
        repo = mock()
        useCase = ListProducts(repo)
    }

    @Test
    fun`get list of products`() = runTest {
        val products = mock.getProductList()
        val filter = ProductSearchFilter()
        whenever(repo.getProductsList(filter)).thenReturn(
            flow {
                emit(PagingData.from(products))
            }
        )
        val r = useCase.invoke(filter)
        advanceUntilIdle()
        r.collect{ data ->
            verify(repo, times(1)).getProductsList(filter)
        }
    }

}