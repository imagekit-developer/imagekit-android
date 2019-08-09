package com.imagekit.android.data

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import com.google.gson.Gson
import com.imagekit.android.ImageKitCallback
import com.imagekit.android.R
import com.imagekit.android.entity.FileExtension
import com.imagekit.android.entity.UploadError
import com.imagekit.android.entity.UploadResponse
import com.imagekit.android.util.SharedPrefUtil
import com.mashape.unirest.http.Unirest
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Inject


class Repository @Inject constructor(private val context: Context, private val sharedPrefUtil: SharedPrefUtil) {

    // Takes Bitmap
    @SuppressLint("CheckResult")
    fun upload(
        fileName: String,
        signature: String,
        timestamp: Long,
        useUniqueFilename: Boolean = true,
        tags: Array<String>?,
        folder: String?,
        image: Bitmap,
        imageKitCallback: ImageKitCallback
    ) {
        val uploadUrl = "https://upload.imagekit.io/rest/api/image/v2/${sharedPrefUtil.getImageKitId()}"

        var commaSeparatedTags: String? = null
        if (tags != null)
            commaSeparatedTags = tags.joinToString { "\'$it\'" }

        Single.just(bitmapToFile(context, fileName, image))
            .subscribeOn(Schedulers.io())
            .map { file ->
                Unirest.post(uploadUrl)
                    .header("accept", "application/json")
                    .field("file", file)
                    .field("filename", file.name)
                    .field("apiKey", sharedPrefUtil.getClientPublicKey())
                    .field("signature", signature)
                    .field("timestamp", timestamp.toString())
                    .field("useUniqueFilename", useUniqueFilename)
                    .field("tags", commaSeparatedTags)
                    .field("folder", folder)
                    .asString()
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                when (result.code) {
                    200 -> imageKitCallback.onSuccess(Gson().fromJson(result.body, UploadResponse::class.java))
                    else -> imageKitCallback.onError(Gson().fromJson(result.body, UploadError::class.java))
                }
            }, { imageKitCallback.onError(UploadError(true)) })
    }

    @SuppressLint("CheckResult")
    fun upload(
        fileName: String,
        signature: String,
        timestamp: Long,
        useUniqueFilename: Boolean = true,
        tags: Array<String>?,
        folder: String?,
        file: File,
        imageKitCallback: ImageKitCallback
    ) {

        if (file.extension.isEmpty()) {
            imageKitCallback.onError(UploadError(false, 1400, context.getString(R.string.error_invalid_file_format)))
            return
        }

        val uploadUrl: String
        try {
            uploadUrl = when (FileExtension.valueOf(file.extension.toLowerCase())) {
                FileExtension.PNG,
                FileExtension.JPG,
                FileExtension.JPEG,
                FileExtension.WEBP,
                FileExtension.GIF -> "https://upload.imagekit.io/rest/api/image/v2/${sharedPrefUtil.getImageKitId()}"
                FileExtension.PDF,
                FileExtension.JS,
                FileExtension.CSS,
                FileExtension.TXT -> "https://upload.imagekit.io/rest/api/static/v2/${sharedPrefUtil.getImageKitId()}"
            }
        } catch (exception: IllegalStateException) {
            imageKitCallback.onError(UploadError(false, 1400, context.getString(R.string.error_invalid_file_format)))
            return
        }

        var commaSeparatedTags: String? = null
        if (tags != null)
            commaSeparatedTags = tags.joinToString { "\'$it\'" }

        Single.just(
            Unirest.post(uploadUrl)
                .header("accept", "application/json")
                .field("file", file)
                .field("filename", fileName)
                .field("apiKey", sharedPrefUtil.getClientPublicKey())
                .field("signature", signature)
                .field("timestamp", timestamp.toString())
                .field("useUniqueFilename", useUniqueFilename)
                .field("tags", commaSeparatedTags)
                .field("folder", folder)
                .asString()
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                when (result.code) {
                    200 -> imageKitCallback.onSuccess(Gson().fromJson(result.body, UploadResponse::class.java))
                    else -> imageKitCallback.onError(Gson().fromJson(result.body, UploadError::class.java))
                }
            }, { imageKitCallback.onError(UploadError(true)) })
    }

    @Throws(IOException::class)
    private fun bitmapToFile(context: Context, filename: String, bitmap: Bitmap): File {
        val f = File(context.cacheDir, filename)
        f.createNewFile()

        //Convert bitmap to byte array
        val bos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos)
        val bitmapdata = bos.toByteArray()

        //write the bytes in file
        val fos = FileOutputStream(f)
        fos.write(bitmapdata)
        fos.flush()
        fos.close()

        return f
    }
}