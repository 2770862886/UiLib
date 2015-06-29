package com.example.liangchao.uilib;

import android.os.Bundle;

public abstract class Presenter<U extends Ui> {
    private U mUi;

    public void onUiReady(U ui) {
        mUi = ui;
    }

    public final void onUiDestroy() {
        onUiUnready();
        mUi = null;
    }

    public void onUiUnready() {
    }

    public void onSaveInstanceState(Bundle outState) {
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
    }

    public U getUi() {
        return mUi;
    }

    public interface OnClickListener {
        void onClick(int id);
    }
}
