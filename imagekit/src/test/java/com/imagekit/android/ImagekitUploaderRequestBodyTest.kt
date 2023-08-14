package com.imagekit.android

import com.google.gson.Gson
import com.imagekit.android.entity.UploadError
import com.imagekit.android.entity.UploadResponse
import com.imagekit.android.retrofit.NetworkManager
import okhttp3.MultipartReader
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import java.net.HttpURLConnection

@RunWith(RobolectricTestRunner::class)
class ImagekitUploaderRequestBodyTest : ImageKitCallback {
    private val mockWebServer: MockWebServer = MockWebServer()

    @Before
    fun setUp() {
        val context = RuntimeEnvironment.getApplication()
        mockWebServer.start()
        NetworkManager.setBaseUrl(mockWebServer.url("/").toString())
        ImageKit.init(
            context = context,
            publicKey = "dummy_public_key",
            urlEndpoint = "https://ik.imagekit.io/demo"
        )
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun uploadRequestBodyParameters() {
        mockWebServer.enqueue(MockResponse().setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody("{\"fileId\" : \"fileId\",\"name\": \"sample-image.jpg\"}"))

        val dummyUrl = "https://dummy.io/static/dummy.jpg"
        val dummyToken = "dummy_token"
        val file = "sample-image.jpg"
        val tagsList = arrayOf("test", "sdk")
        val destinationFolder = "/tmp/test"
        val coordinates = "10,10,100,100"
        val responseKeys = "tags,customCoordinates,isPrivateFile"
        val webhook = "https://dummy.io/hook"
        val extensionsList = listOf(
            mapOf("name" to "remove-bg", "options" to mapOf("add_shadow" to true)),
            mapOf("name" to "google-auto-tagging", "minConfidence" to 80, "maxTags" to 5),
        )
        val metadata = mapOf("device_name" to "Emulator", "uid" to 167434)

        ImageKit.getInstance().uploader().upload(
            file = dummyUrl,
            token = dummyToken,
            fileName = file,
            useUniqueFileName = true,
            tags = tagsList,
            folder = destinationFolder,
            isPrivateFile = false,
            customCoordinates = coordinates,
            responseFields = responseKeys,
            extensions = extensionsList,
            webhookUrl = webhook,
            overwriteFile = false,
            overwriteAITags = false,
            overwriteTags = true,
            overwriteCustomMetadata = true,
            customMetadata = metadata,
            imageKitCallback = this
        )
        val request = mockWebServer.takeRequest()
        kotlin.test.assertEquals("POST", request.method)
        val bodyParams = mutableMapOf<String, Any>()
        MultipartReader(request.body, request.headers["Content-Type"]?.split('=')?.get(1) ?: "").use {
            while (true) {
                it.nextPart()?.let { part ->
                    bodyParams[part.headers["Content-Disposition"]?.split('=')?.get(1)?.removeSurrounding("\"") ?: ""] = part.body.readUtf8()
                } ?: break
            }
        }
        kotlin.test.assertEquals(dummyUrl, bodyParams["file"])
        kotlin.test.assertEquals(dummyToken, bodyParams["token"].toString())
        kotlin.test.assertEquals(file, bodyParams["fileName"].toString())
        kotlin.test.assertEquals("true", bodyParams["useUniqueFileName"].toString())
        kotlin.test.assertEquals(tagsList.joinToString(","), bodyParams["tags"].toString())
        kotlin.test.assertEquals(destinationFolder, bodyParams["folder"].toString())
        kotlin.test.assertEquals("false", bodyParams["isPrivateFile"].toString())
        kotlin.test.assertEquals(coordinates, bodyParams["customCoordinates"].toString())
        kotlin.test.assertEquals(responseKeys, bodyParams["responseFields"].toString())
        kotlin.test.assertEquals(Gson().toJson(extensionsList), bodyParams["extensions"])
        kotlin.test.assertEquals(webhook, bodyParams["webhookUrl"].toString())
        kotlin.test.assertEquals("false", bodyParams["overwriteFile"].toString())
        kotlin.test.assertEquals("false", bodyParams["overwriteAITags"].toString())
        kotlin.test.assertEquals("true", bodyParams["overwriteTags"].toString())
        kotlin.test.assertEquals("true", bodyParams["overwriteCustomMetadata"].toString())
        kotlin.test.assertEquals(Gson().toJson(metadata), bodyParams["customMetadata"])
    }

    override fun onSuccess(uploadResponse: UploadResponse) {
    }

    override fun onError(uploadError: UploadError) {
    }
}