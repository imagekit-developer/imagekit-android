package com.imagekit.android.data

import android.annotation.SuppressLint
import android.content.Context
import com.google.gson.Gson
import com.imagekit.android.ImageKitCallback
import com.imagekit.android.R
import com.imagekit.android.entity.UploadError
import com.imagekit.android.entity.UploadPolicy
import com.imagekit.android.entity.UploadResponse
import com.imagekit.android.retrofit.NetworkManager
import com.imagekit.android.util.LogUtil
import com.imagekit.android.util.SharedPrefUtil
import io.reactivex.Single
import retrofit2.HttpException
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.math.pow


class Repository @Inject constructor(
    private val context: Context,
    private val sharedPrefUtil: SharedPrefUtil
) {
    companion object {
        private const val DURATION_EXPIRY_MINUTES = 45L
    }

    @SuppressLint("CheckResult")
    fun upload(
        file: Any,
        token: String,
        fileName: String,
        useUniqueFilename: Boolean?,
        tags: Array<String>?,
        folder: String?,
        isPrivateFile: Boolean?,
        customCoordinates: String?,
        responseFields: String?,
        extensions: List<Map<String, Any>>?,
        webhookUrl: String?,
        overwriteFile: Boolean?,
        overwriteAITags: Boolean?,
        overwriteTags: Boolean?,
        overwriteCustomMetadata: Boolean?,
        customMetadata: Map<String, Any>?,
        uploadPolicy: UploadPolicy,
        imageKitCallback: ImageKitCallback,
        retryCount: Int = 0
    ) {
        Single.timer(getRetryTimeOut(uploadPolicy, retryCount), TimeUnit.MILLISECONDS).subscribe { _, _ ->
            NetworkManager.getFileUploadCall(
                token,
                file,
                fileName,
                useUniqueFilename,
                tags,
                folder,
                isPrivateFile,
                customCoordinates,
                responseFields,
                extensions,
                webhookUrl,
                overwriteFile,
                overwriteAITags,
                overwriteTags,
                overwriteCustomMetadata,
                customMetadata
            ).doOnError { e ->
                if(retryCount == uploadPolicy.maxErrorRetries) {
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
                    } else {
                        e.message?.let {
                            imageKitCallback.onError(UploadError(true, message = it))
                        } ?: run {
                            imageKitCallback.onError(UploadError(true))
                        }
                    }
                } else {
                    upload(
                        file,
                        token,
                        fileName,
                        useUniqueFilename,
                        tags,
                        folder,
                        isPrivateFile,
                        customCoordinates,
                        responseFields,
                        extensions,
                        webhookUrl,
                        overwriteFile,
                        overwriteAITags,
                        overwriteTags,
                        overwriteCustomMetadata,
                        customMetadata,
                        uploadPolicy,
                        imageKitCallback,
                        retryCount + 1
                    )
                }
            }.subscribe({ response ->
                imageKitCallback.onSuccess(
                    Gson().fromJson(response.string(), UploadResponse::class.java)
                )
            }, { e ->
                LogUtil.logError(e)
            })
        }
    }

    private fun getRetryTimeOut(policy: UploadPolicy, retryCount: Int): Long =
        if (policy.backoffPolicy == UploadPolicy.BackoffPolicy.LINEAR)
            policy.backoffMillis * retryCount
        else if (policy.backoffPolicy == UploadPolicy.BackoffPolicy.EXPONENTIAL && retryCount > 0)
            policy.backoffMillis * 2.0.pow(retryCount - 1).toLong()
        else 0L
}