package com.imagekit.android.retrofit

import android.content.Context
import com.imagekit.android.R
import com.imagekit.android.SignatureUtil
import com.imagekit.android.entity.SignatureResponse
import com.imagekit.android.util.LogUtil
import com.imagekit.android.util.SharedPrefUtil
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SignatureApi @Inject constructor(private val context: Context, private val sharedPrefUtil: SharedPrefUtil){

    fun getSignature(
        headerMap: Map<String, String>? = null,
        expire: String
    ): Single<Response<SignatureResponse>>? {
        val endPoint = sharedPrefUtil.getClientAuthenticationEndpoint()
        if (endPoint.isBlank()) {
            LogUtil.logError(context.getString(R.string.error_authentication_endpoint_is_missing))
            return null
        }

        val token =
            "apiKey=CLIENT_PUBLIC_KEY&filename=filename&timestamp=${System.currentTimeMillis()}"

        val json = JSONObject()
        json.put("token", token)
        json.put("signature", SignatureUtil.sign(token, expire))

        return Single.just(
            NetworkManager
                .getApiInterface()
                .getSignature(endPoint, headerMap, token, expire)
                .execute()

//                    httpResponse
        ).subscribeOn(Schedulers.io())
    }
}