package com.drs.dseller.feature_products.usecases

import com.drs.dseller.BaseTest
import com.drs.dseller.feature_products.domain.model.ProductDetail
import com.drs.dseller.feature_products.domain.repository.ProductRepository
import com.drs.dseller.feature_products.domain.usecases.GetProductDetail
import com.drs.dseller.feature_products.response.ProductResponse
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class GetProductDetailShould:BaseTest() {

    private lateinit var repo:ProductRepository<ProductResponse<ProductDetail>>
    private lateinit var useCase:GetProductDetail

    @Before
    fun init(){
        repo = mock()
        useCase = GetProductDetail(repo)
    }

    @Test
    fun`get product detail`() = runTest{
        val id = "s5s5ds"
        val product = ProductDetail(name = "Indian Terrain T-Shirt Blue")
        whenever(repo.getProductDetail(id)).thenReturn(
            ProductResponse.Success(product)
        )
        val r = useCase.invoke(id)
        verify(repo, times(1)).getProductDetail(id)
        assertEquals((r as ProductResponse.Success).result.name,"Indian Terrain T-Shirt Blue")
    }

    @Test
    fun`send error details in unavailable`() = runTest{
        val id = "s5s5ds"
        whenever(repo.getProductDetail(id)).thenReturn(
            ProductResponse.Error("Error")
        )
        val r = useCase.invoke(id)
        assertEquals((r as ProductResponse.Error).message,"Error")
    }

}