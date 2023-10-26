package com.drs.dseller.feature_account.presentation.screens.user_orders

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.drs.dseller.R
import com.drs.dseller.core.ui_elements.clickableWithoutRipple
import com.drs.dseller.core.utils.AppUtils
import com.drs.dseller.feature_account.domain.model.UserOrder
import com.drs.dseller.ui.theme.AppTypography
import com.drs.dseller.ui.theme.Black80
import com.drs.dseller.ui.theme.Grey80
import com.drs.dseller.ui.theme.White80

@Composable
fun UserOrderComponent(
    order:UserOrder,
    orderClicked:(String) -> Unit
){
    val interactionSource = remember {
        MutableInteractionSource()
    }
    val itemModifier = remember {
        Modifier
            .clickableWithoutRipple(
                interactionSource
            ){
                orderClicked(order.orderId)
            }
    }

    Row(
        modifier = itemModifier
            .fillMaxWidth()
            .padding(
                vertical = dimensionResource(id = R.dimen.twenty_five_dp)
            )
    ){
        Column(
            modifier = Modifier
                .weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ){
            Text(
                modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.ten_dp)),
                text = "Order Id: ${order.invoiceNo}",
                style = AppTypography.headlineSmall,
                color = Black80
            )
            Text(
                modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.five_dp)),
                text = "Purchase Date:",
                style = AppTypography.titleSmall,
                color = Grey80
            )
            Text(
                text = AppUtils.getFormattedDate(order.purchaseDate),
                style = AppTypography.titleSmall,
                color = Grey80
            )
        }
        Column(
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.End
        ){
            Text(
                modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.ten_dp)),
                text = "Order Value:",
                style = AppTypography.bodySmall,
                color = Grey80
            )
            Text(
                text = "${AppUtils.getCurrencySymbol("en","in")} ${order.billValue}",
                style = AppTypography.bodyMedium,
                color = Black80
            )
        }
    }
}