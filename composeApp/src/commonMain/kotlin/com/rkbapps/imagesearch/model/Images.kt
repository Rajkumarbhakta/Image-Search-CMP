package com.rkbapps.imagesearch.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class Images(

    @SerialName("page") val page: Int? = null,
    @SerialName("per_page") val perPage: Int? = null,
    @SerialName("photos") val photos: ArrayList<Photos> = arrayListOf(),
    @SerialName("total_results") val totalResults: Int? = null,
    @SerialName("next_page") val nextPage: String? = null

)