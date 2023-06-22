package com.imagekit.android.preprocess

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import android.graphics.BitmapFactory
import android.graphics.Point
import android.media.MediaCodecInfo
import android.media.MediaExtractor
import android.media.MediaFormat
import android.net.Uri
import androidx.annotation.WorkerThread
import com.imagekit.android.util.BitmapUtil
import com.linkedin.android.litr.MediaTransformer
import com.linkedin.android.litr.TransformationListener
import com.linkedin.android.litr.analytics.TrackTransformationInfo
import java.io.File
import java.io.IOException
import java.util.UUID

sealed class UploadPreprocessor<T> {

    override fun equals(other: Any?): Boolean = this === other

    override fun hashCode(): Int = System.identityHashCode(this)

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

        fun limit(width: Int, height: Int): Builder = apply { maxLimits = width to height }

        fun crop(p1: Point, p2: Point): Builder = apply { cropPoints = p1 to p2 }

        fun rotate(degrees: Float): Builder = apply { rotationAngle = degrees }

        fun format(format: CompressFormat): Builder = apply { outputFormat = format }

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

    private lateinit var _listener: TransformationListener
    var listener: TransformationListener
        get() = _listener
        set(value) {
            _listener = value
        }

    @WorkerThread
    @Throws(IOException::class)
    override fun outputFile(input: File, fileName: String, context: Context): File {
        val tempFile = input.copyTo(File(context.externalCacheDir, input.name), overwrite = true)
        val outFile = File(context.externalCacheDir, fileName).also { it.createNewFile() }

        val extractor = MediaExtractor().apply { setDataSource(tempFile.path) }
        val tracks = List(extractor.trackCount) { extractor.getTrackFormat(it) }
        val videoTrack = tracks.find { it.getString(MediaFormat.KEY_MIME)?.startsWith("video") == true }
        val audioTrack = tracks.find { it.getString(MediaFormat.KEY_MIME)?.startsWith("audio") == true }

        val targetVideo = MediaFormat().apply {
            setString(MediaFormat.KEY_MIME, videoTrack?.getString(MediaFormat.KEY_MIME) ?: MediaFormat.MIMETYPE_VIDEO_MPEG2)
            setInteger(MediaFormat.KEY_WIDTH, (videoTrack?.getInteger(MediaFormat.KEY_WIDTH) ?: 0).coerceAtMost(limit.first))
            setInteger(MediaFormat.KEY_HEIGHT, (videoTrack?.getInteger(MediaFormat.KEY_HEIGHT) ?: 0).coerceAtMost(limit.second))
            setInteger(MediaFormat.KEY_I_FRAME_INTERVAL, keyFramesInterval)
            setInteger(MediaFormat.KEY_BIT_RATE, targetVideoBitrate)
            setInteger(MediaFormat.KEY_FRAME_RATE, frameRate)
            setInteger(MediaFormat.KEY_COLOR_FORMAT, MediaCodecInfo.CodecCapabilities.COLOR_FormatSurface)
        }
        val targetAudio = MediaFormat().apply {
            setString(MediaFormat.KEY_MIME, audioTrack?.getString(MediaFormat.KEY_MIME))
            setInteger(MediaFormat.KEY_CHANNEL_COUNT, videoTrack?.getInteger(MediaFormat.KEY_CHANNEL_COUNT) ?: 2)
            setInteger(MediaFormat.KEY_SAMPLE_RATE, videoTrack?.getInteger(MediaFormat.KEY_SAMPLE_RATE)!!)
            setInteger(MediaFormat.KEY_BIT_RATE, targetAudioBitrate)
        }
        MediaTransformer(context.applicationContext).transform(
            UUID.randomUUID().toString(),
            Uri.parse(tempFile.path),
            outFile.path,
            targetVideo,
            targetAudio,
            _listener,
            null
        )

        return outFile
    }

    class Builder {
        private var maxLimits: Pair<Int, Int> = Int.MAX_VALUE to Int.MAX_VALUE
        private var frameRate: Int = 30
        private var keyFrameInterval: Int = 3
        private var audioBitrate: Int = 128
        private var videoBitrate: Int = 640

        fun limit(width: Int, height: Int): Builder = apply { maxLimits = width to height }

        fun frameRate(frameRateValue: Int): Builder = apply { frameRate = frameRateValue }

        fun keyFramesInterval(interval: Int): Builder = apply { keyFrameInterval = interval }

        fun targetAudioBitrateKbps(targetAudioBitrateKbps: Int): Builder = apply {
            audioBitrate = targetAudioBitrateKbps * 1024
        }

        fun targetVideoBitrateKbps(targetVideoBitrateKbps: Int): Builder = apply {
            videoBitrate = targetVideoBitrateKbps * 1024
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