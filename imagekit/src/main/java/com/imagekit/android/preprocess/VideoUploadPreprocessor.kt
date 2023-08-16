package com.imagekit.android.preprocess

import android.content.Context
import android.media.MediaCodecInfo
import android.media.MediaExtractor
import android.media.MediaFormat
import android.net.Uri
import androidx.annotation.WorkerThread
import com.linkedin.android.litr.MediaTransformer
import com.linkedin.android.litr.TransformationListener
import java.io.File
import java.io.IOException
import java.util.UUID

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
            setInteger(MediaFormat.KEY_CHANNEL_COUNT, audioTrack?.getInteger(MediaFormat.KEY_CHANNEL_COUNT) ?: 2)
            setInteger(MediaFormat.KEY_SAMPLE_RATE, audioTrack?.getInteger(MediaFormat.KEY_SAMPLE_RATE) ?: 0)
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