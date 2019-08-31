package com.imagekit.android

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ImageKit.init(applicationContext, Constants.CLIENT_PUBLIC_KEY, Constants.IMAGEKIT_ID, "https://ik.imagekit.io/demo", "https://ik.imagekit.io/demo")

        btUploadImage.setOnClickListener { startActivity(Intent(this@MainActivity, UploadImageActivity::class.java)) }

        btUrlConstruct.setOnClickListener { startActivity(Intent(this@MainActivity, FetchImageActivity::class.java)) }
    }
}