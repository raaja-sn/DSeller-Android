package com.drs.dseller.core.ui_elements.appbar

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.drs.dseller.R
import com.drs.dseller.core.ui_elements.clickableWithoutRipple
import com.drs.dseller.core.ui_elements.fonts.appFonts
import com.drs.dseller.feature_account.presentation.ROUTE_ACCOUNT
import com.drs.dseller.feature_account.presentation.toAccount
import com.drs.dseller.feature_home.ROUTE_HOME
import com.drs.dseller.feature_orders.presentation.ROUTE_CART
import com.drs.dseller.feature_orders.presentation.toShoppingCart
import com.drs.dseller.feature_products.presentation.ROUTE_PRODUCTS
import com.drs.dseller.ui.theme.AppTypography
import com.drs.dseller.ui.theme.Black80
import com.drs.dseller.ui.theme.Green40
import com.drs.dseller.ui.theme.Green40Transparent

@Composable
fun AppBottomNavigationBar(
    cartQuantity:Int,
    navHostController: NavHostController
){

    val backStackEntry = navHostController.currentBackStackEntryAsState()

    val interactionSource = remember {
        MutableInteractionSource()
    }

    val shopModifier= remember {
        Modifier
            .clickableWithoutRipple(
                interactionSource
            ) {
                getParentRoute(backStackEntry)?.let {
                    if (it != ROUTE_PRODUCTS && it != ROUTE_HOME) {
                        navigateToShop(it, navHostController)
                    }
                }
            }
    }

    val cartModifier = remember{
        Modifier.clickableWithoutRipple(
                interactionSource
            ) {
                getParentRoute(backStackEntry)?.let {
                    navigateToCart(it, navHostController)
                }
            }
    }

    val accountModifier = remember{
        Modifier
            .clickableWithoutRipple(
                interactionSource
            ) {
                getParentRoute(backStackEntry)?.let {
                    navigateToAccount(it, navHostController)
                }
            }
    }

    BottomAppBar(
        modifier = Modifier
            .shadow(elevation = 5.dp, shape =  RoundedCornerShape(
                topStart = dimensionResource(id = R.dimen.twenty_five_dp),
                topEnd = dimensionResource(id = R.dimen.twenty_five_dp)
            ), spotColor = Black80)
            .clip(
                RoundedCornerShape(
                    topStart = dimensionResource(id = R.dimen.twenty_five_dp),
                    topEnd = dimensionResource(id = R.dimen.twenty_five_dp)
                )
            )
            .height(60.dp)
            .fillMaxWidth()
            ,
        containerColor = Color.White
    ) {
        BottomNavElement(
            modifier = shopModifier,
            isSelected = (getParentRoute(backStackEntry) ?: "") == ROUTE_HOME || (getParentRoute(backStackEntry) ?: "") == ROUTE_PRODUCTS,
            type = 1
        )
        BottomNavCartElement(
            modifier = cartModifier,
            cartQuantity = cartQuantity,
            isSelected = (getParentRoute(backStackEntry) ?: "") == ROUTE_CART,
            type = 2
        )
        BottomNavElement(
            modifier = accountModifier,
            isSelected = (getParentRoute(backStackEntry) ?: "") == ROUTE_ACCOUNT,
            0
        )


    }
}

@Composable
private fun RowScope.BottomNavElement(
    modifier:Modifier,
    isSelected:Boolean,
    type:Int
){
    Column(
        modifier = modifier
            .weight(1f),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Icon(
            modifier = Modifier
                .size(dimensionResource(id = R.dimen.thirty_dp))
                .padding(dimensionResource(id = R.dimen.five_dp)),
            painter = painterResource(id = getIconId(type,isSelected)),
            contentDescription = "Bottom Navigation Icon",
            tint = Color.Unspecified
        )
        Text(
            text = stringResource(id = getIconLabel(type)),
            style = AppTypography.bodySmall,
            color = getIconLabelColor(isSelected)
        )
    }

}

@Composable
private fun RowScope.BottomNavCartElement(
    modifier:Modifier,
    cartQuantity:Int,
    isSelected:Boolean,
    type:Int
){
    Column(
        modifier = modifier
            .weight(1f),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Box(
            modifier = Modifier
                .size(dimensionResource(id = R.dimen.thirty_dp))
        ) {
            Icon(
                modifier = Modifier
                    .size(dimensionResource(id = R.dimen.thirty_dp))
                    .padding(dimensionResource(id = R.dimen.five_dp)),
                painter = painterResource(id = getIconId(type,isSelected)),
                contentDescription = "Bottom Navigation Icon",
                tint = Color.Unspecified
            )
            if(!isSelected && cartQuantity > 0){
                Text(
                    modifier = Modifier
                        .size(20.dp)
                        .clip(CircleShape)
                        .background(Green40)
                        .padding(top = 2.dp)
                        .align(Alignment.TopEnd),
                    text = cartQuantity.toString(),
                    style = TextStyle(
                        textAlign = TextAlign.Center,
                        fontSize = 12.sp,
                        fontFamily = appFonts,
                        fontWeight = FontWeight.Normal
                    ),
                    color = Color.White
                )
            }
        }
        Text(
            text = stringResource(id = getIconLabel(type)),
            style = AppTypography.bodySmall,
            color = getIconLabelColor(isSelected)
        )
    }
}

private fun getParentRoute(backStackEntry: State<NavBackStackEntry?>) =
    backStackEntry.value?.destination?.parent?.route

private fun getIconId(type:Int,isSelected:Boolean):Int{
    return when(type){
        1 -> {
            if(isSelected){
                R.drawable.ic_shop_selected
            }else{
                R.drawable.ic_shop_unselected
            }
        }
        2 -> {
            if(isSelected){
                R.drawable.ic_cart_selected
            }else{
                R.drawable.ic_cart_unselected
            }
        }
        else -> {
            if(isSelected){
                R.drawable.ic_account_selected
            }else{
                R.drawable.ic_account_unselected
            }
        }
    }
}

private fun getIconLabelColor(isSelected:Boolean):Color{
    return  if(isSelected){
        Green40
    }else{
        Black80
    }
}

private fun getIconLabel(type:Int):Int{
    return when(type){
        1 -> {
            R.string.bottom_nav_icon_shop
        }
        2 -> {
            R.string.bottom_nav_icon_cart
        }
        else -> {
            R.string.bottom_nav_icon_account
        }
    }
}

private fun navigateToShop(parentRoute:String,navHostController: NavHostController){
    when(parentRoute){
        ROUTE_CART ->{
            navHostController.popBackStack(ROUTE_CART,true)
        }
        ROUTE_ACCOUNT ->{
            navHostController.popBackStack(ROUTE_ACCOUNT,true)
        }
    }
}

private fun navigateToCart(parentRoute: String,navHostController: NavHostController){
    if(parentRoute == ROUTE_ACCOUNT){
        navHostController.popBackStack(ROUTE_ACCOUNT,true)
    }
    navHostController.toShoppingCart()
}

private fun navigateToAccount(parentRoute: String,navHostController: NavHostController){
    if(parentRoute == ROUTE_CART){
        navHostController.popBackStack(ROUTE_CART,true)
    }
    navHostController.toAccount(navHostController)
}


@Preview
@Composable
private fun BottomNAvPreview(){
    val navHostController = rememberNavController()
    AppBottomNavigationBar(20,navHostController = navHostController)
}