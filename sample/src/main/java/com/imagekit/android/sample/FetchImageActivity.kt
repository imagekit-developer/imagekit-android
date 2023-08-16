package com.imagekit.android.sample

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.imagekit.android.ImageKit
import com.imagekit.android.entity.CropMode
import com.imagekit.android.entity.Rotation
import com.imagekit.android.entity.TransformationPosition
import com.imagekit.android.sample.databinding.ActivityFetchImageBinding
import com.squareup.picasso.Picasso

class FetchImageActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityFetchImageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFetchImageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btTran2.setOnClickListener(this)
        binding.btTran1.setOnClickListener(this)
        binding.btTran3.setOnClickListener(this)
        binding.btTran4.setOnClickListener(this)
        binding.btTran5.setOnClickListener(this)
        binding.btTran6.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        showImage(
            when (v!!.id) {
                R.id.btTran1 -> {
                    //https://ik.imagekit.io/demo/img/tr:w-300.00,h-200.00,cm-pad_resize,bg-F3F3F3/plant.jpeg
                    ImageKit.getInstance().url("plant.jpeg", "https://ik.imagekit.io/demo/img")
                        .width(300)
                        .height(200)
                        .cropMode(CropMode.PAD_RESIZE)
                        .background("F3F3F3")
                        .create()
                }
                R.id.btTran2 -> {
                    //https://ik.imagekit.io/demo/default-image.jpg?tr=h-400.00,ar-3-2
                    ImageKit.getInstance().url(
                        path = "default-image.jpg",
                        transformationPosition = TransformationPosition.QUERY
                    )
                        .height(400)
                        .aspectRatio(3, 2)
                        .create()
                }
                R.id.btTran3 -> {
                    //https://ik.imagekit.io/demo/medium_cafe_B1iTdD0C.jpg?tr=oi-logo-white_SJwqB4Nfe.png,ox-10,oy-20
                    ImageKit.getInstance()
                        .url(
                            src = "https://ik.imagekit.io/demo/medium_cafe_B1iTdD0C.jpg",
                            transformationPosition = TransformationPosition.PATH
                        )
                        .create()

                }
                R.id.btTran4 -> {
                    //https://ik.imagekit.io/demo/tr:oi-logo-white_SJwqB4Nfe.png,ox-N10,oy-20/medium_cafe_B1iTdD0C.jpg
                    ImageKit.getInstance()
                        .url(src = "https://ik.imagekit.io/demo/tr:oi-logo-white_SJwqB4Nfe.png,ox-N10,oy-20/medium_cafe_B1iTdD0C.jpg")
                        .create()
                }
                R.id.btTran5 -> {
                    //https://ik.imagekit.io/demo/img/plant.jpeg?tr=w-400,ot-Hand with a green plant,otc-264120,ots-30,ox-10,oy-10
                    ImageKit.getInstance()
                        .url(src = "https://ik.imagekit.io/demo/img/plant.jpeg?tr=oi-logo-white_SJwqB4Nfe.png,ox-10,oy-20")
                        .addCustomTransformation("w", "400")
                        .create()
                }
                R.id.btTran6 -> {
                    //https://ik.imagekit.io/demo/img/default-image.jpg?tr=w-400.00,h-300.00:rt-90
                    ImageKit.getInstance()
                        .url(src = "https://ik.imagekit.io/demo/img/tr:ot-Hand%20with%20a%20green%20plant,otc-264120,ots-30,ox-10,oy-10/default-image.jpg")
                        .width(400)
                        .height(300)
                        .chainTransformation()
                        .setResponsive(
                            view = binding.ivImage,
                            minSize = 200,
                            maxSize = 1600,
                            step = 100
                        )
                        .create()
                }
                else ->
                    ImageKit.getInstance().url("plant.jpeg", "https://ik.imagekit.io/demo/img")
                        .create()
            }
        )
    }

    private fun showImage(imagePath: String) {
        binding.tvConstructedUrl.text = "Image Url: $imagePath"

        Picasso.get()
            .load(imagePath)
            .into(binding.ivImage)
    }
}
