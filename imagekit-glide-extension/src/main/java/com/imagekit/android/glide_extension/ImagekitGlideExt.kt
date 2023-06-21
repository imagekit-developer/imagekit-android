package com.imagekit.android.glide_extension

import android.graphics.drawable.Drawable
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.imagekit.android.ImagekitUrlConstructor

fun ImagekitUrlConstructor.createWithGlide(
    placeholderImage: Int?,
    errorImage: Int?
): RequestBuilder<Drawable> {
    val builder = Glide.with(context)
        .load(create())
        .error(errorImage)
    if (placeholderImage != null) {
        return builder.placeholder(placeholderImage)
    }
    return builder
}
