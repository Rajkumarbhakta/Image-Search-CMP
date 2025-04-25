package com.rkbapps.imagesearch.util

data class UiState<T>(
    val data: T? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val isError: Boolean = false,
)
