package com.drs.dseller.feature_account.presentation.screens.invoice

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.drs.dseller.R
import com.drs.dseller.core.utils.AppUtils
import com.drs.dseller.feature_account.domain.model.FullInvoice
import com.drs.dseller.feature_account.domain.model.InvoiceProduct
import com.drs.dseller.feature_account.presentation.states.UserInvoiceState
import com.drs.dseller.ui.theme.AppTypography
import com.drs.dseller.ui.theme.Black80
import com.drs.dseller.ui.theme.Grey60
import com.drs.dseller.ui.theme.Grey80
import com.drs.dseller.ui.theme.White80

@Composable
fun InvoiceBody(
    innerPadding: PaddingValues,
    state:UserInvoiceState
){

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .padding(innerPadding)
            .verticalScroll(state = scrollState)
            .padding(horizontal = dimensionResource(id = R.dimen.twenty_dp)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        InvoiceInfo(
            invoiceNo = state.invoice?.invoiceNo?:"",
            purchaseDate = state.invoice?.purchaseDate
        )
        if(state.invoice != null){
            ProductList(products = state.invoice.products)
            Total(
                shippingCost = state.invoice.shippingCost,
                billValue = state.invoice.billValue
            )
        }

    }

}

@Composable
private fun InvoiceInfo(
    invoiceNo:String,
    purchaseDate:Long?
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .padding(vertical = dimensionResource(id = R.dimen.twenty_dp))
                .size(dimensionResource(id = R.dimen.thirty_dp)),
            painter = painterResource(id = R.drawable.dseller_logo_transparant) ,
            contentDescription = stringResource(id = R.string.description_logo)
        )
        Text(
            modifier = Modifier
                .padding(
                    top = dimensionResource(id = R.dimen.ten_dp),
                    bottom = dimensionResource(id = R.dimen.thirty_dp)
                ),
            text = "Invoice",
            style = AppTypography.headlineMedium,
            color = Black80,
            textAlign = TextAlign.Center,
            textDecoration = TextDecoration.Underline
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = dimensionResource(id = R.dimen.five_dp)),
            text = "Order Id: $invoiceNo",
            style = AppTypography.headlineSmall,
            color = Black80,
            textAlign = TextAlign.Start
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = dimensionResource(id = R.dimen.fifteen_dp)),
            text = "Purchase Date: ${AppUtils.getFormattedDate(purchaseDate)}",
            style = AppTypography.bodySmall,
            color = Grey60,
            textAlign = TextAlign.Start
        )
        Divider(
            thickness = 1.dp,
            color = White80
        )
    }
}

@Composable
private fun ProductList(
    products:List<InvoiceProduct>
){
    products.forEach {product ->
        PurchasedProduct(
            invoiceProduct = product
        )
    }
}

@Composable
private fun PurchasedProduct(
    invoiceProduct:InvoiceProduct
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = dimensionResource(id = R.dimen.twenty_dp)),
        verticalAlignment = Alignment.Bottom
    ) {

        Column(
            modifier = Modifier
                .weight(1f)
        ){

            Text(
                modifier = Modifier
                    .padding(bottom = dimensionResource(id = R.dimen.five_dp)),
                text = invoiceProduct.productName,
                style = AppTypography.bodyMedium,
                color = Black80,
                textAlign = TextAlign.Start,
                maxLines = 2
            )
            Text(
                modifier = Modifier
                    .padding(bottom = dimensionResource(id = R.dimen.five_dp)),
                text = "Price: ${AppUtils.getCurrencySymbol("en","in")} ${invoiceProduct.price}",
                style = AppTypography.bodySmall,
                color = Grey80,
                textAlign = TextAlign.Start,
                maxLines = 1
            )
            Text(
                modifier = Modifier
                    .padding(bottom = dimensionResource(id = R.dimen.five_dp)),
                text = "Quantity: ${invoiceProduct.quantity}",
                style = AppTypography.bodySmall,
                color = Grey80,
                textAlign = TextAlign.Start,
                maxLines = 1
            )
        }
        Text(
            modifier = Modifier
                .padding(bottom = dimensionResource(id = R.dimen.five_dp)),
            text = "${AppUtils.getCurrencySymbol("en","in")} " +
                    "${AppUtils.getTwoDecimalSpacedPrice(invoiceProduct.quantity * invoiceProduct.price)}",
            style = AppTypography.bodyMedium,
            color = Black80,
            textAlign = TextAlign.Start
        )
    }
    Divider(
        thickness = 1.dp,
        color = White80
    )
}

@Composable
private fun Total(
    shippingCost:Double,
    billValue:Double
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = dimensionResource(id = R.dimen.twenty_five_dp),
                    bottom = dimensionResource(id = R.dimen.ten_dp)
                ),
            text = "Shipping Cost: ${AppUtils.getTwoDecimalSpacedPrice(shippingCost)}",
            style = AppTypography.bodySmall,
            color = Grey60,
            textAlign = TextAlign.End
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = dimensionResource(id = R.dimen.twenty_five_dp)),
            text = "Total: ${AppUtils.getTwoDecimalSpacedPrice(billValue)}",
            style = AppTypography.bodyMedium,
            color = Black80,
            textAlign = TextAlign.End
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun InvoicePreview(){
    InvoiceBody(innerPadding = PaddingValues(0.dp),
        state = UserInvoiceState(
            invoice = FullInvoice(
                invoiceNo = "DSE0000056",
                billValue = 345345.0,
                shippingCost = 3434.0,
                products = listOf(
                    InvoiceProduct("Some big product name which will move to the next line of the item 1",20,500.0),
                    InvoiceProduct("Some big product name which will move to the next line of the item 2",20,500.0),
                    InvoiceProduct("Some big product name which will move to the next line of the item 3",20,500.0),
                    InvoiceProduct("Some big product name which will move to the next line of the item 4",20,500.0),
                    InvoiceProduct("Some big product name which will move to the next line of the item 5",20,500.0),
                    InvoiceProduct("Some big product name which will move to the next line of the item 6",20,500.0)

                    )
            )
        )
    )
}