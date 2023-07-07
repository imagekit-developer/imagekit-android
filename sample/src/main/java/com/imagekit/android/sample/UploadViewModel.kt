package com.imagekit.android.sample

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.Exception

class UploadViewModel : ViewModel() {
    private val service: UploadAuthService = Retrofit.Builder()
        .baseUrl("YOUR_AUTH_SERVER")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(UploadAuthService::class.java)

    suspend fun getUploadToken(payload: Map<String, Any>): Map<String, String>? =
        withContext(context = viewModelScope.coroutineContext + Dispatchers.IO) {
            try {
                service.generateUploadAuthToken(TokenRequest(
                    uploadPayload = payload,
                    expire = 60,
                    publicKey = "YOUR_PUBLIC_KEY"
                ))
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }

}