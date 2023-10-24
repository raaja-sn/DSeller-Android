package com.drs.dseller.feature_account.domain.repository

/**
 * Repository interface to work with user accounts, for updating user details
 *
 * @param U The user object containing mandatory fields like name, phone number which is to be updated
 * @param R The result of user account operation . Preferably sealed class with success and error results.
 */
interface AccountRepository<U,R> {

    /**
     * Update the user details.
     *
     * @param user The user object to update containing fields like name, phone number
     * @return [R] The result of update operation
     */
    suspend fun updateUser(user:U):R

}