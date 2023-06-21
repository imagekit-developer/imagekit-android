package com.example.imagekit.android.picasso_extension

import com.imagekit.android.ImagekitUrlConstructor
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator

fun ImagekitUrlConstructor.createWithPicasso(
    placeholderImage: Int?,
    errorImage: Int?
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