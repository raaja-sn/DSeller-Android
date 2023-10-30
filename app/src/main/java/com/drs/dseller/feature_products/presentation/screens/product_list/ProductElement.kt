package com.drs.dseller.feature_products.presentation.screens.product_list

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
import com.drs.dseller.core.ui_elements.clickableWithoutRipple
import com.drs.dseller.core.utils.AppUtils
import com.drs.dseller.feature_products.domain.model.Product
import com.drs.dseller.feature_products.domain.model.ProductPicture
import com.drs.dseller.ui.theme.AppTypography
import com.drs.dseller.ui.theme.Black80
import com.drs.dseller.ui.theme.Green40
import com.drs.dseller.ui.theme.Grey60

@Composable
fun ProductElement(
    cardWidth: Dp = dimensionResource(id = R.dimen.product_card_width),
    cardHeight:Dp = dimensionResource(id = R.dimen.product_card_height),
    product:Product,
    placeHolderImageId:Int = R.drawable.place_holder_medium,
    productClicked:(String) -> Unit,
    addToCart:(Product) -> Unit
){
    val interactionSource = remember{ MutableInteractionSource() }
    val cardModifier = remember(product.productId){
        Modifier
            .wrapContentSize()
            .width(cardWidth)
            .height(cardHeight)
            .padding(vertical = 15.dp)
            .clickableWithoutRipple(interactionSource){
                productClicked(product.productId)
            }
    }

    val iconModifier = remember{
        Modifier
            .padding(start = 10.dp, end = 15.dp)
            .clip(RoundedCornerShape(35))
            .clickable {
                addToCart(product)
            }
    }

    OutlinedCard(
        modifier = cardModifier,
        shape = RoundedCornerShape(25.dp),
        colors = CardDefaults.outlinedCardColors(
            containerColor = Color.White
        ),
        border = BorderStroke(1.dp, Grey60)
    ) {

        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .size(
                    dimensionResource(id = R.dimen.product_img_size),
                    dimensionResource(id = R.dimen.product_img_size)
                ),
            model = ImageRequest.Builder(LocalContext.current)
                .data(getProductImageFromListOfImages(0,product.productPictures))
                .crossfade(300)
                .placeholder(placeHolderImageId)
                .error(placeHolderImageId)
                .build(),
            contentDescription = "${product.name} image",
            contentScale = ContentScale.Crop
        )

        Text(
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 10.dp),
            text = product.name,
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
                text = getPriceWithCurrencyCode(product.price),
                style = AppTypography.titleSmall,
                maxLines = 1,
                color = Black80,
            )

            Icon(
                modifier = iconModifier
                    .background(Green40)
                    .size(37.dp)
                    .padding(12.dp)
                    .align(Alignment.CenterEnd),
                painter = painterResource(id = R.drawable.ic_add),
                contentDescription = "Add product to cart",
                tint = Color.Unspecified
            )


        }

    }
}

private fun getPriceWithCurrencyCode(price:Double):String {
    return "${AppUtils.getCurrencySymbol("en","in")} $price"
}

fun getProductImageFromListOfImages(idx:Int, productImages: List<ProductPicture>?):String{
    return if(productImages == null || idx >= productImages.size){
        return ""
    }else{
        productImages[idx].pictureUrl
    }
}



@Preview
@Composable
private fun ProductElementPreview(){

    ProductElement(
        product = Product(
            name = "Samsung S22 5G ",
            price = 5000.0,
        ), productClicked = {}, addToCart = {})

}