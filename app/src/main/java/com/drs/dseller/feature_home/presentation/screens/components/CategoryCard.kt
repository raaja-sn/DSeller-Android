@file:OptIn(ExperimentalMaterial3Api::class)

package com.drs.dseller.feature_home.presentation.screens.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.drs.dseller.R
import com.drs.dseller.ui.theme.AppTypography
import com.drs.dseller.ui.theme.Black80
import com.drs.dseller.ui.theme.Green40

@Composable
fun CategoryCard(
    cardModifier:Modifier,
    imageModifier:Modifier,
    textModifier:Modifier,
    category:String,
    categoryImage:String,
    placeHolderId:Int,
    index:Int,
    containerColor: Color,
    clickCallback:(Int)->Unit
){
    OutlinedCard(
        modifier = cardModifier,
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = containerColor.copy(alpha = 0.1f)
        ),
        border = BorderStroke(
            1.dp,containerColor
        ),
        onClick = {
            clickCallback(index)
        }
    ) {

        AsyncImage(
            modifier = imageModifier,
            model = ImageRequest.Builder(LocalContext.current)
                .data(categoryImage)
                .crossfade(300)
                .build(),
            contentDescription = category,
            alignment = Alignment.TopCenter,
            placeholder = painterResource(id = placeHolderId),
            error = painterResource(id = placeHolderId),
            contentScale = ContentScale.Crop
        )
        Text(
            modifier = textModifier,
            text = category,
            style = AppTypography.titleMedium,
            color = Black80,
            textAlign = TextAlign.Center,
            maxLines = 2
        )

    }

}

@Preview
@Composable
private fun CardPreview(){
    CategoryCard(
        cardModifier = Modifier
            .size(200.dp, 240.dp)
            .wrapContentHeight(),
        imageModifier = Modifier
            .fillMaxWidth()
            .size(150.dp, 150.dp),
        textModifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 20.dp),
        category = "Fresh Fruits & Vegetables",
        categoryImage = "https://dseller-test.s3.ap-south-1.amazonaws.com/categories/Electronics.jpg",
        placeHolderId = R.drawable.place_holder_medium,
        index = 5,
        containerColor = Green40,
    ){

    }
}