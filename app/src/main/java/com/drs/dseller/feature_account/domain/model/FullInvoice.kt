package com.drs.dseller.feature_account.domain.model

import java.util.Date

data class FullInvoice(
    val invoiceNo:String = "",
    val products:List<InvoiceProduct> = listOf(),
    val purchaseDate: Long = 0,
    val billValue:Double = 0.0,
    val shippingCost:Double = 0.0
)
