package com.imagekit.android.retrofit;

import android.util.Log;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by Jaison on 01/03/16.
 */
public class NetworkManager {

    private static final String TAG = "Application Handler";

    private static ApiInterface apiInterface;

    public static void initialize() {
        createRetrofitObject();
    }

    private static void createRetrofitObject()
    {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();


        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getBaseURL())
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        apiInterface = retrofit.create(ApiInterface.class);
    }

    private static String getBaseURL() {
        return "https://api.imagekit.io/v1/files/upload/";
    }

    public static ApiInterface getApiInterface()
    {
        if (apiInterface == null)
        {
            Log.i(TAG, "Api interface is null");
            createRetrofitObject();
        }
        return apiInterface;
    }
}
