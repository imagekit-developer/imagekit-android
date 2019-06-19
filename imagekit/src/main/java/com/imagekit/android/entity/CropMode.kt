package com.imagekit.android.entity

enum class CropMode constructor(val value: String) {
    RESIZE("resize"),
    EXTRACT("extract"),
    PAD_EXTRACT("pad_extract"),
    PAD_RESIZE("pad_resize")
}