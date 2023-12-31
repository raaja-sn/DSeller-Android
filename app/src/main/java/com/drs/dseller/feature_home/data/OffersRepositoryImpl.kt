package com.drs.dseller.feature_home.data

import com.drs.dseller.feature_home.domain.model.HomeOffer
import com.drs.dseller.feature_home.domain.repository.OffersRepository
import com.drs.dseller.feature_home.response.HomeResponse

class OffersRepositoryImpl: OffersRepository<HomeResponse<List<HomeOffer>>> {

    override suspend fun getHomeOffers(): HomeResponse<List<HomeOffer>> {
        return HomeResponse.Success(listOf(
            HomeOffer(image = "https://dseller.s3.ap-south-1.amazonaws.com/offers/banner1.png"),
            HomeOffer(image = "https://dseller.s3.ap-south-1.amazonaws.com/offers/banner2.png"),
            HomeOffer(image = "https://dseller.s3.ap-south-1.amazonaws.com/offers/banner3.png")
        ))
    }
}