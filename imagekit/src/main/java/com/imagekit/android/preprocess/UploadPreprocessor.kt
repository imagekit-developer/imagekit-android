package com.imagekit.android.preprocess

import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import android.graphics.Point
import java.io.File

sealed class UploadPreprocessor<T> {

    private val customProcesses: MutableList<Preprocess<T>> = mutableListOf()

    override fun equals(other: Any?): Boolean {
        return this === other
    }

    override fun hashCode(): Int {
        return System.identityHashCode(this)
    }

    fun executeProcess(source: T): T {
        var output = source
        customProcesses.forEach {
            output = it.process(output)
        }
        return output
    }
}

class ImageUploadPreprocessor private constructor(
    val limit: Pair<Int, Int>,
    val cropPoints: Pair<Point, Point>,
    val format: CompressFormat,
    val rotation: Float
) : UploadPreprocessor<Bitmap>() {

    class Builder {
        private var maxLimits: Pair<Int, Int> = Int.MAX_VALUE to Int.MAX_VALUE
        private var cropPoints: Pair<Point, Point> = Point(0, 0) to Point(Int.MAX_VALUE, Int.MAX_VALUE)
        private var outputFormat: CompressFormat = CompressFormat.JPEG
        private var rotationAngle: Float = 0f

        fun limit(width: Int, height: Int): Builder {
            maxLimits = width to height
            return this
        }

        fun crop(p1: Point, p2: Point): Builder {
            cropPoints = p1 to p2
            return this
        }

        fun format(format: CompressFormat): Builder {
            outputFormat = format
            return this
        }

        fun rotate(degrees: Float): Builder {
            rotationAngle = degrees
            return this
        }

        fun build(): ImageUploadPreprocessor = ImageUploadPreprocessor(
            limit = maxLimits,
            cropPoints = cropPoints,
            format = outputFormat,
            rotation = rotationAngle
        )
    }
}

class VideoUploadPreprocessor private constructor(
    val limit: Pair<Int, Int>,
    val cropPoints: Pair<Point, Point>,
    val format: CompressFormat,
    val rotation: Float
) : UploadPreprocessor<File>() {

    class Builder {
        private var maxLimits: Pair<Int, Int> = Int.MAX_VALUE to Int.MAX_VALUE
        private var cropPoints: Pair<Point, Point> = Point(0, 0) to Point(Int.MAX_VALUE, Int.MAX_VALUE)
        private var outputFormat: CompressFormat = CompressFormat.JPEG
        private var rotationAngle: Float = 0f

        fun limit(width: Int, height: Int): Builder {
            maxLimits = width to height
            return this
        }

        fun crop(p1: Point, p2: Point): Builder {
            cropPoints = p1 to p2
            return this
        }

        fun format(format: CompressFormat): Builder {
            outputFormat = format
            return this
        }

        fun rotate(degrees: Float): Builder {
            rotationAngle = degrees
            return this
        }

        fun build(): VideoUploadPreprocessor = VideoUploadPreprocessor(
            limit = maxLimits,
            cropPoints = cropPoints,
            format = outputFormat,
            rotation = rotationAngle
        )
    }
}