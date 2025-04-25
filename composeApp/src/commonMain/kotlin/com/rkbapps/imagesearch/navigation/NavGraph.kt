package com.rkbapps.imagesearch.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder

import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.rkbapps.imagesearch.screens.home.HomeScreen
import com.rkbapps.imagesearch.screens.image_preview.ImagePreviewScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Routes.Home) {
        mainGraph(navController)
    }
}


fun NavGraphBuilder.mainGraph(navController: NavHostController) {
    composable<Routes.Home> {
        HomeScreen(navController)
    }
    composable<Routes.ImagePreview> {
        val url = it.toRoute<Routes.ImagePreview>().url
        ImagePreviewScreen(navController = navController, uri = url)
    }
}