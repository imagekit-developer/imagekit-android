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
            publicKey = "public_5P5QM23aRv9XkOcfJO1okZ0DzOw=",
            urlEndpoint = "https://ik.imagekit.io/tqhfz73me",
            transformationPosition = TransformationPosition.PATH,
            authenticationEndpoint = "https://2303-2401-4900-1c46-e905-38ef-8dd2-56ae-1080.ngrok-free.app/"
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