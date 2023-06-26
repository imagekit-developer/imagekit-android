package com.example.imagekit.android.fresco_extension

import android.net.Uri
import android.view.View
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.view.DraweeView
import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.imagepipeline.request.ImageRequest
import com.facebook.imagepipeline.request.ImageRequestBuilder
import com.facebook.imagepipeline.request.Postprocessor
import com.imagekit.android.ImagekitUrlConstructor

fun ImagekitUrlConstructor.createWithFresco(
    postprocessor: Postprocessor? = null
): ImageRequest = ImageRequestBuilder
    .newBuilderWithSource(Uri.parse(create()))
    .setPostprocessor(postprocessor)
    .build()

fun ImageRequest.buildWithTarget(view: SimpleDraweeView) {
    view.controller = Fresco.newDraweeControllerBuilder()
        .setImageRequest(this)
        .setOldController(view.controller)
        .build()
}