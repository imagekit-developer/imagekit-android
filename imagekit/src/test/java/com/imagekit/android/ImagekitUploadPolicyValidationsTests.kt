package com.imagekit.android

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.BatteryManager
import android.os.PowerManager
import com.imagekit.android.entity.UploadError
import com.imagekit.android.entity.UploadPolicy
import com.imagekit.android.entity.UploadResponse
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.Shadows
import org.robolectric.shadows.ShadowBatteryManager
import org.robolectric.shadows.ShadowConnectivityManager
import org.robolectric.shadows.ShadowNetworkInfo
import org.robolectric.shadows.ShadowPowerManager

@RunWith(RobolectricTestRunner::class)
class ImagekitUploadPolicyValidationsTests : ImageKitCallback {

    private lateinit var shadowConnectivityManager: ShadowConnectivityManager
    private lateinit var shadowBatteryManager: ShadowBatteryManager
    private lateinit var shadowPowerManager: ShadowPowerManager

    private val urlEndpoint: String = "https://ik.imagekit.io/demo"
    var policyError: UploadError? = null

    @Before
    fun setUp() {
        val testContext = RuntimeEnvironment.getApplication()

        shadowConnectivityManager = Shadows.shadowOf(
            testContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        )
        shadowBatteryManager = Shadows.shadowOf(
            testContext.getSystemService(Context.BATTERY_SERVICE) as BatteryManager
        )
        shadowPowerManager = Shadows.shadowOf(
            testContext.getSystemService(Context.POWER_SERVICE) as PowerManager
        )

        ImageKit.init(
            context = testContext,
            urlEndpoint = urlEndpoint
        )
    }

    @After
    fun tearDown() {
    }

    @Test
    fun checkUploadPolicySuccessfulValidation() {
        val policyCheckResult = ImageKit.getInstance().uploader().checkUploadPolicy(
            UploadPolicy.Builder().build(),
            this
        )
        kotlin.test.assertEquals(true, policyCheckResult)
    }

    @Test
    fun checkUploadPolicyViolationByNetworkType() {
        shadowConnectivityManager.setActiveNetworkInfo(
            ShadowNetworkInfo.newInstance(NetworkInfo.DetailedState.CONNECTED, ConnectivityManager.TYPE_MOBILE, 0, true, true)
        )
        val policyCheckResult = ImageKit.getInstance().uploader().checkUploadPolicy(
            UploadPolicy.Builder().requireNetworkType(UploadPolicy.NetworkType.UNMETERED).build(),
            this
        )
        kotlin.test.assertEquals(false, policyCheckResult)
        kotlin.test.assertEquals("POLICY_ERROR_METERED_NETWORK", policyError?.statusCode)
    }

    @Test
    fun checkUploadPolicyViolationByBatteryState() {
        shadowBatteryManager.setIsCharging(false)
        val policyCheckResult = ImageKit.getInstance().uploader().checkUploadPolicy(
            UploadPolicy.Builder().requiresBatteryCharging(true).build(),
            this
        )
        kotlin.test.assertEquals(false, policyCheckResult)
        kotlin.test.assertEquals("POLICY_ERROR_BATTERY_DISCHARGING", policyError?.statusCode)
    }

    @Test
    fun checkUploadPolicyViolationByDeviceIdleState() {
        shadowPowerManager.setIsDeviceIdleMode(false)
        val policyCheckResult = ImageKit.getInstance().uploader().checkUploadPolicy(
            UploadPolicy.Builder().requiresDeviceIdle(true).build(),
            this
        )
        kotlin.test.assertEquals(false, policyCheckResult)
        kotlin.test.assertEquals("POLICY_ERROR_DEVICE_NOT_IDLE", policyError?.statusCode)
    }

    override fun onSuccess(uploadResponse: UploadResponse) = Unit

    override fun onError(uploadError: UploadError) {
        policyError = uploadError
    }
}