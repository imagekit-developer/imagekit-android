package com.imagekit.android.entity

enum class ImagekitResponseCode(val responseCode: Int) {
    INVALID_FORM_PARAM(400),
    INVALID_SIGNATURE(403),
    INVALID_IMAGEKIT_ID(404),
    SERVER_ERROR(500);

    companion object {
        private val map = values().associateBy(ImagekitResponseCode::responseCode)
        fun fromInt(type: Int) = map[type]
    }
}