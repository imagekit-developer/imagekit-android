package com.imagekit.android.entity

class UploadPolicy private constructor(
    val networkType: NetworkType,
    val requiresCharging: Boolean,
    val requiresIdle: Boolean,
    val maxErrorRetries: Int,
    val backoffMillis: Long,
    val backoffPolicy: BackoffPolicy
) {

    enum class NetworkType {
        ANY,
        UNMETERED
    }

    enum class BackoffPolicy {
        LINEAR,
        EXPONENTIAL
    }

    class Builder {
        private var networkType = NetworkType.ANY
        private var requiresCharging = false
        private var requiresIdle = false
        private var maxRetries = DEFAULT_MAX_ERROR_RETRIES
        private var backoffMillis = DEFAULT_BACKOFF_MILLIS
        private var backoffPolicy = DEFAULT_BACKOFF_POLICY

        fun requireNetworkType(networkPolicy: NetworkType): Builder {
            this.networkType = networkPolicy
            return this
        }

        fun requiresBatteryCharging(requiresCharging: Boolean): Builder {
            this.requiresCharging = requiresCharging
            return this
        }

        fun requiresDeviceIdle(requiresIdle: Boolean): Builder {
            this.requiresIdle = requiresIdle
            return this
        }

        fun maxRetries(maxRetries: Int): Builder {
            require(maxRetries >= 0) { "maxRetries cannot be a negative integer" }
            this.maxRetries = maxRetries
            return this
        }

        fun backoffCriteria(backoffMillis: Long, backoffPolicy: BackoffPolicy): Builder {
            require(backoffMillis >= 0) { "backoffMillis cannot be a negative integer" }
            this.backoffMillis = backoffMillis
            this.backoffPolicy = backoffPolicy
            return this
        }

        fun build(): UploadPolicy {
            return UploadPolicy(
                networkType,
                requiresCharging,
                requiresIdle,
                maxRetries,
                backoffMillis,
                backoffPolicy
            )
        }
    }

    companion object {
        private const val DEFAULT_MAX_ERROR_RETRIES = 5
        private const val DEFAULT_BACKOFF_MILLIS = 1000L
        private val DEFAULT_BACKOFF_POLICY = BackoffPolicy.LINEAR

        fun defaultPolicy(): UploadPolicy = Builder()
            .requireNetworkType(NetworkType.ANY)
            .requiresDeviceIdle(false)
            .requiresBatteryCharging(false)
            .maxRetries(DEFAULT_MAX_ERROR_RETRIES)
            .backoffCriteria(
                backoffMillis = DEFAULT_BACKOFF_MILLIS,
                backoffPolicy = DEFAULT_BACKOFF_POLICY
            )
            .build()
    }
}
