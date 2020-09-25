package com.imagekit.android.retrofit;

import com.imagekit.android.entity.SignatureResponse;

import java.util.Map;

import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ApiInterface {
    @Multipart
    @POST("/v1/files/upload")
    Single<ResponseBody> uploadImage(
            @Part MultipartBody.Part file
            , @Part MultipartBody.Part publicKey
            , @Part MultipartBody.Part signature
            , @Part MultipartBody.Part expire
            , @Part MultipartBody.Part token
            , @Part MultipartBody.Part fileName
            , @Part MultipartBody.Part useUniqueFileName
            , @Part MultipartBody.Part tags
            , @Part MultipartBody.Part folder
            , @Part MultipartBody.Part isPrivateFile
            , @Part MultipartBody.Part customCoordinates
            , @Part MultipartBody.Part responseFields
    );

    @GET()
    Single<SignatureResponse> getSignature(
            @Url String url,
            @HeaderMap Map<String, String> headerMap,
            @Query("expire") String expire
    );

    @GET()
    Single<SignatureResponse> getSignature(
            @Url String url,
            @Query("expire") String expire
    );
}
