package com.imagekit.android

import android.graphics.Bitmap
import com.imagekit.android.data.Repository
import java.io.File
import javax.inject.Inject

class ImagekitUploader @Inject constructor(private val mRepository: Repository) {

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
    fun upload(
        image: Bitmap,
        fileName: String,
        signature: String,
        timestamp: Long,
        useUniqueFilename: Boolean = true,
        tags: Array<String>?,
        folder: String?,
        imageKitCallback: ImageKitCallback
    ) = mRepository.upload(
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
     * Method to upload a file to ImageKit. Permitted types: JPG, PNG, WebP, GIF, PDF, JS, CSS and TXT
     * @param file The file that is to be uploaded
     * @param fileName The name with which the file has to be uploaded
     * @param signature HMAC-SHA1 signature generated for the file upload.
     * See <a href="https://docs.imagekit.io/#server-side-file-upload">https://docs.imagekit.io/#server-side-file-upload</a>
     * for more information.
     * @param timestamp UTC timestamp in seconds. The request will be valid for 30 minutes from this timestamp.
     * @param useUniqueFilename “true” or “false”. If set to true, ImageKit will add a unique code to the filename parameter
     * to get a unique filename. If false, the file is uploaded with the filename parameter as name. If an file exists
     * with the same name, this new file will override it. Default is “true”
     * @param tags Array of tags e.g tag1,tag2,tag3. The maximum length of all characters should not exceed 500.
     * % is not allowed. If this field is not specified and the file is overwritten then tags will be removed.
     * @param folder The folder path (eg- /images/folder/) in which the file has to be uploaded. Default is “/”
     * @param imageKitCallback Callback to communicate the result of the upload operation
     */
    fun upload(
        file: File,
        fileName: String,
        signature: String,
        timestamp: Long,
        useUniqueFilename: Boolean = true,
        tags: Array<String>?,
        folder: String?,
        imageKitCallback: ImageKitCallback
    ) = mRepository.upload(
        fileName,
        signature,
        timestamp,
        useUniqueFilename,
        tags,
        folder,
        file,
        imageKitCallback
    )
}