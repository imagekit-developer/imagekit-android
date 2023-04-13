package com.imagekit.android.download

import android.graphics.drawable.Drawable
import android.widget.ImageView



interface ImageKitLoader {
    fun loadImage(
        url: String,
        imageView: ImageView,
        options: ImageKitOptions?,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    )

    fun loadImageWithPlaceholder(
        url: String,
        imageView: ImageView,
        placeholderResId: Int,
        options: ImageKitOptions?,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    )

    fun loadImageIntoTarget(
        url: String,
        target: Target<*>,
        options: ImageKitOptions?,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    )
}