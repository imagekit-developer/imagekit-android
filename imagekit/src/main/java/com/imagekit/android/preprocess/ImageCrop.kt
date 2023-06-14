package com.imagekit.android.preprocess

import android.graphics.Bitmap
import android.graphics.Point

internal class ImageCrop(
    private val topLeft: Point,
    private val bottomRight: Point
) : Preprocess<Bitmap> {
    override fun process(source: Bitmap): Bitmap =
        Bitmap.createBitmap(
            source,
            topLeft.x,
            topLeft.y,
            bottomRight.x - topLeft.x,
            bottomRight.y - topLeft.y
        )
}