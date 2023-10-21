package com.drs.dseller.feature_orders.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drs.dseller.core.domain.usecases.shopping_cart_use_cases.ShoppingCartUseCases
import com.drs.dseller.feature_orders.domain.usecases.OrdersUseCases
import com.drs.dseller.feature_orders.presentation.states.CartOrderErrorState
import com.drs.dseller.feature_orders.presentation.states.CartState
import com.drs.dseller.feature_orders.response.ShoppingOrderResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingOrderViewModel @Inject constructor(
    private val cartUseCases: ShoppingCartUseCases,
    private val orderUseCases:OrdersUseCases
):ViewModel(){

    private val _cartScreenState = mutableStateOf(getInitialState())
    val cartScreenState: State<CartState> = _cartScreenState

    private fun getInitialState():CartState{
        val cartFlow = cartUseCases.getAllProducts()
        return CartState(
            cartFlow = cartFlow,
            cartItems = cartFlow.value
        )
    }

    fun onCartEvent(event:CartEvent){
        when(event){
            is CartEvent.DecrementProductQuantity -> {
                if(cartUseCases.getProduct(event.productId).quantity == 1)return
                cartUseCases.decrementQuantity(1,event.productId)
                updateCartState()
            }
            CartEvent.GetCartProducts -> {
                updateCartState()
            }
            is CartEvent.IncrementProductQuantity -> {
                cartUseCases.incrementQuantity(1,event.productId)
                updateCartState()
            }
            is CartEvent.RemoveProductFromCart -> {
                if(!cartUseCases.hasProduct(event.productId)) return
                cartUseCases.removeProduct(event.productId)
                updateCartState()
            }
        }
    }

    fun onOrderEvent(event:OrderEvent){
        when(event){
            is OrderEvent.ChangeErrorState -> {
                _cartScreenState.value = _cartScreenState.value.copy(
                    errorState = CartOrderErrorState(
                        isError = event.isError
                    )
                )
            }
            OrderEvent.PlaceOrder -> {
                placeOrder()
            }

            OrderEvent.ResetOrderCompleteFlag -> {
                _cartScreenState.value = _cartScreenState.value.copy(
                    isOrderComplete = false
                )
            }
        }
    }

    private fun updateCartState(){
        _cartScreenState.value = _cartScreenState.value.copy(
            cartItems = cartUseCases.getAllProducts().value
        )
    }

    private fun placeOrder(){
        viewModelScope.launch {
            _cartScreenState.value = _cartScreenState.value.copy(
                isPlacingOrder = true
            )
            when(val r = orderUseCases.placeOrder(cartUseCases.getAllProducts().value)){
                is ShoppingOrderResponse.Error -> {
                    _cartScreenState.value = _cartScreenState.value.copy(
                        isPlacingOrder = false,
                        errorState = CartOrderErrorState(
                            isError = true,
                            message = r.message
                        )
                    )
                }
                is ShoppingOrderResponse.Success -> {
                    cartUseCases.clearCart()
                    _cartScreenState.value = _cartScreenState.value.copy(
                        isPlacingOrder = false,
                        isOrderComplete = true,
                        cartItems = cartUseCases.getAllProducts().value
                    )
                }
            }
        }
    }


}

sealed class OrderEvent{

    data object PlaceOrder:OrderEvent()

    /**
     * Change the [CartState.errorState] to notify the UI about the current error state
     */
    data class ChangeErrorState(val isError:Boolean):OrderEvent()

    /**
     *  Reset the [CartState.isOrderComplete] flag in [CartState] which is used to notify that the order is complete
     */
    data object ResetOrderCompleteFlag:OrderEvent()
}

sealed class CartEvent{
    /**
     * Get all the products added to the cart
     */
    data object GetCartProducts: CartEvent()

    /**
     * Increment a products quantity added to the cart
     */
    data class IncrementProductQuantity(val productId:String):CartEvent()

    /**
     * Decrement a products quantity added to the cart
     */
    data class DecrementProductQuantity(val productId:String):CartEvent()

    /**
     * Remove a product from the cart
     */
    data class RemoveProductFromCart(val productId:String):CartEvent()
}