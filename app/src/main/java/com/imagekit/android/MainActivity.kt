package com.imagekit.android

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.imagekit.android.entity.TransformationPosition
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ImageKit.init(
            context = applicationContext,
            publicKey = Constants.CLIENT_PUBLIC_KEY,
            urlEndpoint = "https://ik.imagekit.io/demo",
            transformationPosition = TransformationPosition.PATH,
            authenticationEndpoint = "https://imagekit.io/temp/client-side-upload-signature"
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