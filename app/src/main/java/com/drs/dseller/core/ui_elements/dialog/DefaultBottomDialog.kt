@file:OptIn(ExperimentalMaterial3Api::class)

package com.drs.dseller.core.ui_elements.dialog

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import com.drs.dseller.R
import com.drs.dseller.ui.theme.AppTypography
import com.drs.dseller.ui.theme.Green40

@Composable
fun DefaultBottomDialog(
    title:String,
    message:String,
    background:Color = Green40,
    textColor:Color = Color.White,
    positiveText:String?,
    negativeText:String?,
    positiveCallback:(() -> Unit)?,
    negativeCallback:(() -> Unit)?
) {

    Column(
        modifier = Modifier
            .clip(
                RoundedCornerShape(
                    topStart = dimensionResource(id = R.dimen.twenty_dp),
                    topEnd = dimensionResource(id = R.dimen.twenty_dp)
                )
            )
            .wrapContentHeight()
            .fillMaxWidth()
            .background(background)

    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = dimensionResource(id = R.dimen.fifteen_dp))
                .padding(
                    vertical = dimensionResource(id = R.dimen.fifteen_dp),
                    horizontal = dimensionResource(
                        id = R.dimen.twenty_five_dp
                    )
                ),
            text = title,
            style = AppTypography.titleLarge,
            color = textColor,
            textAlign = TextAlign.Center
        )
        Text(
            modifier = Modifier
            .padding(
                horizontal = dimensionResource(
                    id = R.dimen.twenty_five_dp
                )
            ),
            text = message,
            style = AppTypography.bodyMedium,
            color = textColor
        )

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ){
            positiveText?.let{
                Button(
                    modifier = Modifier
                        .padding(dimensionResource(id = R.dimen.fifteen_dp)),
                    onClick = { positiveCallback?.invoke()},
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent
                    )
                ) {
                    Text(
                        text = positiveText,
                        style = AppTypography.bodyMedium,
                        color = textColor
                    )
                }
            }

            negativeText?.let {
                Button(
                    modifier = Modifier
                        .padding(dimensionResource(id = R.dimen.fifteen_dp)),
                    onClick = { negativeCallback?.invoke()},
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent
                    )
                ) {
                    Text(
                        text = negativeText,
                        style = AppTypography.bodyMedium,
                        color = textColor
                    )
                }
            }
        }


    }

    BackHandler(true) {
        negativeCallback?.invoke()
    }

}