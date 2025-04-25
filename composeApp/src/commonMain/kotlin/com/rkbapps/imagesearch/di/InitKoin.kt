package com.rkbapps.imagesearch.di

import com.rkbapps.imagesearch.di.module.networkModule
import com.rkbapps.imagesearch.di.module.platformModule
import com.rkbapps.imagesearch.di.module.provideRepositories
import com.rkbapps.imagesearch.di.module.provideViewModels
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(provideRepositories, provideViewModels, platformModule, networkModule)
    }
}