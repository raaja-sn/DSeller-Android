package com.drs.dseller.core.domain.usecases

import com.drs.dseller.core.domain.model.AppUser
import com.drs.dseller.core.domain.repository.SessionRepository
import com.drs.dseller.core.response.SessionResponse
import javax.inject.Inject

/**
 * Get the existing user
 *
 * @param repo Repository object to perform the shopper or seller login
 */
class GetUser @Inject constructor(
    private val repo: SessionRepository<SessionResponse<AppUser>>
) {

    suspend operator fun invoke() =
        repo.getUser()
}