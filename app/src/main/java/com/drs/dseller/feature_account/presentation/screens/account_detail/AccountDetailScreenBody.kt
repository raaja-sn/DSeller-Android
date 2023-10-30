@file:OptIn(ExperimentalMaterial3Api::class)

package com.drs.dseller.feature_account.presentation.screens.account_detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import com.drs.dseller.R
import com.drs.dseller.core.ui_elements.GreenCircularProgressIndicator
import com.drs.dseller.core.ui_elements.buttons.RoundCorneredButton
import com.drs.dseller.core.ui_elements.text_fields.FormTextField
import com.drs.dseller.feature_account.presentation.states.AccountDetailScreenState

@Composable
fun AccountDetailScreenBody(
    state:AccountDetailScreenState,
    nameChanged:(String) -> Unit,
    phoneNumberChanged:(String) -> Unit,
    update:() -> Unit
){

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = dimensionResource(id = R.dimen.twenty_dp)),
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = dimensionResource(id = R.dimen.thirty_dp)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier
                    .clip(RoundedCornerShape(dimensionResource(id = R.dimen.twenty_five_dp)))
                    .size(dimensionResource(id = R.dimen.seventy_dp)),
                painter = painterResource(id = R.drawable.avatar),
                contentDescription = stringResource(id = R.string.description_account_profile_image),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.thirty_dp)))
            FormTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                title = stringResource(id = R.string.signup_name),
                fieldText = state.name,
                textChangeCallback = nameChanged
            )

            FormTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                title = stringResource(id = R.string.signup_phone),
                fieldText = state.phoneNumber,
                textChangeCallback = phoneNumberChanged,
                imeAction = ImeAction.Done
            )
        }

        Column(
            modifier = Modifier
                .padding(vertical = dimensionResource(id = R.dimen.twenty_dp))
                .align(Alignment.BottomCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            RoundCorneredButton(
                modifier = Modifier
                    .fillMaxWidth(),
                buttonText = stringResource(id = R.string.account_detail_update),
                isEnabled = !state.updatingUser,
                clickCallback = update
            )
            GreenCircularProgressIndicator(
                isVisible = state.updatingUser
            )
        }

    }

}


@Preview(showBackground = true)
@Composable
private fun AccountDetailPreview(){
    AccountDetailScreenBody(
        state = AccountDetailScreenState(
            name = "Raaja",
            phoneNumber = "3453453543",
            updatingUser = false
        ),
        nameChanged = {},
        phoneNumberChanged = {},
        {})
}