package com.drs.dseller.feature_user_session.usecases

import com.drs.dseller.BaseTest
import com.drs.dseller.core.domain.model.AppUser
import com.drs.dseller.core.domain.repository.SessionRepository
import com.drs.dseller.core.domain.usecases.LogoutUser
import com.drs.dseller.core.response.SessionResponse
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class LogoutUserShould :BaseTest() {

    private lateinit var repo: SessionRepository<SessionResponse<AppUser>>
    private lateinit var useCase: LogoutUser

    @Before
    fun init() {
        repo = mock()
        useCase = LogoutUser(repo)
    }

    @Test
    fun `logout the current user`() = runTest {
        whenever(repo.logoutUser()).thenReturn(
            SessionResponse.Success(AppUser())
        )
        val r = useCase.invoke()
        verify(repo, times(1)).logoutUser()
        assertTrue(r is SessionResponse.Success)
    }

    @Test
    fun `send error when user cannot be logged out`() = runTest {
        whenever(repo.logoutUser()).thenReturn(
            SessionResponse.Error("Error")
        )
        val r = useCase.invoke()
        assertEquals((r as SessionResponse.Error).message,"Error")
    }

}