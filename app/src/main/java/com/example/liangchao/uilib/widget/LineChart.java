package com.example.liangchao.uilib.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PathEffect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class LineChart extends View implements View.OnLayoutChangeListener {

    private static final String TAG = "LineChart";

    private static final int MARGIN_LEFT        = 4;
    private static final int MARGIN_RIGHT       = 4;
    private static final int MARGIN_TOP         = 8;
    private static final int MARGIN_BOTTOM      = 8;

    private static final int MIN_POINT_X        = 15;

    // TODO
    private Paint mBaseline, mReferenceLine, mCursor;

    private int mLeft, mRight, mTop, mBottom, mWidth, mHeight;
    private float mBaseY, mScaleX, mScaleY;

    private int mBackgroundColor;

    private float mLastClosingPrice;
    private float[] mPrices;

    public LineChart(Context context, AttributeSet attrs) {
        super(context, attrs);

        mBaseline = new Paint();
        mBaseline.setStyle(Paint.Style.STROKE);
        mBaseline.setStrokeWidth(1f);
        mBaseline.setAntiAlias(true);
        mBaseline.setColor(Color.GRAY);
        PathEffect effects = new DashPathEffect(new float[] {1, 2, 4, 8}, 1);
        mBaseline.setPathEffect(effects);

        addOnLayoutChangeListener(this);
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

        computeScale();
        drawBaseline(canvas);
        drawData(canvas);
        drawCursor(canvas);

        dump();
    }

    private boolean ensureData() {
        return mPrices != null && mPrices.length > 0;
    }

    private void computeScale() {
        if (!ensureData())
            return;

        float max = 0, min = Float.MAX_VALUE;
        for (int i = 0; i < mPrices.length; i++) {
            if (max < mPrices[i])
                max = mPrices[i];
            if (min > mPrices[i])
                min = mPrices[i];
        }


    }

    private void drawBaseline(Canvas canvas) {
        canvas.drawLine(mLeft, mBaseY, mRight, mBaseY, mBaseline);
    }

    private void drawData(Canvas canvas) {
        if (!ensureData())
            return;


    }

    private void drawCursor(Canvas canvas) {

    }

    void dump() {
        Log.v(TAG, "Left=" + mLeft + "; Right=" + mRight + "; Top=" + mTop + "; Bottom=" + mBottom);
        Log.v(TAG, "Width=" + mWidth + "; Height=" + mHeight);
        Log.v(TAG, "BaseY=" + mBaseY);
    }

    /******************************************************************************
     * Public operation
     ******************************************************************************/

    public void setLastClosingPrice(float price) {
        if (price < 0) {
            mLastClosingPrice = 0;
        } else {
            mLastClosingPrice = price;
        }
    }

    public void setPriceArray(float[] array) {
        mPrices = array;
    }
}
