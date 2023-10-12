package com.drs.dseller.feature_home.usecases

import com.drs.dseller.BaseTest
import com.drs.dseller.feature_home.HomeMocks
import com.drs.dseller.feature_home.domain.model.Category
import com.drs.dseller.feature_home.domain.repository.CategoryRepository
import com.drs.dseller.feature_home.domain.usecases.GetCategories
import com.drs.dseller.feature_home.response.HomeResponse
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class GetCategoriesShould:BaseTest() {

    private lateinit var repo:CategoryRepository<HomeResponse<List<Category>>>
    private lateinit var useCase:GetCategories

    @Before
    fun init(){
        repo = mock()
        useCase = GetCategories(repo)
    }

    @Test
    fun `get product categories`() = runTest {
        val categories= HomeMocks.getCategories()
        whenever(repo.getCategories()).thenReturn(
            HomeResponse.Success(categories)
        )
        val r = useCase.invoke()
        verify(repo, times(1)).getCategories()
        assertEquals((r as HomeResponse.Success).result,categories)
    }

    @Test
    fun `send error when categories cannot be fetched`() = runTest {
        whenever(repo.getCategories()).thenReturn(
            HomeResponse.Error("Error")
        )
        val r = useCase.invoke()
        assertEquals((r as HomeResponse.Error).message,"Error")
    }

}