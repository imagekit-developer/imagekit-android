package com.imagekit.android.download.picasso
import android.content.Context
import android.graphics.Bitmap
import android.widget.ImageView
import com.imagekit.android.download.ImageKitOptions
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import java.lang.Exception

class PicassoImageKitLoaderTest {

    @Mock
    private lateinit var mockContext: Context

    @Mock
    private lateinit var mockPicasso: Picasso

    @Mock
    private lateinit var mockRequestCreator: RequestCreator

    @Mock
    private lateinit var mockImageView: ImageView

    @Captor
    private lateinit var onSuccessCaptor: ArgumentCaptor<Callback>

    @Captor
    private lateinit var onErrorCaptor: ArgumentCaptor<Callback>

    private lateinit var picassoImageKitLoader: PicassoImageKitLoader
   private  var requestCreator=mock(RequestCreator::class.java);
    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        picassoImageKitLoader = PicassoImageKitLoader(mockContext)
         requestCreator = mock(RequestCreator::class.java)

    }

    @Test
    fun testLoadImage() {
        val mockUrl = "https://example.com/image.jpg"
        val mockOptions: ImageKitOptions? = null
        val mockOnSuccess: () -> Unit = {}
        val mockOnError: (Exception) -> Unit = {}

        `when`(mockPicasso.load(mockUrl)).thenReturn(mockRequestCreator)
//        `when`(mockRequestCreator.into(mockImageView, onSuccessCaptor.capture(), onErrorCaptor.capture())).thenReturn(mockRequestCreator)

        picassoImageKitLoader.loadImage(mockUrl, mockImageView, mockOptions, mockOnSuccess, mockOnError)

        // Verify that the Picasso request was created with the correct URL
        verify(mockPicasso).load(mockUrl)

        // Verify that the applyOptions() method was called with the correct request and options
//        verify(picassoImageKitLoader).applyOptions(eq(mockRequestCreator), eq(mockOptions))

        // Verify that the onSuccess and onError callbacks were captured and can be invoked
        onSuccessCaptor.value.onSuccess()
        onErrorCaptor.value.onError(null)

        // Assert that the onSuccess and onError callbacks were invoked
        verify(mockOnSuccess).invoke()
        verify(mockOnError).invoke(any())
    }

    @Test
    fun testLoadImageWithPlaceholder() {
        val imageView = mock(ImageView::class.java)
        // Mock Picasso.Builder and Picasso.load methods
        val picassoBuilder = mock(Picasso.Builder::class.java)
        val picasso = mock(Picasso::class.java)
        `when`(picassoBuilder.build()).thenReturn(picasso)
        `when`(picasso.load(anyString())).thenReturn(requestCreator)
        `when`(requestCreator.placeholder(anyInt())).thenReturn(requestCreator)
        `when`(requestCreator.apply(any())).thenReturn(requestCreator)
        `when`(requestCreator.into(eq(imageView), any())).thenAnswer {
            val callback = it.arguments[1] as Callback
            callback.onSuccess()
            null
        }

//        // Call the loadImageWithPlaceholder method
//        picassoImageKitLoader.loadImageWithPlaceholder(
//            "https://example.com/image.jpg",
//            imageView,
//            R.drawable.placeholder,
//            options,
//            onSuccess,
//            onError
//        )

        // Verify that Picasso.Builder, Picasso.load, RequestCreator.placeholder, and RequestCreator.apply methods are called with correct arguments
//        verify(picassoBuilder).build()
//        verify(picasso).load("https://example.com/image.jpg")
//        verify(requestCreator).placeholder(R.drawable.placeholder)
//        verify(requestCreator).apply(eq(options))
//        // Verify that RequestCreator.into method is called with correct arguments, and the onSuccess callback is triggered
//        verify(requestCreator).into(eq(imageView), any())
//        verify(onSuccess).invoke()
//        // Verify that onError callback is not triggered
//        verify(onError, never()).invoke(any())
    }
}