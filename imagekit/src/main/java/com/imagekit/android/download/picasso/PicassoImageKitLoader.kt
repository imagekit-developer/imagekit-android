package com.imagekit.android.download.picasso

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.imagekit.android.download.ImageKitLoader
import com.imagekit.android.download.ImageKitOptions
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator
import com.squareup.picasso.Target
import java.lang.Exception

class PicassoImageKitLoader(private val context: Context) : ImageKitLoader {

    override fun loadImage(
        url: String,
        imageView: ImageView,
        options: ImageKitOptions?,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    ) {
        // Implement loading image with Picasso without a placeholder
        val request = Picasso.Builder(context)
            .build()
            .load(url)
            .apply { applyOptions(this, options) }
            .into(imageView, object : Callback {
                override fun onSuccess() {
                    // Handle successful loading of the image here
                    onSuccess()
                }

                override fun onError(e: Exception?) {

                }
            })
    }

    override fun loadImageWithPlaceholder(
        url: String,
        imageView: ImageView,
        placeholderResId: Int,
        options: ImageKitOptions?,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    ) {
        // Implement loading image with Picasso with a placeholder
        val request = Picasso.Builder(context)
            .build()
            .load(url)
            .placeholder(placeholderResId)
            .apply { applyOptions(this, options) }
            .into(imageView, object : Callback {
                override fun onSuccess() {
                    // Handle successful loading of the image here
                    onSuccess()
                }

                override fun onError(e: Exception?) {

                }
            })
        //finish this code chatgpt

    }

    override fun loadImageIntoTarget(
        url: String,
        target: com.imagekit.android.download.Target<*>,
        options: ImageKitOptions?,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    ) {
        val picasso = Picasso.Builder(context).build()

        // Create an ImageViewTarget to display the loaded image in an ImageView
        val imageViewTarget = object : Target {
            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                // Call the onSuccess callback with the loaded bitmap
                onSuccess()
            }

            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                // Call the onError callback with the exception
                onError(Exception("Failed to load bitmap"))
            }

            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
                // Do nothing
            }
        }

        // Implement loading image with Picasso into the ImageViewTarget
        picasso.load(url)
            .apply { applyOptions(this, options) } // Apply ImageKitOptions to the Picasso request
            .into(imageViewTarget)
    }

    private fun applyOptions(request: RequestCreator, options: ImageKitOptions?) {
        // Apply ImageKitOptions to the Picasso request here
        // For example:
        // if (options != null) {
        //     if (options.width > 0 && options.height > 0) {
        //         request.resize(options.width, options.height)
        //     }
        //     if (options.placeholderResId > 0) {
        //         request.placeholder(options.placeholderResId)
        //     }
        //     // Add other options as needed
        // }
    }
}

