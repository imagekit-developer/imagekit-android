package com.imagekit.android.glide_extension

import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.imagekit.android.ImagekitUrlConstructor

fun ImagekitUrlConstructor.createWithGlide(
    @DrawableRes placeholderImage: Int?,
    @DrawableRes errorImage: Int?
): RequestBuilder<Drawable> = Glide.with(context)
    .load(create())
    .error(errorImage)
