package com.drs.dseller.feature_account.usecases

import com.drs.dseller.BaseTest
import com.drs.dseller.feature_account.domain.model.AccountUser
import com.drs.dseller.feature_account.domain.repository.AccountRepository
import com.drs.dseller.feature_account.domain.usecases.UpdateUser
import com.drs.dseller.feature_account.response.AccountResponse
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class UpdateUserShould :BaseTest() {

    private lateinit var repo:AccountRepository<AccountUser,AccountResponse<AccountUser>>
    private lateinit var useCase:UpdateUser

    @Before
    fun init(){
        repo = mock()
        useCase = UpdateUser(repo)
    }

    @Test
    fun`update user details`() = runTest {
        val updatedUser = AccountUser(name = "Test User")
        whenever(repo.updateUser(updatedUser)).thenReturn(
            AccountResponse.Success(updatedUser)
        )
        val r = useCase.invoke(updatedUser)
        verify(repo, times(1)).updateUser(updatedUser)
        assertEquals((r as AccountResponse.Success).data.name,"Test User")
    }

    @Test
    fun`send error when user cannot be updated`() = runTest {
        val updatedUser = AccountUser(name = "Test User")
        whenever(repo.updateUser(updatedUser)).thenReturn(
            AccountResponse.Error("Error")
        )
        val r = useCase.invoke(updatedUser)
        assertEquals((r as AccountResponse.Error).message,"Error")
    }

}