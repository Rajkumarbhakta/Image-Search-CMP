package com.rkbapps.imagesearch.network

import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import kotlinx.io.IOException
import kotlinx.serialization.SerializationException

sealed class NetworkResponse<out T> {
    data class Success<T>(val value: T) : NetworkResponse<T>()
    sealed class Error : NetworkResponse<Nothing>() {
        data class HttpError(val errorCode: Int, val error: Exception) : Error()
        data object NetworkError : Error()
        data object SerializationError : Error()
    }
}

suspend fun <T> safeRequest(block: suspend () -> T): NetworkResponse<T> {
    return try {
        NetworkResponse.Success(block.invoke())
    } catch (e: ClientRequestException) {
        NetworkResponse.Error.HttpError(e.response.status.value, e)
    } catch (e: ServerResponseException) {
        NetworkResponse.Error.HttpError(e.response.status.value, e)
    } catch (e: IOException) {
        NetworkResponse.Error.NetworkError
    } catch (e: SerializationException) {
        NetworkResponse.Error.SerializationError
    }
}




