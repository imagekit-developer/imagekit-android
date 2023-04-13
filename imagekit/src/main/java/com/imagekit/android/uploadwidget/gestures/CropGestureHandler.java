package com.imagekit.android.uploadwidget.gestures;

import android.view.MotionEvent;

interface CropGestureHandler {

    void setNext(CropGestureHandler nextHandler);

    void handleTouchEvent(MotionEvent event, boolean isAspectRatioLocked);
}
