package com.imagekit.android.uploadwidget

import android.app.Activity
import android.content.ClipData
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.imagekit.android.R
import com.imagekit.android.uploadwidget.model.BitmapManager
import com.imagekit.android.uploadwidget.model.CropRotateResult
import com.imagekit.android.uploadwidget.utils.UploadUtil

class UploadActivity : AppCompatActivity() {
    lateinit var imageUpload : ImageView
    var MEDIA_CHOOSER_REQUEST_CODE : Int = 2000;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar_upload)
        setSupportActionBar(toolbar)
        if (getSupportActionBar() != null)
        {
            getSupportActionBar()?.setDisplayShowTitleEnabled(true)
            getSupportActionBar()?.setTitle("Upload")
        }
        val buttonChooseImage = findViewById<Button>(R.id.button_img_choose) as Button

        buttonChooseImage.setOnClickListener{
            UploadUtil.openMediaChooser(this,MEDIA_CHOOSER_REQUEST_CODE)
        }
            /*val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
            bottomNavigationView.setOnNavigationItemSelectedListener {
                when(it.itemId){
                    R.id.item_crop_rotate->showCropRotateFragment()
                    R.id.item_brightness->showBrightnessFragment()
                    R.id.item_upload->uploadImage()

                }
                true
            }*/
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == MEDIA_CHOOSER_REQUEST_CODE)
        {
            if (resultCode == Activity.RESULT_OK && data != null) {
                val uris: ArrayList<Uri>? = extractImageUris(data)
                showImages(uris)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    val takeFlags: Int = data.getFlags() and Intent.FLAG_GRANT_READ_URI_PERMISSION
                    if (uris != null) {
                        /*for (uri in uris) {
                            if (DocumentsContract.isDocumentUri(this, uri)) {
                                getContentResolver().takePersistableUriPermission(uri, takeFlags)
                            }
                        }*/
                    }
                }

            } else {
                setResult(Activity.RESULT_CANCELED)
                finish()
            }
        }
    }
    private lateinit var uri : Uri
    private fun showImages(uris: java.util.ArrayList<Uri>?) {
        if (uris != null) {
            uri = uris.get(0) as Uri
            val intent = Intent(this, ImagePreviewActivity::class.java)
            intent.putExtra("EDIT_URI", uri.toString())
            startActivity(intent)

        }
    }

    private fun extractImageUris(data: Intent): ArrayList<Uri>? {
        val imageUris = ArrayList<Uri>()
        var clipData: ClipData? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            clipData = data.clipData
        }
        if (clipData != null) {
            for (i in 0 until clipData.itemCount) {
                imageUris.add(clipData.getItemAt(i).uri)
            }
        } else if (data.data != null) {
            imageUris.add(data.data)
        }
        return imageUris
    }
}