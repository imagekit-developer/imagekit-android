package com.imagekit.android

import android.app.Activity
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import com.imagekit.android.entity.CropMode
import com.imagekit.android.entity.FocusType
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.android.controller.ActivityController

@RunWith(RobolectricTestRunner::class)
class ResponsiveUrlTest {
    private lateinit var imageView: ImageView
    private lateinit var activityController: ActivityController<Activity>

    private val urlEndpoint = "https://ik.imagekit.io/demo"

    @Before
    fun setUp() {
        val testContext = RuntimeEnvironment.getApplication()
        activityController = Robolectric.buildActivity(Activity::class.java)
        activityController.create()
        val frameLayout = FrameLayout(activityController.get())
        imageView = ImageView(activityController.get()).apply {
            layoutParams = ViewGroup.LayoutParams(1366, 768)
        }
        frameLayout.addView(imageView)
        imageView.measure(1366, 768)
        imageView.layout(0, 0, 1366, 768)

        ImageKit.init(
            context = testContext,
            urlEndpoint = urlEndpoint
        )
    }

    @After
    fun tearDown() {
        activityController.destroy()
    }

    @Test
    fun setResponsiveDimensionsWithDefaultParams() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/medium_cafe_B1iTdD0C.jpg?x-test-header=Test&tr=w-1400,h-800,dpr-1.00,cm-resize,fo-center"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    src = "https://ik.imagekit.io/demo/medium_cafe_B1iTdD0C.jpg",
                )
                .setResponsive(view = imageView)
                .addCustomQueryParameter("x-test-header", "Test")
                .create()

        kotlin.test.assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun setResponsiveDimensionsWithCustomStepParam() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/medium_cafe_B1iTdD0C.jpg?x-test-header=Test&tr=w-1380,h-780,dpr-1.00,cm-resize,fo-center"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    src = "https://ik.imagekit.io/demo/medium_cafe_B1iTdD0C.jpg",
                )
                .setResponsive(
                    view = imageView,
                    step = 20
                )
                .addCustomQueryParameter("x-test-header", "Test")
                .create()

        kotlin.test.assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun setResponsiveDimensionsWithCustomMinMaxSizeParams() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/medium_cafe_B1iTdD0C.jpg?x-test-header=Test&tr=w-1000,h-900,dpr-1.00,cm-resize,fo-center"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    src = "https://ik.imagekit.io/demo/medium_cafe_B1iTdD0C.jpg",
                )
                .setResponsive(
                    view = imageView,
                    minSize = 900,
                    maxSize = 1000,
                )
                .addCustomQueryParameter("x-test-header", "Test")
                .create()

        kotlin.test.assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun setResponsiveDimensionsWithAllCustomParams() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/medium_cafe_B1iTdD0C.jpg?x-test-header=Test&tr=w-1050,h-850,dpr-1.00,cm-extract,fo-left"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    src = "https://ik.imagekit.io/demo/medium_cafe_B1iTdD0C.jpg",
                )
                .setResponsive(
                    view = imageView,
                    minSize = 850,
                    maxSize = 1050,
                    step = 50,
                    cropMode = CropMode.EXTRACT,
                    focus = FocusType.LEFT
                )
                .addCustomQueryParameter("x-test-header", "Test")
                .create()

        kotlin.test.assertEquals(expectedTransformation, actualTransformation)
    }
}