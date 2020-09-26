package com.imagekit.android.entity

data class UploadResponse(
    val fileId: String,
    val name: String,
    val url: String,
    val thumbnail: String,
    val height: Int,
    val width: Int,
    val size: Int,
    val fileType: String,
    val filePath: String,
    val tags: Array<String>,
    val isPrivateFile: Boolean,
    val customCoordinates: String,
    val metadata: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UploadResponse

        if (fileId != other.fileId) return false
        if (name != other.name) return false
        if (url != other.url) return false
        if (thumbnail != other.thumbnail) return false
        if (height != other.height) return false
        if (width != other.width) return false
        if (size != other.size) return false
        if (fileType != other.fileType) return false
        if (filePath != other.filePath) return false
        if (!tags.equals(other.tags)) return false
        if (isPrivateFile != other.isPrivateFile) return false
        if (customCoordinates != other.customCoordinates) return false
        if (metadata != other.metadata) return false

        return true
    }

    override fun hashCode(): Int {
        var result = fileId.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + url.hashCode()
        result = 31 * result + thumbnail.hashCode()
        result = 31 * result + height
        result = 31 * result + width
        result = 31 * result + size
        result = 31 * result + fileType.hashCode()
        result = 31 * result + filePath.hashCode()
        result = 31 * result + tags.hashCode()
        result = 31 * result + isPrivateFile.hashCode()
        result = 31 * result + customCoordinates.hashCode()
        result = 31 * result + metadata.hashCode()
        return result
    }
}