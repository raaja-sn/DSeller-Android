package com.drs.dseller.feature_onboarding.data.repository

import com.amplifyframework.auth.AuthUserAttribute
import com.amplifyframework.auth.AuthUserAttributeKey
import com.amplifyframework.auth.cognito.exceptions.service.UserNotFoundException
import com.amplifyframework.auth.exceptions.NotAuthorizedException
import com.amplifyframework.auth.options.AuthSignUpOptions
import com.amplifyframework.kotlin.core.Amplify
import com.drs.dseller.core.constants.AppConstants
import com.drs.dseller.core.domain.model.AppUserWithPassword
import com.drs.dseller.core.network.ServiceGenerator
import com.drs.dseller.core.utils.NetworkUtils
import com.drs.dseller.feature_onboarding.data.network.OnBoardingService
import com.drs.dseller.feature_onboarding.domain.repository.OnBoardingWithConfirmCodeRepository
import com.drs.dseller.feature_onboarding.response.OnBoardingResponse

class OnBoardingWithConfirmCodeImpl(
    private val serviceGenerator:ServiceGenerator
) : OnBoardingWithConfirmCodeRepository<AppUserWithPassword, OnBoardingResponse<Unit>> {

    override suspend fun registerUser(user: AppUserWithPassword): OnBoardingResponse<Unit> {
        return try{
            val signUpOptions = AuthSignUpOptions.builder().apply {
                this.userAttributes(listOf(
                    AuthUserAttribute(AuthUserAttributeKey.name(),user.fullname),
                    AuthUserAttribute(AuthUserAttributeKey.phoneNumber(),user.phoneNumber),
                    AuthUserAttribute(AuthUserAttributeKey.custom(AppConstants.D_SELLER_USER_ID_ATTRIBUTE_KEY),"userID")
                ))
            }.build()
            Amplify.Auth.signUp(
                user.email,
                user.password,
                signUpOptions
            )
            OnBoardingResponse.Success(Unit)
        }catch(e:Exception){
            OnBoardingResponse.Error(e.message?:"")
        }
    }

    override suspend fun loginUser(user: AppUserWithPassword): OnBoardingResponse<Unit> {
        return try{
            if(!Amplify.Auth.fetchAuthSession().isSignedIn){
                Amplify.Auth.signIn(
                    user.email,
                    user.password
                )
            }
            val attrs = Amplify.Auth.fetchUserAttributes()
            var hasCustomUserId = false
            for(attribute in attrs){
                if(attribute.key == AuthUserAttributeKey.custom(AppConstants.D_SELLER_USER_ID_ATTRIBUTE_KEY)){
                    hasCustomUserId = true
                }
            }
            /* If the user is not saved in the backend user database.Add The user to the backend
                and save the user id from the backend as a custom attribute in Cognito user pool
             */
            if(!hasCustomUserId){
                val result = addNewUserToBackend()
                if(!result.first) throw Exception(result.second)

                addCustomUserIdAsCognitoAttribute(result.second)
            }
            OnBoardingResponse.Success(Unit)
        }catch(e:Exception){
            val message = when(e){
                is UserNotFoundException, is NotAuthorizedException ->{
                    "Email or password is incorrect. Verify and try again."
                }
                else ->e.message ?:""
            }
            OnBoardingResponse.Error(message)
        }
    }

    override suspend fun confirmCodeForRegistration(user: AppUserWithPassword, code: String): OnBoardingResponse<Unit> {
        return try{
            Amplify.Auth.confirmSignUp(user.email,code)
            Amplify.Auth.signIn(user.email,user.password)
            val result = addNewUserToBackend()
            if(!result.first) throw Exception(result.second)

            addCustomUserIdAsCognitoAttribute(result.second)
            OnBoardingResponse.Success(Unit)
        }catch(e:Exception){
            OnBoardingResponse.Error(e.message?:"")
        }
    }

    /**
     *  Adds the new user details from Cognito to the Backend Server and retrieve it's id from backend database.
     *
     *  @return Returns a Pair of object with the first value being a boolean, to flag if the operation is success or failure.
     *  If success the user ID is placed on the second entry.
     */
     private suspend fun addNewUserToBackend():Pair<Boolean,String>{
        val userAttrs = Amplify.Auth.fetchUserAttributes()
        var fullName = ""
        var email = ""
        var phoneNumber = ""
        for(attrs in userAttrs){
            if(attrs.key == AuthUserAttributeKey.name()) fullName = attrs.value
            if(attrs.key == AuthUserAttributeKey.email()) email = attrs.value
            if(attrs.key == AuthUserAttributeKey.phoneNumber()) phoneNumber = attrs.value

        }
        val resp = serviceGenerator.generateService(OnBoardingService::class.java)
            .createNewUser(
                fullName,
                email,
                phoneNumber
            )
        return if(resp.code() == 201){
            Pair(true,resp.body()?.Id?:"")
        }else{
            Pair(false,NetworkUtils.parseErrorResponse(resp.errorBody()).message)
        }
    }


    private suspend fun addCustomUserIdAsCognitoAttribute(userId:String){
        Amplify.Auth.updateUserAttribute(
            AuthUserAttribute(
                AuthUserAttributeKey.custom(AppConstants.D_SELLER_USER_ID_ATTRIBUTE_KEY),
                userId
            )
        )
    }
}