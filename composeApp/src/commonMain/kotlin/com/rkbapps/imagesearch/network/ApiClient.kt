package com.rkbapps.imagesearch.network

import com.rkbapps.imagesearch.network.ApiConstants.API_KEY
import com.rkbapps.imagesearch.network.ApiConstants.BASE_URL
import com.rkbapps.imagesearch.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class ApiClient {
    companion object {

        val client = HttpClient {
            //for default request
            defaultRequest {
                url(BASE_URL)
                header("Authorization", API_KEY)
            }
            //for json serialization
            install(ContentNegotiation) {
                json(
                    json = Json {
                        ignoreUnknownKeys = true
                        isLenient = true
                        prettyPrint = true
                    },
                    contentType = ContentType.parse("application/json")
                )
            }

            expectSuccess = true
            //for timeout
            install(HttpTimeout) {
                val timeout = 30000L
                connectTimeoutMillis = timeout
                requestTimeoutMillis = timeout
                socketTimeoutMillis = timeout
            }

            //for logging
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.ALL
                logger = object : Logger {
                    override fun log(message: String) {
                        Log.d("API Client", message)
                    }
                }
            }
        }
    }

}