package com.example.imagekit.android.picasso_extension

import androidx.annotation.DrawableRes
import com.imagekit.android.ImagekitUrlConstructor
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator

fun ImagekitUrlConstructor.createWithPicasso(
    @DrawableRes placeholderImage: Int?,
    @DrawableRes errorImage: Int?
): RequestCreator = Picasso.get()
    .load(create())
    .placeholder(placeholderImage!!)
    .error(errorImage!!)