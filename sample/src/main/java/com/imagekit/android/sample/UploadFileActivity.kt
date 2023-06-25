package com.imagekit.android.sample

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.imagekit.android.ImageKit
import com.imagekit.android.ImageKitCallback
import com.imagekit.android.entity.UploadError
import com.imagekit.android.entity.UploadResponse
import com.imagekit.android.preprocess.ImageUploadPreprocessor
import com.imagekit.android.preprocess.VideoUploadPreprocessor
import com.imagekit.android.sample.databinding.ActivityUploadFileBinding
import java.io.*
import java.util.UUID


class UploadFileActivity : AppCompatActivity(), ImageKitCallback, View.OnClickListener {
    private var uploadResultDialog: AlertDialog? = null
    private var loadingDialog: AlertDialog? = null

    private var file: File? = null

    override fun onClick(v: View?) {
        selectVideo()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        copyAssets()
        val binding = ActivityUploadFileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btUpload.setOnClickListener(this)

    }

    private fun selectVideo() {
        val photoPickerIntent = Intent(Intent.ACTION_PICK)
        photoPickerIntent.type = "video/*"
        startActivityForResult(photoPickerIntent, 1331)
    }

    private fun uploadImage() {
        file?.let {
            loadingDialog = AlertDialog.Builder(this)
                .setMessage("Uploading file...")
                .setCancelable(false)
                .show()

            ImageKit.getInstance().uploader().upload(
                file = file!!,
                fileName = file!!.name,
                useUniqueFilename = true,
                tags = arrayOf("nice", "copy", "books"),
                folder = "/dummy/folder/",
                preprocessor = VideoUploadPreprocessor.Builder()
                    .limit(200, 200)
                    .build(),
                imageKitCallback = this
            )
        }
    }

    override fun onError(uploadError: UploadError) {
        Log.d(MainActivity::class.simpleName, "ERROR")
        loadingDialog?.dismiss()
        uploadResultDialog = AlertDialog.Builder(this)
            .setTitle("Upload Failed")
            .setMessage("Error: ${uploadError.message}")
            .setNeutralButton("Ok") { _, _ ->
                // Do nothing
            }.show()
    }

    override fun onSuccess(uploadResponse: UploadResponse) {
        Log.d(MainActivity::class.simpleName, "SUCCESS")
        loadingDialog?.dismiss()

        uploadResultDialog = AlertDialog.Builder(this)
            .setTitle("Upload Complete")
            .setMessage("The uploaded file can be accessed using url: ${uploadResponse.url}")
            .setNeutralButton("Ok") { _, _ ->
                // Do nothing
            }.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        loadingDialog?.dismiss()
        uploadResultDialog?.dismiss()
    }

    private fun copyAssets() {
        val assetManager = assets
        val filename = "sample.pdf"

        var `in`: InputStream? = null
        var out: OutputStream? = null
        try {
            `in` = assetManager.open(filename)
            file = File(filesDir, filename)
            out = FileOutputStream(file!!)
            copyFile(`in`!!, out)
        } catch (e: IOException) {
            Log.e("tag", "Failed to copy asset file: $filename", e)
        } finally {
            if (`in` != null) {
                try {
                    `in`.close()
                } catch (e: IOException) {
                    // NOOP
                }

            }
            if (out != null) {
                try {
                    out.close()
                } catch (e: IOException) {
                    // NOOP
                }

            }
        }
    }

    @Throws(IOException::class)
    private fun copyFile(`in`: InputStream, out: OutputStream) {
        val buffer = ByteArray(1024)
        var read: Int = `in`.read(buffer)
        while (read != -1) {
            out.write(buffer, 0, read)
            read = `in`.read(buffer)
        }
    }

    override fun onActivityResult(reqCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(reqCode, resultCode, data)


        if (resultCode == Activity.RESULT_OK) {
            try {
                val videoUri = data!!.data
                contentResolver.openInputStream(videoUri!!)?.use { input ->
                    file = File(cacheDir, "sample.mp4").also { it.createNewFile() }
                    FileOutputStream(file).use {
                        copyFile(input, it)
                        uploadImage()
                    }
                }
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show()
            }

        } else {
            Toast.makeText(this, "You haven't picked Image", Toast.LENGTH_LONG).show()
        }
    }
}
