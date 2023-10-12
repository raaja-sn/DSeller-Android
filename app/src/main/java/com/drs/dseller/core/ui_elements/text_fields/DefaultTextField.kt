@file:OptIn(ExperimentalMaterial3Api::class)

package com.drs.dseller.core.ui_elements.text_fields

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.drs.dseller.R
import com.drs.dseller.ui.theme.AppTypography
import com.drs.dseller.ui.theme.Black80
import com.drs.dseller.ui.theme.Green40
import com.drs.dseller.ui.theme.Grey60
import com.drs.dseller.ui.theme.White80

@ExperimentalMaterial3Api
@Composable
fun DefaultTextField(
    modifier:Modifier = Modifier,
    fieldText:String = "",
    enabled:Boolean = true,
    keyboardType:KeyboardType,
    editListener:(String) -> Unit,
){

    val interactionSource = remember {
        MutableInteractionSource()
    }

    var numberMatcher = remember{
        getNumberMatcher(keyboardType)
    }

    BasicTextField(
        modifier = modifier
            .wrapContentHeight(),
        singleLine = true,
        textStyle = AppTypography.bodyLarge,
        value = fieldText,
        visualTransformation = if(keyboardType == KeyboardType.Password){
            PasswordVisualTransformation()
        }else{
            VisualTransformation.None
        },
        onValueChange = {
            if((keyboardType == KeyboardType.Number || keyboardType == KeyboardType.Decimal)){
                if(numberMatcher.matches(it)){
                    editListener.invoke(it)
                }
            }else{
                editListener.invoke(it)
            }
        },
        interactionSource = interactionSource,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType)
    ){
        TextFieldDefaults.DecorationBox(
            value = fieldText,
            innerTextField = it,
            enabled = enabled,
            singleLine = true,
            visualTransformation = if(keyboardType == KeyboardType.Password){
                PasswordVisualTransformation()
            }else{
                VisualTransformation.None
                 },
            interactionSource = interactionSource,
            colors = TextFieldDefaults.colors(
                focusedTextColor = Black80,
                unfocusedTextColor = Black80,
                focusedPlaceholderColor = Grey60,
                unfocusedPlaceholderColor = Grey60,
                unfocusedIndicatorColor = White80,
                focusedIndicatorColor = Green40,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent
            ),
            contentPadding = PaddingValues(
                horizontal = 0.dp,
                vertical = dimensionResource(R.dimen.ten_dp)
            )
        )
    }


}

private fun getNumberMatcher(type:KeyboardType):Regex{
    return if(type == KeyboardType.Number){
        Regex("^\\d+$")
    }else if(type == KeyboardType.Decimal){
        Regex("^\\d+\\.\\d+$")
    }else{
        Regex("^.$")
    }
}




@Preview
@Composable
private fun TextFieldPreview(){
    DefaultTextField(Modifier.width(250.dp), fieldText = "Hello",true,KeyboardType.Email){

    }
}