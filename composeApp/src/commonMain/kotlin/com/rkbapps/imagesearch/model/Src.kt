package com.rkbapps.imagesearch.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class Src(

    @SerialName("original") val original: String? = null,
    @SerialName("large2x") val large2x: String? = null,
    @SerialName("large") val large: String? = null,
    @SerialName("medium") val medium: String? = null,
    @SerialName("small") val small: String? = null,
    @SerialName("portrait") val portrait: String? = null,
    @SerialName("landscape") val landscape: String? = null,
    @SerialName("tiny") val tiny: String? = null

)