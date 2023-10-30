package com.drs.dseller.feature_account.domain.usecases

import com.drs.dseller.feature_account.domain.model.AccountUser
import com.drs.dseller.feature_account.domain.repository.AccountRepository
import com.drs.dseller.feature_account.response.AccountResponse
import javax.inject.Inject

/**
 * Update the user details
 *
 * @param repo Account repository used to update user details
 */
class UpdateUser @Inject constructor(
    private val repo:AccountRepository<AccountUser,AccountResponse<AccountUser>>
) {

    suspend operator fun invoke(user:AccountUser) =
        repo.updateUser(user)

}