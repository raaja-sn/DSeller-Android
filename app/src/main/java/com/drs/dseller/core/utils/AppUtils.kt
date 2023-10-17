package com.drs.dseller.core.utils

import java.util.Currency
import java.util.Locale

class AppUtils {

    companion object{

        fun getCurrencySymbol(
            language:String,
            country:String
        ):String{
            return Currency.getInstance(Locale(language, country)).symbol
        }
    }
}