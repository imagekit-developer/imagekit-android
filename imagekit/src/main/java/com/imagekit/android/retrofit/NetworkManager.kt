package com.imagekit.android.retrofit

import android.content.pm.ApplicationInfo
import android.util.Log
import com.google.gson.Gson
import com.imagekit.android.ImageKit
import com.imagekit.android.entity.SignatureResponse
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

internal object NetworkManager {
    private const val TAG = "Application Handler"
    private var apiInterface: ApiInterface? = null
    fun initialize() {
//        createRetrofitObject()
    }

    private fun createRetrofitObject() {
        val okHttpClient = OkHttpClient.Builder()
            .readTimeout(0, TimeUnit.SECONDS)
            .apply {
                if (ImageKit.getInstance().context.applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0) {
                    addInterceptor(HttpLoggingInterceptor()
                        .setLevel(HttpLoggingInterceptor.Level.BODY)
                    )
                }
            }
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        apiInterface = retrofit.create(ApiInterface::class.java)
    }

    private var baseURL: String = "https://upload.imagekit.io/"

    fun getApiInterface(): ApiInterface {
        if (apiInterface == null) {
            Log.i(TAG, "Api interface is null")
            createRetrofitObject()
        }
        return apiInterface!!
    }

    fun getSignature(
        endPoint: String,
        expire: String
    ): Single<SignatureResponse> {
        return getApiInterface()
            .getSignature(endPoint, expire)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getFileUploadCall(
        token: String,
        file: Any,
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
    ): Single<ResponseBody> {
        val commaSeparatedTags = getCommaSeparatedTagsFromTags(tags)

        val profileImagePart: MultipartBody.Part = if (file is File) {
            val fileBody = RequestBody.create("image/png".toMediaTypeOrNull(), file.readBytes())
            // MultipartBody.Part is used to send also the actual file name
            MultipartBody.Part.createFormData(
                "file",
                fileName,
                fileBody
            )
        } else {
            MultipartBody.Part.createFormData("file", file as String)
        }

        return getApiInterface()
            .uploadImage(
                profileImagePart,
                MultipartBody.Part.createFormData("token", token),
                MultipartBody.Part.createFormData("fileName", fileName),
                if (useUniqueFilename != null) MultipartBody.Part.createFormData(
                    "useUniqueFileName",
                    useUniqueFilename.toString()
                ) else null,
                if (commaSeparatedTags != null) MultipartBody.Part.createFormData(
                    "tags",
                    commaSeparatedTags
                ) else null,
                if (folder != null) MultipartBody.Part.createFormData(
                    "folder",
                    folder
                ) else null,
                if (isPrivateFile != null) MultipartBody.Part.createFormData(
                    "isPrivateFile",
                    isPrivateFile.toString()
                ) else null,
                if (customCoordinates != null) MultipartBody.Part.createFormData(
                    "customCoordinates",
                    customCoordinates
                ) else null,
                if (responseFields != null) MultipartBody.Part.createFormData(
                    "responseFields",
                    responseFields
                ) else null,
                if (extensions != null) MultipartBody.Part.createFormData(
                    "extensions",
                    Gson().toJson(extensions)
                ) else null,
                if (webhookUrl != null) MultipartBody.Part.createFormData(
                    "webhookUrl",
                    webhookUrl
                ) else null,
                if (overwriteFile != null) MultipartBody.Part.createFormData(
                    "overwriteFile",
                    overwriteFile.toString()
                ) else null,
                if (overwriteAITags != null) MultipartBody.Part.createFormData(
                    "overwriteAITags",
                    overwriteAITags.toString()
                ) else null,
                if (overwriteTags != null) MultipartBody.Part.createFormData(
                    "overwriteTags",
                    overwriteTags.toString()
                ) else null,
                if (overwriteCustomMetadata != null) MultipartBody.Part.createFormData(
                    "overwriteCustomMetadata",
                    overwriteCustomMetadata.toString()
                ) else null,
                if (customMetadata != null) MultipartBody.Part.createFormData(
                    "customMetadata",
                    Gson().toJson(customMetadata)
                ) else null,

            )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    }

    private fun getCommaSeparatedTagsFromTags(tags: Array<String>?): String? {
        return tags?.joinToString(",")
    }

    fun setBaseUrl(url: String) {
        baseURL = url
    }
}