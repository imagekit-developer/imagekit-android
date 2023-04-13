package com.imagekit.android.download

data class ImageKitOptions(
    val width: Int? = null,
    val height: Int? = null,
    val quality: Int? = null,
    val crop: String? = null,
    val rotation: Int? = null,
)