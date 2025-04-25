package com.rkbapps.imagesearch.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.rkbapps.imagesearch.model.Photos
import com.rkbapps.imagesearch.network.ApiService
import com.rkbapps.imagesearch.network.NetworkResponse
import kotlinx.io.IOException
import kotlinx.serialization.SerializationException

class ImagePagingSource(
    private val apiService: ApiService
):PagingSource<Int,Photos>() {
    override fun getRefreshKey(state: PagingState<Int, Photos>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photos> {
        val currentPage = params.key ?: 1
        return try {
            when(val response = apiService.getImages(page = currentPage)){
                is NetworkResponse.Error.HttpError -> {
                   LoadResult.Error(response.error)
                }
                NetworkResponse.Error.NetworkError -> {
                    LoadResult.Error(IOException())
                }
                NetworkResponse.Error.SerializationError -> {
                    LoadResult.Error(SerializationException())
                }
                is NetworkResponse.Success->{
                    val endIndex = (response.value.totalResults?:0)/ApiService.PER_PAGE_ITEMS
                    LoadResult.Page(
                        data = response.value.photos,
                        prevKey = if(currentPage == 1) null else -1,
                        nextKey = if (currentPage>endIndex) null else currentPage + 1
                    )
                }
            }

        }catch (e:Exception){
                LoadResult.Error(e)
        }
    }
}