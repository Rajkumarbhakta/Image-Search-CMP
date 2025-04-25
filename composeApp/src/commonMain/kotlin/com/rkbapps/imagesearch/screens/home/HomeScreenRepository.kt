package com.rkbapps.imagesearch.screens.home

import androidx.paging.Pager
import app.cash.paging.PagingConfig
import com.rkbapps.imagesearch.model.Photos
import com.rkbapps.imagesearch.network.ApiService
import com.rkbapps.imagesearch.network.ApiService.Companion.PER_PAGE_ITEMS
import com.rkbapps.imagesearch.network.NetworkResponse
import com.rkbapps.imagesearch.paging.ImagePagingSource
import com.rkbapps.imagesearch.util.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeScreenRepository(
    private val apiService: ApiService
) {
    private val _images = MutableStateFlow(UiState<List<Photos>>())
    val images = _images.asStateFlow()

    suspend fun loadImages() {
        _images.value = UiState(isLoading = true)
        when (val response = apiService.getImages()) {
            is NetworkResponse.Error.HttpError -> {
                _images.value = UiState(error = response.error.message)
            }

            NetworkResponse.Error.NetworkError -> {
                _images.value = UiState(error = "Network Error")
            }

            NetworkResponse.Error.SerializationError -> {
                _images.value = UiState(error = "Something went wrong")
            }

            is NetworkResponse.Success -> {
                _images.value = UiState(data = response.value.photos)
            }
        }
    }

    fun paginatedImages() = Pager(
        config = PagingConfig(
            pageSize = PER_PAGE_ITEMS,
            enablePlaceholders = false,
            initialLoadSize = PER_PAGE_ITEMS * 2
        ),
        pagingSourceFactory = { ImagePagingSource(apiService) }
    ).flow


}