package com.drs.dseller.feature_products.presentation.screens.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.drs.dseller.R
import com.drs.dseller.core.ui_elements.buttons.RoundCorneredButton
import com.drs.dseller.core.utils.AppUtils
import com.drs.dseller.feature_products.domain.model.ProductDetail
import com.drs.dseller.feature_products.presentation.states.ProductDetailState
import com.drs.dseller.ui.theme.AppTypography
import com.drs.dseller.ui.theme.Black80
import com.drs.dseller.ui.theme.Grey80
import com.drs.dseller.ui.theme.White80
import kotlin.random.Random

@Composable
fun ProductDetailBody(
    state:ProductDetailState,
    addToCart:(Int)->Unit,
){
    val scrollState= rememberScrollState()
    val quantity = remember {
        mutableIntStateOf(0)
    }

    val incrementQuantity:()->Unit = remember{
        {

            quantity.intValue = quantity.intValue.inc()
        }
    }
    val decrementQuantity:()->Unit = remember{
        decrement@{
            if(quantity.intValue < 1) return@decrement
            quantity.intValue = quantity.intValue.dec()
        }
    }

    val addToBasket:()->Unit = remember{
        {
            addToCart(quantity.intValue)
        }
    }

    Box(
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState),
        ) {

            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 12f)
                    .clip(
                        RoundedCornerShape(
                            bottomStart = dimensionResource(id = R.dimen.twenty_dp),
                            bottomEnd = dimensionResource(id = R.dimen.twenty_dp)
                        )
                    ),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(getProductImageFromListOfImages(0,state.productDetail?.productPictures))
                    .placeholder(R.drawable.place_holder_small)
                    .error(R.drawable.place_holder_small)
                    .crossfade(200)
                    .build(),
                contentDescription = stringResource(id = R.string.description_product_detail_image),
                contentScale = ContentScale.Crop
            )

            ProductTitle(state = state)
            ProductShortDescription(state = state)
            ProductQuantityAndPrice(
                state = state,
                quantity = quantity.intValue,
                increaseQuantity = incrementQuantity,
                reduceQuantity = decrementQuantity
            )
            ProductDescription(state = state)
            Review()

        }

        RoundCorneredButton(
            modifier = Modifier
                .padding(
                    start = dimensionResource(id = R.dimen.twenty_dp),
                    end = dimensionResource(id = R.dimen.twenty_dp),
                    bottom = dimensionResource(id = R.dimen.twenty_dp)
                )
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            buttonText = stringResource(id = R.string.product_detail_add_to_basket),
            clickCallback = addToBasket
        )
    }



}

@Composable
private fun ProductTitle(
state:ProductDetailState
){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(id = R.dimen.twenty_dp))
    ) {
        Text(
            modifier = Modifier
                .padding(
                    end = dimensionResource(id = R.dimen.ten_dp),
                    top = dimensionResource(id = R.dimen.ten_dp)
                )
                .align(Alignment.CenterStart),
            text = state.productDetail?.name?:"--",
            style = AppTypography.headlineSmall,
            color = Black80,
        )

        Icon(
            modifier = Modifier
                .padding(start = dimensionResource(id = R.dimen.ten_dp))
                .size(dimensionResource(id = R.dimen.twenty_five_dp))
                .align(Alignment.CenterEnd),
            painter = painterResource(id = R.drawable.favorite),
            contentDescription = stringResource(id = R.string.description_product_detail_favorite),
            tint = Color.Unspecified
        )

    }
}

@Composable
private fun ProductShortDescription(
    state:ProductDetailState
){
    Text(
        modifier = Modifier
            .padding(
                horizontal = dimensionResource(id = R.dimen.twenty_dp),
            ),
        text = state.productDetail?.descriptionShort?:"--",
        style = AppTypography.titleSmall,
        color = Grey80,
    )
}

@Composable
private fun ProductQuantityAndPrice(
    state: ProductDetailState,
    quantity:Int,
    increaseQuantity:() -> Unit,
    reduceQuantity:() -> Unit
){
    val incrementModifier = remember{
        Modifier
            .clickable(onClick = increaseQuantity)
    }
    val decrementModifier = remember{
        Modifier
            .clickable(onClick = reduceQuantity)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = dimensionResource(id = R.dimen.twenty_dp),
                vertical = dimensionResource(id = R.dimen.ten_dp)
            ),
        contentAlignment = Alignment.CenterStart
    ){
        Row(
            modifier = Modifier
                .padding(0.dp)
                .wrapContentWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            Icon(
                modifier = decrementModifier
                    .size(dimensionResource(id = R.dimen.fourty_dp)),
                painter = painterResource(id = R.drawable.ic_negative),
                contentDescription = stringResource(id = R.string.description_product_detail_reduce_quantity),
                tint = Color.Unspecified
            )

            OutlinedTextField(
                modifier = Modifier
                    .size(dimensionResource(id = R.dimen.seventy_dp))
                    .padding(dimensionResource(id = R.dimen.ten_dp))
                    .align(Alignment.CenterVertically),
                value = quantity.toString(),
                onValueChange = {

                },
                enabled = false,
                singleLine = true,
                textStyle = AppTypography.bodyMedium.copy(
                    textAlign = TextAlign.Center
                ),
                shape = RoundedCornerShape(dimensionResource(id = R.dimen.twenty_dp)),
                colors = OutlinedTextFieldDefaults.colors(
                    disabledTextColor = Black80,
                    disabledBorderColor = White80
                )
            )
            Icon(
                modifier = incrementModifier
                    .size(dimensionResource(id = R.dimen.fourty_dp)),
                painter = painterResource(id = R.drawable.ic_positive_green),
                contentDescription = stringResource(id = R.string.description_product_detail_add_quantity),
                tint = Color.Unspecified
            )
        }

        Text(
            modifier = Modifier
                .align(Alignment.CenterEnd),
            text = "${AppUtils.getCurrencySymbol("en","in")}" +
                    "${state.productDetail?.price ?: "0.0"}",
            style = AppTypography.headlineSmall,
            color = Black80,
        )
    }
}

@Composable
private fun ProductDescription(
    state: ProductDetailState
){
    Divider(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = dimensionResource(id = R.dimen.twenty_dp),
                end = dimensionResource(id = R.dimen.twenty_dp),
                top = dimensionResource(id = R.dimen.twenty_dp),
                bottom = dimensionResource(id = R.dimen.ten_dp)
            ),
        thickness = 1.dp,
        color = White80
    )
    Text(
        modifier = Modifier
            .padding(
                horizontal = dimensionResource(id = R.dimen.twenty_dp)
            )
            .padding(top = dimensionResource(id = R.dimen.five_dp)),
        text = stringResource(id = R.string.product_detail_description_title),
        style = AppTypography.titleMedium,
        color = Black80,
    )
    Text(
        modifier = Modifier
            .padding(
                horizontal = dimensionResource(id = R.dimen.twenty_dp),
            )
            .padding(top = dimensionResource(id = R.dimen.ten_dp)),
        text = state.productDetail?.descriptionLong?:"--",
        style = AppTypography.bodySmall,
        color = Grey80,
    )
}

@Composable
private fun Review(

){
    val rating = remember{
        val times = Random.nextInt(4)+1
        Pair(times,5-times)
    }
    Divider(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = dimensionResource(id = R.dimen.twenty_dp),
                end = dimensionResource(id = R.dimen.twenty_dp),
                top = dimensionResource(id = R.dimen.twenty_dp),
                bottom = dimensionResource(id = R.dimen.ten_dp)
            ),
        thickness = 1.dp,
        color = White80
    )
    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = dimensionResource(id = R.dimen.twenty_dp))
        .padding(bottom = dimensionResource(id = R.dimen.two_hundred_dp))
    ){
        Text(
            modifier = Modifier
                .padding(
                    vertical = dimensionResource(id = R.dimen.ten_dp)
                )
                .align(Alignment.CenterStart),
            text = stringResource(id = R.string.product_detail_review_title),
            style = AppTypography.titleMedium,
            color = Black80,
        )
        Row(
            modifier = Modifier
                .align(Alignment.CenterEnd)
        ) {
            repeat(rating.first){
                ReviewStar(isPositive = true)
            }
            repeat(rating.second){
                ReviewStar(isPositive = false)
            }
        }
    }
}

@Composable
private fun ReviewStar(
isPositive:Boolean
){
    Icon(
        modifier = Modifier
            .size(dimensionResource(id = R.dimen.twenty_five_dp))
            .padding(5.dp),
        painter = if (isPositive) {
            painterResource(id = R.drawable.ic_positive_rating)
        } else {
            painterResource(id = R.drawable.ic_negative_rating)
        },
        contentDescription = stringResource(id = R.string.description_product_detail_rating),
        tint = Color.Unspecified
    )

}


@Preview
@Composable
private fun ProductDetailPreview(){

    ProductDetailBody(state = ProductDetailState(
        productDetail = ProductDetail(
            name = "Some very long product name",
            descriptionShort = "Some very long product description which goes to next line",
            descriptionLong = "A long description of the selected product, added by the seller," +
                    "which describes the detailed info about the product, which the shopper reads " +
                    "before placing an order for the product",
            price = 5000.00
        )
    )){}
}