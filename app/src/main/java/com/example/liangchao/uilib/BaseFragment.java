package com.example.liangchao.uilib;

import android.app.DialogFragment;
import android.os.Bundle;

public abstract class BaseFragment<T extends Presenter<U>, U extends Ui> extends DialogFragment {

    private T mPresenter;

    public abstract T createPresenter();
    public abstract U getUi();

    protected BaseFragment() {
        mPresenter = createPresenter();
    }

    public T getPresenter() {
        return mPresenter;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter.onUiReady(getUi());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            mPresenter.onRestoreInstanceState(savedInstanceState);
        }

        setStyle(DialogFragment.STYLE_NORMAL, 0);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mPresenter.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.onUiDestroy();
    }
}
