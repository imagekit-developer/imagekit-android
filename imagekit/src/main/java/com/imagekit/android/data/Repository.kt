package com.imagekit.android.data

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import com.google.gson.Gson
import com.imagekit.android.ImageKitCallback
import com.imagekit.android.R
import com.imagekit.android.entity.SignatureResponse
import com.imagekit.android.entity.UploadError
import com.imagekit.android.entity.UploadResponse
import com.imagekit.android.retrofit.SignatureApi
import com.imagekit.android.retrofit.UploadApi
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
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
        useUniqueFilename: Boolean = true,
        tags: Array<String>?,
        folder: String?,
        isPrivateFile: Boolean? = null,
        customCoordinates: String? = null,
        responseFields: String? = null,
        signatureHeaders: Map<String, String>? = null,
        imageKitCallback: ImageKitCallback
    ) {

        val expire = ((System.currentTimeMillis() / 1000) + TimeUnit.MINUTES.toSeconds(
            DURATION_EXPIRY_MINUTES
        )).toString()
        val signatureObservable =
            signatureApi.getSignature(signatureHeaders, expire)?.toObservable()
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
                BiFunction<Response<SignatureResponse>, File, Response<ResponseBody>> { result: Response<SignatureResponse>, file: File ->
                    if (result.isSuccessful)
                        uploadApi.getFileUploadCall(
                            result.body()!!,
                            file,
                            fileName,
                            useUniqueFilename,
                            tags,
                            folder,
                            isPrivateFile,
                            customCoordinates,
                            responseFields,
                            expire
                        ).execute()
                    else {
                        val json = JSONObject()
                        json.put(
                            "message",
                            context.getString(R.string.error_signature_generation_failed)
                        )
                        Response.error(
                            400,
                            ResponseBody.create(
                                MediaType.parse("application/json"),
                                json.toString()
                            )
                        )
                    }
                })
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({ result ->
                    if (result.isSuccessful) {
                        imageKitCallback.onSuccess(
                            Gson().fromJson(
                                result.body()!!.string(),
                                UploadResponse::class.java
                            )
                        )
                    } else {
                        imageKitCallback.onError(
                            Gson().fromJson(
                                result.errorBody()!!.string(),
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
        signatureHeaders: Map<String, String>? = null,
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
        val signatureSingle = signatureApi.getSignature(signatureHeaders, expire)
        if (signatureSingle != null) {
            signatureSingle
                .subscribeOn(Schedulers.io())
                .flatMap { result ->
                    Single.just(
                        if (result.isSuccessful)
                            uploadApi.getFileUploadCall(
                                result.body()!!,
                                file,
                                fileName,
                                useUniqueFilename,
                                tags,
                                folder,
                                isPrivateFile,
                                customCoordinates,
                                responseFields,
                                expire
                            ).execute()
                        else {
                            val json = JSONObject()
                            json.put(
                                "message",
                                context.getString(R.string.error_signature_generation_failed)
                            )
                            Response.error(
                                400,
                                ResponseBody.create(
                                    MediaType.parse("application/json"),
                                    json.toString()
                                )
                            )
                        }
                    )
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result ->
                    if (result.isSuccessful) {
                        imageKitCallback.onSuccess(
                            Gson().fromJson(
                                result.body()!!.string(),
                                UploadResponse::class.java
                            )
                        )
                    } else {
                        imageKitCallback.onError(
                            Gson().fromJson(
                                result.errorBody()!!.string(),
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
        isPrivateFile: Boolean = false,
        customCoordinates: String? = null,
        responseFields: String? = null,
        signatureHeaders: Map<String, String>? = null,
        imageKitCallback: ImageKitCallback
    ) {
        val expire = ((System.currentTimeMillis() / 1000) + TimeUnit.MINUTES.toSeconds(
            DURATION_EXPIRY_MINUTES
        )).toString()
        val signatureSingle = signatureApi.getSignature(signatureHeaders, expire)
        if (signatureSingle != null) {
            signatureSingle
                .subscribeOn(Schedulers.io())
                .flatMap { result ->
                    Single.just(
                        if (result.isSuccessful)
                            uploadApi.getFileUploadCall(
                                result.body()!!,
                                fileUrl,
                                fileName,
                                useUniqueFilename,
                                tags,
                                folder,
                                isPrivateFile,
                                customCoordinates,
                                responseFields,
                                expire
                            ).execute()
                        else {
                            val json = JSONObject()
                            json.put(
                                "message",
                                context.getString(R.string.error_signature_generation_failed)
                            )
                            Response.error(
                                400,
                                ResponseBody.create(
                                    MediaType.parse("application/json"),
                                    json.toString()
                                )
                            )
                        }
                    )
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result ->
                    if (result.isSuccessful) {
                        imageKitCallback.onSuccess(
                            Gson().fromJson(
                                result.body()!!.string(),
                                UploadResponse::class.java
                            )
                        )
                    } else {
                        imageKitCallback.onError(
                            Gson().fromJson(
                                result.errorBody()!!.string(),
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
}