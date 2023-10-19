package com.drs.dseller.core.ui_elements

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.ui.Modifier


fun Modifier.clickableWithoutRipple(
    interactionSource: MutableInteractionSource,
    onClick:() -> Unit,
):Modifier{
    return this.clickable(
        interactionSource = interactionSource,
        indication = null,
        onClick = onClick
    )
}