package com.imagekit.android.retrofit

import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.http.*

interface ApiInterface {
    @Multipart
    @POST("api/v2/files/upload")
    fun uploadImage(
        @Part file: MultipartBody.Part?,
        @Part token: MultipartBody.Part?,
        @Part fileName: MultipartBody.Part?,
        @Part useUniqueFileName: MultipartBody.Part?,
        @Part tags: MultipartBody.Part?,
        @Part folder: MultipartBody.Part?,
        @Part isPrivateFile: MultipartBody.Part?,
        @Part customCoordinates: MultipartBody.Part?,
        @Part responseFields: MultipartBody.Part?,
        @Part extensions: MultipartBody.Part?,
        @Part webhookUrl: MultipartBody.Part?,
        @Part overwriteFile: MultipartBody.Part?,
        @Part overwriteAITags: MultipartBody.Part?,
        @Part overwriteTags: MultipartBody.Part?,
        @Part overwriteCustomMetadata: MultipartBody.Part?,
        @Part customMetadata: MultipartBody.Part?,
    ): Single<ResponseBody>

}