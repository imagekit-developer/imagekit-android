package com.imagekit.android.util

import android.util.Log

object LogUtil {
    private const val TAG = "ImageKitError"

    fun logError(error: Throwable) {
        Log.e(TAG, error.toString())
    }

    fun logError(error: String) {
        Log.e(TAG, error)
    }
}