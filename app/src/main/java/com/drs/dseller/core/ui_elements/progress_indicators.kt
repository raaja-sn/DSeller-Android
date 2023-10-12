package com.drs.dseller.core.ui_elements

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.drs.dseller.R
import com.drs.dseller.ui.theme.Green40

@Composable
fun GreenCircularProgressIndicator(
    modifier:Modifier = Modifier,
    isVisible:Boolean
){

    if(!isVisible) return

    CircularProgressIndicator(
        modifier = modifier
            .padding(vertical = dimensionResource(id = R.dimen.fifteen_dp))
            .size(dimensionResource(id = R.dimen.thirty_dp)),
        color = Green40
    )

}

@Preview
@Composable
fun IndicatorPreview(){
    GreenCircularProgressIndicator(isVisible = true)
}