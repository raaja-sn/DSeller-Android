package com.drs.dseller.core.domain.repository

/**
 * Repository interface to maintain User Authorization Info
 */
interface AuthRepository {

    /**
     * Get the current auth token for this session. If the token is expired get a refreshed token.
     *
     * @param shouldRefresh Flag to force refresh teh auth token before returning
     * @return The auth token for the user session
     */
    suspend fun getAuthToken(shouldRefresh:Boolean):String

}