package com.github.Humilton.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ScrollView;

/**
 * Created by Hamilton on 2017/9/5.
 */

public class FriendInfoRecyclerView extends RecyclerView {
    private ScrollView mScrollView;
    private int[] locations = new int[2];

    public FriendInfoRecyclerView(Context context) {
        super(context);
    }

    public FriendInfoRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public FriendInfoRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setScrollView(ScrollView scroll) {
        this.mScrollView = scroll;
    }

//    @Override
//    public boolean fling(int velocityX, int velocityY) {
//        boolean result = super.fling(velocityX, velocityY);
//
//        this.getLocationOnScreen(locations);
//        boolean res = (this.computeVerticalScrollOffset() == 0);
//
//        if(velocityY < 0 && res) mScrollView.fling(velocityY);
//        return result;
//    }
}
