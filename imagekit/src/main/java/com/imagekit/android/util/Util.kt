package com.imagekit.android.util

import android.graphics.Bitmap
import java.io.ByteArrayOutputStream

object Util{
    // convert from bitmap to byte array
    fun getBytesFromBitmap(bitmap: Bitmap): ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream)
        return stream.toByteArray()
    }
}