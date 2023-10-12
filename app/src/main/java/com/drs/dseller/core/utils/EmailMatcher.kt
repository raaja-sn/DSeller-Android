package com.drs.dseller.core.utils

class EmailMatcher {

    companion object{
        fun match(email:String):Boolean{
            val pattern = "^[\\w-.]+@[\\w-]+\\.[a-z]{2,10}$"
            return Regex(pattern,RegexOption.IGNORE_CASE).matches(email)
        }
    }

}