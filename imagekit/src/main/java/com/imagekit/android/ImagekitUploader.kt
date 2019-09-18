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
     * @param useUniqueFilename “true” or “false”. If set to true, ImageKit will add a unique code to the filename parameter
     * to get a unique filename. If false, the image is uploaded with the filename parameter as name. If an image exists
     * with the same name, this new image will override it. Default is “true”
     * @param tags Array of tags e.g tag1,tag2,tag3. The maximum length of all characters should not exceed 500.
     * % is not allowed. If this field is not specified and the file is overwritten then tags will be removed.
     * @param folder The folder path (eg- /images/folder/) in which the image has to be uploaded. Default is “/”
     * @param isPrivateFile “true” or “false”. If set to true, the file is marked as private which restricts access to the original
     * image URL and unnamed image transformations without signed URLs. Without the signed URL, only named transformations work
     * on private images. Default is “false”
     * @param customCoordinates Define an important area in the image. To be passed as a string with
     * the x and y coordinates of the top-left corner,
     * and width and height of the area of interest in format x,y,width,height. For example - 10,10,100,100.
     * Can be used with fo-custom transformation.
     * If this field is not specified and the file is overwritten, then customCoordinates will be removed.
     * @param responseFields Comma-separated values of the fields that you want ImageKit.io to return in response.
     * For example, set the value of this field to tags,customCoordinates,isPrivateFile,metadata to get value of tags,
     * customCoordinates, isPrivateFile , and metadata in the response.
     * @param imageKitCallback Callback to communicate the result of the upload operation
     */
    fun upload(
        file: Bitmap,
        fileName: String,
        useUniqueFilename: Boolean = true,
        tags: Array<String>?,
        folder: String?,
        isPrivateFile: Boolean = false,
        customCoordinates: String? = null,
        responseFields: String? = null,
        signatureHeaders: Map<String, String>? = null,
        imageKitCallback: ImageKitCallback
    ) = mRepository.upload(
        file,
        fileName,
        useUniqueFilename,
        tags,
        folder,
        isPrivateFile,
        customCoordinates,
        responseFields,
        signatureHeaders,
        imageKitCallback
    )

    /**
     * Method to upload a file to ImageKit. Permitted types: JPG, PNG, WebP, GIF, PDF, JS, CSS and TXT
     * @param file The file that is to be uploaded
     * @param fileName The name with which the file has to be uploaded
     * @param useUniqueFilename “true” or “false”. If set to true, ImageKit will add a unique code to the filename parameter
     * to get a unique filename. If false, the image is uploaded with the filename parameter as name. If an image exists
     * with the same name, this new image will override it. Default is “true”
     * @param tags Array of tags e.g tag1,tag2,tag3. The maximum length of all characters should not exceed 500.
     * % is not allowed. If this field is not specified and the file is overwritten then tags will be removed.
     * @param folder The folder path (eg- /images/folder/) in which the image has to be uploaded. Default is “/”
     * @param isPrivateFile “true” or “false”. If set to true, the file is marked as private which restricts access to the original
     * image URL and unnamed image transformations without signed URLs. Without the signed URL, only named transformations work
     * on private images. Default is “false”
     * @param customCoordinates Define an important area in the image. To be passed as a string with
     * the x and y coordinates of the top-left corner,
     * and width and height of the area of interest in format x,y,width,height. For example - 10,10,100,100.
     * Can be used with fo-custom transformation.
     * If this field is not specified and the file is overwritten, then customCoordinates will be removed.
     * @param responseFields Comma-separated values of the fields that you want ImageKit.io to return in response.
     * For example, set the value of this field to tags,customCoordinates,isPrivateFile,metadata to get value of tags,
     * customCoordinates, isPrivateFile , and metadata in the response.
     * @param imageKitCallback Callback to communicate the result of the upload operation
     */
    fun upload(
        file: File,
        fileName: String,
        useUniqueFilename: Boolean = true,
        tags: Array<String>?,
        folder: String?,
        isPrivateFile: Boolean = false,
        customCoordinates: String? = null,
        responseFields: String? = null,
        signatureHeaders: Map<String, String>? = null,
        imageKitCallback: ImageKitCallback
    ) = mRepository.upload(
        file,
        fileName,
        useUniqueFilename,
        tags,
        folder,
        isPrivateFile,
        customCoordinates,
        responseFields,
        signatureHeaders,
        imageKitCallback
    )

    /**
     * Method to upload a file from a url to ImageKit. Permitted types: JPG, PNG, WebP, GIF, PDF, JS, CSS and TXT
     * @param fileUrl The fileUrl from which to download the file that is to be uploaded
     * @param fileName The name with which the file has to be uploaded
     * @param useUniqueFilename “true” or “false”. If set to true, ImageKit will add a unique code to the filename parameter
     * to get a unique filename. If false, the image is uploaded with the filename parameter as name. If an image exists
     * with the same name, this new image will override it. Default is “true”
     * @param tags Array of tags e.g tag1,tag2,tag3. The maximum length of all characters should not exceed 500.
     * % is not allowed. If this field is not specified and the file is overwritten then tags will be removed.
     * @param folder The folder path (eg- /images/folder/) in which the image has to be uploaded. Default is “/”
     * @param isPrivateFile “true” or “false”. If set to true, the file is marked as private which restricts access to the original
     * image URL and unnamed image transformations without signed URLs. Without the signed URL, only named transformations work
     * on private images. Default is “false”
     * @param customCoordinates Define an important area in the image. To be passed as a string with
     * the x and y coordinates of the top-left corner,
     * and width and height of the area of interest in format x,y,width,height. For example - 10,10,100,100.
     * Can be used with fo-custom transformation.
     * If this field is not specified and the file is overwritten, then customCoordinates will be removed.
     * @param responseFields Comma-separated values of the fields that you want ImageKit.io to return in response.
     * For example, set the value of this field to tags,customCoordinates,isPrivateFile,metadata to get value of tags,
     * customCoordinates, isPrivateFile , and metadata in the response.
     * @param imageKitCallback Callback to communicate the result of the upload operation
     */
    fun upload(
        file: String,
        fileName: String,
        useUniqueFilename: Boolean = true,
        tags: Array<String>?,
        folder: String?,
        isPrivateFile: Boolean = false,
        customCoordinates: String? = null,
        responseFields: String? = null,
        signatureHeaders: Map<String, String>? = null,
        imageKitCallback: ImageKitCallback
    ) = mRepository.upload(
        file,
        fileName,
        useUniqueFilename,
        tags,
        folder,
        isPrivateFile,
        customCoordinates,
        responseFields,
        signatureHeaders,
        imageKitCallback
    )
}