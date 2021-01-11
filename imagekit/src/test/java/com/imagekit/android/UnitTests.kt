package com.imagekit.android

import android.app.Application
import android.content.SharedPreferences
import com.imagekit.android.entity.*
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
    fun basic_url_generation_path() {
        val actual = ImageKit.getInstance()
            .url(path = "medium_cafe_B1iTdD0C.jpg")
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun basic_url_generation_url_endpoint_and_path() {
        val actual = ImageKit.getInstance()
            .url(urlEndpoint = "https://ik.imagekit.io/demo", path = "medium_cafe_B1iTdD0C.jpg")
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun basic_url_generation_source() {
        val actual = ImageKit.getInstance()
            .url(src = "https://ik.imagekit.io/demo/medium_cafe_B1iTdD0C.jpg")
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_width_300() {
        val actual = ImageKit.getInstance()
            .url(path = "medium_cafe_B1iTdD0C.jpg")
            .width(width = 300)
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:w-300/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_height_300() {
        val actual = ImageKit.getInstance()
            .url(path = "medium_cafe_B1iTdD0C.jpg")
            .height(height = 300)
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:h-300/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_aspect_ratio_2_by_3() {
        val actual = ImageKit.getInstance()
            .url(path = "medium_cafe_B1iTdD0C.jpg")
            .aspectRatio(width = 2, height = 3)
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:ar-2-3/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_crop_maintain_ratio() {
        val actual = ImageKit.getInstance()
            .url(path = "medium_cafe_B1iTdD0C.jpg")
            .crop(cropType = CropType.MAINTAIN_RATIO)
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:c-maintain_ratio/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_crop_force() {
        val actual = ImageKit.getInstance()
            .url(path = "medium_cafe_B1iTdD0C.jpg")
            .crop(cropType = CropType.FORCE)
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:c-force/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_crop_at_least() {
        val actual = ImageKit.getInstance()
            .url(path = "medium_cafe_B1iTdD0C.jpg")
            .crop(cropType = CropType.AT_LEAST)
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:c-at_least/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_crop_at_max() {
        val actual = ImageKit.getInstance()
            .url(path = "medium_cafe_B1iTdD0C.jpg")
            .crop(cropType = CropType.AT_MAX)
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:c-at_max/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_crop_mode_resize() {
        val actual = ImageKit.getInstance()
            .url(path = "medium_cafe_B1iTdD0C.jpg")
            .cropMode(cropMode = CropMode.RESIZE)
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:cm-resize/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_crop_mode_extract() {
        val actual = ImageKit.getInstance()
            .url(path = "medium_cafe_B1iTdD0C.jpg")
            .cropMode(cropMode = CropMode.EXTRACT)
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:cm-extract/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_crop_mode_pad_extract() {
        val actual = ImageKit.getInstance()
            .url(path = "medium_cafe_B1iTdD0C.jpg")
            .cropMode(cropMode = CropMode.PAD_EXTRACT)
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:cm-pad_extract/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_crop_mode_pad_resize() {
        val actual = ImageKit.getInstance()
            .url(path = "medium_cafe_B1iTdD0C.jpg")
            .cropMode(cropMode = CropMode.PAD_RESIZE)
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:cm-pad_resize/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_focus_type_center() {
        val actual = ImageKit.getInstance()
            .url(path = "medium_cafe_B1iTdD0C.jpg")
            .focus(focusType = FocusType.CENTER)
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:fo-center/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_focus_type_top() {
        val actual = ImageKit.getInstance()
            .url(path = "medium_cafe_B1iTdD0C.jpg")
            .focus(focusType = FocusType.TOP)
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:fo-top/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_focus_type_left() {
        val actual = ImageKit.getInstance()
            .url(path = "medium_cafe_B1iTdD0C.jpg")
            .focus(focusType = FocusType.LEFT)
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:fo-left/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_focus_type_bottom() {
        val actual = ImageKit.getInstance()
            .url(path = "medium_cafe_B1iTdD0C.jpg")
            .focus(focusType = FocusType.BOTTOM)
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:fo-bottom/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_focus_type_right() {
        val actual = ImageKit.getInstance()
            .url(path = "medium_cafe_B1iTdD0C.jpg")
            .focus(focusType = FocusType.RIGHT)
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:fo-right/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_focus_type_top_left() {
        val actual = ImageKit.getInstance()
            .url(path = "medium_cafe_B1iTdD0C.jpg")
            .focus(focusType = FocusType.TOP_LEFT)
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:fo-top_left/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_focus_type_top_right() {
        val actual = ImageKit.getInstance()
            .url(path = "medium_cafe_B1iTdD0C.jpg")
            .focus(focusType = FocusType.TOP_RIGHT)
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:fo-top_right/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_focus_type_bottom_left() {
        val actual = ImageKit.getInstance()
            .url(path = "medium_cafe_B1iTdD0C.jpg")
            .focus(focusType = FocusType.BOTTOM_LEFT)
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:fo-bottom_left/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_focus_type_bottom_right() {
        val actual = ImageKit.getInstance()
            .url(path = "medium_cafe_B1iTdD0C.jpg")
            .focus(focusType = FocusType.BOTTOM_RIGHT)
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:fo-bottom_right/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_focus_type_auto() {
        val actual = ImageKit.getInstance()
            .url(path = "medium_cafe_B1iTdD0C.jpg")
            .focus(focusType = FocusType.AUTO)
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:fo-auto/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_focus_100_300() {
        val actual = ImageKit.getInstance()
            .url(path = "medium_cafe_B1iTdD0C.jpg")
            .width(width = 300)
            .height(height = 300)
            .cropMode(cropMode = CropMode.EXTRACT)
            .focus(x = 100, y = 300)
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:w-300,h-300,cm-extract,x-100,y-300/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_quality_50() {
        val actual = ImageKit.getInstance()
            .url(path = "medium_cafe_B1iTdD0C.jpg")
            .quality(quality = 50)
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:q-50/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_format_auto() {
        val actual = ImageKit.getInstance()
            .url(path = "medium_cafe_B1iTdD0C.jpg")
            .format(format = Format.AUTO)
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:f-auto/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_format_webp() {
        val actual = ImageKit.getInstance()
            .url(path = "medium_cafe_B1iTdD0C.jpg")
            .format(format = Format.WEBP)
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:f-webp/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_format_jpg() {
        val actual = ImageKit.getInstance()
            .url(path = "medium_cafe_B1iTdD0C.jpg")
            .format(format = Format.JPG)
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:f-jpg/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_format_jpeg() {
        val actual = ImageKit.getInstance()
            .url(path = "medium_cafe_B1iTdD0C.jpg")
            .format(format = Format.JPEG)
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:f-jpeg/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_format_png() {
        val actual = ImageKit.getInstance()
            .url(path = "medium_cafe_B1iTdD0C.jpg")
            .format(format = Format.PNG)
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:f-png/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_blur_50() {
        val actual = ImageKit.getInstance()
            .url(path = "medium_cafe_B1iTdD0C.jpg")
            .blur(blur = 50)
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:bl-50/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_grayscale() {
        val actual = ImageKit.getInstance()
            .url(path = "medium_cafe_B1iTdD0C.jpg")
            .effectGray(flag = true)
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:e-grayscale/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_dpr_2_5() {
        val actual = ImageKit.getInstance()
            .url(path = "medium_cafe_B1iTdD0C.jpg")
            .dpr(dpr = 2.5F)
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:dpr-2.50/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_named_transform_test() {
        val actual = ImageKit.getInstance()
            .url(path = "medium_cafe_B1iTdD0C.jpg")
            .named(namedTransformation = "test")
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:n-test/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_default_image_medium_cafe_b_1_i_td_d_0_c_jpg() {
        val actual = ImageKit.getInstance()
            .url(path = "non_existent_image.jpg")
            .defaultImage(defaultImage = "medium_cafe_B1iTdD0C.jpg")
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:di-medium_cafe_B1iTdD0C.jpg/non_existent_image.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_progressive() {
        val actual = ImageKit.getInstance()
            .url(path = "medium_cafe_B1iTdD0C.jpg")
            .progressive(flag = true)
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:pr-true/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_lossless() {
        val actual = ImageKit.getInstance()
            .url(path = "medium_cafe_B1iTdD0C.jpg")
            .lossless(flag = true)
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:lo-true/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_trim() {
        val actual = ImageKit.getInstance()
            .url(path = "medium_cafe_B1iTdD0C.jpg")
            .trim(flag = true)
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:t-true/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_trim_50() {
        val actual = ImageKit.getInstance()
            .url(path = "medium_cafe_B1iTdD0C.jpg")
            .trim(value = 50)
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:t-50/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_overlay_image_logo_white_s_jwq_b_4_nfe_png() {
        val actual = ImageKit.getInstance()
            .url(path = "medium_cafe_B1iTdD0C.jpg")
            .overlayImage(overlayImage = "logo-white_SJwqB4Nfe.png")
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:oi-logo-white_SJwqB4Nfe.png/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_overlay_image_quality_50() {
        val actual = ImageKit.getInstance()
            .url(path = "medium_cafe_B1iTdD0C.jpg")
            .overlayImage(overlayImage = "logo-white_SJwqB4Nfe.png")
            .overlayImageQuality(quality = 50)
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:oi-logo-white_SJwqB4Nfe.png,oiq-50/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_overlay_image_cropping() {
        val actual = ImageKit.getInstance()
            .url(path = "medium_cafe_B1iTdD0C.jpg")
            .overlayImage(overlayImage = "logo-white_SJwqB4Nfe.png")
            .overlayImageCropping(cropMode = CropMode.EXTRACT)
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:oi-logo-white_SJwqB4Nfe.png,oic-extract/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_overlay_trimming() {
        val actual = ImageKit.getInstance()
            .url(path = "medium_cafe_B1iTdD0C.jpg")
            .overlayImage(overlayImage = "logo-white_SJwqB4Nfe.png")
            .overlayImageTrim(overlayImageTrim = false)
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:oi-logo-white_SJwqB4Nfe.png,oit-false/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_overlay_aspect_ratio_4_3() {
        val actual = ImageKit.getInstance()
            .url(path = "medium_cafe_B1iTdD0C.jpg")
            .overlayImage(overlayImage = "logo-white_SJwqB4Nfe.png")
            .overlayImageAspectRatio(width = 4, height = 3)
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:oi-logo-white_SJwqB4Nfe.png,oiar-4-3/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_overlay_image_background_000000() {
        val actual = ImageKit.getInstance()
            .url(path = "medium_cafe_B1iTdD0C.jpg")
            .overlayImage(overlayImage = "logo-white_SJwqB4Nfe.png")
            .overlayImageBackground(overlayImageBackground = "000000")
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:oi-logo-white_SJwqB4Nfe.png,oibg-000000/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_overlay_image_border_3_000000() {
        val actual = ImageKit.getInstance()
            .url(path = "medium_cafe_B1iTdD0C.jpg")
            .overlayImage(overlayImage = "logo-white_SJwqB4Nfe.png")
            .overlayImageBorder(borderWidth = 3, borderColor = "000000")
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:oi-logo-white_SJwqB4Nfe.png,oib-3_000000/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_overlay_image_dpr_3_0() {
        val actual = ImageKit.getInstance()
            .url(path = "medium_cafe_B1iTdD0C.jpg")
            .overlayImage(overlayImage = "logo-white_SJwqB4Nfe.png")
            .overlayImageDPR(dpr = 3.0F)
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:oi-logo-white_SJwqB4Nfe.png,oidpr-3.00/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_overlay_focus_center() {
        val actual = ImageKit.getInstance()
            .url(path = "medium_cafe_B1iTdD0C.jpg")
            .overlayImage(overlayImage = "logo-white_SJwqB4Nfe.png")
            .overlayFocus(overlayFocus = OverlayFocusType.CENTER)
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:oi-logo-white_SJwqB4Nfe.png,ofo-center/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_overlay_focus_top() {
        val actual = ImageKit.getInstance()
            .url(path = "medium_cafe_B1iTdD0C.jpg")
            .overlayImage(overlayImage = "logo-white_SJwqB4Nfe.png")
            .overlayFocus(overlayFocus = OverlayFocusType.TOP)
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:oi-logo-white_SJwqB4Nfe.png,ofo-top/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_overlay_focus_left() {
        val actual = ImageKit.getInstance()
            .url(path = "medium_cafe_B1iTdD0C.jpg")
            .overlayImage(overlayImage = "logo-white_SJwqB4Nfe.png")
            .overlayFocus(overlayFocus = OverlayFocusType.LEFT)
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:oi-logo-white_SJwqB4Nfe.png,ofo-left/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_overlay_focus_bottom() {
        val actual = ImageKit.getInstance()
            .url(path = "medium_cafe_B1iTdD0C.jpg")
            .overlayImage(overlayImage = "logo-white_SJwqB4Nfe.png")
            .overlayFocus(overlayFocus = OverlayFocusType.BOTTOM)
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:oi-logo-white_SJwqB4Nfe.png,ofo-bottom/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_overlay_focus_right() {
        val actual = ImageKit.getInstance()
            .url(path = "medium_cafe_B1iTdD0C.jpg")
            .overlayImage(overlayImage = "logo-white_SJwqB4Nfe.png")
            .overlayFocus(overlayFocus = OverlayFocusType.RIGHT)
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:oi-logo-white_SJwqB4Nfe.png,ofo-right/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_overlay_focus_top_left() {
        val actual = ImageKit.getInstance()
            .url(path = "medium_cafe_B1iTdD0C.jpg")
            .overlayImage(overlayImage = "logo-white_SJwqB4Nfe.png")
            .overlayFocus(overlayFocus = OverlayFocusType.TOP_LEFT)
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:oi-logo-white_SJwqB4Nfe.png,ofo-top_left/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_overlay_focus_top_right() {
        val actual = ImageKit.getInstance()
            .url(path = "medium_cafe_B1iTdD0C.jpg")
            .overlayImage(overlayImage = "logo-white_SJwqB4Nfe.png")
            .overlayFocus(overlayFocus = OverlayFocusType.TOP_RIGHT)
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:oi-logo-white_SJwqB4Nfe.png,ofo-top_right/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_overlay_focus_bottom_left() {
        val actual = ImageKit.getInstance()
            .url(path = "medium_cafe_B1iTdD0C.jpg")
            .overlayImage(overlayImage = "logo-white_SJwqB4Nfe.png")
            .overlayFocus(overlayFocus = OverlayFocusType.BOTTOM_LEFT)
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:oi-logo-white_SJwqB4Nfe.png,ofo-bottom_left/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_overlay_focus_bottom_right() {
        val actual = ImageKit.getInstance()
            .url(path = "medium_cafe_B1iTdD0C.jpg")
            .overlayImage(overlayImage = "logo-white_SJwqB4Nfe.png")
            .overlayFocus(overlayFocus = OverlayFocusType.BOTTOM_RIGHT)
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:oi-logo-white_SJwqB4Nfe.png,ofo-bottom_right/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_overlay_x_50() {
        val actual = ImageKit.getInstance()
            .url(path = "medium_cafe_B1iTdD0C.jpg")
            .overlayImage(overlayImage = "logo-white_SJwqB4Nfe.png")
            .overlayX(overlayX = 50)
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:oi-logo-white_SJwqB4Nfe.png,ox-50/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_overlay_y_50() {
        val actual = ImageKit.getInstance()
            .url(path = "medium_cafe_B1iTdD0C.jpg")
            .overlayImage(overlayImage = "logo-white_SJwqB4Nfe.png")
            .overlayY(overlayY = 50)
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:oi-logo-white_SJwqB4Nfe.png,oy-50/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_overlay_width_50() {
        val actual = ImageKit.getInstance()
            .url(path = "medium_cafe_B1iTdD0C.jpg")
            .overlayImage(overlayImage = "logo-white_SJwqB4Nfe.png")
            .overlayWidth(overlayWidth = 50)
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:oi-logo-white_SJwqB4Nfe.png,ow-50/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_overlay_height_50() {
        val actual = ImageKit.getInstance()
            .url(path = "medium_cafe_B1iTdD0C.jpg")
            .overlayImage(overlayImage = "logo-white_SJwqB4Nfe.png")
            .overlayHeight(overlayHeight = 50)
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:oi-logo-white_SJwqB4Nfe.png,oh-50/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_overlay_text_overlay_made_easy() {
        val actual = ImageKit.getInstance()
            .url(path = "medium_cafe_B1iTdD0C.jpg")
            .overlayText(overlayText = "overlay made easy")
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:ot-overlay+made+easy/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_overlay_text_encoded_b_3_zlcmxhe_s_bt_yw_rl_ig_vhc_3_k() {
        val actual = ImageKit.getInstance()
            .url(path = "medium_cafe_B1iTdD0C.jpg")
            .overlayTextEncoded(overlayTextEncoded = "b3ZlcmxheSBtYWRlIGVhc3k=")
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:ote-b3ZlcmxheSBtYWRlIGVhc3k%3D/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_overlay_text_color_00_aaff_55() {
        val actual = ImageKit.getInstance()
            .url(path = "medium_cafe_B1iTdD0C.jpg")
            .overlayText(overlayText = "overlay made easy")
            .overlayTextColor(overlayTextColor = "00AAFF55")
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:ot-overlay+made+easy,otc-00AAFF55/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_overlay_text_width_200_px() {
        val actual = ImageKit.getInstance()
            .url(path = "medium_cafe_B1iTdD0C.jpg")
            .overlayText(overlayText = "overlay made easy")
            .overlayTextFontSize(overlayTextFontSize = 45)
            .overlayTextWidth(width = 200)
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:ot-overlay+made+easy,ots-45,otw-200/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_overlay_text_padding_40_px() {
        val actual = ImageKit.getInstance()
            .url(path = "medium_cafe_B1iTdD0C.jpg")
            .overlayText(overlayText = "overlay made easy")
            .overlayTextFontSize(overlayTextFontSize = 45)
            .overlayTextWidth(width = 200)
            .overlayTextPadding(overlayTextPadding = 40)
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:ot-overlay+made+easy,ots-45,otw-200,otp-40/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_overlay_text_padding_25_px_75_px() {
        val actual = ImageKit.getInstance()
            .url(path = "medium_cafe_B1iTdD0C.jpg")
            .overlayText(overlayText = "overlay made easy")
            .overlayTextFontSize(overlayTextFontSize = 45)
            .overlayTextWidth(width = 200)
            .overlayTextPadding(verticalPadding = 25, horizontalPadding = 75)
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:ot-overlay+made+easy,ots-45,otw-200,otp-25_75/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_overlay_text_padding_25_px_75_px_60_px() {
        val actual = ImageKit.getInstance()
            .url(path = "medium_cafe_B1iTdD0C.jpg")
            .overlayText(overlayText = "overlay made easy")
            .overlayTextFontSize(overlayTextFontSize = 45)
            .overlayTextWidth(width = 200)
            .overlayTextPadding(topPadding = 25, horizontalPadding = 75, bottomPadding = 60)
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:ot-overlay+made+easy,ots-45,otw-200,otp-25_75_60/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_overlay_text_padding_25_px_50_px_75_px_100_px() {
        val actual = ImageKit.getInstance()
            .url(path = "medium_cafe_B1iTdD0C.jpg")
            .overlayText(overlayText = "overlay made easy")
            .overlayTextFontSize(overlayTextFontSize = 45)
            .overlayTextWidth(width = 200)
            .overlayTextPadding(
                topPadding = 25,
                rightPadding = 50,
                bottomPadding = 75,
                leftPadding = 100
            )
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:ot-overlay+made+easy,ots-45,otw-200,otp-25_50_75_100/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_overlay_text_inner_alignment() {
        val actual = ImageKit.getInstance()
            .url(path = "medium_cafe_B1iTdD0C.jpg")
            .overlayText(overlayText = "overlay made easy")
            .overlayTextFontSize(overlayTextFontSize = 45)
            .overlayTextWidth(width = 200)
            .overlayTextInnerAlignment(overlayTextInnerAlignment = OverlayTextInnerAlignment.RIGHT)
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:ot-overlay+made+easy,ots-45,otw-200,otia-right/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_overlay_text_background_color_00_aaff_55() {
        val actual = ImageKit.getInstance()
            .url(path = "medium_cafe_B1iTdD0C.jpg")
            .overlayText(overlayText = "overlay made easy")
            .overlayTextBackground(overlayTextColor = "00AAFF55")
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:ot-overlay+made+easy,otbg-00AAFF55/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_overlay_circle() {
        val actual = ImageKit.getInstance()
            .url(path = "medium_cafe_B1iTdD0C.jpg")
            .overlayBackground(overlayBackground = "FFFFFF80")
            .overlayRadius(radius = 150)
            .overlayHeight(overlayHeight = 300)
            .overlayWidth(overlayWidth = 300)
            .overlayFocus(overlayFocus = OverlayFocusType.CENTER)
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:obg-FFFFFF80,or-150,oh-300,ow-300,ofo-center/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_overlay_text_transparency_50() {
        val actual = ImageKit.getInstance()
            .url(path = "medium_cafe_B1iTdD0C.jpg")
            .overlayText(overlayText = "overlay made easy")
            .overlayTextTransparency(overlayTextTransparency = 50)
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:ot-overlay+made+easy,oa-50/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_overlay_font_family_abril_fat_face() {
        val actual = ImageKit.getInstance()
            .url(path = "medium_cafe_B1iTdD0C.jpg")
            .overlayText(overlayText = "overlay made easy")
            .overlayTextFontFamily(overlayTextFontFamily = OverlayTextFont.ABRIL_FAT_FACE)
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:ot-overlay+made+easy,otf-AbrilFatFace/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_overlay_font_family_amarnath() {
        val actual = ImageKit.getInstance()
            .url(path = "medium_cafe_B1iTdD0C.jpg")
            .overlayText(overlayText = "overlay made easy")
            .overlayTextFontFamily(overlayTextFontFamily = OverlayTextFont.AMARANTH)
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:ot-overlay+made+easy,otf-Amarnath/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_overlay_font_family_arvo() {
        val actual = ImageKit.getInstance()
            .url(path = "medium_cafe_B1iTdD0C.jpg")
            .overlayText(overlayText = "overlay made easy")
            .overlayTextFontFamily(overlayTextFontFamily = OverlayTextFont.ARVO)
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:ot-overlay+made+easy,otf-Arvo/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_overlay_font_family_audiowide() {
        val actual = ImageKit.getInstance()
            .url(path = "medium_cafe_B1iTdD0C.jpg")
            .overlayText(overlayText = "overlay made easy")
            .overlayTextFontFamily(overlayTextFontFamily = OverlayTextFont.AUDIOWIDE)
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:ot-overlay+made+easy,otf-Audiowide/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_overlay_font_family_exo() {
        val actual = ImageKit.getInstance()
            .url(path = "medium_cafe_B1iTdD0C.jpg")
            .overlayText(overlayText = "overlay made easy")
            .overlayTextFontFamily(overlayTextFontFamily = OverlayTextFont.EXO)
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:ot-overlay+made+easy,otf-exo/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_overlay_font_family_fredoka_one() {
        val actual = ImageKit.getInstance()
            .url(path = "medium_cafe_B1iTdD0C.jpg")
            .overlayText(overlayText = "overlay made easy")
            .overlayTextFontFamily(overlayTextFontFamily = OverlayTextFont.FREDOKA_ONE)
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:ot-overlay+made+easy,otf-FredokaOne/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_overlay_font_family_kanit() {
        val actual = ImageKit.getInstance()
            .url(path = "medium_cafe_B1iTdD0C.jpg")
            .overlayText(overlayText = "overlay made easy")
            .overlayTextFontFamily(overlayTextFontFamily = OverlayTextFont.KANIT)
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:ot-overlay+made+easy,otf-Kanit/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_overlay_font_family_lato() {
        val actual = ImageKit.getInstance()
            .url(path = "medium_cafe_B1iTdD0C.jpg")
            .overlayText(overlayText = "overlay made easy")
            .overlayTextFontFamily(overlayTextFontFamily = OverlayTextFont.LATO)
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:ot-overlay+made+easy,otf-Lato/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_overlay_font_family_lobster() {
        val actual = ImageKit.getInstance()
            .url(path = "medium_cafe_B1iTdD0C.jpg")
            .overlayText(overlayText = "overlay made easy")
            .overlayTextFontFamily(overlayTextFontFamily = OverlayTextFont.LOBSTER)
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:ot-overlay+made+easy,otf-Lobster/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_overlay_font_family_lora() {
        val actual = ImageKit.getInstance()
            .url(path = "medium_cafe_B1iTdD0C.jpg")
            .overlayText(overlayText = "overlay made easy")
            .overlayTextFontFamily(overlayTextFontFamily = OverlayTextFont.LORA)
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:ot-overlay+made+easy,otf-Lora/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_overlay_font_family_monoton() {
        val actual = ImageKit.getInstance()
            .url(path = "medium_cafe_B1iTdD0C.jpg")
            .overlayText(overlayText = "overlay made easy")
            .overlayTextFontFamily(overlayTextFontFamily = OverlayTextFont.MONOTON)
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:ot-overlay+made+easy,otf-Monoton/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_overlay_font_family_montserrat() {
        val actual = ImageKit.getInstance()
            .url(path = "medium_cafe_B1iTdD0C.jpg")
            .overlayText(overlayText = "overlay made easy")
            .overlayTextFontFamily(overlayTextFontFamily = OverlayTextFont.MONTSERRAT)
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:ot-overlay+made+easy,otf-Montserrat/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_overlay_font_family_pt_mono() {
        val actual = ImageKit.getInstance()
            .url(path = "medium_cafe_B1iTdD0C.jpg")
            .overlayText(overlayText = "overlay made easy")
            .overlayTextFontFamily(overlayTextFontFamily = OverlayTextFont.PT_MONO)
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:ot-overlay+made+easy,otf-PT_Mono/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_overlay_font_family_pt_serif() {
        val actual = ImageKit.getInstance()
            .url(path = "medium_cafe_B1iTdD0C.jpg")
            .overlayText(overlayText = "overlay made easy")
            .overlayTextFontFamily(overlayTextFontFamily = OverlayTextFont.PT_SERIF)
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:ot-overlay+made+easy,otf-PT_Serif/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_overlay_font_family_open_sans() {
        val actual = ImageKit.getInstance()
            .url(path = "medium_cafe_B1iTdD0C.jpg")
            .overlayText(overlayText = "overlay made easy")
            .overlayTextFontFamily(overlayTextFontFamily = OverlayTextFont.OPEN_SANS)
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:ot-overlay+made+easy,otf-OpenSans/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_overlay_font_family_roboto() {
        val actual = ImageKit.getInstance()
            .url(path = "medium_cafe_B1iTdD0C.jpg")
            .overlayText(overlayText = "overlay made easy")
            .overlayTextFontFamily(overlayTextFontFamily = OverlayTextFont.ROBOTO)
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:ot-overlay+made+easy,otf-Roboto/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_overlay_font_family_stag_old_standard() {
        val actual = ImageKit.getInstance()
            .url(path = "medium_cafe_B1iTdD0C.jpg")
            .overlayText(overlayText = "overlay made easy")
            .overlayTextFontFamily(overlayTextFontFamily = OverlayTextFont.STAG_OLD_STANDARD)
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:ot-overlay+made+easy,otf-stagOldStandard/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_overlay_font_family_ubuntu() {
        val actual = ImageKit.getInstance()
            .url(path = "medium_cafe_B1iTdD0C.jpg")
            .overlayText(overlayText = "overlay made easy")
            .overlayTextFontFamily(overlayTextFontFamily = OverlayTextFont.UBUNTU)
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:ot-overlay+made+easy,otf-Ubuntu/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_overlay_font_family_vollkorn() {
        val actual = ImageKit.getInstance()
            .url(path = "medium_cafe_B1iTdD0C.jpg")
            .overlayText(overlayText = "overlay made easy")
            .overlayTextFontFamily(overlayTextFontFamily = OverlayTextFont.VOLLKORN)
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:ot-overlay+made+easy,otf-Vollkorn/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_overlay_font_size_45() {
        val actual = ImageKit.getInstance()
            .url(path = "medium_cafe_B1iTdD0C.jpg")
            .overlayText(overlayText = "overlay made easy")
            .overlayTextFontSize(overlayTextFontSize = 45)
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:ot-overlay+made+easy,ots-45/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_overlay_text_typography_bold() {
        val actual = ImageKit.getInstance()
            .url(path = "medium_cafe_B1iTdD0C.jpg")
            .overlayText(overlayText = "overlay made easy")
            .overlayTextTypography(overlayTextTypography = OverlayTextTypography.BOLD)
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:ot-overlay+made+easy,ott-b/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_overlay_text_typography_italics() {
        val actual = ImageKit.getInstance()
            .url(path = "medium_cafe_B1iTdD0C.jpg")
            .overlayText(overlayText = "overlay made easy")
            .overlayTextTypography(overlayTextTypography = OverlayTextTypography.ITALICS)
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:ot-overlay+made+easy,ott-i/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_overlay_text_typography_bold_italics() {
        val actual = ImageKit.getInstance()
            .url(path = "medium_cafe_B1iTdD0C.jpg")
            .overlayText(overlayText = "overlay made easy")
            .overlayTextTypography(overlayTextTypography = OverlayTextTypography.BOLD_ITALICS)
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:ot-overlay+made+easy,ott-bi/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_overlay_text_alpha_5() {
        val actual = ImageKit.getInstance()
            .url(path = "medium_cafe_B1iTdD0C.jpg")
            .overlayText(overlayText = "overlay made easy")
            .overlayAlpha(overlayAlpha = 5)
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:ot-overlay+made+easy,oa-5/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_overlay_background_00_aaff_55() {
        val actual = ImageKit.getInstance()
            .url(path = "medium_cafe_B1iTdD0C.jpg")
            .overlayBackground(overlayBackground = "00AAFF55")
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:obg-00AAFF55/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_color_profile() {
        val actual = ImageKit.getInstance()
            .url(path = "medium_cafe_B1iTdD0C.jpg")
            .colorProfile(flag = true)
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:cp-true/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_metadata() {
        val actual = ImageKit.getInstance()
            .url(path = "medium_cafe_B1iTdD0C.jpg")
            .metadata(flag = true)
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:md-true/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_rotation_auto() {
        val actual = ImageKit.getInstance()
            .url(path = "medium_cafe_B1iTdD0C.jpg")
            .rotation(rotation = Rotation.AUTO)
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:rt-auto/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_rotation_0() {
        val actual = ImageKit.getInstance()
            .url(path = "medium_cafe_B1iTdD0C.jpg")
            .rotation(rotation = Rotation.VALUE_0)
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:rt-0/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_rotation_90() {
        val actual = ImageKit.getInstance()
            .url(path = "medium_cafe_B1iTdD0C.jpg")
            .rotation(rotation = Rotation.VALUE_90)
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:rt-90/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_rotation_180() {
        val actual = ImageKit.getInstance()
            .url(path = "medium_cafe_B1iTdD0C.jpg")
            .rotation(rotation = Rotation.VALUE_180)
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:rt-180/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_rotation_270() {
        val actual = ImageKit.getInstance()
            .url(path = "medium_cafe_B1iTdD0C.jpg")
            .rotation(rotation = Rotation.VALUE_270)
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:rt-270/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_rotation_360() {
        val actual = ImageKit.getInstance()
            .url(path = "medium_cafe_B1iTdD0C.jpg")
            .rotation(rotation = Rotation.VALUE_360)
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:rt-360/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_radius_5() {
        val actual = ImageKit.getInstance()
            .url(path = "medium_cafe_B1iTdD0C.jpg")
            .radius(radius = 5)
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:r-5/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_round() {
        val actual = ImageKit.getInstance()
            .url(path = "medium_cafe_B1iTdD0C.jpg")
            .round()
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:r-max/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_background_00_aaff_55() {
        val actual = ImageKit.getInstance()
            .url(path = "medium_cafe_B1iTdD0C.jpg")
            .background(backgroundColor = "00AAFF55")
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:bg-00AAFF55/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_border_5_00_aaff_55() {
        val actual = ImageKit.getInstance()
            .url(path = "medium_cafe_B1iTdD0C.jpg")
            .border(borderWidth = 5, borderColor = "00AAFF55")
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:b-5_00AAFF55/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_contrast() {
        val actual = ImageKit.getInstance()
            .url(path = "medium_cafe_B1iTdD0C.jpg")
            .effectContrast(flag = true)
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:e-contrast/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_sharpen_5() {
        val actual = ImageKit.getInstance()
            .url(path = "medium_cafe_B1iTdD0C.jpg")
            .effectSharpen(value = 5)
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:e-sharpen-5/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_usm() {
        val actual = ImageKit.getInstance()
            .url(path = "medium_cafe_B1iTdD0C.jpg")
            .effectUSM(radius = 5F, sigma = 5F, amount = 5F, threshold = 5F)
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:e-usm-5.00-5.00-5.00-5.00/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_chain_transformation() {
        val actual = ImageKit.getInstance()
            .url(urlEndpoint = "https://ik.imagekit.io/demo", path = "medium_cafe_B1iTdD0C.jpg")
            .height(height = 300)
            .chainTransformation()
            .rotation(rotation = Rotation.VALUE_90)
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:h-300:rt-90/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun transformations_custom_transfomation_w_300() {
        val actual = ImageKit.getInstance()
            .url(urlEndpoint = "https://ik.imagekit.io/demo", path = "medium_cafe_B1iTdD0C.jpg")
            .addCustomTransformation(key = "w", value = "300")
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:w-300/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun image_path_with_transformations_height_300_width_300_and_rotate_90_path_transformations() {
        val actual = ImageKit.getInstance()
            .url(urlEndpoint = "https://ik.imagekit.io/demo", path = "medium_cafe_B1iTdD0C.jpg")
            .height(height = 300)
            .width(width = 300)
            .chainTransformation()
            .rotation(rotation = Rotation.VALUE_90)
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/tr:h-300,w-300:rt-90/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}"
        )
    }

    @Test
    fun image_path_with_transformations_height_300_width_300_and_rotate_90_query_transformations() {
        val actual = ImageKit.getInstance()
            .url(
                urlEndpoint = "https://ik.imagekit.io/demo",
                path = "medium_cafe_B1iTdD0C.jpg",
                transformationPosition = TransformationPosition.QUERY
            )
            .height(height = 300)
            .width(width = 300)
            .chainTransformation()
            .rotation(rotation = Rotation.VALUE_90)
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}&tr=h-300,w-300:rt-90"
        )
    }

    @Test
    fun src_with_transformations_height_300_width_300_and_rotate_90_without_transforms() {
        val actual = ImageKit.getInstance()
            .url(src = "https://ik.imagekit.io/demo/medium_cafe_B1iTdD0C.jpg")
            .height(height = 300)
            .width(width = 300)
            .chainTransformation()
            .rotation(rotation = Rotation.VALUE_90)
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}&tr=h-300,w-300:rt-90"
        )
    }

    @Test
    fun src_with_transformations_height_300_width_300_and_rotate_90_with_path_transforms() {
        val actual = ImageKit.getInstance()
            .url(src = "https://ik.imagekit.io/demo/tr:h-300.00,w-300.00:rt-90/medium_cafe_B1iTdD0C.jpg")
            .height(height = 300)
            .width(width = 300)
            .chainTransformation()
            .rotation(rotation = Rotation.VALUE_90)
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}&tr=h-300,w-300:rt-90"
        )
    }

    @Test
    fun src_with_transformations_height_300_width_300_and_rotate_90_with_query_transforms() {
        val actual = ImageKit.getInstance()
            .url(src = "https://ik.imagekit.io/demo/medium_cafe_B1iTdD0C.jpg?tr=h-300.00,w-300.00:rt-90")
            .height(height = 300)
            .width(width = 300)
            .chainTransformation()
            .rotation(rotation = Rotation.VALUE_90)
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/medium_cafe_B1iTdD0C.jpg?${ImageKit.IK_VERSION_KEY}=android-${BuildConfig.API_VERSION}&tr=h-300,w-300:rt-90"
        )
    }

    @Test
    fun src_with_transformations_height_300_width_300_and_rotate_90_empty_url() {
        val actual = ImageKit.getInstance()
            .url(src = "")
            .create()
        assertEquals(actual, "")
    }
}