package com.drs.dseller.core.ui_elements.text_fields

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.drs.dseller.R
import com.drs.dseller.ui.theme.AppTypography
import com.drs.dseller.ui.theme.Grey80

@ExperimentalMaterial3Api
@Composable
fun FormTextField(
    modifier:Modifier = Modifier,
    title:String,
    enabled:Boolean = true,
    fieldText:String,
    keyboardType: KeyboardType = KeyboardType.Text,
    textChangeCallback:(String) -> Unit
){
    Column(
        modifier = modifier
            .padding(bottom = dimensionResource(R.dimen.twenty_dp))
    ) {
        Text(
            modifier = modifier
                .padding(vertical = dimensionResource(R.dimen.ten_dp)),
            text = title,
            style = AppTypography.titleMedium,
            color = Grey80
        )
        DefaultTextField(
            modifier,
            fieldText,
            enabled,
            keyboardType,
            textChangeCallback
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun FormTextFieldPreview(){
    FormTextField(title = "Full Name", fieldText = "Some Text"){

    }
}