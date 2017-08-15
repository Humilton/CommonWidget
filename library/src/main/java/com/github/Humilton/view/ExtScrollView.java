package com.github.Humilton.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.ScrollView;

/**
 * Created by adminjackfy on 2017/7/18.
 */

public class ExtScrollView extends ScrollView {
    private int mSlop;
    private float mDownX;
    private float mDownY;
    private boolean mSwiping;

    public ExtScrollView(Context context) {
        super(context);
        init();
    }

    public ExtScrollView(Context context, AttributeSet attrs,
                         int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public ExtScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        ViewConfiguration vc = ViewConfiguration.get(this.getContext());
        mSlop = vc.getScaledTouchSlop();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean res = super.onInterceptTouchEvent(ev);
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = ev.getX();
                mDownY = ev.getY();
                mSwiping = false;
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_MOVE:
                float x = ev.getX();
                float y = ev.getY();
                float xDelta = Math.abs(x - mDownX);
                float yDelta = Math.abs(y - mDownY);

                if (yDelta > mSlop && yDelta / 2 > xDelta) {
                    mSwiping = true;
                    res = true;
                }
                break;
        }

        return res;
    }
}
