package com.imagekit.android.sample

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.imagekit.android.ImageKit
import com.imagekit.android.entity.TransformationPosition
import com.imagekit.android.sample.databinding.ActivityMainBinding

//import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ImageKit.init(
            context = applicationContext,
            publicKey = "YOUR_PUBLIC_KEY",
            urlEndpoint = "https://ik.imagekit.io/YOUR_IMAGEKIT_ID",
            transformationPosition = TransformationPosition.PATH,
            authenticationEndpoint = "YOUR_AUTHENTICATION_ENDPOINT"
        )

        binding.btUploadImage.setOnClickListener {
            startActivity(
                Intent(
                    this@MainActivity,
                    UploadImageActivity::class.java
                )
            )
        }

        binding.btUploadFile.setOnClickListener {
            startActivity(
                Intent(
                    this@MainActivity,
                    UploadFileActivity::class.java
                )
            )
        }

        binding.btUrlConstruct.setOnClickListener {
            startActivity(
                Intent(
                    this@MainActivity,
                    FetchImageActivity::class.java
                )
            )
        }
    }
}