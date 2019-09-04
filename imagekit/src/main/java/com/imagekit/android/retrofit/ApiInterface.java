package com.imagekit.android.retrofit;


import com.imagekit.android.entity.SignatureResponse;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by Jaison on 19/07/16.
 */
public interface ApiInterface {
    @Multipart
    @POST("https://api.imagekit.io/v1/files/upload")
    Call<ResponseBody> uploadImage(
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
    Call<SignatureResponse> getSignature(
            @Url String url,
            @HeaderMap Map<String, String> headerMap,
            @Query("token") String token,
            @Query("expire") String expire
    );

    @GET()
    Call<SignatureResponse> getSignature(
            @Url String url,
            @Query("token") String token,
            @Query("expire") String expire
    );
}
