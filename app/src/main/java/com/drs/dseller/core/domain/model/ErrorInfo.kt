package com.drs.dseller.core.domain.model

data class ErrorInfo(
    val title:String,
    val message:String,
    val positiveText:String?,
    val negativeText:String?,
    val positiveCallback:(()->Unit)?,
    val negativeCallback:(()->Unit)?
)
