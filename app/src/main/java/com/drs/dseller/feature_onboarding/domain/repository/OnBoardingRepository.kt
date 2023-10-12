package com.drs.dseller.feature_onboarding.domain.repository

/**
 * Repository Interface to work with Shoppers and sellers
 * @param U The user object for login or registration, containing mandatory fields like name,email and phone number
 * @param R The result of on boarding operation . Preferably sealed class with success and error results.
 */
interface OnBoardingRepository<U,R>{

    /**
     * Register a user
     * @param user The user object containing mandatory registration fields like email, password, name .etc
     *
     * @return [R] The result of the registration event
     */
    suspend fun registerUser(user:U):R

    /**
     * Login a user
     * @param user The user object containing mandatory login fields like email, password .etc
     *
     * @return [R] The result of the registration event
     */
    suspend fun loginUser(user:U):R




}