package com.rkbapps.imagesearch.di.module

import com.rkbapps.imagesearch.network.ApiClient
import com.rkbapps.imagesearch.network.ApiService
import com.rkbapps.imagesearch.screens.home.HomeScreenRepository
import com.rkbapps.imagesearch.screens.home.HomeScreenViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

expect val platformModule: Module

val provideRepositories = module {
    factory { HomeScreenRepository(get()) }
}

val networkModule = module {
    single { ApiClient.client }
    single { ApiService(get()) }
}

val provideViewModels = module {
    viewModelOf(::HomeScreenViewModel)
}