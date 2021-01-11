package com.imagekit.android

import android.app.Application
import android.content.Context
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.lang.Exception
import kotlin.test.assertEquals

@RunWith(MockitoJUnitRunner::class)
class ErrorTests {

//    @Test
//    fun notInitialized(){
//        try{
//            ImageKit.getInstance()
//            Assert.fail("Should have thrown IllegalStateException");
//        } catch( e: IllegalStateException){
//            assertEquals("Must Initialize ImageKit before using getInstance()", e.message)
//        }
//    }

    @Test
    fun invalidContext() {
        val mockContext = Mockito.mock(Context::class.java)
        try {
            ImageKit.init(context = mockContext, urlEndpoint = "")
            Assert.fail("Should have thrown Exception");
        } catch (e: Exception) {
            assertEquals("Application Context Expected!!", e.message)
        }
    }

    @Test
    fun missingUrlEndpoint() {
        val mockContext = Mockito.mock(Application::class.java)
        try {
            ImageKit.init(context = mockContext, urlEndpoint = "")
            Assert.fail("Should have thrown IllegalStateException");
        } catch (e: IllegalStateException) {
            assertEquals("Missing urlEndpoint during initialization", e.message)
        }
    }
}