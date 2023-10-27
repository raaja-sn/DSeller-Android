package com.drs.dseller.feature_account.presentation.screens.user_orders

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.drs.dseller.R
import com.drs.dseller.feature_account.domain.model.UserOrder
import com.drs.dseller.ui.theme.AppTypography
import com.drs.dseller.ui.theme.Black80
import com.drs.dseller.ui.theme.White80
import kotlinx.coroutines.flow.flow
import java.util.Date

@Composable
fun UserOrdersBody(
    innerPadding:PaddingValues,
    ordersPagingItems:LazyPagingItems<UserOrder>,
    orderClicked:(String) -> Unit
){

    val listState = rememberLazyListState()

    val orders = remember {
        mutableStateOf(ordersPagingItems)
    }

    LazyColumn(
        modifier = Modifier
            .padding(innerPadding)
            .padding(horizontal = dimensionResource(id = R.dimen.twenty_dp))
            .fillMaxSize(),
        state = listState,
    ){
        items(
            count = orders.value.itemCount,
            key = orders.value.itemKey { order: UserOrder ->
                order.orderId
            }
        ){ idx ->
            orders.value[idx]?.let{
                UserOrderComponent(
                    order = it,
                    orderClicked = orderClicked
                )
                if(idx != orders.value.itemCount-1){
                    Divider(
                        thickness = 1.dp,
                        color = White80
                    )
                }
            }
        }
    }


}

@Preview(showBackground = true)
@Composable
private fun UserOrderListPreview(){
    val fl = flow{
        /*emit(PagingData.from(listOf(
            UserOrder("DES000056", invoiceNo = "DES000056", billValue = 500000.0, purchaseDate = Date().time),
            UserOrder("DES000057", invoiceNo = "DES000057", billValue = 500000.0, purchaseDate = Date().time),
            UserOrder("DES000058", invoiceNo = "DES000058", billValue = 500000.0, purchaseDate = Date().time),
            UserOrder("DES000059", invoiceNo = "DES000059", billValue = 500000.0, purchaseDate = Date().time),
            UserOrder("DES000060", invoiceNo = "DES000060", billValue = 500000.0, purchaseDate = Date().time),
            UserOrder("DES000061", invoiceNo = "DES000061", billValue = 500000.0, purchaseDate = Date().time),
        )))*/
        emit(PagingData.from(listOf<UserOrder>()))
    }
    UserOrdersBody(
        innerPadding = PaddingValues(0.dp) ,
        ordersPagingItems = fl.collectAsLazyPagingItems(),
        ){

    }
}