package com.imagekit.android.entity

data class UploadError(
    val exception: Boolean,
    val statusNumber: Int = 1500,
    val statusCode: String = "SERVER_ERROR",
    val message: String = "Internal server error while uploading the file"
)