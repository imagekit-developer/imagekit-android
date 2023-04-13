package com.imagekit.android.uploadwidget.views

import android.content.Context
import android.graphics.Matrix
import android.graphics.PointF
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import androidx.annotation.Nullable
import androidx.appcompat.widget.AppCompatImageView


class ImageKitView : AppCompatImageView, View.OnTouchListener,
    GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener{
    //Construction details
    private var myContext: Context? = null
    private var myScaleDetector: ScaleGestureDetector? = null
    private var myGestureDetector: GestureDetector? = null
    var myMatrix: Matrix? = null
    private var matrixValue: FloatArray? = null
    var zoomMode = 0

    // required Scales
    var presentScale = 1f
    var minimumScale = 1f
    var maximumScale = 4f

    //Dimensions
    var originalWidth = 0f
    var originalHeight = 0f
    var mViewedWidth = 0
    var mViewedHeight = 0
    private var lastPoint = PointF()
    private var startPoint = PointF()

    constructor(context: Context) : super(context) {
        constructionDetails(context)
    }

    constructor(context: Context, @Nullable attrs: AttributeSet?) : super(context, attrs) {
        constructionDetails(context)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context!!,
        attrs,
        defStyleAttr
    )
    private fun constructionDetails(context: Context){
        super.setClickable(true)
        myContext=context
        myScaleDetector= ScaleGestureDetector(context,ScalingListener())
        myMatrix=Matrix()
        matrixValue=FloatArray(10)
        imageMatrix = myMatrix
        scaleType = ScaleType.MATRIX
        myGestureDetector = GestureDetector(context, this)
        setOnTouchListener(this)
    }
    private inner class ScalingListener : ScaleGestureDetector.SimpleOnScaleGestureListener(){
        override fun onScaleBegin(detector: ScaleGestureDetector?): Boolean {
            zoomMode = 2
            return true
        }

        override fun onScale(detector: ScaleGestureDetector): Boolean {
            var mScaleFactor = detector.scaleFactor
            val previousScale = mScaleFactor
            presentScale*=mScaleFactor
            if (presentScale > maximumScale) {
                presentScale = maximumScale
                mScaleFactor = maximumScale / previousScale
            }else if (presentScale < minimumScale) {
                presentScale = minimumScale
                mScaleFactor = minimumScale / previousScale
            }
            if (originalWidth * presentScale <= mViewedWidth
                || originalHeight * presentScale <= mViewedHeight
            ) {
                myMatrix!!.postScale(
                    mScaleFactor, mScaleFactor, mViewedWidth / 2.toFloat(),
                    mViewedHeight / 2.toFloat()
                )
            } else {
                myMatrix!!.postScale(
                    mScaleFactor, mScaleFactor,
                    detector.focusX, detector.focusY
                )
            }
            fittedTranslation()
            return true

        }
    }
    private fun putToScreen() {
        presentScale = 1f
        val factor: Float
        val mDrawable = drawable
        if (mDrawable == null || mDrawable.intrinsicWidth == 0 || mDrawable.intrinsicHeight == 0) return
        val mImageWidth = mDrawable.intrinsicWidth
        val mImageHeight = mDrawable.intrinsicHeight
        val factorX = mViewedWidth.toFloat() / mImageWidth.toFloat()
        val factorY = mViewedHeight.toFloat() / mImageHeight.toFloat()
        factor = factorX.coerceAtMost(factorY)
        myMatrix!!.setScale(factor, factor)

        // Centering the image
        var repeatedYSpace = (mViewedHeight.toFloat()
                - factor * mImageHeight.toFloat())
        var repeatedXSpace = (mViewedWidth.toFloat()
                - factor * mImageWidth.toFloat())
        repeatedYSpace /= 2.toFloat()
        repeatedXSpace /= 2.toFloat()
        myMatrix!!.postTranslate(repeatedXSpace, repeatedYSpace)
        originalWidth = mViewedWidth - 2 * repeatedXSpace
        originalHeight = mViewedHeight - 2 * repeatedYSpace
        imageMatrix = myMatrix
    }
    fun fittedTranslation() {
        myMatrix!!.getValues(matrixValue)
        val translationX =
            matrixValue!![Matrix.MTRANS_X]
        val translationY =
            matrixValue!![Matrix.MTRANS_Y]
        val fittedTransX = getFittedTranslation(translationX, mViewedWidth.toFloat(), originalWidth * presentScale)
        val fittedTransY = getFittedTranslation(translationY, mViewedHeight.toFloat(), originalHeight * presentScale)
        if (fittedTransX != 0f || fittedTransY != 0f) myMatrix!!.postTranslate(fittedTransX, fittedTransY)
    }

    private fun getFittedTranslation(mTranslate: Float,vSize: Float, cSize: Float): Float {
        val minimumTranslation: Float
        val maximumTranslation: Float
        if (cSize <= vSize) {
            minimumTranslation = 0f
            maximumTranslation = vSize - cSize
        } else {
            minimumTranslation = vSize - cSize
            maximumTranslation = 0f
        }
        if (mTranslate < minimumTranslation) {
            return -mTranslate + minimumTranslation
        }
        if (mTranslate > maximumTranslation) {
            return -mTranslate + maximumTranslation
        }
        return 0F
    }
    private fun getFixDragTrans(delta: Float, viewedSize: Float, detailSize: Float): Float {
        return if (detailSize <= viewedSize) {
            0F
        } else delta
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        mViewedWidth = MeasureSpec.getSize(widthMeasureSpec)
        mViewedHeight = MeasureSpec.getSize(heightMeasureSpec)
        if (presentScale == 1f) {

            // Merged onto the Screen
            putToScreen()
        }
    }
    override fun onTouch(mView: View, mMouseEvent: MotionEvent): Boolean {
        myScaleDetector!!.onTouchEvent(mMouseEvent)
        myGestureDetector!!.onTouchEvent(mMouseEvent)
        val currentPoint = PointF(mMouseEvent.x, mMouseEvent.y)

        val mDisplay = this.display
        val mLayoutParams = this.layoutParams
        mLayoutParams.width = mDisplay.width
        mLayoutParams.height = mDisplay.height
        this.layoutParams = mLayoutParams
        when (mMouseEvent.action) {
            MotionEvent.ACTION_DOWN -> {
                lastPoint.set(currentPoint)
                startPoint.set(lastPoint)
                zoomMode = 1
            }
            MotionEvent.ACTION_MOVE -> if (zoomMode == 1) {
                val changeInX = currentPoint.x - lastPoint.x
                val changeInY = currentPoint.y - lastPoint.y
                val fixedTranslationX = getFixDragTrans(changeInX, mViewedWidth.toFloat(), originalWidth * presentScale)
                val fixedTranslationY = getFixDragTrans(changeInY, mViewedHeight.toFloat(), originalHeight * presentScale)
                myMatrix!!.postTranslate(fixedTranslationX, fixedTranslationY)
                fittedTranslation()
                lastPoint[currentPoint.x] = currentPoint.y
            }
            MotionEvent.ACTION_POINTER_UP -> zoomMode = 0
        }
        imageMatrix = myMatrix
        return false
    }

    override fun onDown(p0: MotionEvent?): Boolean {
        return false
    }
    override fun onShowPress(p0: MotionEvent?) {}
    override fun onSingleTapUp(p0: MotionEvent?): Boolean {
        return false
    }
    override fun onScroll(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean {
        return false
    }
    override fun onLongPress(p0: MotionEvent?) {}
    override fun onFling(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean {
        return false
    }
    override fun onSingleTapConfirmed(p0: MotionEvent?): Boolean {
        return false
    }
    override fun onDoubleTap(p0: MotionEvent?): Boolean {
        return false
    }
    override fun onDoubleTapEvent(p0: MotionEvent?): Boolean {
        return false
    }
}