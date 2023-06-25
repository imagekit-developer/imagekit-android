package com.imagekit.android.retrofit

import android.util.Log
import com.imagekit.android.entity.SignatureResponse
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.time.Duration
import java.util.concurrent.TimeUnit

object NetworkManager {
    private const val TAG = "Application Handler"
    private var apiInterface: ApiInterface? = null
    fun initialize() {
        createRetrofitObject()
    }

    private fun createRetrofitObject() {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient.Builder()
            .readTimeout(20, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .addInterceptor(BuildVersionQueryInterceptor())
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        apiInterface = retrofit.create(ApiInterface::class.java)
    }

    val baseURL: String
        get() = "https://api.imagekit.io/"

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
        publicKey: String,
        signatureResponse: SignatureResponse,
        file: Any,
        fileName: String,
        useUniqueFilename: Boolean,
        tags: Array<String>?,
        folder: String?,
        isPrivateFile: Boolean?,
        customCoordinates: String?,
        responseFields: String?
    ): Single<ResponseBody> {
        val commaSeparatedTags = getCommaSeparatedTagsFromTags(tags)

        val profileImagePart: MultipartBody.Part

        profileImagePart = if (file is File) {
            val fileBody = RequestBody.create(MediaType.parse("image/png"), file.readBytes())
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
                MultipartBody.Part.createFormData(
                    "publicKey",
                    publicKey
                ),
                MultipartBody.Part.createFormData("signature", signatureResponse.signature),
                MultipartBody.Part.createFormData(
                    "expire",
                    signatureResponse.expire.toString()
                ),
                MultipartBody.Part.createFormData("token", signatureResponse.token),
                MultipartBody.Part.createFormData("fileName", fileName),
                MultipartBody.Part.createFormData(
                    "useUniqueFileName",
                    useUniqueFilename.toString()
                ),
                if (commaSeparatedTags != null) MultipartBody.Part.createFormData(
                    "tags",
                    commaSeparatedTags
                ) else null,
                if (folder != null) MultipartBody.Part.createFormData(
                    "folder",
                    folder
                ) else null,
                MultipartBody.Part.createFormData("isPrivateFile", isPrivateFile.toString()),
                if (customCoordinates != null) MultipartBody.Part.createFormData(
                    "customCoordinates",
                    customCoordinates
                ) else null,
                if (responseFields != null) MultipartBody.Part.createFormData(
                    "responseFields",
                    responseFields
                ) else null
            )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    }

    private fun getCommaSeparatedTagsFromTags(tags: Array<String>?): String? {
        return tags?.joinToString { "\'$it\'" }
    }
}