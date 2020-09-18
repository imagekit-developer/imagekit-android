package com.imagekit.android

import android.app.Application
import android.content.SharedPreferences
import com.imagekit.android.entity.CropMode
import com.imagekit.android.entity.Rotation
import com.imagekit.android.entity.TransformationPosition
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.*
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import kotlin.test.assertEquals

@RunWith(MockitoJUnitRunner::class)
class Tests {

    private val clientPublicKey: String = "Dummy public key"
    private val urlEndpoint = "https://ik.imagekit.io/demo"

    @Before
    fun init() {
        val mockPrefs = Mockito.mock(SharedPreferences::class.java)
        val mockContext = Mockito.mock(Application::class.java)
        val mockEditor = Mockito.mock(SharedPreferences.Editor::class.java)
        Mockito.`when`(mockContext.getSharedPreferences(anyString(), anyInt()))
            .thenReturn(mockPrefs)
        Mockito.`when`<String>(
            mockPrefs.getString(
                eq("Transformation Position Endpoint"),
                anyString()
            )
        ).thenReturn(TransformationPosition.PATH.name)
        Mockito.`when`<String>(mockPrefs.getString(eq("ImageKit Url Endpoint"), anyString()))
            .thenReturn(urlEndpoint)
        Mockito.`when`<SharedPreferences.Editor>(mockPrefs.edit()).thenReturn(mockEditor)
        Mockito.`when`<SharedPreferences.Editor>(mockEditor.putString(anyString(), anyString()))
            .thenReturn(mockEditor)

        ImageKit.init(
            context = mockContext,
            publicKey = clientPublicKey,
            urlEndpoint = urlEndpoint,
            transformationPosition = TransformationPosition.PATH,
            authenticationEndpoint = "https://imagekit.io/temp/client-side-upload-signature"
        )
    }

    @Test
    fun urlConstructionTransformation1() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/img/tr:w-300.00,h-200.00,cm-pad_resize,bg-F3F3F3/plant.jpeg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        val actualTransformation =
            ImageKit.getInstance().url("plant.jpeg", "https://ik.imagekit.io/demo/img")
                .width(300f)
                .height(200f)
                .cropMode(CropMode.PAD_RESIZE)
                .background("F3F3F3")
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun urlConstructionTransformation2() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/default-image.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}&tr=h-400.00,ar-3-2"
        val actualTransformation =
            ImageKit.getInstance().url(
                path = "default-image.jpg",
                transformationPosition = TransformationPosition.QUERY
            )
                .height(400f)
                .aspectRatio(3, 2)
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun urlConstructionTransformation3() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}&tr=oi-logo-white_SJwqB4Nfe.png,ox-10,oy-20"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    src = "https://ik.imagekit.io/demo/medium_cafe_B1iTdD0C.jpg",
                    transformationPosition = TransformationPosition.PATH
                )
                .overlayImage("logo-white_SJwqB4Nfe.png")
                .overlayX(10)
                .overlayY(20)
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun urlConstructionTransformation4() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/tr:oi-logo-white_SJwqB4Nfe.png,ox-N10,oy-20/medium_cafe_B1iTdD0C.jpg"
        val actualTransformation =
            ImageKit.getInstance()
                .url(src = "https://ik.imagekit.io/demo/tr:oi-logo-white_SJwqB4Nfe.png,ox-N10,oy-20/medium_cafe_B1iTdD0C.jpg")
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun urlConstructionTransformation5() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/img/plant.jpeg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}&tr=w-400,ot-Hand with a green plant,otc-264120,ots-30,ox-10,oy-10"
        val actualTransformation =
            ImageKit.getInstance()
                .url(src = "https://ik.imagekit.io/demo/img/plant.jpeg?tr=oi-logo-white_SJwqB4Nfe.png,ox-10,oy-20")
                .addCustomTransformation("w", "400")
                .overlayText("Hand with a green plant")
                .overlayTextColor("264120")
                .overlayTextFontSize(30)
                .overlayX(10)
                .overlayY(10)
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun urlConstructionTransformation6() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/img/default-image.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}&tr=w-400.00,h-300.00:rt-90"
        val actualTransformation =
            ImageKit.getInstance()
                .url(src = "https://ik.imagekit.io/demo/img/tr:ot-Hand%20with%20a%20green%20plant,otc-264120,ots-30,ox-10,oy-10/default-image.jpg")
                .width(400f)
                .height(300f)
                .chainTransformation()
                .rotation(Rotation.VALUE_90)
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun urlConstructionOverridingUrlendpointParameter() {
        val expectedTransformation =
            "https://ik.imagekit.io/modified_imagekitid/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    path="medium_cafe_B1iTdD0C.jpg",
                    urlEndpoint = "https://ik.imagekit.io/modified_imagekitid"
                )
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }
}