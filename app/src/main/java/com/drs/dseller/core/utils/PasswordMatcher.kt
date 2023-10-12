package com.drs.dseller.core.utils

class PasswordMatcher {

    companion object{
        fun match(password:String):Boolean{
            val pattern = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[*@!_.-])[a-zA-Z\\d@!*_.-]{8,25}$"
            return Regex(pattern).matches(password)
        }
    }
}