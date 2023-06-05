package com.imagekit.android.preprocess

sealed class UploadPreprocessor {
    override fun equals(other: Any?): Boolean {
        return this === other
    }

    override fun hashCode(): Int {
        return System.identityHashCode(this)
    }

    abstract fun executeProcess(src: Any)
}

class ImageUploadPreprocessor(
) : UploadPreprocessor() {
    class Builder {

    }

    override fun executeProcess(src: Any) {
        TODO("Not yet implemented")
    }
}