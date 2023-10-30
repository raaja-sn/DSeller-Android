@file:OptIn(ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class)

package com.drs.dseller.feature_home.presentation.screens.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.drs.dseller.R
import com.drs.dseller.core.ui_elements.text_fields.SearchField
import com.drs.dseller.feature_home.domain.model.Category
import com.drs.dseller.feature_home.presentation.states.HomeState
import com.drs.dseller.ui.theme.AppTypography
import com.drs.dseller.ui.theme.Black80

@Composable
fun HomeBody(
    state:HomeState,
    searchChanged:(String) -> Unit,
    categoryClicked:(Int) -> Unit,
){
    Column(
        modifier = Modifier
            .padding(horizontal = dimensionResource(id = R.dimen.twenty_dp))
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            modifier = Modifier
                .padding(
                    top = dimensionResource(id = R.dimen.ten_dp),
                    bottom = dimensionResource(id = R.dimen.thirty_five_dp)
                )
                .size(dimensionResource(id = R.dimen.twenty_five_dp)),
            painter = painterResource(id = R.drawable.dseller_logo_orange),
            contentDescription = stringResource(id = R.string.description_logo))

        SearchField(
            modifier = Modifier
                .fillMaxWidth(),
            text = state.searchQuery,
            placeHolderText = stringResource(id = R.string.search_placeholder_product),
            textChangeCallback = searchChanged
        )

        val offerCardWidth = LocalConfiguration.current.screenWidthDp - 40
        val offerCardHeight = (offerCardWidth * 0.309).toInt()

        val pagerState = rememberPagerState {
            state.offers.size
        }

        val cardModifier = Modifier
            .padding(vertical = dimensionResource(id = R.dimen.fifteen_dp))
            .wrapContentSize()
            .size(
                dimensionResource(id = R.dimen.category_card_width),
                dimensionResource(id = R.dimen.category_card_height)
            )

        val imageModifier = Modifier
            .fillMaxWidth()
            .size(
                dimensionResource(id = R.dimen.category_img_size),
                dimensionResource(id = R.dimen.category_img_size)
            )
        val textModifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 15.dp)

        LazyVerticalGrid(
            modifier = Modifier,
            columns = GridCells.Fixed(2)
        ){
            item(
                span = {
                    GridItemSpan(2)
                }
            ) {
                HorizontalPager(
                    modifier = Modifier
                        .fillMaxWidth(),
                    beyondBoundsPageCount = 3,
                    state = pagerState
                ) { page ->
                    OfferImage(
                        imageModifier = Modifier
                            .padding(top = 20.dp)
                            .fillMaxWidth()
                            .size(offerCardWidth.dp, offerCardHeight.dp),
                        image = state.offers[page].image,
                        placeHolderId = R.drawable.place_holder_banner,
                        index = 0
                    )
                }
            }

            item(
                span = {
                    GridItemSpan(2)
                }
            ){
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = dimensionResource(id = R.dimen.twenty_dp)),
                    text = stringResource(id = R.string.home_categories_title),
                    style = AppTypography.headlineMedium,
                    color = Black80,
                    textAlign = TextAlign.Start
                )
            }

            itemsIndexed(
                state.categories,
                key = { index:Int,item: Category ->
                        item.name
                }
            ){ index:Int,item:Category ->
                CategoryCard(
                    cardModifier = cardModifier,
                    imageModifier = imageModifier,
                    textModifier = textModifier,
                    category = item.name,
                    categoryImage = item.image,
                    placeHolderId = R.drawable.place_holder_medium,
                    index = index,
                    containerColor = CategoryCardColors[index],
                    clickCallback = categoryClicked
                )
            }

        }



    }
}

val CategoryCardColors = listOf(
    Color(0xFF53B175),
    Color(0xFFF8A44C),
    Color(0xFFF7A593),
    Color(0xFFD3B0E0),
    Color(0xFFFDE598),
    Color(0xFFB7DFF5),
    Color(0xFF836AF6),
    Color(0xFFD73B77)
)

@Preview
@Composable
private fun HomePreview(){
    HomeBody(state = HomeState(

    )
        , searchChanged = {}, categoryClicked ={} )
}