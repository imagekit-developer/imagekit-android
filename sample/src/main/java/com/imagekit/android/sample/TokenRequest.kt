package com.imagekit.android.sample

data class TokenRequest(
    private val uploadPayload: Map<String, Any>,
    private val expire: Int,
    private val publicKey: String
)
