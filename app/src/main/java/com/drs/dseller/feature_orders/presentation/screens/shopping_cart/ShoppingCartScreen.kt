package com.drs.dseller.feature_orders.presentation.screens.shopping_cart

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.drs.dseller.R
import com.drs.dseller.core.domain.model.shopping_cart.CartProduct
import com.drs.dseller.core.ui_elements.appbar.AppBottomNavigationBar
import com.drs.dseller.core.ui_elements.buttons.RoundCorneredButton
import com.drs.dseller.feature_orders.presentation.CartEvent
import com.drs.dseller.feature_orders.presentation.OrderEvent
import com.drs.dseller.feature_orders.presentation.ShoppingOrderViewModel
import com.drs.dseller.feature_orders.presentation.states.CartState
import com.drs.dseller.feature_orders.presentation.toOrderSuccess
import com.drs.dseller.ui.theme.AppTypography
import com.drs.dseller.ui.theme.Black80
import com.drs.dseller.ui.theme.Grey80

@Composable
fun ShoppingCartScreen(
    state: CartState,
    vm:ShoppingOrderViewModel,
    navHostController: NavHostController
){

    val incrementQuantity = remember{
        {cartProduct:CartProduct ->
            vm.onCartEvent(CartEvent.IncrementProductQuantity(cartProduct.productId))
        }
    }

    val decrementQuantity = remember{
        {cartProduct:CartProduct ->
            vm.onCartEvent(CartEvent.DecrementProductQuantity(cartProduct.productId))
        }
    }

    val removeProduct = remember{
        {cartProduct:CartProduct ->
            vm.onCartEvent(CartEvent.RemoveProductFromCart(cartProduct.productId))
        }
    }

    val placeOrder = remember{
        {
            vm.onOrderEvent(OrderEvent.PlaceOrder)
        }
    }

    if(state.isOrderComplete){
        navHostController.toOrderSuccess()
        vm.onOrderEvent(OrderEvent.ResetOrderCompleteFlag)
    }

    val cartItems = state.cartFlow.collectAsStateWithLifecycle().value

    Scaffold(
        bottomBar = {
            AppBottomNavigationBar(
                cartQuantity = cartItems.size,
                navHostController = navHostController
            )
        }
    ) { it ->
        Box(
            modifier = Modifier
                .background(Color.White)
                .padding(it)
        ){
            ShoppingCartBody(
                state = state,
                incrementQuantity = incrementQuantity,
                decrementQuantity = decrementQuantity,
                deleteProduct = removeProduct,
                placeOrder = placeOrder
            )
            AnimatedVisibility(
                modifier = Modifier
                    .align(Alignment.BottomCenter),
                visible = state.errorState.isError,
                enter = slideInVertically { it },
                exit = slideOutVertically { it }
            ) {
                val closeDialog = remember{
                    {
                        vm.onOrderEvent(OrderEvent.ChangeErrorState(false))
                    }
                }
                val tryAgain = remember {
                    {
                        vm.onOrderEvent(OrderEvent.PlaceOrder)
                        vm.onOrderEvent(OrderEvent.ChangeErrorState(false))
                    }
                }
                OrderFailedDialog(
                    message = state.errorState.message,
                    closeDialog,
                    tryAgain
                )
            }
        }
    }

}

@Composable
private fun OrderFailedDialog(
    message:String,
    close:() -> Unit,
    tryAgain:() -> Unit
){
    val dialogModifier = remember {
        Modifier
            .clickable(onClick = close)
    }
    Column(
        modifier = Modifier
            .shadow(
                elevation = 20.dp,
                shape = RoundedCornerShape(
                    topStart = dimensionResource(id = R.dimen.twenty_five_dp),
                    topEnd = dimensionResource(id = R.dimen.twenty_five_dp)
                ),
                clip = true
            )
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(
                    topStart = dimensionResource(id = R.dimen.twenty_five_dp),
                    topEnd = dimensionResource(id = R.dimen.twenty_five_dp)
                )
            )
            .background(Color.White)
    ) {

        Icon(
            modifier = dialogModifier
                .padding(dimensionResource(id = R.dimen.ten_dp))
                .size(dimensionResource(id = R.dimen.fourty_dp))
                .padding(dimensionResource(id = R.dimen.twelve_dp))
                .align(Alignment.Start),
            painter = painterResource(id = R.drawable.ic_close),
            contentDescription = stringResource(id = R.string.description_order_failed_close),
            tint = Color.Unspecified
        )
        Image(
            modifier = Modifier
                .size(dimensionResource(id = R.dimen.two_hundred_dp))
                .align(Alignment.CenterHorizontally),
            painter = painterResource(id = R.drawable.img_order_failed),
            contentDescription = stringResource(id = R.string.description_order_failed_image),
            contentScale = ContentScale.Crop
        )
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            text = stringResource(id = R.string.cart_order_filed_title),
            style = AppTypography.headlineMedium,
            color = Black80,
            textAlign = TextAlign.Center
        )
        Text(
            modifier = Modifier
                .width(dimensionResource(id = R.dimen.four_hundred_dp))
                .padding(horizontal = dimensionResource(id = R.dimen.twenty_dp))
                .padding(top = dimensionResource(id = R.dimen.fifteen_dp))
                .align(Alignment.CenterHorizontally),
            text = message,
            style = AppTypography.titleSmall,
            color = Grey80,
            textAlign = TextAlign.Center
        )
        RoundCorneredButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = dimensionResource(id = R.dimen.twenty_dp))
                .padding(
                    top = dimensionResource(id = R.dimen.thirty_dp),
                    bottom = dimensionResource(id = R.dimen.twenty_dp)
                ),
            buttonText = stringResource(id = R.string.cart_order_filed_try_again),
            clickCallback = tryAgain
        )
    }

}

@Preview
@Composable
private fun OrderFailedPreview(){
    OrderFailedDialog(message = "Some very long message that will definitly not fit in a single line, or even a double line and it will move to the third line as well", close = { /*TODO*/ }) {
        
    }
}