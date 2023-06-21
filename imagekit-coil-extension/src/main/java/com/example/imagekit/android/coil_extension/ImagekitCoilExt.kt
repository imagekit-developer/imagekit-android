package com.example.imagekit.android.coil_extension

import coil.request.ImageRequest
import com.imagekit.android.ImagekitUrlConstructor

fun ImagekitUrlConstructor.createWithCoil(
    placeholderImage: Int?,
    errorImage: Int?
): ImageRequest.Builder = ImageRequest.Builder(context)
    .data(create())
    /*.apply {
        if (placeholderImage != null) {
            placeholder(placeholderImage)
        }
        if (errorImage != null) {
            error(errorImage)
        }
    }*/