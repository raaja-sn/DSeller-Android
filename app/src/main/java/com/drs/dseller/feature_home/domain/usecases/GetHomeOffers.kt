package com.drs.dseller.feature_home.domain.usecases

import com.drs.dseller.feature_home.domain.model.HomeOffer
import com.drs.dseller.feature_home.domain.repository.OffersRepository
import com.drs.dseller.feature_home.response.HomeResponse
import javax.inject.Inject

/**
 * Get Home Offers
 *
 * @param repo Repository object to get home screen offers
 */
class GetHomeOffers @Inject constructor(
    private val repo:OffersRepository<HomeResponse<List<HomeOffer>>>
) {

    suspend operator fun invoke() =
        repo.getHomeOffers()

}