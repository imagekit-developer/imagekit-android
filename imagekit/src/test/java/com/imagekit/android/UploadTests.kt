package com.imagekit.android

import android.app.Application
import android.content.SharedPreferences
import android.graphics.BitmapFactory
import android.media.Image
import com.imagekit.android.entity.SignatureResponse
import com.imagekit.android.entity.TransformationPosition
import com.imagekit.android.entity.UploadError
import com.imagekit.android.entity.UploadResponse
import com.imagekit.android.retrofit.SignatureApi
import com.imagekit.android.util.SharedPrefUtil
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.io.File
import java.io.FileInputStream
import java.lang.reflect.Field
import java.lang.reflect.Modifier
import java.net.HttpURLConnection
import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeUnit
import kotlin.test.assertEquals
import android.graphics.Bitmap
import com.imagekit.android.retrofit.ApiInterface
import com.imagekit.android.retrofit.BuildVersionQueryInterceptor
import com.imagekit.android.retrofit.NetworkManager
import com.imagekit.android.util.BitmapUtil
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.mockito.MockitoAnnotations
import org.powermock.api.mockito.PowerMockito
import org.powermock.core.classloader.annotations.PowerMockIgnore
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import kotlin.test.assertNull


@RunWith(PowerMockRunner::class)
@PowerMockIgnore("javax.net.ssl.*")
@PrepareForTest(NetworkManager::class)
class UploadTests {

    private val clientPublicKey: String = "Dummy public key"
    private val urlEndpoint = "https://ik.imagekit.io/demo"
    private var mockWebServer = MockWebServer()
    private var signatureApi: SignatureApi? = null
    private var mockPrefs: SharedPreferences? = null

    @Before
    fun init() {
        mockWebServer.start()

        mockPrefs = PowerMockito.mock(SharedPreferences::class.java)
        val mockContext = PowerMockito.mock(Application::class.java)
        val mockEditor = PowerMockito.mock(SharedPreferences.Editor::class.java)

        MockitoAnnotations.initMocks(this);
        Mockito.`when`(mockContext.getSharedPreferences(
            ArgumentMatchers.anyString(),
            ArgumentMatchers.anyInt()
        )).thenReturn(mockPrefs)

        Mockito.`when`<String>(mockPrefs!!.getString(
            ArgumentMatchers.eq("Client Authentication Endpoint"),
            ArgumentMatchers.anyString()
        )).thenReturn(mockWebServer.url("/temp/client-side-upload-signature").toString())

        Mockito.`when`<String>(mockPrefs!!.getString(
            ArgumentMatchers.eq("Client Public Key"),
            ArgumentMatchers.anyString()
        )).thenReturn(clientPublicKey)

        Mockito.`when`<SharedPreferences.Editor>(mockPrefs!!.edit()).thenReturn(mockEditor)

        Mockito.`when`<SharedPreferences.Editor>(mockEditor.putString(
            ArgumentMatchers.anyString(),
            ArgumentMatchers.anyString()
        )).thenReturn(mockEditor)

        Mockito.`when`<String>(mockContext.getString(
            ArgumentMatchers.eq(R.string.error_signature_generation_failed)
        )).thenReturn("Signature generation failed.")

        Mockito.`when`<String>(mockContext.getString(
            ArgumentMatchers.eq(R.string.error_authentication_endpoint_is_missing)
        )).thenReturn("Upload failed! Authentication endpoint is missing!")

        Mockito.`when`<String>(mockContext.getString(
            ArgumentMatchers.eq(R.string.error_file_not_found)
        )).thenReturn("The file you are trying to upload was not found.")

        PowerMockito.mockStatic(NetworkManager::class.java)
        Mockito.`when`<ApiInterface>(NetworkManager.getApiInterface()).thenReturn(mockRetrofit())

        val mockSharedPrefUtil = SharedPrefUtil(mockContext)
        signatureApi = SignatureApi(mockContext, mockSharedPrefUtil)

        ImageKit.init(
            context = mockContext,
            publicKey = clientPublicKey,
            urlEndpoint = urlEndpoint,
            transformationPosition = TransformationPosition.PATH,
            authenticationEndpoint = mockWebServer.url("/temp/client-side-upload-signature").toString()
        )
    }

    @After
    fun teardown() { mockWebServer.shutdown() }


    @Test
    fun emptyAuthUrl() {
        val future = CompletableFuture<String>()

        val mockSignatureResponse = MockResponse().setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody("{\"token\": \"Token\", \"signature\": \"Signature\"}")
        mockWebServer.enqueue(mockSignatureResponse)

        Mockito.`when`<String>(mockPrefs!!.getString(
            ArgumentMatchers.eq("Client Authentication Endpoint"),
            ArgumentMatchers.anyString()
        )).thenReturn("")

        ImageKit.getInstance().uploader().upload(
            file = "http://ik.imagekit.io/demo/img/default-image.jpg",
            fileName = "default-image.jpg",
            imageKitCallback = object : ImageKitCallback {

                override fun onSuccess(uploadResponse: UploadResponse) {
                    Assert.fail("Should not succeed")
                }

                override fun onError(uploadError: UploadError) {
                    assertEquals(uploadError.message, "Upload failed! Authentication endpoint is missing!")
                    assertEquals(uploadError.exception, true)
                    assertEquals(uploadError.statusCode, "SERVER_ERROR")
                    assertEquals(uploadError.statusNumber, 1500)
                    future.complete("DONE")
                }

            })
        future.get()
    }

    @Test
    fun uploadFile() {
        val future = CompletableFuture<String>()

        val mockSignatureResponse = MockResponse().setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody("{\"token\": \"Token\", \"signature\": \"Signature\"}")

        val mockUploadResponse = MockResponse().setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody("{\"fileId\" : \"fileId\",\"name\": \"sample_hash.pdf\",\"url\": \"https://ik.imagekit.io/your_imagekit_id/tmp/test/sample_hash.pdf\",\"size\" : 83622,\"filePath\": \"/tmp/test/sample_hash.pdf\",\"tags\": [\"test\"],\"isPrivateFile\" : false,\"fileType\": \"pdf\"}")
        mockWebServer.enqueue(mockSignatureResponse)
        mockWebServer.enqueue(mockUploadResponse)

        val file = File("src/test/java/com/imagekit/android/sample.pdf")

        ImageKit.getInstance().uploader().upload(
            file = file,
            fileName = "sample.pdf",
            useUniqueFilename = true,
            tags = arrayOf("test"),
            folder = "/tmp/test",
            imageKitCallback = object : ImageKitCallback {

                override fun onSuccess(uploadResponse: UploadResponse) {
                    assertEquals(uploadResponse.fileId, "fileId")
                    assertEquals(uploadResponse.name, "sample_hash.pdf")
                    assertEquals(uploadResponse.url, "https://ik.imagekit.io/your_imagekit_id/tmp/test/sample_hash.pdf")
                    assertEquals(uploadResponse.size, 83622)
                    assertEquals(uploadResponse.filePath, "/tmp/test/sample_hash.pdf")
                    assertEquals(uploadResponse.isPrivateFile , false)
                    Assert.assertArrayEquals(uploadResponse.tags, Array(1){"test"})
                    assertEquals(uploadResponse.fileType, "pdf")
                    future.complete("SUCCESS")
                }

                override fun onError(uploadError: UploadError) {
                    Assert.fail("Should succeed")
                }

            })
        future.get()
    }

    @Test
    fun uploadFileVariation() {
        val future = CompletableFuture<String>()

        val mockSignatureResponse = MockResponse().setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody("{\"token\": \"Token\", \"signature\": \"Signature\"}")
        val mockUploadResponse = MockResponse().setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody("{\"fileId\" : \"fileId\",\"name\": \"sample_hash.pdf\",\"url\": \"https://ik.imagekit.io/your_imagekit_id/tmp/test/sample_hash.pdf\",\"size\" : 83622,\"filePath\": \"/tmp/test/sample_hash.pdf\",\"tags\": [],\"isPrivateFile\" : false,\"fileType\": \"pdf\"}")
        mockWebServer.enqueue(mockSignatureResponse)
        mockWebServer.enqueue(mockUploadResponse)

        val file = File("src/test/java/com/imagekit/android/sample.pdf")

        ImageKit.getInstance().uploader().upload(
            file = file,
            fileName = "sample.pdf",
            imageKitCallback = object : ImageKitCallback {

                override fun onSuccess(uploadResponse: UploadResponse) {
                    assertEquals(uploadResponse.fileId, "fileId")
                    assertEquals(uploadResponse.name, "sample_hash.pdf")
                    assertEquals(uploadResponse.url, "https://ik.imagekit.io/your_imagekit_id/tmp/test/sample_hash.pdf")
                    assertEquals(uploadResponse.size, 83622)
                    assertEquals(uploadResponse.filePath, "/tmp/test/sample_hash.pdf")
                    Assert.assertArrayEquals(uploadResponse.tags, Array(0){})
                    assertEquals(uploadResponse.isPrivateFile , false)
                    assertEquals(uploadResponse.fileType, "pdf")
                    future.complete("SUCCESS")
                }

                override fun onError(uploadError: UploadError) {
                    Assert.fail("Should succeed")
                }

            })
        future.get()
    }

    @Test
    fun uploadFileNonExistentFile() {
        val future = CompletableFuture<String>()

        val mockSignatureResponse = MockResponse().setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody("{\"token\": \"Token\", \"signature\": \"Signature\"}")
        mockWebServer.enqueue(mockSignatureResponse)

        val file = File("src/test/java/com/imagekit/android/non-existent.pdf")

        ImageKit.getInstance().uploader().upload(
            file = file,
            fileName = "sample.pdf",
            useUniqueFilename = true,
            tags = arrayOf("test"),
            folder = "/tmp/test",
            imageKitCallback = object : ImageKitCallback {

                override fun onSuccess(uploadResponse: UploadResponse) {
                    Assert.fail("Should not succeed")
                }

                override fun onError(uploadError: UploadError) {
                    assertEquals(uploadError.message, "The file you are trying to upload was not found.")
                    assertEquals(uploadError.exception, true)
                    assertEquals(uploadError.statusCode, "SERVER_ERROR")
                    assertEquals(uploadError.statusNumber, 1500)
                    future.complete("DONE")
                }

            })
        future.get()
    }

    @Test
    fun uploadFromURL() {
        val future = CompletableFuture<String>()

        val mockSignatureResponse = MockResponse().setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody("{\"token\": \"Token\", \"signature\": \"Signature\"}")
        val mockUploadResponse = MockResponse().setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody("{\"fileId\" : \"fileId\",\"name\": \"default-image-test_hash.jpg\", \"height\": 1000, \"width\": 1000,\"url\": \"https://ik.imagekit.io/your_imagekit_id/tmp/test/default-image-test_hash.jpg\",\"thumbnail\": \"https://ik.imagekit.io/your_imagekit_id/tmp/test/default-image-test_hash.jpg\",\"size\" : 83622,\"filePath\": \"/tmp/test/default-image-test_hash.jpg\",\"isPrivateFile\" : false,\"fileType\": \"jpg\", \"tags\": [\"test\"], \"customCoordinates\":\"0,0,200,200\"}")
        mockWebServer.enqueue(mockSignatureResponse)
        mockWebServer.enqueue(mockUploadResponse)

        ImageKit.getInstance().uploader().upload(
            file = "http://ik.imagekit.io/demo/img/default-image.jpg",
            fileName = "default-image-test.jpg",
            useUniqueFilename = true,
            tags = arrayOf("test"),
            folder = "/tmp/test",
            customCoordinates = "0,0,200,200",
            responseFields = "fileId,name,url,height,width,size,filePath,isPrivateFile,fileType",
            imageKitCallback = object : ImageKitCallback {

                override fun onSuccess(uploadResponse: UploadResponse) {
                    assertEquals(uploadResponse.fileId, "fileId")
                    assertEquals(uploadResponse.name, "default-image-test_hash.jpg")
                    assertEquals(uploadResponse.url, "https://ik.imagekit.io/your_imagekit_id/tmp/test/default-image-test_hash.jpg")
                    assertEquals(uploadResponse.height, 1000)
                    assertEquals(uploadResponse.width, 1000)
                    assertEquals(uploadResponse.size, 83622)
                    Assert.assertArrayEquals(uploadResponse.tags, Array(1){"test"})
                    assertEquals(uploadResponse.filePath, "/tmp/test/default-image-test_hash.jpg")
                    assertEquals(uploadResponse.isPrivateFile , false)
                    assertEquals(uploadResponse.fileType, "jpg")
                    assertEquals(uploadResponse.customCoordinates, "0,0,200,200")
                    assertEquals(uploadResponse.thumbnail, "https://ik.imagekit.io/your_imagekit_id/tmp/test/default-image-test_hash.jpg")
                    assertNull(uploadResponse.metadata)
                    future.complete("SUCCESS")
                }

                override fun onError(uploadError: UploadError) {
                    Assert.fail("Should succeed")
                }

            })
        future.get()
    }

    @Test
    fun uploadFromURLVariation() {
        val future = CompletableFuture<String>()

        val mockSignatureResponse = MockResponse().setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody("{\"token\": \"Token\", \"signature\": \"Signature\"}")
        val mockUploadResponse = MockResponse().setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody("{\"fileId\" : \"fileId\",\"name\": \"default-image-test_hash.jpg\", \"height\": 1000, \"width\": 1000,\"url\": \"https://ik.imagekit.io/your_imagekit_id/tmp/test/default-image-test_hash.jpg\",\"size\" : 83622,\"filePath\": \"/tmp/test/default-image-test_hash.jpg\",\"tags\": [],\"isPrivateFile\" : false,\"fileType\": \"jpg\"}")
        mockWebServer.enqueue(mockSignatureResponse)
        mockWebServer.enqueue(mockUploadResponse)

        ImageKit.getInstance().uploader().upload(
            file = "http://ik.imagekit.io/demo/img/default-image.jpg",
            fileName = "default-image-test.jpg",
            imageKitCallback = object : ImageKitCallback {

                override fun onSuccess(uploadResponse: UploadResponse) {
                    assertEquals(uploadResponse.fileId, "fileId")
                    assertEquals(uploadResponse.name, "default-image-test_hash.jpg")
                    assertEquals(uploadResponse.url, "https://ik.imagekit.io/your_imagekit_id/tmp/test/default-image-test_hash.jpg")
                    assertEquals(uploadResponse.height, 1000)
                    assertEquals(uploadResponse.width, 1000)
                    assertEquals(uploadResponse.size, 83622)
                    Assert.assertArrayEquals(uploadResponse.tags, Array(0){})
                    assertEquals(uploadResponse.filePath, "/tmp/test/default-image-test_hash.jpg")
                    assertEquals(uploadResponse.isPrivateFile , false)
                    assertEquals(uploadResponse.fileType, "jpg")
                    future.complete("SUCCESS")
                }

                override fun onError(uploadError: UploadError) {
                    Assert.fail("Should succeed")
                }

            })
        future.get()
    }

    @Test
    fun uploadFromURLFailedSignature(){
        val future = CompletableFuture<String>()

        val mockSignatureResponse = MockResponse().setResponseCode(HttpURLConnection.HTTP_BAD_REQUEST)
        val mockUploadResponse = MockResponse().setResponseCode(HttpURLConnection.HTTP_BAD_REQUEST)
        mockWebServer.enqueue(mockSignatureResponse)
        mockWebServer.enqueue(mockUploadResponse)

        ImageKit.getInstance().uploader().upload(
            file = "http://ik.imagekit.io/demo/img/default-image.jpg",
            fileName = "default-image-test.jpg",
            useUniqueFilename = true,
            tags = arrayOf("test"),
            folder = "/tmp/test",
            imageKitCallback = object: ImageKitCallback{

                override fun onSuccess(uploadResponse: UploadResponse){
                    Assert.fail("Should not succeed")
                }

                override fun onError(uploadError: UploadError){
                    assertEquals(uploadError.message, "Signature generation failed.")
                    assertEquals(uploadError.exception, true)
                    assertEquals(uploadError.statusCode, "SERVER_ERROR")
                    assertEquals(uploadError.statusNumber, 1500)
                    future.complete("DONE")
                }

            })

        future.get()
    }

    @Test
    fun uploadImage() {
        val future = CompletableFuture<String>()

        val mockSignatureResponse = MockResponse().setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody("{\"token\": \"Token\", \"signature\": \"Signature\"}")
        val mockUploadResponse = MockResponse().setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody("{\"fileId\" : \"fileId\",\"name\": \"default-image-test_hash.jpg\", \"height\": 1000, \"width\": 1000,\"url\": \"https://ik.imagekit.io/your_imagekit_id/tmp/test/default-image-test_hash.jpg\",\"size\" : 83622,\"filePath\": \"/tmp/test/default-image-test_hash.jpg\",\"tags\": [\"test\"],\"isPrivateFile\" : false,\"fileType\": \"jpg\"}")
        mockWebServer.enqueue(mockSignatureResponse)
        mockWebServer.enqueue(mockUploadResponse)

        val bitmap = PowerMockito.mock(Bitmap::class.java)

        ImageKit.getInstance().uploader().upload(
            file = bitmap,
            fileName = "default-image-test.jpg",
            useUniqueFilename = true,
            tags = arrayOf("test"),
            folder = "/tmp/test",
            imageKitCallback = object : ImageKitCallback {

                override fun onSuccess(uploadResponse: UploadResponse) {
                    assertEquals(uploadResponse.fileId, "fileId")
                    assertEquals(uploadResponse.name, "default-image-test_hash.jpg")
                    assertEquals(uploadResponse.url, "https://ik.imagekit.io/your_imagekit_id/tmp/test/default-image-test_hash.jpg")
                    assertEquals(uploadResponse.height, 1000)
                    assertEquals(uploadResponse.width, 1000)
                    assertEquals(uploadResponse.size, 83622)
                    assertEquals(uploadResponse.filePath, "/tmp/test/default-image-test_hash.jpg")
                    assertEquals(uploadResponse.isPrivateFile , false)
                    Assert.assertArrayEquals(uploadResponse.tags, Array(1){"test"})
                    assertEquals(uploadResponse.fileType, "jpg")
                    future.complete("SUCCESS")
                }

                override fun onError(uploadError: UploadError) {
                    Assert.fail("Should succeed")
                }

            })
        future.get()
    }

    @Test
    fun uploadImageVariation() {
        val future = CompletableFuture<String>()

        val mockSignatureResponse = MockResponse().setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody("{\"token\": \"Token\", \"signature\": \"Signature\"}")
        val mockUploadResponse = MockResponse().setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody("{\"fileId\" : \"fileId\",\"name\": \"default-image-test_hash.jpg\", \"height\": 1000, \"width\": 1000,\"url\": \"https://ik.imagekit.io/your_imagekit_id/tmp/test/default-image-test_hash.jpg\",\"size\" : 83622,\"filePath\": \"/tmp/test/default-image-test_hash.jpg\",\"tags\": [],\"isPrivateFile\" : false,\"fileType\": \"jpg\"}")
        mockWebServer.enqueue(mockSignatureResponse)
        mockWebServer.enqueue(mockUploadResponse)

        val bitmap = Mockito.mock(Bitmap::class.java)

        ImageKit.getInstance().uploader().upload(
            file = bitmap,
            fileName = "sample.jpg",
            imageKitCallback = object : ImageKitCallback {

                override fun onSuccess(uploadResponse: UploadResponse) {
                    assertEquals(uploadResponse.fileId, "fileId")
                    assertEquals(uploadResponse.name, "default-image-test_hash.jpg")
                    assertEquals(uploadResponse.url, "https://ik.imagekit.io/your_imagekit_id/tmp/test/default-image-test_hash.jpg")
                    assertEquals(uploadResponse.height, 1000)
                    assertEquals(uploadResponse.width, 1000)
                    assertEquals(uploadResponse.size, 83622)
                    assertEquals(uploadResponse.filePath, "/tmp/test/default-image-test_hash.jpg")
                    Assert.assertArrayEquals(uploadResponse.tags, Array(0){})
                    assertEquals(uploadResponse.isPrivateFile , false)
                    assertEquals(uploadResponse.fileType, "jpg")
                    future.complete("SUCCESS")
                }

                override fun onError(uploadError: UploadError) {
                    Assert.fail("Should succeed")
                }

            })
        future.get()

    }


    fun mockRetrofit() : ApiInterface{
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor(BuildVersionQueryInterceptor())
            .build()

        val url : String = mockWebServer.url("/").toString()
        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        return retrofit.create(ApiInterface::class.java)
    }
}
