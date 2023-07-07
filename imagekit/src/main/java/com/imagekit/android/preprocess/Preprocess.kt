package com.imagekit.android.preprocess

import androidx.annotation.WorkerThread

interface Preprocess<T> {
    @WorkerThread
    fun process(source: T): T
}