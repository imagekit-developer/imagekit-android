package com.imagekit.android.preprocess

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import android.graphics.BitmapFactory
import android.graphics.Point
import androidx.annotation.WorkerThread
import com.imagekit.android.util.BitmapUtil
import java.io.File
import java.io.IOException

sealed class UploadPreprocessor<T> {

    override fun equals(other: Any?): Boolean {
        return this === other
    }

    override fun hashCode(): Int {
        return System.identityHashCode(this)
    }

    abstract fun outputFile(input: T, fileName: String, context: Context): File
}

class ImageUploadPreprocessor <I> private constructor(
    private val limit: ImageDimensionsLimiter,
    private val cropPoints: ImageCrop,
    private val rotation: ImageRotation,
    private val format: CompressFormat
) : UploadPreprocessor<I>() {

    @WorkerThread
    @Throws(IOException::class)
    override fun outputFile(input: I, fileName: String, context: Context): File {
        var bitmap = if (input is File) BitmapFactory.decodeFile(input.path) else input as Bitmap
        listOf(limit, cropPoints, rotation).forEach {
            bitmap = it.process(bitmap)
        }
        return BitmapUtil.bitmapToFile(context, fileName, bitmap, format)
    }

    class Builder {
        private var maxLimits: Pair<Int, Int> = Int.MAX_VALUE to Int.MAX_VALUE
        private var cropPoints: Pair<Point, Point> = Point(0, 0) to Point(Int.MAX_VALUE, Int.MAX_VALUE)
        private var outputFormat: CompressFormat = CompressFormat.PNG
        private var rotationAngle: Float = 0f

        fun limit(width: Int, height: Int): Builder {
            maxLimits = width to height
            return this
        }

        fun crop(p1: Point, p2: Point): Builder {
            cropPoints = p1 to p2
            return this
        }

        fun rotate(degrees: Float): Builder {
            rotationAngle = degrees
            return this
        }

        fun format(format: CompressFormat): Builder {
            outputFormat = format
            return this
        }

        fun <I> build(): ImageUploadPreprocessor<I> = ImageUploadPreprocessor(
            limit = ImageDimensionsLimiter(maxLimits.first, maxLimits.second),
            cropPoints = ImageCrop(cropPoints.first, cropPoints.second),
            rotation = ImageRotation(rotationAngle),
            format = outputFormat
        )
    }
}

class VideoUploadPreprocessor private constructor(
    val limit: Pair<Int, Int>,
    val frameRate: Int,
    val keyFramesInterval: Int,
    val targetAudioBitrate: Int,
    val targetVideoBitrate: Int
) : UploadPreprocessor<File>() {

    @WorkerThread
    @Throws(IOException::class)
    override fun outputFile(input: File, fileName: String, context: Context): File {
        TODO("Not yet implemented")
    }

    class Builder {
        private var maxLimits: Pair<Int, Int> = Int.MAX_VALUE to Int.MAX_VALUE
        private var frameRate: Int = 60
        private var keyFrameInterval: Int = 10
        private var audioBitrate: Int = 320
        private var videoBitrate: Int = 640

        fun limit(width: Int, height: Int): Builder {
            maxLimits = width to height
            return this
        }

        fun frameRate(frameRateValue: Int): Builder {
            frameRate = frameRateValue
            return this
        }

        fun keyFramesInterval(interval: Int): Builder {
            keyFrameInterval = interval
            return this
        }

        fun targetAudioBitrateKBps(targetAudioBitrateKBps: Int): Builder {
            audioBitrate = targetAudioBitrateKBps
            return this
        }

        fun targetVideoBitrateKBps(targetVideoBitrateKBps: Int): Builder {
            videoBitrate = targetVideoBitrateKBps
            return this
        }


        fun build(): VideoUploadPreprocessor = VideoUploadPreprocessor(
            limit = maxLimits,
            frameRate = frameRate,
            keyFramesInterval = keyFrameInterval,
            targetAudioBitrate = audioBitrate,
            targetVideoBitrate = videoBitrate
        )
    }
}