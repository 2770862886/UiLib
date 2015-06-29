package com.example.liangchao.uilib;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.liangchao.uilib.widget.LineChart;

import java.text.DecimalFormat;

public class DailyQuizFragment extends BaseFragment<DailyQuizPresenter, DailyQuizPresenter.DailyQuizUi>
        implements DailyQuizPresenter.DailyQuizUi, View.OnClickListener {
    private static final String TAG = "DailyQuizFragment";

    private Context mContext;

    private TextView mTitle;
    private TextView mSubtitle;
    private TextView mCurrentPrice;
    private View mGraphContainer;
    private LineChart mChart;
    private TextView mBuyerRate;
    private TextView mSellerRate;
    private TextView mBonus;
    private TextView mIgnore;

    private DecimalFormat mFormater;
    private String[] mBonusArray;
    private String[] mSubtitleArray;

    private Presenter.OnClickListener mListener;

    public static DailyQuizFragment newInstance() {
        return new DailyQuizFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFormater = new DecimalFormat("0.00");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.dialy_quiz_fragment, container);

        mTitle = (TextView) root.findViewById(R.id.dailyQuiz_title);
        mSubtitle = (TextView) root.findViewById(R.id.dailyQuiz_subtitle);
        mCurrentPrice = (TextView) root.findViewById(R.id.dailyQuiz_currentPrice);
        mGraphContainer = root.findViewById(R.id.dailyQuiz_graph_container);
        mChart = (LineChart) root.findViewById(R.id.dailyQuiz_graph);
        mBuyerRate = (TextView) root.findViewById(R.id.dailyQuiz_buy_title);
        mSellerRate = (TextView) root.findViewById(R.id.dailyQuiz_sell_title);
        mBonus = (TextView) root.findViewById(R.id.dailyQuiz_bonus);
        mIgnore = (TextView) root.findViewById(R.id.dailyQuiz_ignore);

        mBonusArray = mContext.getResources().getStringArray(R.array.dailyQuiz_bonus);
        mSubtitleArray = mContext.getResources().getStringArray(R.array.dailyQuiz_subtitles);

        mBuyerRate.setOnClickListener(this);
        mSellerRate.setOnClickListener(this);
        mIgnore.setOnClickListener(this);

        return root;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
    }

    @Override
    public void onClick(View v) {
        if (mListener != null) {
            if (R.id.dailyQuiz_buy_title == v.getId()) {
                mListener.onClick(DailyQuizPresenter.BUTTON_BUY);
            } else if (R.id.dailyQuiz_sell_title == v.getId()) {
                mListener.onClick(DailyQuizPresenter.BUTTON_SELL);
            } else if (R.id.dailyQuiz_ignore == v.getId()) {
                mListener.onClick(DailyQuizPresenter.BUTTON_IGNORE);
            }
        }
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
        if (mTitle != null) {
            mTitle.setText(title);
        }
    }

    @Override
    public void setSubtitle(String title) {
        if (mSubtitle != null) {
            mSubtitle.setText(title);
        }
    }

    @Override
    public void setSubtitle(int index) {
        if (mSubtitle != null && mSubtitleArray != null && index < mSubtitleArray.length) {
            mSubtitle.setText(mSubtitleArray[index]);
        }
    }

    @Override
    public void setCurrentPrice(float value) {
        if (mCurrentPrice != null && mFormater != null) {
            mCurrentPrice.setText(mFormater.format(value));
        }
    }

    @Override
    public void setPriceArray(float[] array) {

    }

    @Override
    public void setBuyerRate(String value) {
        if (mBuyerRate != null) {
            final String format = mContext.getResources().getString(R.string.dailyQuiz_buy, value);
            int i = format.indexOf('(');
            SpannableString span = new SpannableString(format);
            span.setSpan(new RelativeSizeSpan(1.2f), 0, i, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            span.setSpan(new RelativeSizeSpan(0.6f), i, format.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

            mBuyerRate.setText(span);
        }
    }

    @Override
    public void setSellerRate(String value) {
        if (mSellerRate != null) {
            final String format = mContext.getResources().getString(R.string.dailyQuiz_sell, value);
            int i = format.indexOf('(');
            SpannableString span = new SpannableString(format);
            span.setSpan(new RelativeSizeSpan(1.2f), 0, i, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            span.setSpan(new RelativeSizeSpan(0.6f), i, format.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

            mSellerRate.setText(span);
        }
    }


    @Override
    public void setBonus(int index) {
        if (mBonus != null) {
            StringBuilder buffer = new StringBuilder();
            for (int i = 0; i < mSubtitleArray.length; i++) {
                buffer.append(mBonusArray[i]);
                if (i != mSubtitleArray.length - 1) {
                    buffer.append(" ");
                }
            }

            if (index < 0 || index > mSubtitleArray.length - 1) {
                mBonus.setText(buffer.toString());
                return;
            }

            int start = buffer.indexOf(mBonusArray[index]);
            int end = start + mBonusArray[index].length();
            int forground = getResources().getColor(R.color.bonus_focus);
            SpannableString spannable = new SpannableString(buffer.toString());
            spannable.setSpan(new ForegroundColorSpan(forground), start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

            mBonus.setText(spannable);
        }
    }

    @Override
    public void setGraphBackgroundColor(int color) {
        if (mGraphContainer != null) {
            mGraphContainer.setBackgroundColor(color);
        }
    }

    @Override
    public void registerOnClickListener(Presenter.OnClickListener listener) {
        mListener = listener;
    }
}
