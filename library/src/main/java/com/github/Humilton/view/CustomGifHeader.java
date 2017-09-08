package com.github.Humilton.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andview.refreshview.callback.IHeaderCallBack;
import com.github.Humilton.R;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class CustomGifHeader extends LinearLayout implements IHeaderCallBack {
    private ImageView gifView;
    private TextView mHintTextView;
    private AnimationDrawable animationDrawable;

    public CustomGifHeader(Context context) {
        super(context);
        setBackgroundColor(Color.parseColor("#f3f3f3"));
        initView(context);
    }

    /**
     * @param context
     * @param attrs
     */
    public CustomGifHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.gif_header, this);
        gifView = findViewById(R.id.gif);
        mHintTextView = findViewById(R.id.tv);
        animationDrawable = (AnimationDrawable) gifView.getDrawable();
    }

    public void setRefreshTime(long lastRefreshTime) {
    }

    public void hide() {
        setVisibility(View.GONE);
    }

    public void show() {
        setVisibility(View.VISIBLE);
    }

    @Override
    public void onStateNormal() {
        mHintTextView.setText(R.string.xrefreshview_header_hint_normal);
        animationDrawable.start();
    }

    @Override
    public void onStateReady() {
        mHintTextView.setText(R.string.xrefreshview_header_hint_ready);
    }

    @Override
    public void onStateRefreshing() {
        mHintTextView.setText(R.string.xrefreshview_header_hint_refreshing);
    }

    @Override
    public void onStateFinish(boolean success) {
        mHintTextView.setText(success ? R.string.xrefreshview_header_hint_loaded : R.string.xrefreshview_header_hint_loaded_fail);
        Observable.timer(700, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        animationDrawable.stop();
                    }
                });
    }

    @Override
    public void onHeaderMove(double headerMovePercent, int offsetY, int deltaY) {
        //
    }

    @Override
    public int getHeaderHeight() {
        return getMeasuredHeight();
    }
}
