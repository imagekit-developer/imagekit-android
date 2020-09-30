package com.imagekit.android.data

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import com.google.gson.Gson
import com.imagekit.android.ImageKitCallback
import com.imagekit.android.R
import com.imagekit.android.entity.UploadError
import com.imagekit.android.entity.UploadResponse
import com.imagekit.android.retrofit.SignatureApi
import com.imagekit.android.retrofit.UploadApi
import com.imagekit.android.util.BitmapUtil.bitmapToFile
import retrofit2.HttpException
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class Repository @Inject constructor(
    private val context: Context,
    private val signatureApi: SignatureApi,
    private val uploadApi: UploadApi
) {
    companion object {
        private const val DURATION_EXPIRY_MINUTES = 45L
    }

    // Takes Bitmap
    @SuppressLint("CheckResult")
    fun upload(
        image: Bitmap,
        fileName: String,
        useUniqueFilename: Boolean,
        tags: Array<String>?,
        folder: String?,
        isPrivateFile: Boolean?,
        customCoordinates: String?,
        responseFields: String?,
        imageKitCallback: ImageKitCallback
    ) = upload(
        bitmapToFile(
            context,
            fileName,
            image
        ),
        fileName,
        useUniqueFilename,
        tags,
        folder,
        isPrivateFile,
        customCoordinates,
        responseFields,
        imageKitCallback
    )

    //Takes file
    @SuppressLint("CheckResult")
    fun upload(
        file: File,
        fileName: String,
        useUniqueFilename: Boolean,
        tags: Array<String>?,
        folder: String?,
        isPrivateFile: Boolean?,
        customCoordinates: String?,
        responseFields: String?,
        imageKitCallback: ImageKitCallback
    ) {
        if (!file.exists()) {
            imageKitCallback.onError(
                UploadError(
                    exception = true,
                    message = context.getString(R.string.error_file_not_found)
                )
            )
            return
        }

        val expire = ((System.currentTimeMillis() / 1000) + TimeUnit.MINUTES.toSeconds(
            DURATION_EXPIRY_MINUTES
        )).toString()
        val signatureSingle = signatureApi.getSignature(expire)

        if (signatureSingle != null) {
            signatureSingle
                .subscribe({ result ->
                    uploadApi.getFileUploadCall(
                        result,
                        file,
                        fileName,
                        useUniqueFilename,
                        tags,
                        folder,
                        isPrivateFile,
                        customCoordinates,
                        responseFields,
                        expire
                    ).subscribe({ response ->
                        imageKitCallback.onSuccess(
                            Gson().fromJson(
                                response.string(),
                                UploadResponse::class.java
                            )
                        )
                    }, { e ->
                        if (e is HttpException) {
                            e.response()?.let {
                                try {
                                    imageKitCallback.onError(
                                        Gson().fromJson(
                                            it.errorBody()!!.string(),
                                            UploadError::class.java
                                        )
                                    )
                                } catch (exception: IllegalStateException) {
                                    imageKitCallback.onError(
                                        UploadError(
                                            exception = true,
                                            statusNumber = e.code(),
                                            message = e.message()
                                        )
                                    )
                                }
                            }
                        } else
                            imageKitCallback.onError(UploadError(true))
                    })
                }, {
                    imageKitCallback.onError(
                        UploadError(
                            exception = true,
                            message = context.getString(R.string.error_signature_generation_failed)
                        )
                    )
                })
        } else
            imageKitCallback.onError(
                UploadError(
                    exception = true,
                    message = context.getString(R.string.error_authentication_endpoint_is_missing)
                )
            )
    }

    // Takes Url
    @SuppressLint("CheckResult")
    fun upload(
        fileUrl: String,
        fileName: String,
        useUniqueFilename: Boolean,
        tags: Array<String>?,
        folder: String?,
        isPrivateFile: Boolean,
        customCoordinates: String?,
        responseFields: String?,
        imageKitCallback: ImageKitCallback
    ) {
        val expire = ((System.currentTimeMillis() / 1000) + TimeUnit.MINUTES.toSeconds(
            DURATION_EXPIRY_MINUTES
        )).toString()
        val signatureSingle = signatureApi.getSignature(expire)
        if (signatureSingle != null) {
            signatureSingle
                .subscribe({ result ->
                    uploadApi.getFileUploadCall(
                        result,
                        fileUrl,
                        fileName,
                        useUniqueFilename,
                        tags,
                        folder,
                        isPrivateFile,
                        customCoordinates,
                        responseFields,
                        expire
                    ).subscribe({ response ->
                        imageKitCallback.onSuccess(
                            Gson().fromJson(
                                response.string(),
                                UploadResponse::class.java
                            )
                        )
                    }, { e ->
                        if (e is HttpException) {
                            e.response()?.let {
                                try {
                                    imageKitCallback.onError(
                                        Gson().fromJson(
                                            it.errorBody()!!.string(),
                                            UploadError::class.java
                                        )
                                    )
                                } catch (exception: IllegalStateException) {
                                    imageKitCallback.onError(
                                        UploadError(
                                            exception = true,
                                            statusNumber = e.code(),
                                            message = e.message()
                                        )
                                    )
                                }
                            }
                        } else
                            imageKitCallback.onError(UploadError(true))
                    })
                }, {
                    imageKitCallback.onError(
                        UploadError(
                            exception = true,
                            message = context.getString(R.string.error_signature_generation_failed)
                        )
                    )
                })
        } else {
            imageKitCallback.onError(
                UploadError(
                    exception = true,
                    message = context.getString(R.string.error_authentication_endpoint_is_missing)
                )
            )
        }
    }
}