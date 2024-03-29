package com.imagekit.android.sample

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Point
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.imagekit.android.ImageKit
import com.imagekit.android.ImageKitCallback
import com.imagekit.android.entity.UploadPolicy
import com.imagekit.android.entity.UploadError
import com.imagekit.android.entity.UploadResponse
import com.imagekit.android.preprocess.ImageUploadPreprocessor
import com.imagekit.android.sample.databinding.ActivityUploadImageBinding
import kotlinx.coroutines.launch
import java.io.FileNotFoundException


class UploadImageActivity : AppCompatActivity(), ImageKitCallback, View.OnClickListener {
    private var uploadResultDialog: AlertDialog? = null
    private var loadingDialog: AlertDialog? = null

    private var bitmap: Bitmap? = null

    private lateinit var binding: ActivityUploadImageBinding

    private val viewModel: UploadViewModel by viewModels()

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btSelect -> selectImage()
//            R.id.btSelect -> uploadUrlImage()
            else -> uploadImage()
        }
    }

    private val RESULT_LOAD_IMG = 109

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadImageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btSelect.setOnClickListener(this)
        binding.btUpload.setOnClickListener(this)
    }

    private fun selectImage() {
        val photoPickerIntent = Intent(Intent.ACTION_PICK)
        photoPickerIntent.type = "image/*"
        startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG)
    }

    private fun uploadImage() {
        bitmap?.let {
            loadingDialog = AlertDialog.Builder(this)
                .setMessage("Uploading image...")
                .setCancelable(false)
                .show()

            lifecycleScope.launch {
                val filename = "icLauncher.png"
                val authToken = viewModel.getUploadToken(mapOf(
                    "fileName" to filename,
                    "useUniqueFileName" to "true",
                    "tags" to arrayOf("nice", "copy", "books").joinToString(","),
                    "folder" to "/dummy/folder/"
                ))?.let { it["token"] }.toString()
                ImageKit.getInstance().uploader().upload(
                    file = bitmap!!,
                    token = authToken,
                    fileName = filename,
                    useUniqueFileName = true,
                    tags = arrayOf("nice", "copy", "books"),
                    folder = "/dummy/folder/",
                    policy = UploadPolicy.Builder().maxRetries(3).backoffCriteria(
                        backoffMillis = 100L,
                        backoffPolicy = UploadPolicy.BackoffPolicy.EXPONENTIAL
                    ).build(),
                    preprocessor = ImageUploadPreprocessor.Builder()
                        .limit(400, 400)
                        .rotate(45f)
                        .crop(Point(20, 40), Point(100, 120))
                        .format(Bitmap.CompressFormat.JPEG)
                        .build(),
                    imageKitCallback = this@UploadImageActivity
                )
            }
        }
    }

    private fun uploadUrlImage() {
        loadingDialog = AlertDialog.Builder(this)
            .setMessage("Uploading image...")
            .setCancelable(false)
            .show()

        val filename = "icLauncher.png"
        ImageKit.getInstance().uploader().upload(
            file = "https://ik.imagekit.io/demo/img/default-image.jpg",
            token = "",
            fileName = filename,
            useUniqueFileName = true,
            tags = arrayOf("nice", "copy", "books"),
            policy = UploadPolicy.Builder().requiresBatteryCharging(false).build(),
            folder = "/dummy/folder/",
            imageKitCallback = this
        )
    }

    override fun onActivityResult(reqCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(reqCode, resultCode, data)


        if (resultCode == Activity.RESULT_OK) {
            try {
                val imageUri = data!!.data
                val imageStream = contentResolver.openInputStream(imageUri!!)
                val selectedImage = BitmapFactory.decodeStream(imageStream)
                binding.ivImage.setImageBitmap(selectedImage)

                bitmap = selectedImage
                binding.btUpload.visibility = View.VISIBLE
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show()
            }

        } else {
            Toast.makeText(this, "You haven't picked Image", Toast.LENGTH_LONG).show()
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
            .setMessage("The uploaded image can be accessed using url: ${uploadResponse.url}")
            .setNeutralButton("Ok") { _, _ ->
                // Do nothing
            }.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        loadingDialog?.dismiss()
        uploadResultDialog?.dismiss()
    }
}
