package com.imagekit.android.uploadwidget.gestures;

import android.graphics.Rect;
import android.view.MotionEvent;

class CropBottomRightCornerHandler extends CropOverlayGestureHandler {

    private final CropOverlayGestureCallback listener;

    CropBottomRightCornerHandler(Rect overlay, CropOverlayGestureCallback listener) {
        super(overlay);
        this.listener = listener;
    }

    @Override
    public void handleTouchEvent(MotionEvent event, boolean isAspectRatioLocked) {
        bounds.set(overlay.right - getGestureRegionWidth(), overlay.bottom - getGestureRegionHeight(), overlay.right + getGestureRegionWidth(), overlay.bottom + getGestureRegionHeight());

        super.handleTouchEvent(event, isAspectRatioLocked);
    }

    @Override
    public void handleGesture(MotionEvent event, boolean isAspectRatioLocked) {
        int left = overlay.left;
        int top = overlay.top;
        int right = overlay.right + (int) (event.getX() - prevTouchEventPoint.x);
        int bottom = overlay.bottom + (int) (event.getY() - prevTouchEventPoint.y);

        if (isAspectRatioLocked) {
            left -= bottom - overlay.bottom;
            top -= right - overlay.right;
        }

        if (listener != null) {
            listener.onOverlaySizeChanged(left, top, right, bottom);
        }
    }

}
