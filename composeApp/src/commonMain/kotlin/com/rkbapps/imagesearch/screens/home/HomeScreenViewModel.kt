package com.rkbapps.imagesearch.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeScreenViewModel(
    private val repository: HomeScreenRepository
) : ViewModel() {

    val images = repository.images

    init {
//        loadImages()
    }

    fun loadImages() {
        viewModelScope.launch {
            delay(200)
            repository.loadImages()
        }
    }
    val paginatedImages = repository.paginatedImages().cachedIn(viewModelScope)

}