@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)

package com.drs.dseller.core.ui_elements.text_fields

import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.drs.dseller.R
import com.drs.dseller.ui.theme.AppTypography
import com.drs.dseller.ui.theme.Black80
import com.drs.dseller.ui.theme.Green40
import com.drs.dseller.ui.theme.Grey20
import com.drs.dseller.ui.theme.Grey60
import com.drs.dseller.ui.theme.Grey80

@Composable
fun SearchField(
    modifier: Modifier = Modifier,
    text:String ="",
    placeHolderText:String = "",
    textChangeCallback:(String) ->Unit,
){
    val interactionSource = remember{
        MutableInteractionSource()
    }
    val keyboardController = LocalSoftwareKeyboardController.current

    BasicTextField(
        modifier = modifier
            .wrapContentHeight()
            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.twenty_dp))),
        value = text,
        onValueChange = textChangeCallback,
        singleLine = true,
        textStyle = AppTypography.bodySmall,
        interactionSource = interactionSource,
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
            }
        )
    ) {

        TextFieldDefaults.DecorationBox(
            value = text,
            innerTextField = it,
            enabled = true,
            singleLine = true,
            visualTransformation = VisualTransformation.None,
            interactionSource = interactionSource,
            contentPadding = PaddingValues(
                horizontal = dimensionResource(id = R.dimen.ten_dp),
                vertical = dimensionResource(id = R.dimen.fifteen_dp)
            ),
            placeholder = {
                PlaceHolder(text = placeHolderText)
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Grey20,
                unfocusedContainerColor = Grey20,
                focusedTextColor = Black80,
                unfocusedTextColor = Black80,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            leadingIcon = {
                Image(
                    modifier = Modifier
                        .padding(horizontal = 5.dp),
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = stringResource(id = R.string.description_search_icon))
            },
            trailingIcon = {
                IconButton(
                    modifier = Modifier
                        .padding(horizontal = 5.dp)
                        .size(20.dp),
                    onClick = {
                        textChangeCallback.invoke("")
                    }
                ){
                    Image(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(),
                        painter = painterResource(id = R.drawable.ic_cancel),
                        contentDescription = stringResource(id = R.string.description_cancel_search_icon))
                }
            }
        )

    }
}

@Composable
private fun PlaceHolder(text:String){
    Text(
        text= text,
        style = AppTypography.titleSmall,
        color = Grey80
    )
}

@Preview
@Composable
private fun SearchPreview(){
    SearchField(Modifier.fillMaxWidth(),
        //text = "Hello",
        placeHolderText = "Search Store",
        textChangeCallback = {})
}