package com.example.liangchao.uilib;

import android.graphics.Color;


public class DailyQuizPresenter extends Presenter<DailyQuizPresenter.DailyQuizUi> {

    public static final int BUTTON_BUY    = 0;
    public static final int BUTTON_SELL   = 1;
    public static final int BUTTON_IGNORE = 2;

    DailyQuizPresenter() {}

    public void setTitle(String title) {
        getUi().setTitle(title);
    }

    public void setSubtitle(int index) {
        getUi().setSubtitle(index);
    }

    public void setSubtitle(String title) {
        getUi().setSubtitle(title);
    }

    public void setCurrentPrice(float value) {
        getUi().setCurrentPrice(value);
    }

    public void setPriceArray(float[] array) {
        getUi().setPriceArray(array);
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
        void setCurrentPrice(float value);
        void setPriceArray(float[] array);
        void setBuyerRate(String value);
        void setSellerRate(String value);
        void setBonus(int index);
        void setGraphBackgroundColor(int color);
        void registerOnClickListener(OnClickListener listener);
    }
}
