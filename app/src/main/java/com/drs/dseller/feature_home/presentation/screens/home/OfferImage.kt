package com.drs.dseller.feature_home.presentation.screens.home

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun OfferImage(
    imageModifier: Modifier,
    image:String,
    placeHolderId:Int,
    index:Int,
){

    AsyncImage(
        modifier = imageModifier
            .clip(RoundedCornerShape(15.dp)),
        model = ImageRequest.Builder(LocalContext.current)
            .crossfade(300)
            .data(image)
            .build(),
        contentDescription = "Offers",
        placeholder = painterResource(id = placeHolderId),
        error = painterResource(id = placeHolderId),
        contentScale = ContentScale.Crop,
        )

}
