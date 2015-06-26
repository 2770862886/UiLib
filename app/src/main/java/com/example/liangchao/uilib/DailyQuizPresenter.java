package com.example.liangchao.uilib;

import android.graphics.Color;

/**
 * Created by liangchao on 6/22/15.
 */
public class DailyQuizPresenter extends Presenter<DailyQuizPresenter.DailyQuizUi> {



    public interface DailyQuizUi extends Ui {
        void setTitle(String title);
        void setSubTitle(String title);
        void setCurrentPrice(float value);
        void setPriceArray(float[] array);
        void setUpRate(float value);
        void setDownRate(float value);
        void setBonus(int index);
        void setGraphBackgroundColor(Color backgroud);
    }
}
