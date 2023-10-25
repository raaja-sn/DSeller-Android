package com.drs.dseller.core.utils

import java.text.SimpleDateFormat
import java.util.Currency
import java.util.Date
import java.util.Locale

class AppUtils {

    companion object{

        fun getCurrencySymbol(
            language:String,
            country:String
        ):String{
            return Currency.getInstance(Locale(language, country)).symbol
        }

        fun getFormattedDate(
            dateInMillis:Long
        ):String{
            return SimpleDateFormat("MMM-dd-YYYY").format(Date(dateInMillis))
        }
    }
}