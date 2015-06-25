package com.example.liangchao.uilib;

import android.app.DialogFragment;
import android.os.Bundle;

/**
 * Created by liangchao on 6/22/15.
 */
public class DailyQuizFragment extends BaseFragment<DailyQuizPresenter, DailyQuizPresenter.DailyQuizUi>
        implements DailyQuizPresenter.DailyQuizUi {
    private static final String TAG = "DailyQuizFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO
    }



    public DailyQuizPresenter createPresenter() {
        return new DailyQuizPresenter();
    }

    public DailyQuizFragment getUi() {
        return this;
    }



}
