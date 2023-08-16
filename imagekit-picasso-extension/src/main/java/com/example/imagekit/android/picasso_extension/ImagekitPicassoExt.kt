@file:JvmName("IKPicassoExtension")
package com.example.imagekit.android.picasso_extension

import android.graphics.drawable.Drawable
import com.imagekit.android.ImagekitUrlConstructor
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator

@JvmOverloads
fun ImagekitUrlConstructor.createWithPicasso(
    placeholderImage: Drawable? = null,
    errorImage: Drawable? = null
): RequestCreator = Picasso.get()
    .load(create())
    .apply {
        if (placeholderImage != null) {
            placeholder(placeholderImage)
        }
        if (errorImage != null) {
            error(errorImage)
        }
    }