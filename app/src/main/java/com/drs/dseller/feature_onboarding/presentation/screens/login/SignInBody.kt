@file:OptIn(ExperimentalMaterial3Api::class)

package com.drs.dseller.feature_onboarding.presentation.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import com.drs.dseller.R
import com.drs.dseller.core.ui_elements.GreenCircularProgressIndicator
import com.drs.dseller.core.ui_elements.buttons.RoundCorneredButton
import com.drs.dseller.core.ui_elements.text_fields.FormTextField
import com.drs.dseller.feature_onboarding.presentation.states.LoginUserState
import com.drs.dseller.ui.theme.AppTypography
import com.drs.dseller.ui.theme.Black80
import com.drs.dseller.ui.theme.Green40
import com.drs.dseller.ui.theme.Grey60
import com.drs.dseller.ui.theme.Grey80

@Composable
fun SignInBody(
    state:LoginUserState,
    emailChangeListener:(String) ->Unit,
    passwordChangeListener:(String) ->Unit,
    loginListener:()->Unit,
    signUpListener:() -> Unit
){
    val scrollableState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(id = R.dimen.twenty_five_dp))
            .verticalScroll(scrollableState)
    ){

        val parentModifier = Modifier
        Spacer(
            modifier = Modifier.height(dimensionResource(id = R.dimen.twenty_five_dp))
        )
        Image(
            modifier = parentModifier
                .size(dimensionResource(id = R.dimen.fifty_fifty_dp))
                .align(Alignment.CenterHorizontally),
            painter = painterResource(id = R.drawable.dseller_logo_orange),
            contentDescription = stringResource(id = R.string.description_logo)
        )
        Spacer(
            modifier = parentModifier.height(dimensionResource(id = R.dimen.one_twenty_dp))
        )
        Text(
            modifier = parentModifier
                .padding(vertical = dimensionResource(R.dimen.ten_dp)),
            text = stringResource(R.string.login_title),
            style = AppTypography.headlineMedium,
            color = Black80
        )
        Text(
            modifier = parentModifier
                .padding(vertical = dimensionResource(R.dimen.five_dp))
                .padding(bottom = dimensionResource(id = R.dimen.thirty_dp)),
            text = stringResource(R.string.login_title_info),
            style = AppTypography.bodyLarge,
            color = Grey80
        )

        FormTextField(
            parentModifier
                .fillMaxWidth(),
            title = stringResource(id = R.string.signup_email),
            enabled = true,
            keyboardType = KeyboardType.Email,
            fieldText = state.email,
            textChangeCallback = emailChangeListener
        )

        FormTextField(
            parentModifier
                .fillMaxWidth(),
            title = stringResource(id = R.string.signup_password),
            enabled = true,
            keyboardType = KeyboardType.Password,
            fieldText = state.password,
            textChangeCallback = passwordChangeListener
        )
        Text(
            modifier = parentModifier
                .padding(vertical = dimensionResource(R.dimen.ten_dp))
                .align(Alignment.End),
            text = stringResource(R.string.login_forgot),
            style = AppTypography.bodySmall,
            color = Grey60
        )
        RoundCorneredButton(
            modifier = parentModifier
                .fillMaxWidth()
                .padding(vertical = dimensionResource(id = R.dimen.fifteen_dp)),
            buttonText = stringResource(id = R.string.login_title),
            isEnabled = !state.isLoggingIn,
            clickCallback = loginListener
        )
        GreenCircularProgressIndicator(
            modifier = parentModifier
                .align(Alignment.CenterHorizontally),
            isVisible = state.isLoggingIn
        )
        SignUpText(
            modifier = parentModifier
                .align(Alignment.CenterHorizontally),
            signUpListener
        )
    }

}

@Composable
fun SignUpText(
    modifier: Modifier,
    signUpCallback:() -> Unit
) {
    val signUpInfotext = stringResource(id = R.string.login_signup_info)
    Text(
        modifier = modifier
            .clickable {
                signUpCallback()
            }
            .padding(
                top = dimensionResource(id = R.dimen.twenty_dp),
                bottom = dimensionResource(id = R.dimen.thirty_dp)
            ),
        text = buildAnnotatedString {
            append("$signUpInfotext ")
            withStyle(
                SpanStyle(
                    color = Green40
                )
            ) {
                append(stringResource(id = R.string.signup_title))
            }
        },
        style = AppTypography.titleSmall,
        color = Black80
    )
}


@Preview
@Composable
fun SignInPreview(){
    SignInBody(state = LoginUserState(
        email = "test@gmail.com",
        password = "Secret"
    ), {},{},{},{})
}