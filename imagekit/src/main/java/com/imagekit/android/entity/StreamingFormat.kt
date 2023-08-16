package com.imagekit.android.entity

enum class StreamingFormat(val extension: String) {
    HLS("m3u8"),
    DASH("mpd")
}