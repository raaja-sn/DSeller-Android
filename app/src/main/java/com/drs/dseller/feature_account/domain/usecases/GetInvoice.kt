package com.drs.dseller.feature_account.domain.usecases

import com.drs.dseller.feature_account.domain.model.FullInvoice
import com.drs.dseller.feature_account.domain.repository.UserOrderRepository
import com.drs.dseller.feature_account.response.AccountResponse
import javax.inject.Inject

/**
 * Get the full invoice of an order
 *
 * @param repo The repository to fetch invoice
 */
class GetInvoice @Inject constructor(
    private val repo:UserOrderRepository<AccountResponse<FullInvoice>>
){

    suspend operator fun invoke(orderId:String) =
        repo.getOrderDetail(orderId)

}
