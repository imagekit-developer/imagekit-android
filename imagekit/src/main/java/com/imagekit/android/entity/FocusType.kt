package com.imagekit.android.entity

enum class FocusType constructor(val value: String) {
    CENTER("center"),
    TOP("top"),
    LEFT("left"),
    BOTTOM("bottom"),
    RIGHT("right"),
    TOP_LEFT("top_left"),
    TOP_RIGHT("top_right"),
    BOTTOM_LEFT("bottom_left"),
    BOTTOM_RIGHT("bottom_right"),
    AUTO("auto")
}