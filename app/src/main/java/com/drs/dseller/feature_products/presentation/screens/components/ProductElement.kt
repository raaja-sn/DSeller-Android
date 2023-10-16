package com.drs.dseller.feature_products.presentation.screens.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.drs.dseller.R
import com.drs.dseller.ui.theme.AppTypography
import com.drs.dseller.ui.theme.Black80
import com.drs.dseller.ui.theme.Green40
import com.drs.dseller.ui.theme.Grey60
import java.util.Currency
import java.util.Locale

@Composable
fun ProductElement(
    cardWidth:Dp = dimensionResource(id = R.dimen.product_card_width),
    imageSize:Dp = dimensionResource(id = R.dimen.product_img_size),
    productName:String = "",
    productImage:String = "",
    placeHolderImageId:Int = R.drawable.place_holder_small,
    price:String = "",
    index:Int,
    productClicked:(Int) -> Unit,
    addToCart:(Int) -> Unit
){


    OutlinedCard(
        modifier = Modifier
            .wrapContentHeight()
            .width(cardWidth)
            .height(dimensionResource(id = R.dimen.product_card_height)),
        shape = RoundedCornerShape(15.dp),
        colors = CardDefaults.outlinedCardColors(
            containerColor = Color.White
        ),
        border = BorderStroke(1.dp, Grey60)
    ) {

        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .size(imageSize, imageSize),
            model = ImageRequest.Builder(LocalContext.current)
                .data(productImage)
                .crossfade(300)
                .placeholder(placeHolderImageId)
                .error(placeHolderImageId)
                .build(),
            contentDescription = "$productName image",
            contentScale = ContentScale.Crop
        )

        Text(
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 10.dp),
            text = productName,
            style = AppTypography.titleSmall,
            color = Black80,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f, true),
            contentAlignment = Alignment.BottomCenter
        ) {

            Text(
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .align(Alignment.CenterStart),
                text = getPriceWithCurrencyCode(price),
                style = AppTypography.titleSmall,
                maxLines = 1,
                color = Black80,
            )

            IconButton(
                modifier = Modifier
                    .padding(start = 10.dp, end = 15.dp)
                    .size(40.dp)
                    .clip(RoundedCornerShape(35))
                    .background(Green40)
                    .align(Alignment.CenterEnd),
                onClick = { addToCart(index) }
            ) {
                Image(
                    modifier = Modifier,
                    painter = painterResource(id = R.drawable.ic_add),
                    contentDescription = "Add $productName to cart")
            }

        }

    }
}

fun getPriceWithCurrencyCode(price:String):String {
    return "$currencySymbol $price"
}

val currencySymbol = Currency.getInstance(Locale.getDefault()).symbol

@Preview
@Composable
private fun ProductElementPreview(){

    ProductElement(
        imageSize = 120.dp,
        productName = "Samsung S22 5G ",
        price = "5000",
        index = 0, productClicked = {}, addToCart = {})

}