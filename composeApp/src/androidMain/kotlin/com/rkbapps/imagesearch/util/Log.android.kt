package com.rkbapps.imagesearch.util

import android.util.Log

actual fun logD(tag: String, message: Any) {
    Log.d(tag, message.toString())
}

actual fun logE(tag: String, message: Any, e: Throwable?) {
    Log.e(tag, message.toString(), e)
}

actual fun logI(tag: String, message: Any) {
    Log.i(tag, message.toString())
}