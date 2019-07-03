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

@Suppress("unused")
class ImageKit private constructor(context: Context, clientPublicKey: String, imageKitId: String) {

    @Inject
    internal lateinit var mSharedPrefUtil: SharedPrefUtil

    @Inject
    internal lateinit var mRepository: Repository

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
                throw IllegalStateException("Must Initialize ImageKit before using getInstance()")
            } else {
                imageKit!!
            }
        }

        private lateinit var appComponent: UtilComponent
    }

    /**
     * Method to upload an image to ImageKit.
     * @param image The image bitmap that is to be uploaded
     * @param fileName The name with which the file has to be uploaded
     * @param signature HMAC-SHA1 signature generated for the file upload.
     * See <a href="https://docs.imagekit.io/#server-side-image-upload">https://docs.imagekit.io/#server-side-image-upload</a>
     * for more information.
     * @param timestamp UTC timestamp in seconds. The request will be valid for 30 minutes from this timestamp.
     * @param useUniqueFilename “true” or “false”. If set to true, ImageKit will add a unique code to the filename parameter
     * to get a unique filename. If false, the image is uploaded with the filename parameter as name. If an image exists
     * with the same name, this new image will override it. Default is “true”
     * @param tags Array of tags e.g tag1,tag2,tag3. The maximum length of all characters should not exceed 500.
     * % is not allowed. If this field is not specified and the file is overwritten then tags will be removed.
     * @param folder The folder path (eg- /images/folder/) in which the image has to be uploaded. Default is “/”
     * @param imageKitCallback Callback to communicate the result of the upload operation
     */
    fun uploadImage(
        image: Bitmap,
        fileName: String,
        signature: String,
        timestamp: Long,
        useUniqueFilename: Boolean = true,
        tags: Array<String>?,
        folder: String?,
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

    /**
     * Method to upload an image to ImageKit.
     * @param image The image file that is to be uploaded
     * @param fileName The name with which the file has to be uploaded
     * @param signature HMAC-SHA1 signature generated for the file upload.
     * See <a href="https://docs.imagekit.io/#server-side-image-upload">https://docs.imagekit.io/#server-side-image-upload</a>
     * for more information.
     * @param timestamp UTC timestamp in seconds. The request will be valid for 30 minutes from this timestamp.
     * @param useUniqueFilename “true” or “false”. If set to true, ImageKit will add a unique code to the filename parameter
     * to get a unique filename. If false, the image is uploaded with the filename parameter as name. If an image exists
     * with the same name, this new image will override it. Default is “true”
     * @param tags Array of tags e.g tag1,tag2,tag3. The maximum length of all characters should not exceed 500.
     * % is not allowed. If this field is not specified and the file is overwritten then tags will be removed.
     * @param folder The folder path (eg- /images/folder/) in which the image has to be uploaded. Default is “/”
     * @param imageKitCallback Callback to communicate the result of the upload operation
     */
    fun uploadImage(
        image: File,
        fileName: String,
        signature: String,
        timestamp: Long,
        useUniqueFilename: Boolean = true,
        tags: Array<String>?,
        folder: String?,
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

    /**
     * Method to upload an image to ImageKit.
     * @param file The file that is to be uploaded. Supported formats: PDF, JS, CSS and TXT
     * @param fileName The name with which the file has to be uploaded
     * @param signature HMAC-SHA1 signature generated for the file upload.
     * See <a href="https://docs.imagekit.io/#server-side-image-upload">https://docs.imagekit.io/#server-side-image-upload</a>
     * for more information. * @param timestamp UTC timestamp in seconds. The request will be valid for 30 minutes from this timestamp.
     * @param useUniqueFilename “true” or “false”. If set to true, ImageKit will add a unique code to the filename parameter
     * to get a unique filename. If false, the image is uploaded with the filename parameter as name. If an image exists
     * with the same name, this new image will override it. Default is “true”
     * @param tags Array of tags e.g tag1,tag2,tag3. The maximum length of all characters should not exceed 500.
     * % is not allowed. If this field is not specified and the file is overwritten then tags will be removed.
     * @param folder The folder path (eg- /images/folder/) in which the image has to be uploaded. Default is “/”
     * @param imageKitCallback Callback to communicate the result of the upload operation
     */
    fun uploadFile(
        file: File,
        fileName: String,
        signature: String,
        timestamp: Long,
        useUniqueFilename: Boolean = true,
        tags: Array<String>?,
        folder: String?,
        imageKitCallback: ImageKitCallback
    ) = mRepository.uploadFile(fileName, signature, timestamp, useUniqueFilename, tags, folder, file, imageKitCallback)
}