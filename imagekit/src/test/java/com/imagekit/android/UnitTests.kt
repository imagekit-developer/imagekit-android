package com.imagekit.android

import android.app.Application
import android.content.SharedPreferences
import com.imagekit.android.entity.*
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.*
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import kotlin.test.assertEquals

@RunWith(MockitoJUnitRunner::class)
class UnitTests {

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
            urlEndpoint = urlEndpoint
        )
    }

    @Test
    fun basicURLGenerationPath() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    path="medium_cafe_B1iTdD0C.jpg"
                )
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun basicURLGenerationURLEndpointAndPath() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    path="medium_cafe_B1iTdD0C.jpg",
                    urlEndpoint = "https://ik.imagekit.io/demo"
                )
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }


    @Test
    fun basicURLGenerationSource() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    src = "https://ik.imagekit.io/demo/medium_cafe_B1iTdD0C.jpg"
                )
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun transformationsWidth300() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/tr:w-300/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    path="medium_cafe_B1iTdD0C.jpg"
                )
                .width(300)
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun transformationsHeight300() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/tr:h-300/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    path="medium_cafe_B1iTdD0C.jpg"
                )
                .height(300)
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun transformationsAspectRatio2by3() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/tr:ar-2-3/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    path="medium_cafe_B1iTdD0C.jpg"
                )
                .aspectRatio(2, 3)
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun transformationsCropMaintainAspectRatio() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/tr:c-maintain_ratio/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    path="medium_cafe_B1iTdD0C.jpg"
                )
                .crop(CropType.MAINTAIN_RATIO)
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun transformationsCropMaintainForce() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/tr:c-force/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    path="medium_cafe_B1iTdD0C.jpg"
                )
                .crop(CropType.FORCE)
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun transformationsCropMaintainAtLeast() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/tr:c-at_least/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    path="medium_cafe_B1iTdD0C.jpg"
                )
                .crop(CropType.AT_LEAST)
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun transformationsCropMaintainAtMax() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/tr:c-at_max/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    path="medium_cafe_B1iTdD0C.jpg"
                )
                .crop(CropType.AT_MAX)
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun transformationsCropModeResize() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/tr:cm-resize/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    path="medium_cafe_B1iTdD0C.jpg"
                )
                .cropMode(CropMode.RESIZE)
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun transformationsCropModeExtract() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/tr:cm-extract/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    path="medium_cafe_B1iTdD0C.jpg"
                )
                .cropMode(CropMode.EXTRACT)
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun transformationsCropModePadResize() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/tr:cm-pad_resize/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    path="medium_cafe_B1iTdD0C.jpg"
                )
                .cropMode(CropMode.PAD_RESIZE)
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun transformationsCropModePadExtract() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/tr:cm-pad_extract/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    path="medium_cafe_B1iTdD0C.jpg"
                )
                .cropMode(CropMode.PAD_EXTRACT)
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun transformationsFocusTypeCenter() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/tr:fo-center/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    path="medium_cafe_B1iTdD0C.jpg"
                )
                .focus(FocusType.CENTER)
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun transfomationsFocusTypeTop() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/tr:fo-top/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    path="medium_cafe_B1iTdD0C.jpg"
                )
                .focus(FocusType.TOP)
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun transfomationsFocusTypeLeft() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/tr:fo-left/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    path="medium_cafe_B1iTdD0C.jpg"
                )
                .focus(FocusType.LEFT)
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun transfomationsFocusTypeBottom() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/tr:fo-bottom/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    path="medium_cafe_B1iTdD0C.jpg"
                )
                .focus(FocusType.BOTTOM)
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun transfomationsFocusTypeRight() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/tr:fo-right/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    path="medium_cafe_B1iTdD0C.jpg"
                )
                .focus(FocusType.RIGHT)
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun transfomationsFocusTypeTop_left() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/tr:fo-top_left/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    path="medium_cafe_B1iTdD0C.jpg"
                )
                .focus(FocusType.TOP_LEFT)
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun transfomationsFocusTypeTop_right() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/tr:fo-top_right/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    path="medium_cafe_B1iTdD0C.jpg"
                )
                .focus(FocusType.TOP_RIGHT)
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun transfomationsFocusTypeBottom_left() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/tr:fo-bottom_left/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    path="medium_cafe_B1iTdD0C.jpg"
                )
                .focus(FocusType.BOTTOM_LEFT)
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun transfomationsFocusTypeBottom_right() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/tr:fo-bottom_right/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    path="medium_cafe_B1iTdD0C.jpg"
                )
                .focus(FocusType.BOTTOM_RIGHT)
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun transfomationsFocusTypeAuto() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/tr:fo-auto/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    path="medium_cafe_B1iTdD0C.jpg"
                )
                .focus(FocusType.AUTO)
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun transfomationsQuality50() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/tr:q-50/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    path="medium_cafe_B1iTdD0C.jpg"
                )
                .quality(50)
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun transfomationsFormatAuto() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/tr:f-auto/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    path="medium_cafe_B1iTdD0C.jpg"
                )
                .format(Format.AUTO)
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun transfomationsFormatWebp() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/tr:f-webp/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    path="medium_cafe_B1iTdD0C.jpg"
                )
                .format(Format.WEBP)
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun transfomationsFormatJpg() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/tr:f-jpg/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    path="medium_cafe_B1iTdD0C.jpg"
                )
                .format(Format.JPG)
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun transfomationsFormatJpeg() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/tr:f-jpeg/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    path="medium_cafe_B1iTdD0C.jpg"
                )
                .format(Format.JPEG)
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun transfomationsFormatPng() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/tr:f-png/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    path="medium_cafe_B1iTdD0C.jpg"
                )
                .format(Format.PNG)
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun transfomationsBlur50() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/tr:bl-50/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    path="medium_cafe_B1iTdD0C.jpg"
                )
                .blur(50)
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun transfomationsGrayscale() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/tr:e-grayscale/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    path="medium_cafe_B1iTdD0C.jpg"
                )
                .effectGray(true)
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun transfomationsGrayscaleFalse() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    path="medium_cafe_B1iTdD0C.jpg"
                )
                .effectGray(false)
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun transfomationsDpr25() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/tr:dpr-2.50/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    path="medium_cafe_B1iTdD0C.jpg"
                )
                .dpr(2.5f)
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun transfomationsNamedTransformTest() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/tr:n-test/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    path="medium_cafe_B1iTdD0C.jpg"
                )
                .named("test")
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun transfomationsDefaultImageMedium_cafe_b1itdd0cJpg() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/tr:di-medium_cafe_B1iTdD0C.jpg/non_existent_image.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    path="non_existent_image.jpg"
                )
                .defaultImage("medium_cafe_B1iTdD0C.jpg")
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun transfomationsProgressive() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/tr:pr-true/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    path="medium_cafe_B1iTdD0C.jpg"
                )
                .progressive(true)
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun transfomationsLossless() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/tr:lo-true/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    path="medium_cafe_B1iTdD0C.jpg"
                )
                .lossless(true)
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun transfomationsTrim() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/tr:t-true/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    path="medium_cafe_B1iTdD0C.jpg"
                )
                .trim(true)
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun transfomationsTrim50() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/tr:t-50/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    path="medium_cafe_B1iTdD0C.jpg"
                )
                .trim(50)
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun transfomationsOverlayImageLogo_white_sjwqb4nfePng() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/tr:oi-logo-white_SJwqB4Nfe.png/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    path="medium_cafe_B1iTdD0C.jpg"
                )
                .overlayImage("logo-white_SJwqB4Nfe.png")
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun transfomationsOverlayFocusTypeCenter() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/tr:oi-logo-white_SJwqB4Nfe.png,ofo-center/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    path="medium_cafe_B1iTdD0C.jpg"
                )
                .overlayImage("logo-white_SJwqB4Nfe.png").overlayFocus(OverlayFocusType.CENTER)
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun transfomationsOverlayFocusTypeTop() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/tr:oi-logo-white_SJwqB4Nfe.png,ofo-top/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    path="medium_cafe_B1iTdD0C.jpg"
                )
                .overlayImage("logo-white_SJwqB4Nfe.png").overlayFocus(OverlayFocusType.TOP)
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun transfomationsOverlayFocusTypeLeft() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/tr:oi-logo-white_SJwqB4Nfe.png,ofo-left/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    path="medium_cafe_B1iTdD0C.jpg"
                )
                .overlayImage("logo-white_SJwqB4Nfe.png").overlayFocus(OverlayFocusType.LEFT)
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun transfomationsOverlayFocusTypeBottom() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/tr:oi-logo-white_SJwqB4Nfe.png,ofo-bottom/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    path="medium_cafe_B1iTdD0C.jpg"
                )
                .overlayImage("logo-white_SJwqB4Nfe.png").overlayFocus(OverlayFocusType.BOTTOM)
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun transfomationsOverlayFocusTypeRight() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/tr:oi-logo-white_SJwqB4Nfe.png,ofo-right/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    path="medium_cafe_B1iTdD0C.jpg"
                )
                .overlayImage("logo-white_SJwqB4Nfe.png").overlayFocus(OverlayFocusType.RIGHT)
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun transfomationsOverlayFocusTypeTop_left() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/tr:oi-logo-white_SJwqB4Nfe.png,ofo-top_left/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    path="medium_cafe_B1iTdD0C.jpg"
                )
                .overlayImage("logo-white_SJwqB4Nfe.png").overlayFocus(OverlayFocusType.TOP_LEFT)
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun transfomationsOverlayFocusTypeTop_right() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/tr:oi-logo-white_SJwqB4Nfe.png,ofo-top_right/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    path="medium_cafe_B1iTdD0C.jpg"
                )
                .overlayImage("logo-white_SJwqB4Nfe.png").overlayFocus(OverlayFocusType.TOP_RIGHT)
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun transfomationsOverlayFocusTypeBottom_left() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/tr:oi-logo-white_SJwqB4Nfe.png,ofo-bottom_left/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    path="medium_cafe_B1iTdD0C.jpg"
                )
                .overlayImage("logo-white_SJwqB4Nfe.png").overlayFocus(OverlayFocusType.BOTTOM_LEFT)
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun transfomationsOverlayFocusTypeBottom_right() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/tr:oi-logo-white_SJwqB4Nfe.png,ofo-bottom_right/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    path="medium_cafe_B1iTdD0C.jpg"
                )
                .overlayImage("logo-white_SJwqB4Nfe.png").overlayFocus(OverlayFocusType.BOTTOM_RIGHT)
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun transfomationsOverlayX50() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/tr:oi-logo-white_SJwqB4Nfe.png,ox-50/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    path="medium_cafe_B1iTdD0C.jpg"
                )
                .overlayImage("logo-white_SJwqB4Nfe.png").overlayX(50)
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun transfomationsOverlayY50() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/tr:oi-logo-white_SJwqB4Nfe.png,oy-50/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    path="medium_cafe_B1iTdD0C.jpg"
                )
                .overlayImage("logo-white_SJwqB4Nfe.png").overlayY(50)
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun transfomationsOverlayXNeg50() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/tr:oi-logo-white_SJwqB4Nfe.png,ox-N50/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    path="medium_cafe_B1iTdD0C.jpg"
                )
                .overlayImage("logo-white_SJwqB4Nfe.png").overlayX(-50)
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun transfomationsOverlayYNeg50() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/tr:oi-logo-white_SJwqB4Nfe.png,oy-N50/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    path="medium_cafe_B1iTdD0C.jpg"
                )
                .overlayImage("logo-white_SJwqB4Nfe.png").overlayY(-50)
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun transfomationsOverlayWidth50() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/tr:oi-logo-white_SJwqB4Nfe.png,ow-50/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    path="medium_cafe_B1iTdD0C.jpg"
                )
                .overlayImage("logo-white_SJwqB4Nfe.png").overlayWidth(50)
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun transfomationsOverlayHeight50() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/tr:oi-logo-white_SJwqB4Nfe.png,oh-50/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    path="medium_cafe_B1iTdD0C.jpg"
                )
                .overlayImage("logo-white_SJwqB4Nfe.png").overlayHeight(50)
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun transfomationsOverlayTextOverlayMadeEasy() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/tr:ot-overlay+made+easy/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    path="medium_cafe_B1iTdD0C.jpg"
                )
                .overlayText("overlay made easy")
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun transfomationsOverlayTextColor00aaff55() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/tr:ot-overlay+made+easy,otc-00AAFF55/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    path="medium_cafe_B1iTdD0C.jpg"
                )
                .overlayText("overlay made easy").overlayTextColor("00AAFF55")
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun transfomationsOverlayFontFamilyAbrilfatface() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/tr:ot-overlay+made+easy,otf-AbrilFatFace/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    path="medium_cafe_B1iTdD0C.jpg"
                )
                .overlayText("overlay made easy").overlayTextFontFamily(OverlayTextFont.ABRIL_FAT_FACE)
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun transfomationsOverlayFontFamilyAmarnath() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/tr:ot-overlay+made+easy,otf-Amarnath/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    path="medium_cafe_B1iTdD0C.jpg"
                )
                .overlayText("overlay made easy").overlayTextFontFamily(OverlayTextFont.AMARANTH)
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun transfomationsOverlayFontFamilyArvo() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/tr:ot-overlay+made+easy,otf-Arvo/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    path="medium_cafe_B1iTdD0C.jpg"
                )
                .overlayText("overlay made easy").overlayTextFontFamily(OverlayTextFont.ARVO)
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun transfomationsOverlayFontFamilyAudiowide() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/tr:ot-overlay+made+easy,otf-Audiowide/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    path="medium_cafe_B1iTdD0C.jpg"
                )
                .overlayText("overlay made easy").overlayTextFontFamily(OverlayTextFont.AUDIOWIDE)
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun transfomationsOverlayFontFamilyExo() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/tr:ot-overlay+made+easy,otf-exo/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    path="medium_cafe_B1iTdD0C.jpg"
                )
                .overlayText("overlay made easy").overlayTextFontFamily(OverlayTextFont.EXO)
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun transfomationsOverlayFontFamilyFredokaone() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/tr:ot-overlay+made+easy,otf-FredokaOne/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    path="medium_cafe_B1iTdD0C.jpg"
                )
                .overlayText("overlay made easy").overlayTextFontFamily(OverlayTextFont.FREDOKA_ONE)
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun transfomationsOverlayFontFamilyKanit() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/tr:ot-overlay+made+easy,otf-Kanit/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    path="medium_cafe_B1iTdD0C.jpg"
                )
                .overlayText("overlay made easy").overlayTextFontFamily(OverlayTextFont.KANIT)
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun transfomationsOverlayFontFamilyLato() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/tr:ot-overlay+made+easy,otf-Lato/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    path="medium_cafe_B1iTdD0C.jpg"
                )
                .overlayText("overlay made easy").overlayTextFontFamily(OverlayTextFont.LATO)
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun transfomationsOverlayFontFamilyLobster() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/tr:ot-overlay+made+easy,otf-Lobster/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    path="medium_cafe_B1iTdD0C.jpg"
                )
                .overlayText("overlay made easy").overlayTextFontFamily(OverlayTextFont.LOBSTER)
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun transfomationsOverlayFontFamilyLora() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/tr:ot-overlay+made+easy,otf-Lora/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    path="medium_cafe_B1iTdD0C.jpg"
                )
                .overlayText("overlay made easy").overlayTextFontFamily(OverlayTextFont.LORA)
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun transfomationsOverlayFontFamilyMonoton() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/tr:ot-overlay+made+easy,otf-Monoton/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    path="medium_cafe_B1iTdD0C.jpg"
                )
                .overlayText("overlay made easy").overlayTextFontFamily(OverlayTextFont.MONOTON)
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun transfomationsOverlayFontFamilyMontserrat() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/tr:ot-overlay+made+easy,otf-Montserrat/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    path="medium_cafe_B1iTdD0C.jpg"
                )
                .overlayText("overlay made easy").overlayTextFontFamily(OverlayTextFont.MONTSERRAT)
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun transfomationsOverlayFontFamilyPt_mono() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/tr:ot-overlay+made+easy,otf-PT_Mono/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    path="medium_cafe_B1iTdD0C.jpg"
                )
                .overlayText("overlay made easy").overlayTextFontFamily(OverlayTextFont.PT_MONO)
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun transfomationsOverlayFontFamilyPt_serif() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/tr:ot-overlay+made+easy,otf-PT_Serif/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    path="medium_cafe_B1iTdD0C.jpg"
                )
                .overlayText("overlay made easy").overlayTextFontFamily(OverlayTextFont.PT_SERIF)
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun transfomationsOverlayFontFamilyOpensans() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/tr:ot-overlay+made+easy,otf-OpenSans/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    path="medium_cafe_B1iTdD0C.jpg"
                )
                .overlayText("overlay made easy").overlayTextFontFamily(OverlayTextFont.OPEN_SANS)
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun transfomationsOverlayFontFamilyRoboto() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/tr:ot-overlay+made+easy,otf-Roboto/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    path="medium_cafe_B1iTdD0C.jpg"
                )
                .overlayText("overlay made easy").overlayTextFontFamily(OverlayTextFont.ROBOTO)
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun transfomationsOverlayFontFamilyStagoldstandard() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/tr:ot-overlay+made+easy,otf-stagOldStandard/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    path="medium_cafe_B1iTdD0C.jpg"
                )
                .overlayText("overlay made easy").overlayTextFontFamily(OverlayTextFont.STAG_OLD_STANDARD)
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun transfomationsOverlayFontFamilyUbuntu() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/tr:ot-overlay+made+easy,otf-Ubuntu/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    path="medium_cafe_B1iTdD0C.jpg"
                )
                .overlayText("overlay made easy").overlayTextFontFamily(OverlayTextFont.UBUNTU)
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun transfomationsOverlayFontFamilyVollkorn() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/tr:ot-overlay+made+easy,otf-Vollkorn/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    path="medium_cafe_B1iTdD0C.jpg"
                )
                .overlayText("overlay made easy").overlayTextFontFamily(OverlayTextFont.VOLLKORN)
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun transfomationsOverlayFontSize45() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/tr:ot-overlay+made+easy,ots-45/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    path="medium_cafe_B1iTdD0C.jpg"
                )
                .overlayText("overlay made easy").overlayTextFontSize(45)
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun transfomationsOverlayTextTypographyBold() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/tr:ot-overlay+made+easy,ott-b/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    path="medium_cafe_B1iTdD0C.jpg"
                )
                .overlayText("overlay made easy").overlayTextTypography(OverlayTextTypography.BOLD)
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun transfomationsOverlayTextTypographyItalics() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/tr:ot-overlay+made+easy,ott-i/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    path="medium_cafe_B1iTdD0C.jpg"
                )
                .overlayText("overlay made easy").overlayTextTypography(OverlayTextTypography.ITALICS)
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun transfomationsOverlayTextTypographyBoldItalics() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/tr:ot-overlay+made+easy,ott-bi/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    path="medium_cafe_B1iTdD0C.jpg"
                )
                .overlayText("overlay made easy").overlayTextTypography(OverlayTextTypography.BOLD_ITALICS)
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun transfomationsOverlayTextAlpha5() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/tr:ot-overlay+made+easy,oa-5/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    path="medium_cafe_B1iTdD0C.jpg"
                )
                .overlayText("overlay made easy").overlayAlpha(5)
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun transfomationsOverlayBackground00aaff55() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/tr:obg-00AAFF55/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    path="medium_cafe_B1iTdD0C.jpg"
                )
                .overlayBackground("00AAFF55")
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun transfomationsColorProfile() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/tr:cp-true/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    path="medium_cafe_B1iTdD0C.jpg"
                )
                .colorProfile(true)
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun transfomationsMetadata() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/tr:md-true/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    path="medium_cafe_B1iTdD0C.jpg"
                )
                .metadata(true)
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun transfomationsRotationAuto() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/tr:rt-auto/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    path="medium_cafe_B1iTdD0C.jpg"
                )
                .rotation(Rotation.AUTO)
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun transfomationsRotation0() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/tr:rt-0/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    path="medium_cafe_B1iTdD0C.jpg"
                )
                .rotation(Rotation.VALUE_0)
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun transfomationsRotation90() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/tr:rt-90/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    path="medium_cafe_B1iTdD0C.jpg"
                )
                .rotation(Rotation.VALUE_90)
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun transfomationsRotation180() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/tr:rt-180/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    path="medium_cafe_B1iTdD0C.jpg"
                )
                .rotation(Rotation.VALUE_180)
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun transfomationsRotation270() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/tr:rt-270/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    path="medium_cafe_B1iTdD0C.jpg"
                )
                .rotation(Rotation.VALUE_270)
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun transfomationsRotation360() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/tr:rt-360/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    path="medium_cafe_B1iTdD0C.jpg"
                )
                .rotation(Rotation.VALUE_360)
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun transfomationsRadius5() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/tr:r-5/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    path="medium_cafe_B1iTdD0C.jpg"
                )
                .radius(5)
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun transfomationsRound() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/tr:r-max/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    path="medium_cafe_B1iTdD0C.jpg"
                )
                .round()
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun transfomationsBackground00aaff55() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/tr:bg-00AAFF55/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    path="medium_cafe_B1iTdD0C.jpg"
                )
                .background("00AAFF55")
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun transfomationsBorder_5_00aaff55() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/tr:b-5_00AAFF55/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    path="medium_cafe_B1iTdD0C.jpg"
                )
                .border(5, "00AAFF55")
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun transfomationsContrast() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/tr:e-contrast/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    path="medium_cafe_B1iTdD0C.jpg"
                )
                .effectContrast(true)
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun transfomationsContrastFalse() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    path="medium_cafe_B1iTdD0C.jpg"
                )
                .effectContrast(false)
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun transfomationsSharpen5() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/tr:e-sharpen-5/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    path="medium_cafe_B1iTdD0C.jpg"
                )
                .effectSharpen(5)
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun transfomationsSharpen() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/tr:e-sharpen/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    path="medium_cafe_B1iTdD0C.jpg"
                )
                .effectSharpen()
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun transfomationsUsm() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/tr:e-usm-5.00-5.00-5.00-5.00/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    path="medium_cafe_B1iTdD0C.jpg"
                )
                .effectUSM(5f, 5f, 5f, 5f)
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun transfomationsChainTransformation() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/tr:h-300:rt-90/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    path="medium_cafe_B1iTdD0C.jpg"
                )
                .height(300).chainTransformation().rotation(Rotation.VALUE_90)
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun transfomationsCustomTransfomationW300() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/tr:w-300/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    path = "medium_cafe_B1iTdD0C.jpg"
                )
                .addCustomTransformation("w", "300")
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun transformationCustomQueryParameter() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}&x-test-header=Test"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    src="https://ik.imagekit.io/demo/medium_cafe_B1iTdD0C.jpg"
                )
                .addCustomQueryParameter(hashMapOf("x-test-header" to "Test"))
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun transformationSourceWithQuery() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}&tr=h-200"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    src = "https://ik.imagekit.io/demo/medium_cafe_B1iTdD0C.jpg?tr=h-300"
                )
                .height(200)
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun transformationSourceWithPathTransform() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}&tr=h-200"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    src = "https://ik.imagekit.io/demo/tr:h-300/medium_cafe_B1iTdD0C.jpg"
                )
                .height(200)
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun invalidURL() {
        val expectedTransformation = ""
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    urlEndpoint = "",
                    path = ""
                )
                .height(200)
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

//    @Test
//    fun justForCoverage(){
//        SignatureResponse("Token", "Signature")
//        UploadResponse("fileId", "Name", "URL", "Thumbnail", 300, 300, 123, "FileType", "FilePath", Array(1) {"tag"}, false, "0,0,20,20", "meta")
//
//        Assert.assertTrue(true)
//    }
}