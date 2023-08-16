package com.imagekit.android.preprocess

import android.graphics.Bitmap

internal class ImageDimensionsLimiter(
    private val maxWidth: Int,
    private val maxHeight: Int
) : Preprocess<Bitmap> {
    override fun process(source: Bitmap): Bitmap =
        if (source.width <= maxWidth && source.height <= maxHeight) source
        else Bitmap.createScaledBitmap(
                source,
                source.width.coerceAtMost(maxWidth),
                source.height.coerceAtMost(maxHeight),
                true
            )
}