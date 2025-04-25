package com.rkbapps.imagesearch

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.rkbapps.imagesearch.navigation.NavGraph
import com.rkbapps.imagesearch.theme.AppTheme
import org.koin.compose.KoinContext

@Composable
internal fun App(
    navController: NavHostController = rememberNavController(),
) = AppTheme {
    KoinContext {
        NavGraph(navController)
    }
}
