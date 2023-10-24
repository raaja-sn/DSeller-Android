package com.drs.dseller.feature_account.data.repository

import com.drs.dseller.core.network.ServiceGenerator
import com.drs.dseller.core.utils.NetworkUtils
import com.drs.dseller.feature_account.data.network.UserInvoiceService
import com.drs.dseller.feature_account.domain.model.FullInvoice
import com.drs.dseller.feature_account.domain.repository.UserOrderRepository
import com.drs.dseller.feature_account.response.AccountResponse

class UserOrderRepositoryImpl(
    val serviceGenerator: ServiceGenerator
) : UserOrderRepository<AccountResponse<FullInvoice>> {

    private val invoiceService = serviceGenerator.generateService(UserInvoiceService::class.java)

    override suspend fun getOrderDetail(orderId: String): AccountResponse<FullInvoice> {
        return try{
            val resp = invoiceService.getInvoice(orderId)
            if(resp.code() == 200){
                AccountResponse.Success(resp.body()?:FullInvoice())
            }else{
                throw(Exception(NetworkUtils.parseErrorResponse(resp.errorBody()).message))
            }
        }catch(e:Exception){
            AccountResponse.Error(e.message?:"")
        }
    }
}