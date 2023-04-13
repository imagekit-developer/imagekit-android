package com.imagekit.android.download
interface Target<T> {
    fun onResourceReady(resource: T, url: String)
    fun onError(exception: Exception?)
}