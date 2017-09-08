package com.github.Humilton.view;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.NestedScrollingChild;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ScrollView;

import com.andview.refreshview.XRefreshView;

/**
 * Created by adminjackfy on 2017/7/18.
 */

public class FriendInfoScrollView extends ScrollView {
    private int mSlop;
    private float mDownX;
    private float mDownY;
    private boolean mSwiping;
    private int totalHeight = 0, bottomHeight = 0;

    protected XRefreshView xRefreshView = null;
    protected RecyclerView mRecyclerView = null;
    private int[] locations = new int[2];

    public FriendInfoScrollView(Context context) {
        super(context);
        init();
    }

    public FriendInfoScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public FriendInfoScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        ViewConfiguration vc = ViewConfiguration.get(this.getContext());
        mSlop = vc.getScaledTouchSlop();
    }

    public void setRefresgView(RecyclerView mRecyclerView , int totalHeight) {
        this.mRecyclerView = mRecyclerView;
        this.totalHeight = totalHeight;
        this.bottomHeight = mRecyclerView.getMeasuredHeight();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean res = super.onInterceptTouchEvent(ev);
//        switch (ev.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                mDownX = ev.getX();
//                mDownY = ev.getY();
//                mSwiping = false;
//                break;
//            case MotionEvent.ACTION_CANCEL:
//            case MotionEvent.ACTION_UP:
//                break;
//            case MotionEvent.ACTION_MOVE:
//                float x = ev.getX();
//                float y = ev.getY();
//                float xDelta = Math.abs(x - mDownX);
//                float yDelta = Math.abs(y - mDownY);
//
//                if (yDelta > mSlop && yDelta / 2 > xDelta) {
//                    mSwiping = true;
//
//                    mRecyclerView.getLocationOnScreen(locations);
//                    //res = (mRecyclerView.computeVerticalScrollOffset() == 0) && (locations[1] > 0);
//                    res = (mRecyclerView.computeVerticalScrollOffset() < totalHeight - this.getMeasuredHeight());
//                }
//                break;
//        }

        return true;
    }

//    @Override
//    public void setScrollY(int value) {
//        boolean res = (this.computeVerticalScrollOffset() >= this.getMeasuredHeight() - mRecyclerView.getMeasuredHeight());
//        if(res) mRecyclerView.setScrollY(value);
//        else super.setScrollY(value);
//    }

//    @Override
//    public boolean onTouchEvent(MotionEvent ev) {
////        boolean bScrollThis = (this.computeVerticalScrollOffset() <= totalHeight);
////        Log.e("==>",this.computeVerticalScrollOffset() + ", " + totalHeight + " : res = " + bScrollThis + " : " + mRecyclerView.getScrollY() + ", " + mRecyclerView.computeVerticalScrollOffset());
////        if(bScrollThis){
////            super.onTouchEvent(ev);
////            mRecyclerView.onTouchEvent(ev);
////            if(mRecyclerView.computeVerticalScrollOffset() < totalHeight / 2) {
////                mRecyclerView.onTouchEvent(ev);
////                mRecyclerView.fling(0, velocityY);
////            }
////        }
////        else {
////            mRecyclerView.onTouchEvent(ev);
////            if(mRecyclerView.computeVerticalScrollOffset() < totalHeight / 2) {
////                super.onTouchEvent(ev);
////            }
////        }
//
////        mRecyclerView.onTouchEvent(ev);
////        super.onTouchEvent(ev);
//        return true;
//    }

//    private int velocityY = 0;
//    @Override
//    public void fling(int velocityY) {
//        super.fling(velocityY);
//        this.velocityY = velocityY;
////        boolean res = (this.computeVerticalScrollOffset() <= totalHeight);
////
////        Log.e("==>", "velocityY = " + velocityY + ", res = " + res + ", this.computeVerticalScrollOffset() = " + this.computeVerticalScrollOffset());
////        if(!res) mRecyclerView.fling(0, velocityY);
//////        if(velocityY > 0 && res) mRecyclerView.fling(0, velocityY);
//////
//////        res = (this.computeVerticalScrollOffset() <= totalHeight - mRecyclerView.getMeasuredHeight());
//////        if(velocityY < 0 && res) mRecyclerView.fling(0, velocityY);
//    }
}
