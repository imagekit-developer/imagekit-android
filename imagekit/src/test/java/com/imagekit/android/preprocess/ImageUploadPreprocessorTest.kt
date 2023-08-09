package com.imagekit.android.preprocess

import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Point
import com.imagekit.android.ImageKit
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import java.io.File
import java.io.FileInputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder

@RunWith(RobolectricTestRunner::class)
class ImageUploadPreprocessorTest {
    private val urlEndpoint = "https://ik.imagekit.io/demo"
    private val testFilePath = "src/test/java/com/imagekit/android/sample-image.jpg"
    private lateinit var testBitmap: Bitmap
    lateinit var context: Application


    @Before
    fun setUp() {
        context = RuntimeEnvironment.getApplication()
        testBitmap = BitmapFactory.decodeFile(testFilePath)
        ImageKit.init(
            context = context,
            urlEndpoint = urlEndpoint
        )
    }

    @After
    fun tearDown() {
    }

    @Test
    fun imageDimensionsLimiterTest() {
        val limiter = ImageDimensionsLimiter(
            maxWidth = 300,
            maxHeight = 200
        )
        val outputBitmap = limiter.process(testBitmap)
        kotlin.test.assertEquals(300, outputBitmap.width)
        kotlin.test.assertEquals(200, outputBitmap.height)
    }

    @Test
    fun imageCropTest() {
        val cropper = ImageCrop(
            topLeft = Point(10, 20),
            bottomRight = Point(110, 120),
        )
        val outputBitmap = cropper.process(testBitmap)
        for (x in 10..110) {
            for (y in 20..120) {
                kotlin.test.assertEquals(
                    expected = testBitmap.getPixel(x, y),
                    actual = outputBitmap.getPixel(x - 10, y - 20),
                    message = "Pixel mismatch at Point ($x, $y)"
                )
            }
        }
    }

    @Test
    fun imageFilePreprocessOutputFormatTest() {
        val magicNumbers = mapOf(
            0x89.toByte() to "image/png",
            0xff.toByte() to "image/jpeg",
        )
        val preprocessor = ImageUploadPreprocessor.Builder()
            .format(Bitmap.CompressFormat.PNG)
            .build<File>()
        val outputFile = preprocessor.outputFile(File(testFilePath), "output", context)
        FileInputStream(outputFile.path).use { inputStream ->
            val buffer = ByteArray(1024)
            inputStream.read(buffer, 0, buffer.size)
            val magicNumber = ByteBuffer.wrap(buffer).order(ByteOrder.BIG_ENDIAN).get(0).toInt() and 0xff
            val mimeType = magicNumbers[magicNumber.toByte()]
            kotlin.test.assertEquals("image/png", mimeType)
        }
    }
}