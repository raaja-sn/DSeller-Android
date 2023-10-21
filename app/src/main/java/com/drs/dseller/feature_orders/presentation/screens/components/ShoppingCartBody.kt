package com.drs.dseller.feature_orders.presentation.screens.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.drs.dseller.R
import com.drs.dseller.core.domain.model.shopping_cart.CartProduct
import com.drs.dseller.core.ui_elements.GreenCircularProgressIndicator
import com.drs.dseller.core.ui_elements.buttons.RoundCorneredButton
import com.drs.dseller.core.utils.AppUtils
import com.drs.dseller.feature_orders.presentation.states.CartState
import com.drs.dseller.ui.theme.AppTypography
import com.drs.dseller.ui.theme.Black80
import com.drs.dseller.ui.theme.White80

@Composable
fun ShoppingCartBody(
    state:CartState,
    incrementQuantity:(CartProduct) -> Unit,
    decrementQuantity:(CartProduct) -> Unit,
    deleteProduct:(CartProduct) -> Unit,
    placeOrder:() -> Unit
){

    val listState = remember {
        LazyListState()
    }

    if(state.cartItems.isEmpty()){
        Column {
            TitleBar()
        }
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier
                    .size(dimensionResource(id = R.dimen.three_hundred_dp))
                    .aspectRatio(1f / 1f)
                    .padding(horizontal = dimensionResource(id = R.dimen.thirty_dp)),
                painter = painterResource(id = R.drawable.img_cart_empty),
                contentDescription = stringResource(id = R.string.description_cart_empty),
                contentScale = ContentScale.Crop
            )
            Text(
                text = stringResource(id = R.string.cart_empty),
                style = AppTypography.headlineMedium,
                color = Black80,
                textAlign = TextAlign.Center
            )
        }
    }else{
        Box{
            LazyColumn(
                modifier = Modifier
                    .fillMaxHeight(),
                state = listState
            ){
                item {
                    TitleBar()
                }

                itemsIndexed(
                    items = state.cartItems,
                    key = { idx:Int, item:CartProduct ->
                        item.productId
                    }
                ){ idx:Int, item:CartProduct ->
                    ShoppingCartProduct(
                        cartProduct = item,
                        incrementQuantity = incrementQuantity,
                        decrementQuantity = decrementQuantity,
                        deleteProduct = deleteProduct
                    )
                    Divider(
                        modifier = Modifier
                            .padding(
                                horizontal = dimensionResource(id = R.dimen.fifteen_dp),
                                vertical = dimensionResource(id = R.dimen.fifteen_dp)
                            )
                            .fillMaxWidth(),
                        thickness = 1.dp,
                        color = White80
                    )
                }

                item{
                    CartTotal(total = getCartTotal(state.cartItems))
                }
            }

            RoundCorneredButton(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(dimensionResource(id = R.dimen.twenty_dp)),
                buttonText = stringResource(id = R.string.cart_order_title),
                clickCallback = placeOrder,
                isEnabled = !state.isPlacingOrder
            )

            GreenCircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.BottomCenter),
                isVisible = state.isPlacingOrder
            )
        }
    }


}

@Composable
private fun TitleBar(){
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .height(dimensionResource(id = R.dimen.app_bar_height))
            .padding(vertical = dimensionResource(id = R.dimen.twenty_dp)),
        text = stringResource(id = R.string.cart_title),
        style = AppTypography.headlineMedium,
        color = Black80,
        textAlign = TextAlign.Center
    )
    Divider(
        modifier = Modifier
            .padding(bottom = dimensionResource(id = R.dimen.fifteen_dp)),
        thickness = 1.dp,
        color = White80
    )
}

@Composable
private fun CartTotal(
    total:Double
){
    Row(
        modifier = Modifier
            .padding(bottom = dimensionResource(id = R.dimen.one_twenty_dp))
            .fillMaxWidth()
            .height(dimensionResource(id = R.dimen.cart_product_image_size))
            .padding(horizontal = dimensionResource(id = R.dimen.fifteen_dp)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {

        Text(
            text = stringResource(id = R.string.cart_order_total),
            style = AppTypography.titleMedium,
            color = Black80,
            textAlign = TextAlign.Start
        )
        Text(
            text = "  ${AppUtils.getCurrencySymbol("en","in")} $total",
            style = AppTypography.titleMedium,
            color = Black80,
            textAlign = TextAlign.End
        )
        
    }
}

private fun getCartTotal(cartItems:List<CartProduct>):Double{
    var total = 0.0
    cartItems.forEach { 
        total += (it.price * it.quantity)
    }
    return String.format("%.2f",total).toDouble()
}

@Preview
@Composable
private fun ShoppingCartPreview(){
    ShoppingCartBody(
        state = CartState(
            cartItems = listOf(
                CartProduct(productId = "1", productName = "My long product name 1",quantity = 1, price = 50.0),
                CartProduct(productId = "2", productName = "My long product name 2",quantity = 1, price = 50.5),
                CartProduct(productId = "3", productName = "My long product name 3",quantity = 1, price = 50.06),
                CartProduct(productId = "4", productName = "My long product name 4",quantity = 1, price = 50.6756)
            )/* listOf()*/
        ),
        incrementQuantity = {},
        decrementQuantity = {},
        deleteProduct = {},
        {}
    )
}
