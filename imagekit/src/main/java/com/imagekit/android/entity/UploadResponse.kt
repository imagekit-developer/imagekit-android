package com.imagekit.android.entity

data class UploadResponse(
    val name: String,
    val imagePath: String,
    val size: Int,
    val height: Int,
    val width: Int,
    val url: String
)