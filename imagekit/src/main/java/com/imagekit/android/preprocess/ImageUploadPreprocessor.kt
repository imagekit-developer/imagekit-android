package com.imagekit.android.preprocess

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Point
import androidx.annotation.WorkerThread
import com.imagekit.android.util.BitmapUtil
import java.io.File
import java.io.IOException

class ImageUploadPreprocessor <I> private constructor(
    private val limit: ImageDimensionsLimiter,
    private val cropPoints: ImageCrop?,
    private val rotation: ImageRotation,
    private val format: Bitmap.CompressFormat
) : UploadPreprocessor<I>() {

    @WorkerThread
    @Throws(IOException::class)
    override fun outputFile(input: I, fileName: String, context: Context): File {
        var bitmap = if (input is File) BitmapFactory.decodeFile(input.path) else input as Bitmap
        listOf(limit, cropPoints, rotation).forEach {
            it?.let { bitmap = it.process(bitmap) }
        }
        return BitmapUtil.bitmapToFile(context, fileName, bitmap, format)
    }

    class Builder {
        private var maxLimits: Pair<Int, Int> = Int.MAX_VALUE to Int.MAX_VALUE
        private var cropPoints: Pair<Point, Point>? = null
        private var outputFormat: Bitmap.CompressFormat = Bitmap.CompressFormat.PNG
        private var rotationAngle: Float = 0f

        fun limit(width: Int, height: Int): Builder = apply { maxLimits = width to height }

        fun crop(p1: Point, p2: Point): Builder = apply { cropPoints = p1 to p2 }

        fun rotate(degrees: Float): Builder = apply { rotationAngle = degrees }

        fun format(format: Bitmap.CompressFormat): Builder = apply { outputFormat = format }

        fun <I> build(): ImageUploadPreprocessor<I> = ImageUploadPreprocessor(
            limit = ImageDimensionsLimiter(maxLimits.first, maxLimits.second),
            cropPoints = cropPoints?.let { ImageCrop(it.first, it.second) },
            rotation = ImageRotation(rotationAngle),
            format = outputFormat
        )
    }
}