package com.imagekit.android.retrofit

import com.imagekit.android.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class BuildVersionQueryInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val originalHttpUrl = original.url()
        val url = originalHttpUrl.newBuilder()
            .addQueryParameter("sdk-version", "android-" + BuildConfig.API_VERSION)
            .build()

        // Request customization: add request headers
        val requestBuilder = original.newBuilder().url(url)
        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}