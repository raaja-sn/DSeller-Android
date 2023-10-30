@file:OptIn(ExperimentalMaterial3Api::class)

package com.drs.dseller.feature_account.presentation.screens.account_detail

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.drs.dseller.R
import com.drs.dseller.core.ui_elements.appbar.AppBottomNavigationBar
import com.drs.dseller.core.ui_elements.appbar.DefaultAppBar
import com.drs.dseller.core.ui_elements.dialog.DefaultBottomDialog
import com.drs.dseller.feature_account.presentation.UserAccountDetailEvent
import com.drs.dseller.feature_account.presentation.UserAccountViewModel
import com.drs.dseller.feature_account.presentation.states.AccountBottomNavigationBarState
import com.drs.dseller.feature_account.presentation.states.AccountDetailScreenState
import com.drs.dseller.feature_account.presentation.states.hasValidPhoneNumber
import com.drs.dseller.feature_account.presentation.states.hasValidUserName

@Composable
fun AccountDetailScreen(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    state:AccountDetailScreenState,
    bottomNavState:AccountBottomNavigationBarState,
    vm:UserAccountViewModel,
    navHostController: NavHostController
){

    DisposableEffect(key1 = lifecycleOwner){

        val observer = object:DefaultLifecycleObserver{
            override fun onResume(owner: LifecycleOwner) {
                super.onResume(owner)
                if(state.user != null)return
                vm.onUserAccountDetailEvent(UserAccountDetailEvent.GetAccountUser)
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    val currContext = LocalContext.current

    val nameChanged = remember {
        {name:String ->
            vm.onUserAccountDetailEvent(UserAccountDetailEvent.ChangeName(name))
        }
    }
    val phoneNumberChanged = remember {
        changePhone@{phone:String ->
            if(phone.length >10)return@changePhone
            vm.onUserAccountDetailEvent(UserAccountDetailEvent.ChangePhoneNumber(phone))
        }
    }
    
    val update = remember{
        {
            vm.onUserAccountDetailEvent(UserAccountDetailEvent.ValidateUserDetails(true))
        }
    }
    
    val navClicked:()->Unit = remember{
        {
            navHostController.popBackStack()
        }
    }

    val dlgClose = remember {
        {
            vm.onUserAccountDetailEvent(UserAccountDetailEvent.ChangeErrorState(false,""))
        }
    }
    
    val cartItems = bottomNavState.cartFlow.collectAsStateWithLifecycle().value
    
    val topAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    
    Scaffold(
        topBar = {
            DefaultAppBar(
                scrollBehavior = topAppBarScrollBehavior,
                title = stringResource(id = R.string.account_detail),
                navIconClicked = navClicked
            )
        },
        bottomBar = {
            AppBottomNavigationBar(
                cartQuantity = cartItems.size,
                navHostController = navHostController
            )
        },
        containerColor = Color.White
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            
            AccountDetailScreenBody(
                state = state,
                nameChanged = nameChanged ,
                phoneNumberChanged = phoneNumberChanged,
                update = update
            )
            
            AnimatedVisibility(
                modifier = Modifier
                    .align(Alignment.BottomCenter),
                visible = state.errorState.isError,
                enter = slideInVertically{it},
                exit = slideOutVertically { it }
            ) {
                
                DefaultBottomDialog(
                    title = stringResource(id = R.string.error_title_error),
                    message = state.errorState.message,
                    positiveText = stringResource(id = R.string.dialog_positive_text),
                    negativeText = null,
                    positiveCallback = dlgClose,
                    negativeCallback = null
                )
            }
        }
    }

    SideEffect {
        if(!state.shouldDisplaySuccessInfo) return@SideEffect
        vm.onUserAccountDetailEvent(UserAccountDetailEvent.DisplayOrHIdeSuccessInfo(false))
        Toast.makeText(currContext,currContext.getString(R.string.account_detail_update_success),Toast.LENGTH_SHORT).show()
    }

    ValidateDetails(state = state, shouldValidate = state.shouldValidate, vm = vm )

}

/**
 * Validate the user details before updating
 */
@Composable
private fun ValidateDetails(
    state: AccountDetailScreenState,
    shouldValidate:Boolean,
    vm:UserAccountViewModel
){

    if(!shouldValidate) return
    vm.onUserAccountDetailEvent(UserAccountDetailEvent.ValidateUserDetails(false)) //Run validation once and avoid recomposition

    if(!state.hasValidUserName()){
        vm.onUserAccountDetailEvent(UserAccountDetailEvent.ChangeErrorState(
            true,
            stringResource(id = R.string.signup_error_username)
        ))
        return
    }
    if(!state.hasValidPhoneNumber()){
        vm.onUserAccountDetailEvent(UserAccountDetailEvent.ChangeErrorState(
            true,
            stringResource(id = R.string.signup_error_phone)
        ))
        return
    }
    vm.onUserAccountDetailEvent(UserAccountDetailEvent.UpdateUser)
}