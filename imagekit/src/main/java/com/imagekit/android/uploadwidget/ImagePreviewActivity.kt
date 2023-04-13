package com.imagekit.android.uploadwidget

import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.google.android.mat;qerial.bottomnavigation.BottomNavigationView
import com.imagekit.android.R
import com.imagekit.android.uploadwidget.model.BitmapManager
import com.imagekit.android.uploadwidget.model.CropRotateResult

class ImagePreviewActivity : AppCompatActivity(), CropRotateFragment.Callback {
    public val URI_VAL : String = "EDIT_URI"
    private lateinit var uri : Uri;
    private lateinit var imgPreview : ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_preview)
        var uriString = intent.getStringExtra(URI_VAL)
        uri = Uri.parse(uriString)
        if(uri != null){
            imgPreview = findViewById<ImageView>(R.id.image_preview)
            imgPreview.setImageURI(uri)
        }

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.item_crop_rotate->showCropRotateFragment()
                R.id.item_brightness->showBrightnessFragment()
                R.id.item_upload->uploadImage()

            }
            true
        }
    }

    private fun showCropRotateFragment() {
        var cropRotateFragment: CropRotateFragment =
            CropRotateFragment.newInstance(uri, this)

        supportFragmentManager.beginTransaction()
            .replace(android.R.id.content, cropRotateFragment, null)
            .addToBackStack(null)
            .commit()
    }
    private fun showBrightnessFragment(){

    }
    private fun uploadImage(){

    }
    override fun onCropRotateFinish(uri: Uri?, result: CropRotateResult?, resultBitmap: Bitmap?) {
        BitmapManager.get().save(this, resultBitmap, object : BitmapManager.SaveCallback {
            override fun onSuccess(resultUri: Uri?) {
                imgPreview.setImageURI(resultUri)
            }

            override fun onFailure() {}
        })
    }

    override fun onCropRotateCancel(uri: Uri?) {

    }
}