package com.drs.dseller

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.amplifyframework.auth.AuthUserAttribute
import com.amplifyframework.auth.AuthUserAttributeKey
import com.amplifyframework.auth.cognito.AWSCognitoAuthSession
import com.amplifyframework.auth.options.AuthFetchSessionOptions
import com.amplifyframework.auth.options.AuthSignUpOptions
import com.amplifyframework.kotlin.core.Amplify
import com.drs.dseller.feature_home.presentation.HomeViewModel
import com.drs.dseller.feature_home.presentation.screens.HomeScreen
import com.drs.dseller.feature_onboarding.presentation.screens.confirm_code.ConfirmationCodeScreen
import com.drs.dseller.feature_onboarding.presentation.screens.login.SignInScreen
import com.drs.dseller.feature_onboarding.presentation.screens.signup.SignUpScreen
import com.drs.dseller.feature_onboarding.presentation.toOnboard
import com.drs.dseller.feature_onboarding.presentation.viewmodels.OnBoardingViewModel
import com.drs.dseller.feature_user_session.presentation.screens.SplashScreen
import com.drs.dseller.feature_user_session.presentation.viewmodels.SplashScreenViewModel
import com.drs.dseller.ui.theme.DSellerTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
@AndroidEntryPoint
class DSellerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DSellerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val navController = rememberNavController()

                    NavHost(navController, startDestination = "splash"){

                        composable("splash"){
                            val vm:SplashScreenViewModel = it.getViewModel(navController = navController)
                            SplashScreen(
                                state = vm.splashState.value,
                                vm = vm , navController = navController)
                        }

                        toOnboard(navController = navController)

                        composable("home"){
                            val vm:HomeViewModel = it.getViewModel(navController = navController)

                            HomeScreen(
                                state = vm.homeState.value,
                                vm,
                                navController
                            )

                        }
                    }

                }
            }
        }

    }


}


@Composable
inline fun <reified V:ViewModel> NavBackStackEntry.getViewModel(navController: NavHostController):V{
    val parentRoute = destination.parent?.route?:return hiltViewModel()
    val pEntry = remember(this){
        navController.getBackStackEntry(parentRoute)
    }
    return hiltViewModel(pEntry)
}



@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DSellerTheme {
        Greeting("Android")
    }
}