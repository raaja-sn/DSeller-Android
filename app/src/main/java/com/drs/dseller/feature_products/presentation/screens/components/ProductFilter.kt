package com.drs.dseller.feature_products.presentation.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.drs.dseller.R
import com.drs.dseller.core.ui_elements.buttons.RoundCorneredButton
import com.drs.dseller.feature_products.presentation.ProductScreenFilter
import com.drs.dseller.feature_products.presentation.ProductSortOrder
import com.drs.dseller.ui.theme.AppTypography
import com.drs.dseller.ui.theme.Black80
import com.drs.dseller.ui.theme.Grey20


@Composable
fun ProductFilter(
    currentFilter:ProductScreenFilter,
    sortSelectionCallback:(ProductScreenFilter) -> Unit
){

    var filter = remember{
        mutableStateOf(currentFilter)
    }
    
    val sortSelected = remember{
        { selection:ProductScreenFilter ->
            filter.value = selection
        }
    }

    val applyFilter = remember {
        {
            sortSelectionCallback(filter.value)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(
                RoundedCornerShape(
                    topEnd = dimensionResource(id = R.dimen.twenty_five_dp),
                    topStart = dimensionResource(id = R.dimen.twenty_five_dp)
                )
            )
            .background(Grey20)
            .padding(horizontal = dimensionResource(id = R.dimen.fifteen_dp)),
    ) {
        Text(
            modifier = Modifier
                .padding(vertical = dimensionResource(id = R.dimen.twenty_dp)),
            text = stringResource(id = R.string.product_filter_title),
            style = AppTypography.headlineMedium,
            color = Black80
        )

        SortType(
            selected = currentFilter is ProductScreenFilter.ByName && currentFilter.sortOrder is ProductSortOrder.ASCENDING,
            sortSelectionCallback,
            SortType.ByNameAscending
        )
        SortType(
            selected = currentFilter is ProductScreenFilter.ByName && currentFilter.sortOrder is ProductSortOrder.DESCENDING,
            sortSelectionCallback,
            SortType.ByNameDescending
        )
        SortType(
            selected = currentFilter is ProductScreenFilter.ByPrice && currentFilter.sortOrder is ProductSortOrder.ASCENDING,
            sortSelectionCallback,
            SortType.ByPriceAscending
        )
        SortType(
            selected = currentFilter is ProductScreenFilter.ByPrice && currentFilter.sortOrder is ProductSortOrder.DESCENDING,
            sortSelected,
            SortType.ByPriceDescending
        )

        RoundCorneredButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = dimensionResource(id = R.dimen.twenty_five_dp)),
            buttonText = stringResource(id = R.string.product_filter_button),
            clickCallback = applyFilter)

    }

}

@Composable
private fun SortType(
    selected:Boolean,
    sortSelected:(ProductScreenFilter) -> Unit,
    sortType: SortType
){

    val radioModifier = remember{
        Modifier
            .padding(vertical = 10.dp)
            .clickable {
                if (selected) return@clickable
                when (sortType) {
                    SortType.ByNameAscending -> sortSelected(
                        ProductScreenFilter.ByName(
                            ProductSortOrder.ASCENDING
                        )
                    )

                    SortType.ByNameDescending -> sortSelected(
                        ProductScreenFilter.ByName(
                            ProductSortOrder.DESCENDING
                        )
                    )

                    SortType.ByPriceAscending -> sortSelected(
                        ProductScreenFilter.ByPrice(
                            ProductSortOrder.ASCENDING
                        )
                    )

                    SortType.ByPriceDescending -> sortSelected(
                        ProductScreenFilter.ByPrice(
                            ProductSortOrder.DESCENDING
                        )
                    )
                }
            }
    }
    Row (
        modifier = radioModifier,
        verticalAlignment = Alignment.CenterVertically
    ){
        if(selected){
            Icon(
                modifier = Modifier
                    .padding(end = dimensionResource(id = R.dimen.ten_dp))
                    .size(dimensionResource(id = R.dimen.fourty_dp)),
                painter = painterResource(id = R.drawable.ic_radio_selected),
                contentDescription = stringResource(id = R.string.description_product_list_sort_name_asc),
                tint = Color.Unspecified
                )
        }else{
            Icon(
                modifier = Modifier
                    .padding(end = dimensionResource(id = R.dimen.ten_dp))
                    .size(dimensionResource(id = R.dimen.fourty_dp)),
                painter =  painterResource(id = R.drawable.ic_radio),
                contentDescription = stringResource(id = R.string.description_product_list_sort_name_asc),
                tint = Color.Unspecified
            )
        }

        Text(
            modifier = Modifier
                .padding(horizontal = 10.dp),
            text = getSortTitle(sortType = sortType),
            style = AppTypography.bodyMedium,
            color = Black80
        )
    }
}

@Composable
private fun getSortTitle(sortType:SortType):String{
    return when(sortType){
        SortType.ByNameAscending -> stringResource(id = R.string.product_filter_name_asc)
        SortType.ByNameDescending -> stringResource(id = R.string.product_filter_name_desc)
        SortType.ByPriceAscending -> stringResource(id = R.string.product_filter_price_ascending)
        SortType.ByPriceDescending -> stringResource(id = R.string.product_filter_price_descending)
    }
}

private sealed class SortType{
    data object ByNameAscending:SortType()
    data object ByNameDescending:SortType()

    data object ByPriceAscending:SortType()

    data object ByPriceDescending:SortType()

}

@Preview
@Composable
private fun ProductFilterPreview(){
    ProductFilter(currentFilter = ProductScreenFilter.ByName(ProductSortOrder.DESCENDING)){}
}

