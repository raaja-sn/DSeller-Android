package com.drs.dseller.feature_account.usecases

import com.drs.dseller.BaseTest
import com.drs.dseller.feature_account.domain.model.FullInvoice
import com.drs.dseller.feature_account.domain.repository.UserOrderRepository
import com.drs.dseller.feature_account.domain.usecases.GetInvoice
import com.drs.dseller.feature_account.response.AccountResponse
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class GetInvoiceShould: BaseTest() {

    private lateinit var repo:UserOrderRepository<AccountResponse<FullInvoice>>
    private lateinit var useCase:GetInvoice

    @Before
    fun init(){
        repo = mock()
        useCase = GetInvoice(repo)
    }

    @Test
    fun`get invoice for an order`()= runTest {
        val invoice = FullInvoice(invoiceNo = "Des2343240")
        val orderId = "4655"
        whenever(repo.getOrderDetail(orderId)).thenReturn(
            AccountResponse.Success(invoice)
        )
        val r = useCase.invoke(orderId)
        verify(repo, times(1)).getOrderDetail(orderId)
        assertEquals((r as AccountResponse.Success).data.invoiceNo,"Des2343240")
    }

    @Test
    fun`send error when order cannot be retrieved`()= runTest {
        val orderId = "4655"
        whenever(repo.getOrderDetail(orderId)).thenReturn(
            AccountResponse.Error("Error")
        )
        val r = useCase.invoke(orderId)
        assertEquals((r as AccountResponse.Error).message,"Error")
    }

}