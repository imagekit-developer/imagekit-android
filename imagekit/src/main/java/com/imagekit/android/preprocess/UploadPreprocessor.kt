package com.imagekit.android.preprocess

import android.content.Context
import java.io.File

sealed class UploadPreprocessor<T> {

    override fun equals(other: Any?): Boolean = this === other

    override fun hashCode(): Int = System.identityHashCode(this)

    internal abstract fun outputFile(input: T, fileName: String, context: Context): File
}