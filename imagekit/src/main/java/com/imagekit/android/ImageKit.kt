package com.imagekit.android

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import com.imagekit.android.data.Repository
import com.imagekit.android.exception.ApplicationContextExpectedException
import com.imagekit.android.injection.component.DaggerUtilComponent
import com.imagekit.android.injection.component.UtilComponent
import com.imagekit.android.injection.module.ContextModule
import com.imagekit.android.util.SharedPrefUtil
import java.io.File
import javax.inject.Inject

class ImageKit private constructor(context: Context, clientPublicKey: String, imageKitId: String) {

    @Inject
    lateinit var mSharedPrefUtil: SharedPrefUtil

    @Inject
    lateinit var mRepository: Repository

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

        private var imageKit: ImageKit? = null

        fun init(context: Context, clientPublicKey: String, imageKitId: String) {
            if (context !is Application)
                throw ApplicationContextExpectedException()

            imageKit = ImageKit(context, clientPublicKey, imageKitId)
        }

        fun getInstance(): ImageKit {
            return if (imageKit == null) {
                throw IllegalStateException("Must Initialize CabFare before using getInstance()")
            } else {
                imageKit!!
            }
        }

        private lateinit var appComponent: UtilComponent
    }

    // Takes Bitmap
    fun uploadImage(
        fileName: String,
        signature: String,
        timestamp: Long,
        useUniqueFilename: Boolean = true,
        tags: Array<String>?,
        folder: String?,
        image: Bitmap,
        imageKitCallback: ImageKitCallback
    ) = mRepository.uploadImage(
        fileName,
        signature,
        timestamp,
        useUniqueFilename,
        tags,
        folder,
        image,
        imageKitCallback
    )

    // Takes File
    fun uploadImage(
        fileName: String,
        signature: String,
        timestamp: Long,
        useUniqueFilename: Boolean = true,
        tags: Array<String>?,
        folder: String?,
        image: File,
        imageKitCallback: ImageKitCallback
    ) = mRepository.uploadImage(
        fileName,
        signature,
        timestamp,
        useUniqueFilename,
        tags,
        folder,
        image,
        imageKitCallback
    )

    // Takes File
    fun uploadFile(
        fileName: String,
        signature: String,
        timestamp: Long,
        useUniqueFilename: Boolean = true,
        tags: Array<String>?,
        folder: String?,
        file: File,
        imageKitCallback: ImageKitCallback
    ) = mRepository.uploadFile(fileName, signature, timestamp, useUniqueFilename, tags, folder, file, imageKitCallback)
}