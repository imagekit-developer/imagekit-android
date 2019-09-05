package com.imagekit.android

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.imagekit.android.entity.TransformationPosition
import com.imagekit.android.exception.ApplicationContextExpectedException
import com.imagekit.android.injection.component.DaggerUtilComponent
import com.imagekit.android.injection.component.UtilComponent
import com.imagekit.android.injection.module.ContextModule
import com.imagekit.android.retrofit.NetworkManager
import com.imagekit.android.util.SharedPrefUtil
import javax.inject.Inject

@Suppress("unused")
class ImageKit private constructor(
    val context: Context,
    clientPublicKey: String,
    imageKitEndpoint: String,
    transformationPosition: TransformationPosition,
    authenticationEndpoint: String? = null
) {

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
        mSharedPrefUtil.setImageKitUrlEndpoint(imageKitEndpoint)
        mSharedPrefUtil.setTransformationPosition(transformationPosition)
        mSharedPrefUtil.setClientAuthenticationEndpoint(authenticationEndpoint)

        NetworkManager.initialize()
    }

    companion object {

        @SuppressLint("StaticFieldLeak")
        private var imageKit: ImageKit? = null

        fun init(
            context: Context,
            clientPublicKey: String,
            transformationPosition: TransformationPosition = TransformationPosition.PATH,
            imageKitEndpoint: String,
            authenticationEndpoint: String? = null
        ) {
            if (context !is Application)
                throw ApplicationContextExpectedException()
            else check(!(clientPublicKey.isBlank() || imageKitEndpoint.isBlank())) { "Missing publicKey/urlEndpoint during initialization" }

            imageKit = ImageKit(
                context,
                clientPublicKey,
                imageKitEndpoint,
                transformationPosition,
                authenticationEndpoint
            )
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

    fun url(
        imagePath: String,
        endpoint: String = mSharedPrefUtil.getImageKitUrlEndpoint(),
        transformationPosition: TransformationPosition = mSharedPrefUtil.getTransformationPosition()
    ) =
        ImagekitUrlConstructor(context, endpoint, imagePath, transformationPosition)

    fun url(
        src: String,
        transformationPosition: TransformationPosition = mSharedPrefUtil.getTransformationPosition()
    ) =
        ImagekitUrlConstructor(
            context,
            src,
            transformationPosition
        )

    fun uploader() = mImagekitUploader
}