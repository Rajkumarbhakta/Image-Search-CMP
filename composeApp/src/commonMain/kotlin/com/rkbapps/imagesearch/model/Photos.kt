package com.rkbapps.imagesearch.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class Photos(

    @SerialName("id") val id: Int? = null,
    @SerialName("width") val width: Int? = null,
    @SerialName("height") val height: Int? = null,
    @SerialName("url") val url: String? = null,
    @SerialName("photographer") val photographer: String? = null,
    @SerialName("photographer_url") val photographerUrl: String? = null,
    @SerialName("photographer_id") val photographerId: Long? = null,
    @SerialName("avg_color") val avgColor: String? = null,
    @SerialName("src") val src: Src? = Src(),
    @SerialName("liked") val liked: Boolean? = null,
    @SerialName("alt") val alt: String? = null

)