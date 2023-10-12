package com.drs.dseller.core.domain.repository

/**
 * Repository interface to maintain user sessions
 * @param R The result of user session operations . Preferably sealed class with success and error results.
 */
interface SessionRepository<R> {

    /**
     * Logout a user
     * @return [R] The result of the logout event
     */
    suspend fun logoutUser():R

    /**
     * Login a user
     *
     * @return [R] The logged user
     */
    suspend fun getUser():R
}