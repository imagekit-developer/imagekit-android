package com.imagekit.android.data

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import com.google.gson.Gson
import com.imagekit.android.ImageKitCallback
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

    private val uploadUrl: String = "https://upload.imagekit.io/rest/api/image/v2/${sharedPrefUtil.getImageKitId()}"

    // Takes Bitmap
    @SuppressLint("CheckResult")
    fun uploadImage(
        fileName: String,
        signature: String,
        timestamp: Long,
        useUniqueFilename: Boolean = true,
        tags: Array<String>?,
        folder: String?,
        image: Bitmap,
        imageKitCallback: ImageKitCallback
    ) {
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

    // Takes File
    @SuppressLint("CheckResult")
    fun uploadImage(
        fileName: String,
        signature: String,
        timestamp: Long,
        useUniqueFilename: Boolean = true,
        tags: Array<String>?,
        folder: String?,
        image: File,
        imageKitCallback: ImageKitCallback
    ) {
        var commaSeparatedTags: String? = null
        if (tags != null)
            commaSeparatedTags = tags.joinToString { "\'$it\'" }

        Single.just(
            Unirest.post(uploadUrl)
                .header("accept", "application/json")
                .field("file", image)
                .field("filename", image.name)
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

    // Takes File
    @SuppressLint("CheckResult")
    fun uploadFile(
        fileName: String,
        signature: String,
        timestamp: Long,
        useUniqueFilename: Boolean = true,
        tags: Array<String>?,
        folder: String?,
        file: File,
        imageKitCallback: ImageKitCallback
    ) {
        var commaSeparatedTags: String? = null
        if (tags != null)
            commaSeparatedTags = tags.joinToString { "\'$it\'" }

        Single.just(
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