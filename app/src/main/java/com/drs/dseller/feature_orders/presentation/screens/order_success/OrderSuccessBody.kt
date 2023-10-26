package com.drs.dseller.feature_orders.presentation.screens.order_success

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.drs.dseller.R
import com.drs.dseller.core.ui_elements.buttons.RoundCorneredButton
import com.drs.dseller.core.ui_elements.clickableWithoutRipple
import com.drs.dseller.ui.theme.AppTypography
import com.drs.dseller.ui.theme.Black80
import com.drs.dseller.ui.theme.Grey80

@Composable
fun OrderSuccessBody(
    backToHome:() -> Unit,
    continueShopping:() -> Unit
){
    
    val interactionSource = remember{ MutableInteractionSource() }
    
    val continueClickModifier = remember{
        Modifier.clickableWithoutRipple(
            interactionSource,
            continueShopping
        )
    }

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .background(Color.White)
            .padding(top = dimensionResource(id = R.dimen.one_twenty_dp)),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Image(
            modifier = Modifier
                .padding(end = dimensionResource(id = R.dimen.ten_dp))
                .size(dimensionResource(id = R.dimen.three_hundred_dp))
                .aspectRatio(16f / 12f)
                .padding(horizontal = dimensionResource(id = R.dimen.thirty_dp)),
            painter = painterResource(id = R.drawable.img_order_placed),
            contentDescription = stringResource(id = R.string.description_order_placed),
            contentScale = ContentScale.Crop
        )
        Text(
            modifier = Modifier
                .padding(
                    vertical = dimensionResource(id = R.dimen.twenty_dp),
                    horizontal = dimensionResource(id = R.dimen.twenty_dp)
                )
                .width(dimensionResource(id = R.dimen.four_hundred_dp)),
            text = stringResource(id = R.string.cart_order_placed_title),
            style = AppTypography.headlineMedium,
            color = Black80,
            textAlign = TextAlign.Center
        )
        Text(
            modifier = Modifier
                .width(dimensionResource(id = R.dimen.three_hundred_dp))
                .padding(
                    horizontal = dimensionResource(id = R.dimen.twenty_dp)
                ),
            text = stringResource(id = R.string.cart_order_placed_info),
            style = AppTypography.titleSmall,
            color = Grey80,
            textAlign = TextAlign.Center
        )

        Box(
            modifier = Modifier
                .weight(1f)
                .padding(bottom = dimensionResource(id = R.dimen.twenty_dp))
        ) {
            Column(
                modifier= Modifier
                    .align(Alignment.BottomCenter),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                RoundCorneredButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = dimensionResource(id = R.dimen.twenty_dp)),
                    buttonText = stringResource(id = R.string.cart_order_back),
                    clickCallback = backToHome
                )
                Text(
                    modifier = continueClickModifier
                        .padding(vertical = dimensionResource(id = R.dimen.twenty_dp)),
                    text = stringResource(id = R.string.cart_order_continue),
                    style = AppTypography.headlineSmall,
                    color = Black80,
                    textAlign = TextAlign.Center
                )
            }
        }

    }
}

@Preview
@Composable
private fun OrderSuccessPreview(){
    OrderSuccessBody(backToHome = { /*TODO*/ }) {
        
    }
}

