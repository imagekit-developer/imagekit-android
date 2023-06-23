package com.example.imagekit.android.fresco_extension

import android.net.Uri
import com.facebook.imagepipeline.request.ImageRequest
import com.facebook.imagepipeline.request.ImageRequestBuilder
import com.facebook.imagepipeline.request.Postprocessor
import com.imagekit.android.ImagekitUrlConstructor

fun ImagekitUrlConstructor.createWithFresco(
    postprocessor: Postprocessor? = null
): ImageRequest? = ImageRequestBuilder
    .newBuilderWithSource(Uri.parse(create()))
    .setPostprocessor(postprocessor)
    .build()