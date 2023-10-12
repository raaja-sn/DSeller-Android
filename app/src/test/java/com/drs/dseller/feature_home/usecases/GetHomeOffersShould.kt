package com.drs.dseller.feature_home.usecases

import com.drs.dseller.BaseTest
import com.drs.dseller.feature_home.HomeMocks
import com.drs.dseller.feature_home.domain.model.HomeOffer
import com.drs.dseller.feature_home.domain.repository.OffersRepository
import com.drs.dseller.feature_home.domain.usecases.GetHomeOffers
import com.drs.dseller.feature_home.response.HomeResponse
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class GetHomeOffersShould:BaseTest() {

    private lateinit var repo:OffersRepository<HomeResponse<List<HomeOffer>>>
    private lateinit var useCase:GetHomeOffers

    @Before
    fun init(){
        repo = mock()
        useCase = GetHomeOffers(repo)
    }

    @Test
    fun`get product offers`() = runTest{
        val offers = HomeMocks.getOffers()
        whenever(repo.getHomeOffers()).thenReturn(
            HomeResponse.Success(offers)
        )
        val r = useCase.invoke()
        verify(repo, times(1)).getHomeOffers()
        assertEquals((r as HomeResponse.Success).result,offers)
    }


}