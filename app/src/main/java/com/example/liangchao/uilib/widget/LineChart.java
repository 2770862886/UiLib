package com.example.liangchao.uilib.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PathEffect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import java.util.ArrayList;

public class LineChart extends View implements View.OnLayoutChangeListener {
    private static final String TAG = "LineChart";

    private static final int MARGIN_LEFT        = 10;
    private static final int MARGIN_RIGHT       = 10;
    private static final int MARGIN_TOP         = 20;
    private static final int MARGIN_BOTTOM      = 20;

    private static final int DEFAULT_POINT_X    = 30;
    private static final int MAX_POINT_X        = 800;

    // TODO
    private Paint mBaseline, mLine, mCursor;

    private int mLeft, mRight, mTop, mBottom, mWidth, mHeight;
    private float mBaseY, mScaleY, mScaleX;

    /** Point count to be displayed */
    private int mDataDisplayCount = DEFAULT_POINT_X;
    /** Point index to be displayed */
    private int mStartIndex, mEndIndex;

    private float mLastClosingPrice;
    private ArrayList<Float> mPrices;

    /** Chart backgound color */
    private int mBackgroundColor;

    private ScaleGestureDetector mScaleDetector;

    public LineChart(Context context, AttributeSet attrs) {
        super(context, attrs);

        mBaseline = new Paint();
        mBaseline.setStyle(Paint.Style.STROKE);
        mBaseline.setStrokeWidth(1f);
        mBaseline.setAntiAlias(true);
        mBaseline.setColor(Color.GRAY);
        PathEffect effects = new DashPathEffect(new float[] {1, 2, 4, 8}, 1);
        mBaseline.setPathEffect(effects);

        mLine = new Paint();
        mLine.setStyle(Paint.Style.STROKE);
        mLine.setStrokeWidth(2.5f);
        mLine.setAntiAlias(true);
        mLine.setColor(Color.WHITE);

        mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());

        addOnLayoutChangeListener(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mScaleDetector.onTouchEvent(event);
        return true;
    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom,
                               int oldLeft, int oldTop, int oldRight, int oldBottom) {

        Log.d(TAG, "onLayoutChange() : left=" + left + "; top=" + top + "; right=" + right + "; bottom=" + bottom);

        mWidth = right - left - MARGIN_LEFT - MARGIN_RIGHT;
        mHeight = bottom - top - MARGIN_TOP - MARGIN_BOTTOM;

        mLeft = MARGIN_LEFT;
        mRight = mLeft + mWidth;
        mTop = MARGIN_TOP;
        mBottom = mTop + mHeight;

        mBaseY = (mTop + mBottom) / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(mBackgroundColor);

        computeScale();
        drawBaseline(canvas);
        drawData(canvas);
        drawCursor(canvas);

        dump();
    }

    private boolean ensureData() {
        return mPrices != null && mPrices.size() > 0;
    }

    private void computeScale() {
        if (!ensureData())
            return;

        computeScreen();

        float max = 0, min = Float.MAX_VALUE;
        for (int i = mStartIndex; i < mEndIndex; i++) {
            final float value = mPrices.get(i);

            if (max < value)
                max = value;
            if (min > value)
                min = value;
        }

        Log.d(TAG, "max: " + max + "; min: " + min);

        float range = Math.max(Math.abs(mLastClosingPrice - min), Math.abs(mLastClosingPrice - max)) * 2;
        if (range < 0.000001f && range > -0.000001f) {
            mScaleY = 1f;
        } else {
            mScaleY = -mHeight / range;
        }

        mScaleX = mWidth / mDataDisplayCount - 1;
    }

    /** calculate the start and end position of prices */
    private void computeScreen() {
        if (mDataDisplayCount < mPrices.size()) {
            mEndIndex = mPrices.size() - 1;
            mStartIndex = mEndIndex - mDataDisplayCount + 1;
        } else {
            mEndIndex = mPrices.size() - 1;
            mStartIndex = 0;
        }
    }

    private void changeScaleX(boolean increase) {
        if (increase) {
            if (mDataDisplayCount < MAX_POINT_X) {
                int diff = (int) Math.max(mDataDisplayCount * 1.1, mDataDisplayCount + 1);
                mDataDisplayCount = Math.min(diff, mPrices.size());
            }
        } else {
            if (mDataDisplayCount > 2) {
                mDataDisplayCount = (int) (mDataDisplayCount * 0.9);
            }
        }
    }

    private void moveScreen(boolean direction, int step) {
        if (step > 0) {
            int end = mEndIndex + step;
            if (end >= mPrices.size()) {
                end = mPrices.size() - 1;
            }
            mEndIndex = end;
            mStartIndex = end - mDataDisplayCount + 1;
        } else {
            int start = mStartIndex + step;
            if (start < 0) {
                start = 0;
            }
            mStartIndex = start;
            mEndIndex = start + mDataDisplayCount - 1;
        }
    }

    private void drawBaseline(Canvas canvas) {
        canvas.drawLine(mLeft, mBaseY, mRight, mBaseY, mBaseline);
    }

    private void drawData(Canvas canvas) {
        if (!ensureData())
            return;

        Log.d(TAG, "enter drawData with data:" + mPrices.size());
        Log.d(TAG, "BaseY: " + mBaseY + "; scaleX: " + mScaleX + "; scaleY: " + mScaleY);

        int index = 0;
        float start = mLeft, lastX = -1, lastY = -1;
        for (int i = mStartIndex; i < mEndIndex; i++) {
            final float price = mPrices.get(i);

            float y = (price - mLastClosingPrice) * mScaleY + mBaseY;
            float x = start + mScaleX * index++;

            if (lastX >= 0 && lastY >= 0) {
                canvas.drawLine(lastX, lastY, x, y, mLine);
            }

            lastX = x;
            lastY = y;
        }
    }

    private void drawCursor(Canvas canvas) {
        // TODO
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {

        static final float PER_STEP = 15f;
        float mStep = 0;

        public void onScaleEnd(ScaleGestureDetector detector) {
            Log.d(TAG, "onScaleEnd");
            mStep = 0;
        }

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            Log.d(TAG, "onScale(): " + detector.getScaleFactor());

            final float cur = detector.getCurrentSpan();
            final float pre = detector.getPreviousSpan();
            final float step = cur - pre;

            Log.d(TAG, "Current span: " + cur + "; Previous span: " + pre);

            if (step > 0 && step > (mStep + PER_STEP)) {
                changeScaleX(true);
            } else if (step < 0 && step < (mStep - PER_STEP)) {
                changeScaleX(false);
            }

            mStep = step;

            invalidate();
            return false;
        }
    }

    void dump() {
        Log.v(TAG, "Left=" + mLeft + "; Right=" + mRight + "; Top=" + mTop + "; Bottom=" + mBottom);
        Log.v(TAG, "Width=" + mWidth + "; Height=" + mHeight);
        Log.v(TAG, "BaseY=" + mBaseY);
    }

    /******************************************************************************
     * Public operation
     ******************************************************************************/

    @Override
    public void setBackgroundColor(int color) {
        mBackgroundColor = color;
        invalidate();
    }

    public void setLastClosingPrice(float price) {
        if (price < 0) {
            mLastClosingPrice = 0;
        } else {
            mLastClosingPrice = price;
        }
    }

    public void setPriceArray(float[] array) {
        mPrices = new ArrayList<>(array.length);

        for (float f : array) {
            mPrices.add(f);
        }

        mStartIndex = 0;
        mEndIndex = mPrices.size() - 1;
        mDataDisplayCount = mPrices.size() - 1;

        invalidate();
    }

    public void addPrice(float value) {
        mPrices.add(value);
        mEndIndex = mPrices.size() - 1;
        mDataDisplayCount ++;

        invalidate();
    }

    public void clearPrice() {
        mPrices.clear();

        invalidate();
    }
}
