package com.imagekit.android.util

import android.content.Context
import android.content.SharedPreferences
import com.imagekit.android.entity.TransformationPosition
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPrefUtil @Inject constructor(context: Context) {
    //Shared Preference file name
    private val SHARED_PREF_FILENAME = "SharedPref File"

    //Shared Preference Keys
    private val KEY_CLIENT_PUBLIC_KEY = "Client Public Key"
    private val KEY_IMAGEKIT_URL_ENDPOINT_KEY = "ImageKit Url Endpoint"
    private val KEY_TRANSFORMATION_POSITION_KEY = "Transformation Position Endpoint"
    private val KEY_CLIENT_AUTHENTICATION_ENDPOINT_KEY = "Client Authentication Endpoint"

    private var mPref: SharedPreferences

    init {
        mPref = context.getSharedPreferences(SHARED_PREF_FILENAME, Context.MODE_PRIVATE)
    }

    fun setClientPublicKey(clientPublicKey: String) =
        mPref.edit().putString(KEY_CLIENT_PUBLIC_KEY, clientPublicKey).apply()

    fun getClientPublicKey() = mPref.getString(KEY_CLIENT_PUBLIC_KEY, "")

    fun setImageKitUrlEndpoint(imageKitEndpoint: String) =
        mPref.edit().putString(KEY_IMAGEKIT_URL_ENDPOINT_KEY, imageKitEndpoint).apply()

    fun getImageKitUrlEndpoint() = mPref.getString(KEY_IMAGEKIT_URL_ENDPOINT_KEY, "")!!

    fun setTransformationPosition(transformationPosition: TransformationPosition) =
        mPref.edit().putString(KEY_TRANSFORMATION_POSITION_KEY, transformationPosition.name).apply()

    fun getTransformationPosition() = TransformationPosition.valueOf(
        mPref.getString(
            KEY_TRANSFORMATION_POSITION_KEY,
            TransformationPosition.PATH.name
        )!!
    )

    fun setClientAuthenticationEndpoint(clientAuthenticationEndpoint: String?) =
        mPref.edit().putString(
            KEY_CLIENT_AUTHENTICATION_ENDPOINT_KEY,
            clientAuthenticationEndpoint
        ).apply()

    fun getClientAuthenticationEndpoint() =
        mPref.getString(KEY_CLIENT_AUTHENTICATION_ENDPOINT_KEY, "")!!

}