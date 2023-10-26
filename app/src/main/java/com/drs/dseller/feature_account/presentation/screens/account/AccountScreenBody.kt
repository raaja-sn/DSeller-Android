package com.drs.dseller.feature_account.presentation.screens.account

import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.drs.dseller.R
import com.drs.dseller.core.ui_elements.clickableWithoutRipple
import com.drs.dseller.feature_account.domain.model.AccountUser
import com.drs.dseller.feature_account.presentation.states.AccountScreenState
import com.drs.dseller.ui.theme.AppTypography
import com.drs.dseller.ui.theme.Black80
import com.drs.dseller.ui.theme.Green40
import com.drs.dseller.ui.theme.Grey20
import com.drs.dseller.ui.theme.Grey80
import com.drs.dseller.ui.theme.White80

@Composable
fun AccountScreenBody(
    innerPadding: PaddingValues,
    state: AccountScreenState,
    myDetailClicked:()->Unit,
    myOrdersClicked:() -> Unit,
    aboutClicked:() -> Unit,
    logoutClicked:() -> Unit
){
    
   Box(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize(),
   ){
       Column {

           Row(
               modifier = Modifier
                   .fillMaxWidth()
                   .padding(dimensionResource(id = R.dimen.twenty_dp))
           ) {
               Image(
                   modifier = Modifier
                       .clip(RoundedCornerShape(dimensionResource(id = R.dimen.twenty_five_dp)))
                       .size(dimensionResource(id = R.dimen.seventy_dp)),
                   painter = painterResource(id = R.drawable.avatar),
                   contentDescription = stringResource(id = R.string.description_account_profile_image),
                   contentScale = ContentScale.Crop
               )
               Column {
                   Text(
                       modifier = Modifier
                           .padding(
                               horizontal = dimensionResource(id = R.dimen.twenty_five_dp),
                               vertical = dimensionResource(id = R.dimen.five_dp)
                           ),
                       text = state.user?.name?:"",
                       style = AppTypography.headlineMedium,
                       color = Black80
                   )
                   Text(
                       modifier = Modifier
                           .padding(
                               horizontal = dimensionResource(id = R.dimen.twenty_five_dp),
                               vertical = dimensionResource(id = R.dimen.five_dp)
                           ),
                       text = state.user?.phoneNumber?:"",
                       style = AppTypography.bodySmall,
                       color = Grey80
                   )
               }
           }
           Divider(
               thickness = 1.dp,
               color = White80
           )
           AccountOption(
               title = stringResource(id = R.string.account_order),
               iconId = R.drawable.ic_account_order,
               myOrdersClicked
           )
           AccountOption(
               title = stringResource(id = R.string.account_detail),
               iconId = R.drawable.ic_account_detail,
               myDetailClicked
           )
           AccountOption(
               title = stringResource(id = R.string.account_about),
               iconId = R.drawable.ic_account_about,
               aboutClicked
           )
       }

       Button(
           modifier = Modifier
               .fillMaxWidth()
               .padding(dimensionResource(id = R.dimen.twenty_dp))
               .align(Alignment.BottomCenter),
           colors = ButtonDefaults.buttonColors(
               containerColor = Grey20,
               disabledContainerColor = Grey80
           ),
           shape = RoundedCornerShape(dimensionResource(id = R.dimen.twenty_five_dp)),
           contentPadding = PaddingValues(dimensionResource(id = R.dimen.twenty_dp)),
           onClick = logoutClicked
       ){
           Icon(
               modifier = Modifier
                   .size(dimensionResource(id = R.dimen.twenty_five_dp)),
               painter = painterResource(id = R.drawable.ic_logout),
               contentDescription = stringResource(id = R.string.description_account_logout),
               tint = Color.Unspecified
           )
           Text(
               modifier = Modifier
                   .weight(1f)
                   .padding(horizontal = dimensionResource(id = R.dimen.fifteen_dp)),
               text = stringResource(id = R.string.account_logout),
               style = AppTypography.bodyLarge,
               textAlign = TextAlign.Center,
               color = Green40
           )
       }
   }

}

@Composable
private fun AccountOption(
    title:String,
    iconId:Int,
    callback:() -> Unit
) {

    val rowModifier = remember(title) {
        val interactionSource = MutableInteractionSource()
        Modifier
            .clickableWithoutRipple(
                interactionSource,
                callback
            )
    }
    Row(
        modifier = rowModifier
            .fillMaxWidth()
            .padding(
                horizontal = dimensionResource(id = R.dimen.twenty_dp),
                vertical = dimensionResource(id = R.dimen.twenty_dp)
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .size(dimensionResource(id = R.dimen.twenty_dp)),
            painter = painterResource(id = iconId),
            contentDescription = stringResource(id = R.string.description_account_order),
            tint = Color.Unspecified
        )

        Text(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = dimensionResource(id = R.dimen.fifteen_dp)),
            text = title,
            style = AppTypography.titleMedium,
            textAlign = TextAlign.Start,
            color = Black80
        )
        Icon(
            modifier = Modifier
                .size(dimensionResource(id = R.dimen.ten_dp)),
            painter = painterResource(id = R.drawable.arrow_right_black),
            contentDescription = stringResource(id = R.string.description_arrow_right),
            tint = Color.Unspecified
        )
    }
    Divider(
        thickness = 1.dp,
        color = White80
    )
    
}

@Preview(showBackground = true)
@Composable
private fun AccountScreenPreview(){
    AccountScreenBody(
        innerPadding = PaddingValues(0.dp),
        state = AccountScreenState(
            user = AccountUser(
                name = "Test User Name",
                email = "test_user@gmail.com",
                phoneNumber = "+915489135957"
            )
        ),
        myDetailClicked = {  },
        myOrdersClicked = {  },
        aboutClicked = { },
        {})
}