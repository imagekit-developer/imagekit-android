package com.imagekit.android

import com.imagekit.android.entity.UploadError
import com.imagekit.android.entity.UploadResponse

interface ImageKitCallback {
    fun onSuccess(uploadResponse: UploadResponse)

    fun onError(uploadError: UploadError)
}