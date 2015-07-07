package com.example.liangchao.uilib;

import android.content.Context;
import android.graphics.Color;


public class DailyQuizPresenter extends Presenter<DailyQuizPresenter.DailyQuizUi> {

    public static final int BUTTON_BUY    = 0;
    public static final int BUTTON_SELL   = 1;
    public static final int BUTTON_IGNORE = 2;

    private int mChartBuyBackground;
    private int mChartSellBackground;

    private float mLastClosePrice;

    DailyQuizPresenter() {}

    @Override
    public void onUiReady(DailyQuizPresenter.DailyQuizUi ui) {
        super.onUiReady(ui);

        mChartBuyBackground = ui.getContext().getResources().getColor(R.color.graph_background_up);
        mChartSellBackground = ui.getContext().getResources().getColor(R.color.graph_background_down);
    }

    @Override
    public void onUiUnready() {
    }

    public void setTitle(String title) {
        getUi().setTitle(title);
    }

    public void setSubtitle(int index) {
        getUi().setSubtitle(index);
    }

    public void setSubtitle(String title) {
        getUi().setSubtitle(title);
    }

    public void setLastClosePrice(float value) {
        mLastClosePrice = value;
        getUi().setLastClosePrice(value);
    }

    public void setCurrentPrice(float value) {
        getUi().setCurrentPrice(value);

        if (value >= mLastClosePrice) {
            getUi().setGraphBackgroundColor(mChartBuyBackground);
        } else {
            getUi().setGraphBackgroundColor(mChartSellBackground);
        }
    }

    public void setPriceArray(float[] array) {
        final float current = array[array.length - 1];
        getUi().setPriceArray(array);
        setCurrentPrice(current);
    }

    public void setBuyerRate(float value) {
        String percent = (int) (value * 100) + "%";
        getUi().setBuyerRate(percent);
    }

    public void setSellerRate(float value) {
        String percent = (int) (value * 100) + "%";
        getUi().setSellerRate(percent);
    }

    public void setBonus(int index) {
        getUi().setBonus(index);
    }

    public void setGraphBackground(int color) {
        getUi().setGraphBackgroundColor(color);
    }

    public void registerOnClickListener(OnClickListener listener) {
        getUi().registerOnClickListener(listener);
    }

    public interface DailyQuizUi extends Ui {
        void setTitle(String title);
        void setSubtitle(int index);
        void setSubtitle(String title);
        void setLastClosePrice(float value);
        void setCurrentPrice(float value);
        void setPriceArray(float[] array);
        void setBuyerRate(String value);
        void setSellerRate(String value);
        void setBonus(int index);
        void setGraphBackgroundColor(int color);
        void registerOnClickListener(OnClickListener listener);
    }
}
