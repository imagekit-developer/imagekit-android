package com.imagekit.android.glide_extension

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.imagekit.android.ImagekitUrlConstructor

@SuppressLint("CheckResult")
fun ImagekitUrlConstructor.createWithGlide(
    placeholderImage: Drawable? = null,
    errorImage: Drawable? = null
): RequestBuilder<Drawable> {
    return Glide.with(context)
        .load(create())
        .placeholder(placeholderImage)
        .error(errorImage)
}
