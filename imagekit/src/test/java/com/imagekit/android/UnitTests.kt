package com.imagekit.android

import android.app.Application
import android.content.SharedPreferences
import android.provider.ContactsContract.CommonDataKinds.Im
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
            "https://ik.imagekit.io/demo/medium_cafe_B1iTdD0C.jpg?"
        )
    }

    @Test
    fun basic_url_generation_url_endpoint_and_path() {
        val actual = ImageKit.getInstance()
            .url(urlEndpoint = "https://ik.imagekit.io/demo", path = "medium_cafe_B1iTdD0C.jpg")
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/medium_cafe_B1iTdD0C.jpg?"
        )
    }

    @Test
    fun basic_url_generation_source() {
        val actual = ImageKit.getInstance()
            .url(src = "https://ik.imagekit.io/demo/medium_cafe_B1iTdD0C.jpg")
            .create()
        assertEquals(
            actual,
            "https://ik.imagekit.io/demo/medium_cafe_B1iTdD0C.jpg?"
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
            "https://ik.imagekit.io/demo/tr:w-300/medium_cafe_B1iTdD0C.jpg?"
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
            "https://ik.imagekit.io/demo/tr:h-300/medium_cafe_B1iTdD0C.jpg?"
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
            "https://ik.imagekit.io/demo/tr:ar-2-3/medium_cafe_B1iTdD0C.jpg?"
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
            "https://ik.imagekit.io/demo/tr:c-maintain_ratio/medium_cafe_B1iTdD0C.jpg?"
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
            "https://ik.imagekit.io/demo/tr:c-force/medium_cafe_B1iTdD0C.jpg?"
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
            "https://ik.imagekit.io/demo/tr:c-at_least/medium_cafe_B1iTdD0C.jpg?"
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
            "https://ik.imagekit.io/demo/tr:c-at_max/medium_cafe_B1iTdD0C.jpg?"
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
            "https://ik.imagekit.io/demo/tr:cm-resize/medium_cafe_B1iTdD0C.jpg?"
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
            "https://ik.imagekit.io/demo/tr:cm-extract/medium_cafe_B1iTdD0C.jpg?"
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
            "https://ik.imagekit.io/demo/tr:cm-pad_extract/medium_cafe_B1iTdD0C.jpg?"
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
            "https://ik.imagekit.io/demo/tr:cm-pad_resize/medium_cafe_B1iTdD0C.jpg?"
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
            "https://ik.imagekit.io/demo/tr:fo-center/medium_cafe_B1iTdD0C.jpg?"
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
            "https://ik.imagekit.io/demo/tr:fo-top/medium_cafe_B1iTdD0C.jpg?"
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
            "https://ik.imagekit.io/demo/tr:fo-left/medium_cafe_B1iTdD0C.jpg?"
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
            "https://ik.imagekit.io/demo/tr:fo-bottom/medium_cafe_B1iTdD0C.jpg?"
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
            "https://ik.imagekit.io/demo/tr:fo-right/medium_cafe_B1iTdD0C.jpg?"
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
            "https://ik.imagekit.io/demo/tr:fo-top_left/medium_cafe_B1iTdD0C.jpg?"
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
            "https://ik.imagekit.io/demo/tr:fo-top_right/medium_cafe_B1iTdD0C.jpg?"
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
            "https://ik.imagekit.io/demo/tr:fo-bottom_left/medium_cafe_B1iTdD0C.jpg?"
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
            "https://ik.imagekit.io/demo/tr:fo-bottom_right/medium_cafe_B1iTdD0C.jpg?"
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
            "https://ik.imagekit.io/demo/tr:fo-auto/medium_cafe_B1iTdD0C.jpg?"
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
            "https://ik.imagekit.io/demo/tr:w-300,h-300,cm-extract,x-100,y-300/medium_cafe_B1iTdD0C.jpg?"
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
            "https://ik.imagekit.io/demo/tr:q-50/medium_cafe_B1iTdD0C.jpg?"
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
            "https://ik.imagekit.io/demo/tr:f-auto/medium_cafe_B1iTdD0C.jpg?"
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
            "https://ik.imagekit.io/demo/tr:f-webp/medium_cafe_B1iTdD0C.jpg?"
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
            "https://ik.imagekit.io/demo/tr:f-jpg/medium_cafe_B1iTdD0C.jpg?"
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
            "https://ik.imagekit.io/demo/tr:f-jpeg/medium_cafe_B1iTdD0C.jpg?"
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
            "https://ik.imagekit.io/demo/tr:f-png/medium_cafe_B1iTdD0C.jpg?"
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
            "https://ik.imagekit.io/demo/tr:bl-50/medium_cafe_B1iTdD0C.jpg?"
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
            "https://ik.imagekit.io/demo/tr:e-grayscale/medium_cafe_B1iTdD0C.jpg?"
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
            "https://ik.imagekit.io/demo/tr:dpr-2.50/medium_cafe_B1iTdD0C.jpg?"
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
            "https://ik.imagekit.io/demo/tr:n-test/medium_cafe_B1iTdD0C.jpg?"
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
            "https://ik.imagekit.io/demo/tr:di-medium_cafe_B1iTdD0C.jpg/non_existent_image.jpg?"
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
            "https://ik.imagekit.io/demo/tr:pr-true/medium_cafe_B1iTdD0C.jpg?"
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
            "https://ik.imagekit.io/demo/tr:lo-true/medium_cafe_B1iTdD0C.jpg?"
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
            "https://ik.imagekit.io/demo/tr:t-true/medium_cafe_B1iTdD0C.jpg?"
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
            "https://ik.imagekit.io/demo/tr:t-50/medium_cafe_B1iTdD0C.jpg?"
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
            "https://ik.imagekit.io/demo/tr:cp-true/medium_cafe_B1iTdD0C.jpg?"
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
            "https://ik.imagekit.io/demo/tr:md-true/medium_cafe_B1iTdD0C.jpg?"
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
            "https://ik.imagekit.io/demo/tr:rt-auto/medium_cafe_B1iTdD0C.jpg?"
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
            "https://ik.imagekit.io/demo/tr:rt-0/medium_cafe_B1iTdD0C.jpg?"
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
            "https://ik.imagekit.io/demo/tr:rt-90/medium_cafe_B1iTdD0C.jpg?"
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
            "https://ik.imagekit.io/demo/tr:rt-180/medium_cafe_B1iTdD0C.jpg?"
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
            "https://ik.imagekit.io/demo/tr:rt-270/medium_cafe_B1iTdD0C.jpg?"
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
            "https://ik.imagekit.io/demo/tr:rt-360/medium_cafe_B1iTdD0C.jpg?"
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
            "https://ik.imagekit.io/demo/tr:r-5/medium_cafe_B1iTdD0C.jpg?"
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
            "https://ik.imagekit.io/demo/tr:r-max/medium_cafe_B1iTdD0C.jpg?"
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
            "https://ik.imagekit.io/demo/tr:bg-00AAFF55/medium_cafe_B1iTdD0C.jpg?"
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
            "https://ik.imagekit.io/demo/tr:b-5_00AAFF55/medium_cafe_B1iTdD0C.jpg?"
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
            "https://ik.imagekit.io/demo/tr:e-contrast/medium_cafe_B1iTdD0C.jpg?"
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
            "https://ik.imagekit.io/demo/tr:e-sharpen-5/medium_cafe_B1iTdD0C.jpg?"
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
            "https://ik.imagekit.io/demo/tr:e-usm-5.00-5.00-5.00-5.00/medium_cafe_B1iTdD0C.jpg?"
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
            "https://ik.imagekit.io/demo/tr:h-300:rt-90/medium_cafe_B1iTdD0C.jpg?"
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
            "https://ik.imagekit.io/demo/tr:w-300/medium_cafe_B1iTdD0C.jpg?"
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
            "https://ik.imagekit.io/demo/tr:h-300,w-300:rt-90/medium_cafe_B1iTdD0C.jpg?"
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
            "https://ik.imagekit.io/demo/medium_cafe_B1iTdD0C.jpg?tr=h-300,w-300:rt-90"
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
            "https://ik.imagekit.io/demo/medium_cafe_B1iTdD0C.jpg?tr=h-300,w-300:rt-90"
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
            "https://ik.imagekit.io/demo/medium_cafe_B1iTdD0C.jpg?tr=h-300,w-300:rt-90"
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
            "https://ik.imagekit.io/demo/medium_cafe_B1iTdD0C.jpg?tr=h-300,w-300:rt-90"
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