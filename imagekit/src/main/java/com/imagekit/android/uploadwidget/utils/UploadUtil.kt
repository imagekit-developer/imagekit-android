package com.imagekit.android.uploadwidget.utils

import android.app.Activity
import android.content.Intent
import android.os.Build

class UploadUtil {
    companion object {
        fun openMediaChooser(activity: Activity, requestCode: Int) {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                intent.putExtra(
                    Intent.EXTRA_MIME_TYPES,
                    arrayOf("image/jpeg", "image/jpg", "image/png", "video/*")
                )
                intent.type = "(*/*"
            } else {
                intent.type = "image/*|video/*"
            }
            activity.startActivityForResult(intent, requestCode)
        }

        fun startUploadActivity(activity: Activity, requestCode: Int) {

        }
    }
}