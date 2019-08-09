package com.imagekit.android

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.imagekit.android.exception.ApplicationContextExpectedException
import com.imagekit.android.injection.component.DaggerUtilComponent
import com.imagekit.android.injection.component.UtilComponent
import com.imagekit.android.injection.module.ContextModule
import com.imagekit.android.util.SharedPrefUtil
import javax.inject.Inject

@Suppress("unused")
class ImageKit private constructor(val context: Context, clientPublicKey: String, imageKitId: String) {

    @Inject
    internal lateinit var mSharedPrefUtil: SharedPrefUtil

    @Inject
    internal lateinit var mImagekitUploader: ImagekitUploader

    init {
        appComponent = DaggerUtilComponent.builder()
            .contextModule(ContextModule(context))
            .build()

        appComponent
            .inject(this)

        mSharedPrefUtil.setClientPublicKey(clientPublicKey)
        mSharedPrefUtil.setImageKitId(imageKitId)
    }

    companion object {

        @SuppressLint("StaticFieldLeak")
        private var imageKit: ImageKit? = null

        fun init(context: Context, clientPublicKey: String, imageKitId: String) {
            if (context !is Application)
                throw ApplicationContextExpectedException()

            imageKit = ImageKit(context, clientPublicKey, imageKitId)
        }

        fun getInstance(): ImageKit {
            return if (imageKit == null) {
                throw IllegalStateException("Must Initialize ImageKit before using getInstance()")
            } else {
                imageKit!!
            }
        }

        private lateinit var appComponent: UtilComponent
    }

    fun url(endpoint: String, imagePath: String) = ImagekitUrlConstructor(context, endpoint, imagePath)

    fun uploader() = mImagekitUploader
}