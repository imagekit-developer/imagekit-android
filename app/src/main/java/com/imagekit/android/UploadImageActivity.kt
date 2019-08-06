package com.imagekit.android

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.imagekit.android.Constants.CLIENT_PUBLIC_KEY
import com.imagekit.android.entity.UploadError
import com.imagekit.android.entity.UploadResponse
import kotlinx.android.synthetic.main.activity_upload_image.*
import java.io.FileNotFoundException


class UploadImageActivity : AppCompatActivity(), ImageKitCallback, View.OnClickListener {
    private var bitmap: Bitmap? = null

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btSelect -> selectImage()
            else -> uploadImage()
        }
    }

    private val RESULT_LOAD_IMG = 109

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_image)

        btSelect.setOnClickListener(this)
        btUpload.setOnClickListener(this)
    }

    private fun selectImage() {
        val photoPickerIntent = Intent(Intent.ACTION_PICK)
        photoPickerIntent.type = "image/*"
        startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG)
    }

    private fun uploadImage() {
        bitmap?.let {
            val filename = "icLauncher.png"
            val timestamp = System.currentTimeMillis()
            ImageKit.getInstance().uploadImage(
                    bitmap!!
                    , filename
                    , SignatureUtil.sign("apiKey=$CLIENT_PUBLIC_KEY&filename=$filename&timestamp=$timestamp")
                    , timestamp
                    , true
                    , arrayOf("nice", "copy", "books")
                    , "/rishabh/folder/"
                    , this
            )
        }
    }

    override fun onActivityResult(reqCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(reqCode, resultCode, data)


        if (resultCode == Activity.RESULT_OK) {
            try {
                val imageUri = data!!.data
                val imageStream = contentResolver.openInputStream(imageUri!!)
                val selectedImage = BitmapFactory.decodeStream(imageStream)
                ivImage.setImageBitmap(selectedImage)

                bitmap = selectedImage
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
    }

    override fun onSuccess(uploadResponse: UploadResponse) {
        Log.d(MainActivity::class.simpleName, "SUCCESS")
    }
}
