package com.drs.dseller.core.ui_elements.buttons

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.drs.dseller.R
import com.drs.dseller.ui.theme.AppTypography
import com.drs.dseller.ui.theme.Green40
import com.drs.dseller.ui.theme.Grey60


@Composable
fun RoundCorneredButton(
    modifier:Modifier = Modifier,
    buttonColor:Color = Green40,
    buttonText:String,
    isEnabled:Boolean = true,
    clickCallback:()->Unit
){
    Button(
        modifier = modifier
            .wrapContentHeight(),
        colors = ButtonDefaults.buttonColors(
            buttonColor,
            disabledContainerColor = buttonColor.copy(alpha = 0.5f),
        ),
        enabled = isEnabled,
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.twenty_dp)),
        contentPadding = PaddingValues(vertical = dimensionResource(id = R.dimen.twenty_dp)),
        onClick = clickCallback) {
        Text(
            text = buttonText,
            style = AppTypography.titleMedium,
            color = if(isEnabled) {
                Color.White
            }else{
              Grey60
            },
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
fun ButtonPreview(){
    RoundCorneredButton(Modifier.width(200.dp), Green40,"Greetings",false) {

    }
}