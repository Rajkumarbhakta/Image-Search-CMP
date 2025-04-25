package com.rkbapps.imagesearch

import android.app.Application
import com.rkbapps.imagesearch.di.initKoin
import org.koin.android.ext.koin.androidContext

class BaseApplication :Application(){
    override fun onCreate() {
        super.onCreate()
        initKoin{
            androidContext(this@BaseApplication)
        }
    }
}