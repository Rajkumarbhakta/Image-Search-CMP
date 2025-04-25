package com.rkbapps.imagesearch.network

import com.rkbapps.imagesearch.model.Images
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class ApiService(
    private val client: HttpClient
) {
    suspend fun getImages(perPage: Int = PER_PAGE_ITEMS, page: Int = 1): NetworkResponse<Images> {
        return safeRequest<Images> {
            val response = client.get(urlString = "curated") {
                parameter("per_page", perPage)
                parameter("page", page)
            }.body<Images>()
            response
        }
    }

    companion object {
        const val PER_PAGE_ITEMS = 30
    }

}