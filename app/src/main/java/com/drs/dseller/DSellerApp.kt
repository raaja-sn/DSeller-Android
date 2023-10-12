package com.drs.dseller

import android.app.Application
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.kotlin.core.Amplify
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class DSellerApp : Application() {

    override fun onCreate() {
        super.onCreate()

        try{
            Amplify.addPlugin(AWSCognitoAuthPlugin())
            Amplify.configure(applicationContext)
        }catch(e:Exception){
            println("Amplify Init - ${e.message}")
        }
    }
}