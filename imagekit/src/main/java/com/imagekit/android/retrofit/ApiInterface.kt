package com.imagekit.android.retrofit

import com.imagekit.android.entity.SignatureResponse
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.http.*

interface ApiInterface {
    @Multipart
    @POST("/v1/files/upload")
    fun uploadImage(
        @Part file: MultipartBody.Part?,
        @Part publicKey: MultipartBody.Part?,
        @Part signature: MultipartBody.Part?,
        @Part expire: MultipartBody.Part?,
        @Part token: MultipartBody.Part?,
        @Part fileName: MultipartBody.Part?,
        @Part useUniqueFileName: MultipartBody.Part?,
        @Part tags: MultipartBody.Part?,
        @Part folder: MultipartBody.Part?,
        @Part isPrivateFile: MultipartBody.Part?,
        @Part customCoordinates: MultipartBody.Part?,
        @Part responseFields: MultipartBody.Part?
    ): Single<ResponseBody>

    @GET
    fun getSignature(
        @Url url: String,
        @Query("expire") expire: String
    ): Single<SignatureResponse>
}