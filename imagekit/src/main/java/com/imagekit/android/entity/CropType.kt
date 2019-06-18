package com.imagekit.android.entity

enum class CropType constructor(val value: String) {
    MAINTAIN_RATIO("maintain_ratio"),
    FORCE("force"),
    AT_LEAST("at_least"),
    AT_MAX("at_max")
}