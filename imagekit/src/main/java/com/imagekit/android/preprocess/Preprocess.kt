package com.imagekit.android.preprocess

interface Preprocess<T> {
    fun process(source: T): T
}