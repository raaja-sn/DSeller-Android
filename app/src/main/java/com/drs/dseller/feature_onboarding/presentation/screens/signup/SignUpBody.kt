@file:OptIn(ExperimentalMaterial3Api::class)

package com.drs.dseller.feature_onboarding.presentation.screens.signup

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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import com.drs.dseller.R
import com.drs.dseller.core.ui_elements.GreenCircularProgressIndicator
import com.drs.dseller.core.ui_elements.buttons.RoundCorneredButton
import com.drs.dseller.core.ui_elements.text_fields.FormTextField
import com.drs.dseller.feature_onboarding.presentation.states.RegisterUserState
import com.drs.dseller.ui.theme.AppTypography
import com.drs.dseller.ui.theme.Black80
import com.drs.dseller.ui.theme.Green40
import com.drs.dseller.ui.theme.Grey80

@Composable
fun SignUpScreenBody(
    state: RegisterUserState,
    usernameChangeListener:(String) -> Unit,
    phoneChangedListener:(String) ->Unit,
    emailChangeListener:(String) -> Unit,
    passwordChangeListener:(String) -> Unit,
    signUpListener:()->Unit,
    loginListener:()->Unit
){
    val scrollableState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(scrollableState)
            .padding(horizontal = dimensionResource(id = R.dimen.twenty_five_dp)),
        horizontalAlignment = Alignment.Start
    ) {
        val parentModifier = remember{ Modifier }
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
            text = stringResource(R.string.signup_title),
            style = AppTypography.headlineMedium,
            color = Black80
        )
        Text(
            modifier = parentModifier
                .padding(vertical = dimensionResource(R.dimen.five_dp))
                .padding(bottom = dimensionResource(id = R.dimen.thirty_dp)),
            text = stringResource(R.string.signup_title_info),
            style = AppTypography.bodyLarge,
            color = Grey80
        )

        FormTextField(
            parentModifier
                .fillMaxWidth(),
            stringResource(id = R.string.signup_name),
            true,
            state.name,
            textChangeCallback = usernameChangeListener
        )
        FormTextField(
            parentModifier
                .fillMaxWidth(),
            stringResource(id = R.string.signup_phone),
            true,
            state.phoneNumber,
            KeyboardType.Number,
            phoneChangedListener
        )
        FormTextField(
            parentModifier
                .fillMaxWidth(),
            stringResource(id = R.string.signup_email),
            true,
            state.email,
            KeyboardType.Email,
            emailChangeListener
        )
        FormTextField(
            parentModifier
                .fillMaxWidth(),
            stringResource(id = R.string.signup_password),
            true,
            state.password,
            KeyboardType.Password,
            passwordChangeListener
        )

        TermsText(modifier = parentModifier)

        RoundCorneredButton(
            buttonText = stringResource(id = R.string.signup_title),
            modifier = parentModifier
                .fillMaxWidth()
                .padding(top = dimensionResource(id = R.dimen.twenty_dp)),
            isEnabled = !state.isRegistering,
            clickCallback = signUpListener
        )

        GreenCircularProgressIndicator(
            modifier = parentModifier
                .align(Alignment.CenterHorizontally),
            isVisible = state.isRegistering
        )

        LoginText(
            modifier = parentModifier
                .align(Alignment.CenterHorizontally),
            loginListener
        )

    }
}

@Composable
fun TermsText(
    modifier:Modifier,
){
    val termsText = stringResource(id = R.string.signup_terms_info)
    Text(
        text = buildAnnotatedString {
            append(termsText.substring(0,31))
            withStyle(
                SpanStyle(
                    color = Green40
                )
            ){
                append(termsText.substring(31,48))
            }
            append(termsText.substring(48,52))
            withStyle(
                SpanStyle(
                    color = Green40
                )
            ){
                append(termsText.substring(52,termsText.length))
            }
        },
        style = AppTypography.bodySmall,
        color = Grey80
    )
}

@Composable
fun LoginText(
    modifier: Modifier,
    loginCallback:() -> Unit
) {
    val loginInfoText = stringResource(id = R.string.signup_login_info)
    Text(
        modifier = modifier
            .clickable {
                loginCallback()
            }
            .padding(
                top = dimensionResource(id = R.dimen.twenty_dp),
                bottom = dimensionResource(id = R.dimen.thirty_dp)
            ),
        text = buildAnnotatedString {
            append("$loginInfoText ")
            withStyle(
                SpanStyle(
                    color = Green40
                )
            ) {
                append(stringResource(id = R.string.login_title))
            }
        },
        style = AppTypography.titleSmall,
        color = Black80
    )
}
