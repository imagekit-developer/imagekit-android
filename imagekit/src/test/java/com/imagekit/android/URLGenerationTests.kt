package com.imagekit.android

import android.app.Application
import android.content.SharedPreferences
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
class URLGenerationTests {

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
        )
    }

    @Test
    fun urlConstructionOverridingUrlendpointParameter() {
        val expectedTransformation =
            "https://ik.imagekit.io/modified_imagekitid/medium_cafe_B1iTdD0C.jpg?"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    path = "medium_cafe_B1iTdD0C.jpg",
                    urlEndpoint = "https://ik.imagekit.io/modified_imagekitid"
                )
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun urlConstructionRemovalOfDoubleSlashesInUrlendpoint() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/medium_cafe_B1iTdD0C.jpg?"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    path = "medium_cafe_B1iTdD0C.jpg",
                    urlEndpoint = "https://ik.imagekit.io/demo/"
                )
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun urlConstructionRemovalOfDoubleSlashesInPath() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/medium_cafe_B1iTdD0C.jpg?"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    path = "/medium_cafe_B1iTdD0C.jpg",
                    urlEndpoint = "https://ik.imagekit.io/demo"
                )
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }


    @Test
    fun urlConstructionNewTransformationParameter() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/tr:test-test/medium_cafe_B1iTdD0C.jpg?"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    path = "medium_cafe_B1iTdD0C.jpg",
                    urlEndpoint = "https://ik.imagekit.io/demo"
                )
                .addCustomTransformation("test", "test")
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun urlConstructionSdkVersionAsQueryParameter() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/medium_cafe_B1iTdD0C.jpg?"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    path = "medium_cafe_B1iTdD0C.jpg",
                    urlEndpoint = "https://ik.imagekit.io/demo"
                )
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun urlConstructionQueryTransformations() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/medium_cafe_B1iTdD0C.jpg?tr=w-300,h-300"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    path = "medium_cafe_B1iTdD0C.jpg",
                    transformationPosition = TransformationPosition.QUERY
                )
                .width(300)
                .height(300)
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun urlConstructionChainedTransformation() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/tr:h-300:rt-90/medium_cafe_B1iTdD0C.jpg?"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    path = "medium_cafe_B1iTdD0C.jpg"
                )
                .height(300)
                .chainTransformation()
                .rotation(Rotation.VALUE_90)
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun urlConstructionSourceUrl() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/medium_cafe_B1iTdD0C.jpg?"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    src = "https://ik.imagekit.io/demo/medium_cafe_B1iTdD0C.jpg"
                )
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun urlConstructionCustomQueryParameter() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/medium_cafe_B1iTdD0C.jpg?x-test-header=Test"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    src = "https://ik.imagekit.io/demo/medium_cafe_B1iTdD0C.jpg"
                )
                .addCustomQueryParameter("x-test-header", "Test")
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun urlConstructionCustomQueryParameterWithExisting() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/medium_cafe_B1iTdD0C.jpg?x-test=Test&x-test-header=Test"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    src = "https://ik.imagekit.io/demo/medium_cafe_B1iTdD0C.jpg?x-test=Test"
                )
                .addCustomQueryParameter("x-test-header", "Test")
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun urlConstructionSourceUrlWithPathTransforms() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/medium_cafe_B1iTdD0C.jpg?tr=h-300,w-300:rt-90"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    src = "https://ik.imagekit.io/demo/tr:h-300,w-300:rt-90/medium_cafe_B1iTdD0C.jpg"
                )
                .height(300)
                .width(300)
                .chainTransformation()
                .rotation(Rotation.VALUE_90)
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }

    @Test
    fun urlConstructionCustomQueryParameterWithTransforms() {
        val expectedTransformation =
            "https://ik.imagekit.io/demo/medium_cafe_B1iTdD0C.jpg?x-test-header=Test&tr=h-300,w-300:rt-90"
        val actualTransformation =
            ImageKit.getInstance()
                .url(
                    src = "https://ik.imagekit.io/demo/tr:h-300,w-300:rt-90/medium_cafe_B1iTdD0C.jpg"
                )
                .height(300)
                .width(300)
                .chainTransformation()
                .rotation(Rotation.VALUE_90)
                .addCustomQueryParameter("x-test-header", "Test")
                .create()

        assertEquals(expectedTransformation, actualTransformation)
    }
}