package com.drs.dseller

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.drs.dseller.core.navigation.getViewModel
import com.drs.dseller.feature_account.presentation.Account
import com.drs.dseller.feature_home.Home
import com.drs.dseller.feature_onboarding.presentation.Onboard
import com.drs.dseller.feature_orders.presentation.Cart
import com.drs.dseller.feature_products.presentation.Products
import com.drs.dseller.feature_user_session.presentation.screens.SplashScreen
import com.drs.dseller.feature_user_session.presentation.viewmodels.SplashScreenViewModel
import com.drs.dseller.ui.theme.DSellerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DSellerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DSellerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {

                    val navController = rememberNavController()

                    NavHost(navController, startDestination = "splash"){

                        composable("splash"){
                            val vm:SplashScreenViewModel = it.getViewModel(navController = navController)
                            SplashScreen(
                                state = vm.splashState.value,
                                vm = vm , navController = navController)
                        }

                        Onboard(navController = navController)

                        Home(navHostController =navController)
                        Products(navHostController =navController)
                        Cart(navHostController =navController)
                        Account(navHostController =navController)
                    }

                }
            }
        }

    }
}