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

    // TODO
    private Paint mBaseline, mReferenceLine, mCursor;

    private int mLeft, mRight, mTop, mBottom, mWidth, mHeight;
    private float mBaseY;

    private int mBackgroundColor;

    private float[] mData;

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

        mWidth = right - left;
        mHeight = bottom - top;

        mLeft = MARGIN_LEFT;
        mRight = mWidth - MARGIN_RIGHT;
        mTop = MARGIN_TOP;
        mBottom = mHeight - MARGIN_BOTTOM;

        mBaseY = (mBottom + mTop) / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawBaseline(canvas);

        dump();
    }

    private void drawBaseline(Canvas canvas) {
        canvas.drawLine(mLeft, mBaseY, mRight, mBaseY, mBaseline);
    }

    private void drawData(Canvas canvas) {

    }

    private void drawCursor() {

    }


    void dump() {
        Log.v(TAG, "Left=" + mLeft + "; Right=" + mRight + "; Top=" + mTop + "; Bottom=" + mBottom);
        Log.v(TAG, "Width=" + mWidth + "; Height=" + mHeight);
        Log.v(TAG, "BaseY=" + mBaseY);
    }
}
