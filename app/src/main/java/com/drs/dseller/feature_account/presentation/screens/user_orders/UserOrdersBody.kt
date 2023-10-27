package com.drs.dseller.feature_account.presentation.screens.user_orders

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.drs.dseller.R
import com.drs.dseller.feature_account.domain.model.UserOrder
import com.drs.dseller.ui.theme.White80
import kotlinx.coroutines.flow.flow

@Composable
fun UserOrdersBody(
    innerPadding:PaddingValues,
    orders:LazyPagingItems<UserOrder>,
    orderClicked:(String) -> Unit
){

    val listState = rememberLazyListState()

    LazyColumn(
        modifier = Modifier
            .padding(innerPadding)
            .padding(horizontal = dimensionResource(id = R.dimen.twenty_dp))
            .fillMaxSize(),
        state = listState,
    ){
        items(
            count = orders.itemCount,
            key = orders.itemKey { order: UserOrder ->
                order.orderId
            }
        ){ idx ->
            orders[idx]?.let{
                UserOrderComponent(
                    order = it,
                    orderClicked = orderClicked
                )
                if(idx != orders.itemCount-1){
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
        orders = fl.collectAsLazyPagingItems(),
        ){

    }
}