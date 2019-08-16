package com.imagekit.android

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.imagekit.android.entity.CropMode
import com.imagekit.android.entity.Rotation
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_fetch_image.*

class FetchImageActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fetch_image)

        btTran1.setOnClickListener(this)
        btTran2.setOnClickListener(this)
        btTran3.setOnClickListener(this)
        btTran4.setOnClickListener(this)
        btTran5.setOnClickListener(this)
        btTran6.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        showImage(
            when (v!!.id) {
                R.id.btTran1 -> {
                    //https://ik.imagekit.io/demo/img/plant.jpeg?tr=w-300,h-200,cm-pad_resize,bg-F3F3F3
                    ImageKit.getInstance().url("https://ik.imagekit.io/demo/img", "plant.jpeg")
                        .width(300f)
                        .height(200f)
                        .cropMode(CropMode.PAD_RESIZE)
                        .backgroundHexColor("F3F3F3")
                        .create()
                }
                R.id.btTran2 -> {
                    //https://ik.imagekit.io/demo/img/tr:h-400,ar-3-2/default-image.jpg
                    ImageKit.getInstance().url( "default-image.jpg")
                        .height(400f)
                        .aspectRatio(3, 2)
                        .create()
                }
                R.id.btTran3 -> {
                    //https://ik.imagekit.io/demo/tr:oi-logo-white_SJwqB4Nfe.png,ox-10,oy-20/medium_cafe_B1iTdD0C.jpg
                    ImageKit.getInstance().url("https://ik.imagekit.io/demo", "medium_cafe_B1iTdD0C.jpg")
                        .overlayImage("logo-white_SJwqB4Nfe.png")
                        .overlayPosX(10)
                        .overlayPosY(20)
                        .create()

                }
                R.id.btTran4 -> {
                    //https://ik.imagekit.io/demo/tr:oi-logo-white_SJwqB4Nfe.png,ox-N10,oy-20/medium_cafe_B1iTdD0C.jpg
                    ImageKit.getInstance().url("https://ik.imagekit.io/demo", "medium_cafe_B1iTdD0C.jpg")
                        .overlayImage("logo-white_SJwqB4Nfe.png")
                        .overlayNegX(-10)
                        .overlayPosY(20)
                        .create()
                }
                R.id.btTran5 -> {
                    //https://ik.imagekit.io/demo/img/tr:ot-Hand%20with%20a%20green%20plant,otc-264120,ots-30,ox-10,oy-10/plant.jpeg
                    ImageKit.getInstance().url("https://ik.imagekit.io/demo/img", "plant.jpeg")
                        .overlayText("Hand with a green plant")
                        .overlayTextColor("264120")
                        .overlayTextSize(30)
                        .overlayPosX(10)
                        .overlayPosY(10)
                        .create()
                }
                R.id.btTran6 -> {
                    //https://ik.imagekit.io/demo/img/tr:w-400,h-300:rt-90/default-image.jpg
                    ImageKit.getInstance().url("https://ik.imagekit.io/demo/img", "default-image.jpg")
                        .width(400f)
                        .height(300f)
                        .chainTransformation()
                        .rotate(Rotation.VALUE_90)
                        .create()
                }
                else ->
                    ImageKit.getInstance().url("https://ik.imagekit.io/demo/img", "plant.jpeg")
                        .create()
            }
        )
    }

    private fun showImage(imagePath: String) {
        tvConstructedUrl.text = "Image Url: $imagePath"

        Picasso.get()
            .load(imagePath)
            .into(ivImage)
    }
}
