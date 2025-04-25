package com.rkbapps.imagesearch.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import app.cash.paging.LoadStateError
import app.cash.paging.LoadStateLoading
import app.cash.paging.LoadStateNotLoading
import app.cash.paging.compose.collectAsLazyPagingItems
import coil3.compose.SubcomposeAsyncImage
import com.rkbapps.imagesearch.navigation.Routes
import com.rkbapps.imagesearch.util.getWindowSize
import org.koin.compose.viewmodel.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: HomeScreenViewModel = koinViewModel()
) {
    val photos = viewModel.images.collectAsStateWithLifecycle()
    val listState = rememberLazyStaggeredGridState()
    val paginatedImages = viewModel.paginatedImages.collectAsLazyPagingItems()


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Image Search")
                }
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            /*if (photos.value.isLoading) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            if (photos.value.isError) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Error: ${photos.value.error}", color = Color.Red)
                }
            }
            if (photos.value.data != null && !photos.value.isError && !photos.value.isLoading) {
                val windowSizeClass = getWindowSize()
                val column = when (windowSizeClass.widthSizeClass) {
                    WindowWidthSizeClass.Medium -> StaggeredGridCells.Adaptive(150.dp)
                    WindowWidthSizeClass.Compact -> StaggeredGridCells.Fixed(3)
                    else -> StaggeredGridCells.Fixed(6)
                }

                LazyVerticalStaggeredGrid(
                    state = listState,
                    columns = column,
                    verticalItemSpacing = 4.dp,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    items(items = photos.value.data!!) {
                        ImageItem(it.src?.large!!) {
                            navController.navigate(route = Routes.ImagePreview(url = it.src.original!!))
                        }
                    }
                }
            }*/

            val windowSizeClass = getWindowSize()
            val column = when (windowSizeClass.widthSizeClass) {
                WindowWidthSizeClass.Medium -> StaggeredGridCells.Adaptive(150.dp)
                WindowWidthSizeClass.Compact -> StaggeredGridCells.Fixed(3)
                else -> StaggeredGridCells.Fixed(6)
            }

            LazyVerticalStaggeredGrid(
                state = listState,
                columns = column,
                verticalItemSpacing = 4.dp,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                items(paginatedImages.itemCount) {
                    paginatedImages[it]?.src?.let { imageLinks ->
                        ImageItem(imageLinks.large!!) {
                            navController.navigate(route = Routes.ImagePreview(url = imageLinks.original!!))
                        }
                    }
                }
                paginatedImages.loadState.apply {
                    when {
                        refresh is LoadStateNotLoading && paginatedImages.itemCount < 1 -> {
                            item {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "No Items",
                                        modifier = Modifier.align(Alignment.Center),
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }

                        refresh is LoadStateLoading -> {
                            item {
                                Box(
                                    modifier = Modifier.fillMaxWidth(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator(
                                        Modifier.align(Alignment.Center),
                                    )
                                }
                            }
                        }

                        append is LoadStateLoading -> {
                            item {
                                CircularProgressIndicator(
                                    modifier = Modifier.fillMaxWidth()
                                        .padding(16.dp)
                                        .wrapContentWidth(Alignment.CenterHorizontally)
                                )
                            }
                        }

                        refresh is LoadStateError -> {
                            item {
                                ErrorView(
                                    message = "Something went wrong",
                                    onClickRetry = { paginatedImages.retry() },
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                        }

                        append is LoadStateError -> {
                            item {
                                ErrorView(
                                    message = "Something went wrong",
                                    onClickRetry = { paginatedImages.retry() },
                                    modifier = Modifier
                                )
                            }
                        }
                    }
                }
            }
        }


    }

}

@Composable
fun ErrorView(message: String, onClickRetry: ()->Unit, modifier: Modifier) {
    Box (modifier=modifier, contentAlignment = Alignment.Center){
        Column (horizontalAlignment = Alignment.CenterHorizontally){
            Text(message, color = Color.Red)
            OutlinedButton(
                onClick = onClickRetry,
                shape = RoundedCornerShape(8.dp)
            ){
                Text("Try Again")
            }
        }
    }
}

@Composable
fun ImageItem(url: String, onCLick: () -> Unit = {}) {
    SubcomposeAsyncImage(
        model = url,
        contentDescription = null,
        modifier = Modifier.clip(RoundedCornerShape(8.dp)).clickable {
            onCLick()
        },
        loading = {
            CircularProgressIndicator(modifier = Modifier.size(50.dp))
        }
    )
}


