package com.rkbapps.imagesearch.navigation

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

sealed class Routes {
    @Serializable
    @SerialName("home")
    data object Home : Routes()

    @Serializable
    @SerialName("image-preview")
    data class ImagePreview(val url: String) : Routes()

}