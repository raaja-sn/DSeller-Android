package com.drs.dseller.feature_user_session.presentation.screens

import android.app.Activity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.drs.dseller.R
import com.drs.dseller.core.ui_elements.dialog.DefaultBottomDialog
import com.drs.dseller.core.ui_elements.fonts.appFonts
import com.drs.dseller.feature_home.toHome
import com.drs.dseller.feature_products.presentation.toProducts
import com.drs.dseller.feature_user_session.onBoardUser
import com.drs.dseller.feature_user_session.presentation.states.SplashScreenState
import com.drs.dseller.feature_user_session.presentation.viewmodels.SplashEvent
import com.drs.dseller.feature_user_session.presentation.viewmodels.SplashScreenViewModel
import com.drs.dseller.ui.theme.Green40

@Composable
fun SplashScreen(
    state:SplashScreenState,
    vm:SplashScreenViewModel,
    navController:NavHostController
){


    val moveToSignIn = remember{
        {
            navController.onBoardUser()
        }
    }

    val moveToHome = remember{
        {
           navController.toHome()
        }
    }

    Box(){

        SplashScreenBody(state.isVerifyingSession)

        AnimatedVisibility(
            modifier = Modifier.align(Alignment.BottomCenter),
            visible = state.errorState.isError,
            enter = slideInVertically{it},
            exit = slideOutVertically{it }
        ) {
            DefaultBottomDialog(
                title = stringResource(id = R.string.error_title_default),
                message = state.errorState.message,
                background = Color.White,
                textColor = Green40,
                positiveText = stringResource(id = R.string.dialog_retry_text),
                negativeText = stringResource(id = R.string.dialog_close_text),
                positiveCallback = {
                    vm.onEvent(SplashEvent.VerifySession)
                },
                negativeCallback = {
                    vm.onEvent(SplashEvent.ChangeErrorState(false))
                }
            )
        }
    }

    if(state.shouldVerifySession){
        vm.onEvent(SplashEvent.GetUser)
    }

    DisplaySignIn(shouldSignIn = state.shouldSignIn,moveToSignIn)

    DisplayHome(state.user != null ,moveToHome)

    if(state.shouldSignIn || state.user != null){
        ChangeStatusBarColor(Color.White)
    }else{
        ChangeStatusBarColor(Green40)
    }

}

@Composable
private fun DisplaySignIn(shouldSignIn:Boolean, navigate:()->Unit){
    if(!shouldSignIn) return
    navigate()
}

@Composable
private fun DisplayHome(shouldDisplay:Boolean,navigate:()->Unit){
    if(!shouldDisplay) return
    navigate()
}

@Composable
fun SplashScreenBody(
   isVerifyingSession:Boolean
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Green40),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {

            Image(
                modifier = Modifier
                    .size(dimensionResource(id = R.dimen.seventy_dp)),
                painter = painterResource(id = R.drawable.dseller_logo),
                contentDescription = stringResource(id = R.string.description_logo)
            )
            Text(
                modifier = Modifier
                    .padding(horizontal = dimensionResource(id = R.dimen.ten_dp)),
                text = stringResource(id = R.string.app_name),
                style = TextStyle(
                    fontFamily = appFonts,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 40.sp,
                    letterSpacing = 2.sp
                ),
                color = Color.White
            )
        }

        if(isVerifyingSession){
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = dimensionResource(id = R.dimen.twenty_five_dp))
                    .padding(vertical = dimensionResource(id = R.dimen.fifteen_dp))
                    .size(dimensionResource(id = R.dimen.thirty_dp)),
                color = Color.White
            )
        }
    }
}

@Composable
private fun ChangeStatusBarColor(color:Color){
    val activity = (LocalView.current.context) as Activity
    activity.window.statusBarColor = color.toArgb()
}

@Preview
@Composable
fun SplashPreview(){
    SplashScreenBody(true )
}