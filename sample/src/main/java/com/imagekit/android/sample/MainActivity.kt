package com.imagekit.android.sample

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.imagekit.android.ImageKit
import com.imagekit.android.entity.TransformationPosition
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ImageKit.init(
            context = applicationContext,
            publicKey = "public_5P5QM23aRv9XkOcfJO1okZ0DzOw=",
            urlEndpoint = "https://ik.imagekit.io/tqhfz73me",
            transformationPosition = TransformationPosition.PATH,
            authenticationEndpoint = "https://027e-119-82-70-86.ngrok-free.app/"
        )

        btUploadImage.setOnClickListener {
            startActivity(
                Intent(
                    this@MainActivity,
                    UploadImageActivity::class.java
                )
            )
        }

        btUploadFile.setOnClickListener {
            startActivity(
                Intent(
                    this@MainActivity,
                    UploadFileActivity::class.java
                )
            )
        }

        btUrlConstruct.setOnClickListener {
            startActivity(
                Intent(
                    this@MainActivity,
                    FetchImageActivity::class.java
                )
            )
        }
    }
}