package com.drs.dseller.feature_account

import androidx.paging.PagingData
import com.drs.dseller.BaseTest
import com.drs.dseller.cart.CartMock
import com.drs.dseller.core.domain.model.AppUser
import com.drs.dseller.core.domain.model.shopping_cart.CartProduct
import com.drs.dseller.core.domain.usecases.UserSessionUseCases
import com.drs.dseller.core.domain.usecases.shopping_cart_use_cases.ShoppingCartUseCases
import com.drs.dseller.core.response.SessionResponse
import com.drs.dseller.feature_account.domain.model.AccountUser
import com.drs.dseller.feature_account.domain.model.FullInvoice
import com.drs.dseller.feature_account.domain.model.UserOrderFilter
import com.drs.dseller.feature_account.domain.usecases.AccountUseCases
import com.drs.dseller.feature_account.presentation.UserAccountDetailEvent
import com.drs.dseller.feature_account.presentation.UserAccountEvent
import com.drs.dseller.feature_account.presentation.UserAccountViewModel
import com.drs.dseller.feature_account.presentation.UserInvoiceEvent
import com.drs.dseller.feature_account.presentation.UserOrdersEvent
import com.drs.dseller.feature_account.response.AccountResponse
import com.drs.dseller.feature_user_session.SessionMocks
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class UserAccountViewModelShould:BaseTest() {

    private lateinit var accountUseCases: AccountUseCases
    private lateinit var cartUseCases: ShoppingCartUseCases
    private lateinit var usersessionUseCases:UserSessionUseCases
    private val mock = AccountMocks()
    private lateinit var vm :UserAccountViewModel

    @Before
    fun init(){
        accountUseCases = mock.getUseCases()
        cartUseCases = CartMock().getUseCases()
        usersessionUseCases = SessionMocks().getUseCases()
        whenever(cartUseCases.getAllProducts.invoke()).thenReturn(
            MutableStateFlow(listOf())
        )
        vm = UserAccountViewModel(accountUseCases,usersessionUseCases,cartUseCases)
    }

    @Test
    fun`get account user`() = runTest {
        val mockUser = AppUser(
            fullname = "Raaja"
        )
        whenever(usersessionUseCases.getUser.invoke()).thenReturn(
            SessionResponse.Success(mockUser)
        )
        vm.onUserAccountEvent(UserAccountEvent.GetAccountUser)
        advanceUntilIdle()
        verify(usersessionUseCases.getUser, times(1)).invoke()
        assertEquals(vm.userAccountState.value.user?.name,"Raaja")
    }

    @Test
    fun`send error when user cannot be retrieved`() = runTest {
        whenever(usersessionUseCases.getUser.invoke()).thenReturn(
            SessionResponse.Error("Error")
        )
        vm.onUserAccountEvent(UserAccountEvent.GetAccountUser)
        advanceUntilIdle()
        assertEquals(vm.userAccountState.value.user,null)
    }

    @Test
    fun`update user`() = runTest {
        val mockUser = AppUser(
            fullname = "Raaja",
            phoneNumber = "+918459856852"
        )
        val updatedUser = AccountUser(
            name = "Test User",
            phoneNumber = "+918459856852"
        )
        whenever(accountUseCases.updateUser.invoke(updatedUser)).thenReturn(
            AccountResponse.Success(updatedUser)
        )
        whenever(usersessionUseCases.getUser.invoke()).thenReturn(
            SessionResponse.Success(mockUser)
        )
        vm.onUserAccountDetailEvent(UserAccountDetailEvent.GetAccountUser)
        advanceUntilIdle()
        vm.onUserAccountDetailEvent(UserAccountDetailEvent.ChangeName("Test User"))
        vm.onUserAccountDetailEvent(UserAccountDetailEvent.UpdateUser)
        advanceUntilIdle()
        verify(accountUseCases.updateUser, times(1)).invoke(updatedUser)
        assertEquals(vm.userAccountDetailState.value.user?.name,"Test User")

    }

    @Test
    fun`send error wheh user cannot be updated`() = runTest {
        val mockUser = AppUser(
            fullname = "Raaja",
            phoneNumber = "+918459856852"
        )
        val updatedUser = AccountUser(
            name = "Test User",
            phoneNumber = "+918459856852"
        )
        whenever(accountUseCases.updateUser.invoke(updatedUser)).thenReturn(
            AccountResponse.Error("Error")
        )
        whenever(usersessionUseCases.getUser.invoke()).thenReturn(
            SessionResponse.Success(mockUser)
        )
        vm.onUserAccountDetailEvent(UserAccountDetailEvent.GetAccountUser)
        advanceUntilIdle()
        vm.onUserAccountDetailEvent(UserAccountDetailEvent.ChangeName("Test User"))
        vm.onUserAccountDetailEvent(UserAccountDetailEvent.UpdateUser)
        advanceUntilIdle()
        assertEquals(vm.userAccountDetailState.value.errorState.message,"Error")
        assertEquals(vm.userAccountDetailState.value.user?.name,"Raaja")


    }

    @Test
    fun`don't update user if the user haven't made changes`() = runTest {
        val mockUser = AppUser(
            fullname = "Raaja",
            phoneNumber = "+918459856852"
        )
        val updatedUser = AccountUser(
            name = "Test User",
            phoneNumber = "+918459856852"
        )
        whenever(accountUseCases.updateUser.invoke(updatedUser)).thenReturn(
            AccountResponse.Success(updatedUser)
        )
        whenever(usersessionUseCases.getUser.invoke()).thenReturn(
            SessionResponse.Success(mockUser)
        )
        vm.onUserAccountDetailEvent(UserAccountDetailEvent.GetAccountUser)
        advanceUntilIdle()
        vm.onUserAccountDetailEvent(UserAccountDetailEvent.UpdateUser)
        advanceUntilIdle()
        verify(accountUseCases.updateUser,times(0)).invoke(updatedUser)
        assertEquals(vm.userAccountDetailState.value.user?.name,"Raaja")
    }

    @Test
    fun`get Orders List`() = runTest {
        val filter = UserOrderFilter("4567", pageNumber = 1, pageSize = 20)
        val mockUser = AppUser(
            userId = filter.userId,
            fullname = "Raaja",
            phoneNumber = "+918459856852"
        )

        whenever(usersessionUseCases.getUser.invoke()).thenReturn(
            SessionResponse.Success(mockUser)
        )
        whenever(accountUseCases.getUserOrders.invoke(filter)).thenReturn(
            flow{
                emit(PagingData.from(mock.getOrdersList()))
            }
        )

        vm.onUserAccountEvent(UserAccountEvent.GetAccountUser)
        vm.onOrdersEvent(UserOrdersEvent.ListOrders)
        val job = launch() {
            vm.userOrdersState.value.userOrders.collect{
                verify(accountUseCases.getUserOrders, times(1)).invoke(filter)
            }
        }
        advanceUntilIdle()
        job.cancel()
    }

    @Test
    fun `get invoice`() = runTest {
        val orderId ="45678"
        val order = FullInvoice(invoiceNo = "DES2554455")
        whenever(accountUseCases.getInvoice.invoke(orderId)).thenReturn(
            AccountResponse.Success(order)
        )
        vm.onUserInvoiceEvent(UserInvoiceEvent.SetOrderId(orderId))
        advanceUntilIdle()
        verify(accountUseCases.getInvoice, times(1)).invoke(orderId)
        assertEquals(vm.userInvoiceState.value.invoice?.invoiceNo,"DES2554455")
    }

    @Test
    fun `send error when invoice cannot be fetched`() = runTest {
        val orderId ="45678"
        val order = FullInvoice(invoiceNo = "DES2554455")
        whenever(accountUseCases.getInvoice.invoke(orderId)).thenReturn(
            AccountResponse.Error("Error")
        )
        vm.onUserInvoiceEvent(UserInvoiceEvent.SetOrderId(orderId))
        vm.onUserInvoiceEvent(UserInvoiceEvent.GetInvoice)
        advanceUntilIdle()
        assertEquals(vm.userInvoiceState.value.errorState.message,"Error")
        assertEquals(vm.userInvoiceState.value.invoice,null)
    }

}