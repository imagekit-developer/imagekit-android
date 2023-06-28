package com.imagekit.android

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Bitmap
import android.net.ConnectivityManager
import android.os.BatteryManager
import android.os.Build
import android.os.PowerManager
import com.imagekit.android.data.Repository
import com.imagekit.android.entity.UploadError
import com.imagekit.android.entity.UploadPolicy
import com.imagekit.android.preprocess.ImageUploadPreprocessor
import com.imagekit.android.preprocess.UploadPreprocessor
import com.imagekit.android.preprocess.VideoUploadPreprocessor
import com.imagekit.android.util.BitmapUtil.bitmapToFile
import com.linkedin.android.litr.TransformationListener
import com.linkedin.android.litr.analytics.TrackTransformationInfo
import com.imagekit.android.util.LogUtil
import java.io.File
import java.io.IOException
import javax.inject.Inject

class ImagekitUploader @Inject constructor(
    private val mRepository: Repository,
    private val context: Context
) {

    /**
     * Method to upload an image to ImageKit.
     * @param file The image bitmap that is to be uploaded
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
     * @param policy Set the custom policy to override the default policy for this upload request only.
     * This doesn't modify the default upload policy.
     * @param responseFields Comma-separated values of the fields that you want ImageKit.io to return in response.
     * For example, set the value of this field to tags,customCoordinates,isPrivateFile,metadata to get value of tags,
     * customCoordinates, isPrivateFile , and metadata in the response.
     * @param imageKitCallback Callback to communicate the result of the upload operation
     */
    fun upload(
        file: Bitmap,
        fileName: String,
        useUniqueFilename: Boolean = true,
        tags: Array<String>? = null,
        folder: String? = null,
        isPrivateFile: Boolean = false,
        customCoordinates: String? = null,
        policy: UploadPolicy = ImageKit.getInstance().defaultUploadPolicy,
        responseFields: String? = null,
        preprocessor: ImageUploadPreprocessor<Bitmap>? = null,
        imageKitCallback: ImageKitCallback
    ) {
        if (checkUploadPolicy(policy, imageKitCallback)) {
            try {
                val imageFile = preprocessor?.outputFile(file, fileName, context) ?: bitmapToFile(
                    context,
                    fileName,
                    file,
                    Bitmap.CompressFormat.PNG
                )
                return mRepository.upload(
                    imageFile,
                    fileName,
                    useUniqueFilename,
                    tags,
                    folder,
                    isPrivateFile,
                    customCoordinates,
                    responseFields,
                    policy,
                    imageKitCallback
                )
            } catch (e: IOException) {
                imageKitCallback.onError(UploadError(
                    exception = true,
                    message = context.getString(R.string.error_upload_preprocess)
                ))
            }
        } else {
            LogUtil.logError("Upload failed! Upload Policy Violation!")
        }
    }

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
     * @param policy Set the custom policy to override the default policy for this upload request only.
     * This doesn't modify the default upload policy.
     * @param responseFields Comma-separated values of the fields that you want ImageKit.io to return in response.
     * For example, set the value of this field to tags,customCoordinates,isPrivateFile,metadata to get value of tags,
     * customCoordinates, isPrivateFile , and metadata in the response.
     * @param imageKitCallback Callback to communicate the result of the upload operation
     */
    fun upload(
        file: File,
        fileName: String,
        useUniqueFilename: Boolean = true,
        tags: Array<String>? = null,
        folder: String? = null,
        isPrivateFile: Boolean = false,
        customCoordinates: String? = null,
        responseFields: String? = null,
        policy: UploadPolicy = ImageKit.getInstance().defaultUploadPolicy,
        preprocessor: UploadPreprocessor<File>? = null,
        imageKitCallback: ImageKitCallback
    ) {
        if (checkUploadPolicy(policy, imageKitCallback)) {
            if (!file.exists()) {
                imageKitCallback.onError(
                    UploadError(
                        exception = true,
                        message = context.getString(R.string.error_file_not_found)
                    )
                )
                return
            }
            preprocessor?.let {
            when (it) {
                is ImageUploadPreprocessor -> {
                    try {
                        mRepository.upload(
                            preprocessor.outputFile(file, fileName, context),
                            fileName,
                            useUniqueFilename,
                            tags,
                            folder,
                            isPrivateFile,
                            customCoordinates,
                            responseFields,
                            policy,
                            imageKitCallback
                        )
                    } catch (e: Exception) {
                        imageKitCallback.onError(UploadError(
                            exception = true,
                            message = context.getString(R.string.error_upload_preprocess)
                        ))
                    }
                }
                is VideoUploadPreprocessor -> {
                    var outputFile: File? = null
                    (preprocessor as VideoUploadPreprocessor).listener = object : TransformationListener {
                        override fun onStarted(id: String) {
                        }

                        override fun onProgress(id: String, progress: Float) {
                        }

                        override fun onCompleted(
                            id: String,
                            trackTransformationInfos: MutableList<TrackTransformationInfo>?
                        ) {
                            mRepository.upload(
                                outputFile!!,
                                fileName,
                                useUniqueFilename,
                                tags,
                                folder,
                                isPrivateFile,
                                customCoordinates,
                                responseFields,
                                policy,
                                imageKitCallback
                            )
                        }

                        override fun onCancelled(
                            id: String,
                            trackTransformationInfos: MutableList<TrackTransformationInfo>?
                        ) {
                            println("Process cancelled")
                            imageKitCallback.onError(UploadError(
                                exception = true,
                                message = context.getString(R.string.error_upload_preprocess)
                            ))
                        }

                        override fun onError(
                            id: String,
                            cause: Throwable?,
                            trackTransformationInfos: MutableList<TrackTransformationInfo>?
                        ) {
                            cause?.printStackTrace()
                            imageKitCallback.onError(UploadError(
                                exception = true,
                                message = context.getString(R.string.error_upload_preprocess)
                            ))
                        }
                    }
                    try {
                        outputFile = preprocessor.outputFile(file, fileName, context)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        imageKitCallback.onError(UploadError(
                            exception = true,
                            message = context.getString(R.string.error_upload_preprocess)
                        ))
                    }
                }
            }
        } ?: mRepository.upload(
                file,
                fileName,
                useUniqueFilename,
                tags,
                folder,
                isPrivateFile,
                customCoordinates,
                responseFields,
                policy,
                imageKitCallback
            )
        } else {
            LogUtil.logError("Upload error: upload policy constraints not satisfied")
        }
    }

    /**
     * Method to upload a file from a url to ImageKit. Permitted types: JPG, PNG, WebP, GIF, PDF, JS, CSS and TXT
     * @param file The fileUrl from which to download the file that is to be uploaded
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
     * @param policy Set the custom policy to override the default policy for this upload request only.
     * This doesn't modify the default upload policy.
     * @param responseFields Comma-separated values of the fields that you want ImageKit.io to return in response.
     * For example, set the value of this field to tags,customCoordinates,isPrivateFile,metadata to get value of tags,
     * customCoordinates, isPrivateFile , and metadata in the response.
     * @param imageKitCallback Callback to communicate the result of the upload operation
     */
    fun upload(
        file: String,
        fileName: String,
        useUniqueFilename: Boolean = true,
        tags: Array<String>? = null,
        folder: String? = null,
        isPrivateFile: Boolean = false,
        customCoordinates: String? = null,
        responseFields: String? = null,
        policy: UploadPolicy,
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
        policy,
        imageKitCallback
    )

    private fun checkUploadPolicy(policy: UploadPolicy, imageKitCallback: ImageKitCallback): Boolean {
        if (policy.networkType == UploadPolicy.NetworkType.UNMETERED) {
            val service =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (service.isActiveNetworkMetered) {
                imageKitCallback.onError(
                    UploadError(
                        exception = false,
                        statusNumber = 1501,
                        statusCode = "POLICY_ERROR_METERED_NETWORK",
                        message = "Upload policy error: current network is metered"
                    )
                )
                return false
            }
        }

        if (policy.requiresCharging) {
            val batteryStatus: Intent? =
                IntentFilter(Intent.ACTION_BATTERY_CHANGED).let { ifilter ->
                    context.registerReceiver(null, ifilter)
                }
            val status: Int = batteryStatus?.getIntExtra(BatteryManager.EXTRA_STATUS, -1) ?: -1
            val isCharging: Boolean = status == BatteryManager.BATTERY_STATUS_CHARGING
                    || status == BatteryManager.BATTERY_STATUS_FULL
            if (!isCharging) {
                imageKitCallback.onError(
                    UploadError(
                        exception = false,
                        statusNumber = 1502,
                        statusCode = "POLICY_ERROR_BATTERY_DISCHARGING",
                        message = "Upload policy error: device battery is not charging"
                    )
                )
                return false
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && policy.requiresIdle) {
            val powerManager = context.getSystemService(Context.POWER_SERVICE) as PowerManager
            if (!powerManager.isDeviceIdleMode) {
                imageKitCallback.onError(
                    UploadError(
                        exception = false,
                        statusNumber = 1503,
                        statusCode = "POLICY_ERROR_DEVICE_NOT_IDLE",
                        message = "Upload policy error: device is not in idle mode"
                    )
                )
                return false
            }
        }
        return true
    }

}