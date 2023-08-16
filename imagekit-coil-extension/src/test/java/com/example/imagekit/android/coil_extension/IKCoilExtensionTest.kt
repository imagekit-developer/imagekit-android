package com.example.imagekit.android.coil_extension

import com.imagekit.android.ImageKit
import com.imagekit.android.entity.Rotation
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class IKCoilExtensionTest {

    @Before
    fun setUp() {
        ImageKit.init(
            context = RuntimeEnvironment.getApplication(),
            urlEndpoint = "https://ik.imagekit.io/demo"
        )
    }

    @Test
    fun createWithCoilUrlTest() {
        val expectedUrl = "https://ik.imagekit.io/demo/medium_cafe_B1iTdD0C.jpg?x-test-header=Test&tr=h-300,w-300,rt-90"
        val imageRequest = ImageKit.getInstance()
            .url(
                src = "https://ik.imagekit.io/demo/medium_cafe_B1iTdD0C.jpg"
            )
            .height(300)
            .width(300)
            .rotation(Rotation.VALUE_90)
            .addCustomQueryParameter("x-test-header", "Test")
            .createWithCoil()
            .build()

        kotlin.test.assertEquals(expectedUrl, imageRequest.data)
    }
}