@file:OptIn(ExperimentalMaterial3Api::class)

package com.drs.dseller.feature_onboarding.presentation.screens.confirm_code

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.drs.dseller.R
import com.drs.dseller.core.ui_elements.GreenCircularProgressIndicator
import com.drs.dseller.core.ui_elements.text_fields.FormTextField
import com.drs.dseller.feature_onboarding.presentation.states.ConfirmUserState
import com.drs.dseller.ui.theme.AppTypography
import com.drs.dseller.ui.theme.Black80
import com.drs.dseller.ui.theme.Green40

@Composable
fun ConfirmationCodeBody(
    state: ConfirmUserState,
    codeChangeListener:(String) -> Unit,
    confirmListener:() -> Unit,
    resendCodeListener:() ->Unit
){
    val parentModifier = Modifier

    Box(modifier = parentModifier
        .fillMaxWidth()
        .fillMaxHeight()
        .padding(horizontal = dimensionResource(id = R.dimen.twenty_five_dp))) {

        Column(
        ) {
            Spacer(modifier = parentModifier
                .padding(top = dimensionResource(id = R.dimen.seventy_dp))
            )
            Text(
                text = stringResource(id = R.string.confirmation_title),
                style = AppTypography.headlineLarge,
                color = Black80
            )
            FormTextField(
                modifier = parentModifier
                    .fillMaxWidth(),
                title = stringResource(id = R.string.confirmation_code),
                fieldText = state.confirmationCode,
                textChangeCallback = codeChangeListener,
                keyboardType = KeyboardType.Number
            )
            GreenCircularProgressIndicator(
                parentModifier.align(Alignment.CenterHorizontally),
                state.isConfirming
            )
        }

       Row(
           modifier = parentModifier
               .align(Alignment.BottomCenter)
               .fillMaxWidth(),
           horizontalArrangement = Arrangement.SpaceBetween
       ){
           val resendModifier = remember {
               parentModifier.clickable(onClick =  resendCodeListener)
           }
           Text(
               modifier = resendModifier
                   .padding(vertical = dimensionResource(id = R.dimen.fifteen_dp))
                   .align(Alignment.CenterVertically),
               text = stringResource(id = R.string.confirmation_resend),
               style = AppTypography.titleMedium,
               color = Green40
           )

           IconButton(
               modifier = parentModifier
                   .padding(vertical = dimensionResource(id = R.dimen.fifteen_dp))
                   .size(dimensionResource(id = R.dimen.fifty_fifty_dp))
                   .clip(CircleShape)
                   .background(Green40),
               onClick = confirmListener
           ) {
               Image(
                   modifier = parentModifier
                       .padding(dimensionResource(id = R.dimen.fifteen_dp)),
                   painter = painterResource(id = R.drawable.arrow_right),
                   contentDescription = stringResource(id = R.string.description_confirm_code)
               )
           }
       }
    }
}

@Preview
@Composable
fun ConfirmationScreenPreview(){
    ConfirmationCodeBody(state = ConfirmUserState(),{},{},{})
}