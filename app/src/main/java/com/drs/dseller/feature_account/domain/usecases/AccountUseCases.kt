package com.drs.dseller.feature_account.domain.usecases

import javax.inject.Inject

data class AccountUseCases @Inject constructor(
    val updateUser: UpdateUser,
    val getUserOrders: GetUserOrders,
    val getInvoice: GetInvoice
) {
}