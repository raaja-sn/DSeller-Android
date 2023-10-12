package com.drs.dseller.feature_home.domain.repository

/**
 * Repository for getting product offers.
 *
 * @param R The result of operations. Preferably sealed class with success and error results.
 */
interface OffersRepository<R>{

    /**
     * Get offers for home screen
     *
     * @return [R] The result of the get offers event
     */
    suspend fun getHomeOffers():R

}