package com.imagekit.android.preprocess

import android.graphics.Bitmap
import android.graphics.Matrix

class ImageRotation(private val angle: Float) : Preprocess<Bitmap> {
    override fun process(source: Bitmap): Bitmap = Matrix().let {
        it.setRotate(angle)
        Bitmap.createBitmap(source, 0, 0, source.width, source.height, it, true)
    }
}