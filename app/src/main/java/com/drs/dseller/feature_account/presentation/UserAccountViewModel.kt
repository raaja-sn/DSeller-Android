package com.drs.dseller.feature_account.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.drs.dseller.core.domain.usecases.UserSessionUseCases
import com.drs.dseller.core.domain.usecases.shopping_cart_use_cases.ShoppingCartUseCases
import com.drs.dseller.core.response.SessionResponse
import com.drs.dseller.feature_account.domain.model.AccountUser
import com.drs.dseller.feature_account.domain.model.UserOrderFilter
import com.drs.dseller.feature_account.domain.usecases.AccountUseCases
import com.drs.dseller.feature_account.presentation.states.AccountAppBarState
import com.drs.dseller.feature_account.presentation.states.AccountBottomNavigationBarState
import com.drs.dseller.feature_account.presentation.states.AccountDetailScreenState
import com.drs.dseller.feature_account.presentation.states.AccountErrorState
import com.drs.dseller.feature_account.presentation.states.AccountScreenState
import com.drs.dseller.feature_account.presentation.states.UserInvoiceState
import com.drs.dseller.feature_account.presentation.states.UserOrdersState
import com.drs.dseller.feature_account.response.AccountResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserAccountViewModel @Inject constructor(
    private val accountUseCases: AccountUseCases,
    private val sessionUseCases: UserSessionUseCases,
    cartUseCases: ShoppingCartUseCases
) : ViewModel(){

    private val _userAccountState = mutableStateOf(AccountScreenState())
    val userAccountState:State<AccountScreenState> = _userAccountState

    private val _userAccountDetailState = mutableStateOf(AccountDetailScreenState())
    val userAccountDetailState:State<AccountDetailScreenState> = _userAccountDetailState

    private val _userOrdersState = mutableStateOf(UserOrdersState())
    val userOrdersState:State<UserOrdersState> = _userOrdersState

    private val _userInvoiceState = mutableStateOf(UserInvoiceState())
    val userInvoiceState:State<UserInvoiceState> = _userInvoiceState

    private val _appBarState = mutableStateOf(AccountAppBarState())
    val appBarState:State<AccountAppBarState> = _appBarState

    private val _bottomNavigationState = mutableStateOf(AccountBottomNavigationBarState(
        cartFlow = cartUseCases.getAllProducts()
    ))

    fun onUserAccountEvent(event:UserAccountEvent){
        when(event){
            UserAccountEvent.GetAccountUser -> getAccountUser()
            UserAccountEvent.LogOutUser -> logoutUser()
        }
    }


    private fun getAccountUser(){
        viewModelScope.launch {
            when(val r = sessionUseCases.getUser()){
                is SessionResponse.Error -> {}
                SessionResponse.SignInError -> {}
                is SessionResponse.Success -> {
                    _userAccountState.value = _userAccountState.value.copy(
                        user = r.data.toAccountUser()
                    )
                }
            }
        }
    }

    private fun logoutUser(){
        viewModelScope.launch{
            when(val r = sessionUseCases.logoutUser()){
                is SessionResponse.Error -> {
                    _userAccountState.value = _userAccountState.value.copy(
                        errorState = AccountErrorState(
                            isError = true,
                            message = r.message
                        )
                    )
                }
                SessionResponse.SignInError -> {}
                is SessionResponse.Success -> {
                    _userAccountState.value = _userAccountState.value.copy(
                        logoutComplete = true
                    )
                }
            }
        }
    }

    fun onUserAccountDetailEvent(event:UserAccountDetailEvent){
        when(event){
            UserAccountDetailEvent.GetAccountUser -> getUserDetail()
            UserAccountDetailEvent.UpdateUser -> updateUser()
            is UserAccountDetailEvent.ChangeName -> {
                _userAccountDetailState.value = _userAccountDetailState.value.copy(
                    name = event.name
                )
            }
            is UserAccountDetailEvent.ChangePhoneNumber -> {
                _userAccountDetailState.value = _userAccountDetailState.value.copy(
                    phoneNumber = event.phoneNumber
                )
            }
        }
    }

    private fun getUserDetail(){
        viewModelScope.launch {
            when(val r = sessionUseCases.getUser()){
                is SessionResponse.Error -> {}
                SessionResponse.SignInError -> {}
                is SessionResponse.Success -> {
                    val user = r.data.toAccountUser()
                    _userAccountDetailState.value = _userAccountDetailState.value.copy(
                        user = user.copy(
                            phoneNumber = getNumberWithoutCountryCode(user.phoneNumber)
                        ),
                        name = user.name,
                        phoneNumber = getNumberWithoutCountryCode(user.phoneNumber)
                    )
                }
            }
        }
    }

    private fun getNumberWithoutCountryCode(phoneNumber:String):String = phoneNumber.substring(3,phoneNumber.length)

    private fun updateUser(){
        val updatedName = userAccountDetailState.value.name
        val updatedPhoneNumber = userAccountDetailState.value.phoneNumber
        _userAccountDetailState.value.user?.let{
            if(it.name == updatedName &&
                it.phoneNumber == updatedPhoneNumber) return@let

            viewModelScope.launch {
                _userAccountDetailState.value = _userAccountDetailState.value.copy(
                    updatingUser = true
                )
                when (val r = accountUseCases.updateUser(
                        AccountUser(
                            name= updatedName,
                            phoneNumber = "+91$updatedPhoneNumber"
                        )
                    )
                ) {
                    is AccountResponse.Error -> {
                        _userAccountDetailState.value = _userAccountDetailState.value.copy(
                            updatingUser = false,
                            errorState = AccountErrorState(
                                true,
                                r.message
                            )
                        )
                    }

                    is AccountResponse.Success -> {
                        _userAccountDetailState.value = _userAccountDetailState.value.copy(
                            updatingUser = false,
                            user = r.data,
                        )
                    }
                }
            }
        }
    }

    fun onOrdersEvent(event:UserOrdersEvent){
        when(event){
            UserOrdersEvent.ListOrders -> listOrders()
        }
    }

    private fun listOrders(){
        viewModelScope.launch {
            userAccountState.value.user?.let{
                val filter = UserOrderFilter(
                    userId = it.userId,
                    pageNumber = 1,
                    pageSize = 20
                )
                val flow = accountUseCases.getUserOrders(filter).cachedIn(viewModelScope)
                _userOrdersState.value = _userOrdersState.value.copy(
                    userOrders = flow
                )
            }

        }
    }

     fun onUserInvoiceEvent(event:UserInvoiceEvent){
        when(event){
            UserInvoiceEvent.GetInvoice -> {
                getInvoice()
            }
            is UserInvoiceEvent.SetOrderId -> {
                _userInvoiceState.value = _userInvoiceState.value.copy(
                    orderId = event.orderId
                )
            }
        }
    }

    private fun getInvoice(){
        userInvoiceState.value.orderId?.let{
            viewModelScope.launch {
                _userInvoiceState.value = _userInvoiceState.value.copy(
                    fetchingOrder = true
                )
                when(val r = accountUseCases.getInvoice(it)){
                    is AccountResponse.Error -> {
                        _userInvoiceState.value = _userInvoiceState.value.copy(
                            fetchingOrder =  false,
                            errorState =  AccountErrorState(
                                isError =  true,
                                message = r.message
                            )
                        )
                    }
                    is AccountResponse.Success -> {
                        _userInvoiceState.value = _userInvoiceState.value.copy(
                            fetchingOrder = false,
                            invoice = r.data
                        )
                    }
                }
            }
        }
    }


}

/**
 * Event class for Account Main Screen
 */
sealed class UserAccountEvent{
    data object GetAccountUser:UserAccountEvent()
    data object LogOutUser:UserAccountEvent()
}

/**
 * Event class for Account Detail Screen
 */
sealed class UserAccountDetailEvent{

    data object GetAccountUser:UserAccountDetailEvent()

    data object UpdateUser:UserAccountDetailEvent()

    data class ChangeName(val name:String):UserAccountDetailEvent()
    data class ChangePhoneNumber(val phoneNumber:String):UserAccountDetailEvent()

}

/**
 * Event class for User Orders List screen
 */
sealed class UserOrdersEvent{
    data object ListOrders:UserOrdersEvent()
}

/**
 * Event class for User Order Detail Screen
 */
sealed class UserInvoiceEvent{
    data class SetOrderId(val orderId:String):UserInvoiceEvent()

    data object GetInvoice:UserInvoiceEvent()
}