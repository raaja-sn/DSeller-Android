package com.drs.dseller.feature_user_session.usecases

import com.drs.dseller.BaseTest
import com.drs.dseller.core.domain.model.AppUser
import com.drs.dseller.core.domain.repository.SessionRepository
import com.drs.dseller.core.domain.usecases.GetUser
import com.drs.dseller.core.response.SessionResponse
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class GetUserShould :BaseTest() {

    private lateinit var repo: SessionRepository<SessionResponse<AppUser>>
    private lateinit var useCase: GetUser

    @Before
    fun init(){
        repo = mock()
        useCase = GetUser(repo)
    }


    @Test
    fun `get the current user`() = runTest{
        val appuser = AppUser("Raaja","test@gmail.com")
        whenever(repo.getUser(false)).thenReturn(
            SessionResponse.Success(appuser)
        )
        val r = useCase.invoke()
        verify(repo,times(1)).getUser(false)
        assertEquals((r as SessionResponse.Success).data.fullname , "Raaja")
    }

    @Test
    fun `send error when current user session in unavailable`() = runTest{
        whenever(repo.getUser(false)).thenReturn(
            SessionResponse.Error("Unavailable")
        )
        val r = useCase.invoke()
        assertEquals((r as SessionResponse.Error).message , "Unavailable")
    }
}