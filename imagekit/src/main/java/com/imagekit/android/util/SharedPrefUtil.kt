package com.imagekit.android.util

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class SharedPrefUtil @Inject constructor(context: Context) {
    //Shared Preference file name
    private val SHARED_PREF_FILENAME = "SharedPref File"

    //Shared Preference Keys
    private val KEY_CLIENT_PUBLIC_KEY = "Client Public Key"
    private val KEY_IMAGEKIT_ID_KEY = "ImageKit Id"
    private val KEY_IMAGEKIT_ENDPOINT_KEY = "ImageKit Endpoint"

    private var mPref: SharedPreferences

    init {
        mPref = context.getSharedPreferences(SHARED_PREF_FILENAME, Context.MODE_PRIVATE)
    }

    fun setClientPublicKey(clientPublicKey: String) = mPref.edit().putString(KEY_CLIENT_PUBLIC_KEY, clientPublicKey).apply()
    fun getClientPublicKey() = mPref.getString(KEY_CLIENT_PUBLIC_KEY, null)

    fun setImageKitId(imageKitId: String) = mPref.edit().putString(KEY_IMAGEKIT_ID_KEY, imageKitId).apply()
    fun getImageKitId() = mPref.getString(KEY_IMAGEKIT_ID_KEY, null)

    fun setImageKitEndpoint(imageKitEndpoint: String) = mPref.edit().putString(KEY_IMAGEKIT_ENDPOINT_KEY, imageKitEndpoint).apply()
    fun getImageKitEndpoint() = mPref.getString(KEY_IMAGEKIT_ENDPOINT_KEY, "")!!

    fun clear() = mPref.edit().clear()
}