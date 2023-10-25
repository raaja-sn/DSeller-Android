package com.drs.dseller.feature_account.usecases

import androidx.paging.PagingData
import com.drs.dseller.BaseTest
import com.drs.dseller.feature_account.AccountMocks
import com.drs.dseller.feature_account.domain.model.UserOrder
import com.drs.dseller.feature_account.domain.model.UserOrderFilter
import com.drs.dseller.feature_account.domain.repository.UserOrderListRepository
import com.drs.dseller.feature_account.domain.usecases.GetUserOrders
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class GetUserOrdersShould : BaseTest() {

    private lateinit var repo:UserOrderListRepository<PagingData<UserOrder>,UserOrderFilter>
    private lateinit var useCase:GetUserOrders
    private lateinit var mock:AccountMocks

    @Before
    fun init(){
        mock = AccountMocks()
        repo = mock()
        useCase  = GetUserOrders(repo)
    }

    @Test
    fun `get list of user orders`() = runTest{
        val orders = mock.getOrdersList()
        val filter = UserOrderFilter("4535354")
        whenever(repo.getUserOrders(filter)).thenReturn(
            flow {
                emit(PagingData.from(orders))
            }
        )
        useCase.invoke(filter).collect{
            verify(repo,times(1)).getUserOrders(filter)
        }
    }

}