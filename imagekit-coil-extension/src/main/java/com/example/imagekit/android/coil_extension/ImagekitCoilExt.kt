@file:JvmName("IKCoilExtension")
package com.example.imagekit.android.coil_extension

import android.graphics.drawable.Drawable
import coil.request.ImageRequest
import com.imagekit.android.ImagekitUrlConstructor

@JvmOverloads
fun ImagekitUrlConstructor.createWithCoil(
    placeholderImage: Drawable? = null,
    errorImage: Drawable? = null
): ImageRequest.Builder = ImageRequest.Builder(context)
    .data(create())
    .placeholder(placeholderImage)
    .error(errorImage)