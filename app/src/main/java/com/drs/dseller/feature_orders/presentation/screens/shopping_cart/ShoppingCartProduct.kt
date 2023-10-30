package com.drs.dseller.feature_orders.presentation.screens.shopping_cart

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.drs.dseller.R
import com.drs.dseller.core.domain.model.shopping_cart.CartProduct
import com.drs.dseller.core.ui_elements.clickableWithoutRipple
import com.drs.dseller.core.utils.AppUtils
import com.drs.dseller.ui.theme.AppTypography
import com.drs.dseller.ui.theme.Black80
import com.drs.dseller.ui.theme.Grey60

@Composable
fun ShoppingCartProduct(
cartProduct:CartProduct,
incrementQuantity:(CartProduct) -> Unit,
decrementQuantity:(CartProduct) -> Unit,
deleteProduct:(CartProduct) -> Unit
){

    val interactionSource = remember {
        MutableInteractionSource()
    }
    val incrementModifier = remember {
        Modifier
            .clickableWithoutRipple(
                interactionSource
            ) {
                incrementQuantity(cartProduct)
            }
    }
    val decrementModifier = remember {
        Modifier
            .clickableWithoutRipple(
                interactionSource
            ) {
                decrementQuantity(cartProduct)
            }
    }
    val deleteModifier = remember {
        Modifier.clickable {
            deleteProduct(cartProduct)
        }
    }

    println("Cart Product: ${cartProduct.productName} quantity ${cartProduct.quantity}")
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(id = R.dimen.fifteen_dp))
            .height(dimensionResource(id = R.dimen.cart_product_image_size))
    ){

        AsyncImage(
            modifier = Modifier
                .size(
                    dimensionResource(id = R.dimen.cart_product_image_size),
                    dimensionResource(id = R.dimen.cart_product_image_size)
                ),
            model = ImageRequest.Builder(LocalContext.current)
                .crossfade(200)
                .data(cartProduct.productImage)
                .error(R.drawable.place_holder_medium)
                .placeholder(R.drawable.place_holder_medium)
                .build(),
            contentScale = ContentScale.Crop,
            contentDescription = stringResource(id = R.string.description_cart_product)
        )

        Column(
            modifier = Modifier
                .weight(1f),
            horizontalAlignment = Alignment.Start
        ){
            ProductNameAndQuantity(
                name = cartProduct.productName,
                quantity = cartProduct.quantity ,
                price = cartProduct.price,
                incrementModifier = incrementModifier ,
                decrementModifier = decrementModifier
            )
        }

        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.SpaceBetween
        ){
            Icon(
                modifier = deleteModifier
                    .size(dimensionResource(id = R.dimen.fourty_dp))
                    .padding(dimensionResource(id = R.dimen.twelve_dp)),
                painter = painterResource(id = R.drawable.ic_close),
                contentDescription = stringResource(id = R.string.description_cart_remove_product),
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "${AppUtils.getCurrencySymbol("en","in")} " +
                        "${AppUtils.getTwoDecimalSpacedPrice(cartProduct.price * cartProduct.quantity)}",
                style = AppTypography.titleMedium,
                color = Black80,
                maxLines = 1,
                textAlign = TextAlign.Start
            )
        }

    }
}

@Composable
private fun ColumnScope.ProductNameAndQuantity(
    name:String,
    quantity:Int,
    price:Double,
    incrementModifier:Modifier,
    decrementModifier:Modifier,
){
    Text(
        modifier = Modifier
            .padding(
                horizontal = dimensionResource(id = R.dimen.five_dp),
                vertical = dimensionResource(id = R.dimen.five_dp)
            ),
        text = name,
        style = AppTypography.titleSmall,
        color = Black80,
        maxLines = 2,
        textAlign = TextAlign.Start
    )
    Text(
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.five_dp)),
        text = "Item Price: $price",
        style = AppTypography.bodySmall.copy(
            fontSize = 11.sp
        ),
        color = Grey60,
        maxLines = 1,
        textAlign = TextAlign.Start
    )
    Row(
        modifier = Modifier
            .weight(1f),
        verticalAlignment = Alignment.Bottom
    ) {
        Icon(
            modifier = decrementModifier
                .size(dimensionResource(id = R.dimen.fourty_dp)),
            painter = painterResource(id = R.drawable.ic_cart_decrement),
            contentDescription = stringResource(id = R.string.description_product_detail_reduce_quantity),
            tint = Color.Unspecified
        )

        Text(
            modifier = Modifier
                .padding(
                    horizontal = dimensionResource(id = R.dimen.fifteen_dp),
                    vertical = dimensionResource(id = R.dimen.ten_dp)
                ),
            text = quantity.toString(),
            style = AppTypography.bodySmall,
            color = Black80,
            maxLines = 1,
            textAlign = TextAlign.Center
        )

        Icon(
            modifier = incrementModifier
                .size(dimensionResource(id = R.dimen.fourty_dp)),
            painter = painterResource(id = R.drawable.ic_cart_increment),
            contentDescription = stringResource(id = R.string.description_product_detail_add_quantity),
            tint = Color.Unspecified
        )
    }
}

@Preview
@Composable
private fun ShoppingCartProductPreview(){

    ShoppingCartProduct(
        cartProduct = CartProduct(
            "Some very big product name",
            quantity = 3,
            price = 5000.0
        ),
        incrementQuantity = {},
        decrementQuantity = {},
        deleteProduct = {}
    )
}