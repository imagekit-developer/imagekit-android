package com.imagekit.android.retrofit

import android.content.Context
import com.imagekit.android.R
import com.imagekit.android.entity.SignatureResponse
import com.imagekit.android.util.LogUtil
import com.imagekit.android.util.SharedPrefUtil
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SignatureApi @Inject constructor(
    private val context: Context,
    private val sharedPrefUtil: SharedPrefUtil
) {

    fun getSignature(
        headerMap: Map<String, String>? = null,
        expire: String
    ): Single<SignatureResponse>? {
        val endPoint = sharedPrefUtil.getClientAuthenticationEndpoint()
        if (endPoint.isBlank()) {
            LogUtil.logError(context.getString(R.string.error_authentication_endpoint_is_missing))
            return null
        }

        return if (headerMap != null)
            NetworkManager
                .getApiInterface()
                .getSignature(endPoint, headerMap, expire)
        else
            NetworkManager
                .getApiInterface()
                .getSignature(endPoint, expire)
    }
}