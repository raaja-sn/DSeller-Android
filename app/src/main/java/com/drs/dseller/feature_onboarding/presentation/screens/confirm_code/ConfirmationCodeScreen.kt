@file:OptIn(ExperimentalMaterial3Api::class)

package com.drs.dseller.feature_onboarding.presentation.screens.confirm_code

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.drs.dseller.R
import com.drs.dseller.core.ui_elements.dialog.DefaultBottomDialog
import com.drs.dseller.feature_onboarding.presentation.states.ConfirmUserState
import com.drs.dseller.feature_onboarding.presentation.toSplash
import com.drs.dseller.feature_onboarding.presentation.viewmodels.ConfirmCodeEvent
import com.drs.dseller.feature_onboarding.presentation.viewmodels.OnBoardingViewModel

@Composable
fun ConfirmationCodeScreen(
    state:ConfirmUserState,
    vm:OnBoardingViewModel,
    navController:NavHostController
) {
    val codeChanged = remember{
        codeChange@{ text:String ->
            if(text.length>6)return@codeChange
            vm.onConfirmCodeEvent(ConfirmCodeEvent.ConfirmCodeChanged(text))
        }
    }

    val confirmCode = remember{
        confirmCode@{
            if(state.isConfirming) return@confirmCode
            vm.onConfirmCodeEvent(ConfirmCodeEvent.ConfirmRegistrationCode)
        }
    }

    val moveToSplash = remember{
        {
            navController.toSplash()
        }
    }

    val resendCodeListener = remember{
        {

        }
    }

    Box(){

        ConfirmationCodeBody(
            state = state,
            codeChangeListener = codeChanged,
            confirmListener = confirmCode,
            resendCodeListener = resendCodeListener,
        )

        AnimatedVisibility(
            modifier = Modifier.align(Alignment.BottomCenter),
            visible = state.errorState.isError,
            enter = slideInVertically { it },
            exit = slideOutVertically { it }
        ) {
            DefaultBottomDialog(
                title = stringResource(id = R.string.error_title_error),
                message = state.errorState.message,
                positiveText = null,
                negativeText = stringResource(id = R.string.dialog_close_text),
                positiveCallback = null,
                negativeCallback = {
                    vm.onConfirmCodeEvent(ConfirmCodeEvent.ChangeErrorState(false))
                })
        }

    }

    DisplaySplash(state.isCodeConfirmationComplete,moveToSplash)
}

@Composable
private fun DisplaySplash(shouldDisplay:Boolean,navigate:() -> Unit){
    if(!shouldDisplay) return
    navigate()
}


