package com.imagekit.android.util

import android.util.Log

object LogUtil {
    private const val TAG = "ImageKitError"

    fun logError(error: String) {
        Log.e(TAG, error)
    }
}