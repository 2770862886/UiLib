package com.example.liangchao.uilib;

import android.app.DialogFragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.liangchao.uilib.widget.LineChart;

/**
 * Created by liangchao on 6/22/15.
 */
public class DailyQuizFragment extends BaseFragment<DailyQuizPresenter, DailyQuizPresenter.DailyQuizUi>
        implements DailyQuizPresenter.DailyQuizUi {
    private static final String TAG = "DailyQuizFragment";

    private TextView mSubTitle;
    private TextView mCurrentPrice;
    private LineChart mChart;
    private TextView mUpRate;
    private TextView mDownRate;
    private TextView mBonus;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.dialy_quiz_fragment, null);




        return root;
    }

    public DailyQuizPresenter createPresenter() {
        return new DailyQuizPresenter();
    }

    public DailyQuizFragment getUi() {
        return this;
    }

    /******************************************************************************
     * Ui manipulation
     ******************************************************************************/

    @Override
    public void setTitle(String title) {

    }

    @Override
    public void setSubTitle(String title) {

    }

    @Override
    public void setCurrentPrice(float value) {

    }

    @Override
    public void setPriceArray(float[] array) {

    }

    @Override
    public void setUpRate(float value) {

    }

    @Override
    public void setDownRate(float value) {

    }

    @Override
    public void setBonus(int index) {

    }

    @Override
    public void setGraphBackgroundColor(Color backgroud) {

    }
}
