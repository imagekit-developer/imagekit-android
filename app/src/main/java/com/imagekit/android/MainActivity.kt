package com.imagekit.android

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.imagekit.android.entity.UploadError
import com.imagekit.android.entity.UploadResponse
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ImageKitCallback {
    private val CLIENT_PUBLIC_KEY = "amJ0i84qqCLLbOl25vqSozezhuc="
    private val IMAGEKIT_ID = "bowstring"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ImageKit.init(applicationContext, CLIENT_PUBLIC_KEY, IMAGEKIT_ID)

//        val filename = "icLauncher.png"
//        val timestamp = System.currentTimeMillis()
//        ImageKit.getInstance().uploadImage(
//            filename
//            , SignatureUtil.sign("apiKey=$CLIENT_PUBLIC_KEY&filename=$filename&timestamp=$timestamp")
//            , timestamp
//            , true
//            , arrayOf("nice", "copy", "books")
//            , "/rishabh/folder/"
//            , (ResourcesCompat.getDrawable(resources, R.drawable.ic_agronomist, null) as BitmapDrawable).bitmap
//            , this
//        )

        val imagePath = ImagekitUrlConstructor(this, "https://ik.imagekit.io/bowstring", "icLauncher.png")
            .width(600f)
            .aspectRatio(4,3)
            .create()

        Log.d("Image Path Constructed", imagePath)

        Picasso.get()
            .load(imagePath)
            .into(iv_imagekit)
    }

    override fun onError(uploadError: UploadError) {
        Log.d(MainActivity::class.simpleName, "ERROR")
    }

    override fun onSuccess(uploadResponse: UploadResponse) {
        Log.d(MainActivity::class.simpleName, "SUCCESS")
    }
}
