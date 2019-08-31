package com.imagekit.android.data

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import com.google.gson.Gson
import com.imagekit.android.ImageKitCallback
import com.imagekit.android.R
import com.imagekit.android.SignatureUtil
import com.imagekit.android.entity.SignatureResponse
import com.imagekit.android.entity.UploadError
import com.imagekit.android.entity.UploadResponse
import com.imagekit.android.util.LogUtil
import com.imagekit.android.util.SharedPrefUtil
import com.mashape.unirest.http.HttpResponse
import com.mashape.unirest.http.Unirest
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import local.org.apache.http.HttpStatus
import local.org.apache.http.HttpVersion
import local.org.apache.http.entity.StringEntity
import local.org.apache.http.impl.DefaultHttpResponseFactory
import local.org.apache.http.message.BasicStatusLine
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class Repository @Inject constructor(
    private val context: Context,
    private val sharedPrefUtil: SharedPrefUtil
) {

    companion object {
        private const val DURATION_EXPIRY_MINUTES = 45L
        private const val UPLOAD_URL = "https://api.imagekit.io/v1/files/upload"
    }

    // Takes Bitmap
    @SuppressLint("CheckResult")
    fun upload(
        image: Bitmap,
        fileName: String,
        useUniqueFilename: Boolean = true,
        tags: Array<String>?,
        folder: String?,
        isPrivateFile: Boolean? = null,
        customCoordinates: String? = null,
        responseFields: String? = null,
        imageKitCallback: ImageKitCallback
    ) {
        fun performActualUpload(result: HttpResponse<String>, file: File): HttpResponse<String> {
            return Unirest.post(UPLOAD_URL)
                .header("accept", "application/json")
                .field("file", file)
                .field("publicKey", sharedPrefUtil.getClientPublicKey())
                .field(
                    "signature", Gson().fromJson(
                        result.body,
                        SignatureResponse::class.java
                    ).signature
                )
                .field(
                    "expire",
                    (System.currentTimeMillis() / 1000) + TimeUnit.MINUTES.toSeconds(
                        DURATION_EXPIRY_MINUTES
                    )
                )
                .field(
                    "token", Gson().fromJson(
                        result.body,
                        SignatureResponse::class.java
                    ).token
                )
                .field("fileName", fileName)
                .field("useUniqueFilename", useUniqueFilename)
                .field("tags", getCommaSeparatedTagsFromTags(tags))
                .field("folder", folder)
                .field("isPrivateFile", isPrivateFile)
                .field("customCoordinates", customCoordinates)
                .field("responseFields", responseFields)
                .asString()
        }

        val signatureObservable = getSignature()?.toObservable()
        if (signatureObservable != null) {
            Observable.zip(
                signatureObservable,
                Single.just(
                    bitmapToFile(
                        context,
                        fileName,
                        image
                    )
                ).subscribeOn(Schedulers.io()).toObservable(),
                BiFunction<HttpResponse<String>, File, HttpResponse<String>> { result: HttpResponse<String>, file: File ->
                    performActualUpload(
                        result,
                        file
                    )
                })
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({ result ->
                    when (result.code) {
                        200 -> imageKitCallback.onSuccess(
                            Gson().fromJson(
                                result.body,
                                UploadResponse::class.java
                            )
                        )
                        else -> imageKitCallback.onError(
                            Gson().fromJson(
                                result.body,
                                UploadError::class.java
                            )
                        )
                    }
                }, { imageKitCallback.onError(UploadError(true)) })
        } else
            imageKitCallback.onError(
                UploadError(
                    exception = true,
                    message = context.getString(R.string.error_signature_generation_failed)
                )
            )
    }

    //Takes file
    @SuppressLint("CheckResult")
    fun upload(
        file: File,
        fileName: String,
        useUniqueFilename: Boolean = true,
        tags: Array<String>?,
        folder: String?,
        isPrivateFile: Boolean? = null,
        customCoordinates: String? = null,
        responseFields: String? = null,
        imageKitCallback: ImageKitCallback
    ) {
        if (file.extension.isEmpty()) {
            imageKitCallback.onError(
                UploadError(
                    false,
                    1400,
                    context.getString(R.string.error_invalid_file_format)
                )
            )
            return
        }

        val signatureSingle = getSignature()
        if (signatureSingle != null) {
            signatureSingle
                .subscribeOn(Schedulers.io())
                .flatMap { result ->
                    Single.just(
                        Unirest.post(UPLOAD_URL)
                            .header("accept", "application/json")
                            .field("file", file)
                            .field("publicKey", sharedPrefUtil.getClientPublicKey())
                            .field(
                                "signature", Gson().fromJson(
                                    result.body,
                                    SignatureResponse::class.java
                                ).signature
                            )
                            .field(
                                "expire",
                                System.currentTimeMillis() + TimeUnit.MINUTES.toSeconds(
                                    DURATION_EXPIRY_MINUTES
                                )
                            )
                            .field(
                                "token", Gson().fromJson(
                                    result.body,
                                    SignatureResponse::class.java
                                ).token
                            )
                            .field("fileName", fileName)
                            .field("useUniqueFilename", useUniqueFilename)
                            .field("tags", getCommaSeparatedTagsFromTags(tags))
                            .field("folder", folder)
                            .field("isPrivateFile", isPrivateFile)
                            .field("customCoordinates", customCoordinates)
                            .field("responseFields", responseFields)
                            .asString()
                    )
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result ->
                    when (result.code) {
                        200 -> imageKitCallback.onSuccess(
                            Gson().fromJson(
                                result.body,
                                UploadResponse::class.java
                            )
                        )
                        else -> imageKitCallback.onError(
                            Gson().fromJson(
                                result.body,
                                UploadError::class.java
                            )
                        )
                    }
                }, { imageKitCallback.onError(UploadError(true)) })
        } else
            imageKitCallback.onError(
                UploadError(
                    exception = true,
                    message = context.getString(R.string.error_signature_generation_failed)
                )
            )
    }

    // Takes Url
    @SuppressLint("CheckResult")
    fun upload(
        fileUrl: String,
        fileName: String,
        useUniqueFilename: Boolean = true,
        tags: Array<String>?,
        folder: String?,
        isPrivateFile: Boolean? = null,
        customCoordinates: String? = null,
        responseFields: String? = null,
        imageKitCallback: ImageKitCallback
    ) {
        val signatureSingle = getSignature()
        if (signatureSingle != null) {
            signatureSingle
                .subscribeOn(Schedulers.io())
                .flatMap { result ->
                    Single.just(
                        Unirest.post(UPLOAD_URL)
                            .header("accept", "application/json")
                            .field("file", fileUrl)
                            .field("publicKey", sharedPrefUtil.getClientPublicKey())
                            .field(
                                "signature", Gson().fromJson(
                                    result.body,
                                    SignatureResponse::class.java
                                ).signature
                            )
                            .field(
                                "expire",
                                System.currentTimeMillis() + TimeUnit.MINUTES.toSeconds(
                                    DURATION_EXPIRY_MINUTES
                                )
                            )
                            .field(
                                "token", Gson().fromJson(
                                    result.body,
                                    SignatureResponse::class.java
                                ).token
                            )
                            .field("fileName", fileName)
                            .field("useUniqueFilename", useUniqueFilename)
                            .field("tags", getCommaSeparatedTagsFromTags(tags))
                            .field("folder", folder)
                            .field("isPrivateFile", isPrivateFile)
                            .field("customCoordinates", customCoordinates)
                            .field("responseFields", responseFields)
                            .asString()
                    )
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result ->
                    when (result.code) {
                        200 -> imageKitCallback.onSuccess(
                            Gson().fromJson(
                                result.body,
                                UploadResponse::class.java
                            )
                        )
                        else -> imageKitCallback.onError(
                            Gson().fromJson(
                                result.body,
                                UploadError::class.java
                            )
                        )
                    }
                }, { imageKitCallback.onError(UploadError(true)) })
        } else
            imageKitCallback.onError(
                UploadError(
                    exception = true,
                    message = context.getString(R.string.error_signature_generation_failed)
                )
            )
    }

    private fun getCommaSeparatedTagsFromTags(tags: Array<String>?): String? {
        return tags?.joinToString { "\'$it\'" }
    }

    @Throws(IOException::class)
    private fun bitmapToFile(context: Context, filename: String, bitmap: Bitmap): File {
        val f = File(context.cacheDir, filename)
        f.createNewFile()

        //Convert bitmap to byte array
        val bos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos)
        val bitmapdata = bos.toByteArray()

        //write the bytes in file
        val fos = FileOutputStream(f)
        fos.write(bitmapdata)
        fos.flush()
        fos.close()

        return f
    }

    private fun getSignature(headerMap: Map<String, String>? = null): Single<HttpResponse<String>>? {
        val endPoint = sharedPrefUtil.getClientAuthenticationEndpoint()
        if (endPoint.isBlank()) {
            LogUtil.logError(context.getString(R.string.error_authentication_endpoint_is_missing))
            return null
        }

        val token =
            "apiKey=CLIENT_PUBLIC_KEY&filename=filename&timestamp=${System.currentTimeMillis()}"

        val factory = DefaultHttpResponseFactory()
        val response = factory.newHttpResponse(
            BasicStatusLine(HttpVersion.HTTP_1_1, HttpStatus.SC_OK, null),
            null
        )
        val json = JSONObject()
        json.put("token", token)
        json.put("signature", SignatureUtil.sign(token))
        response.entity = StringEntity(json.toString())
        val httpResponse = HttpResponse<String>(response, String::class.java)

        return Single.just(
//            Unirest.get(endPoint)
//                .headers(headerMap)
//                .asString()

            httpResponse
        ).subscribeOn(Schedulers.io())
    }
}