package com.github.Humilton.view;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
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

public class FriendInfoBehavior extends CoordinatorLayout.Behavior{
    int offsetTotal = 0;
    boolean scrolling = false;

    protected RecyclerView mRecyclerView = null;
    private int[] locations = new int[2];
    protected FriendInfoScrollView mScrollView;
    private boolean isHeadVisible = true;

    public FriendInfoBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
        mRecyclerView = (RecyclerView) target;
        mScrollView = (FriendInfoScrollView) child;

//        mRecyclerView.getLocationOnScreen(locations);
//        boolean res = (mRecyclerView.computeVerticalScrollOffset() == 0) || (locations[1] > 0);
//
//        res = res && ((nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0);
        Log.e("==>", "onStartNestedScroll : " + ((nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0));
        return ((nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0);
//        return false;
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dx, int dy, int[] consumed) {
        //super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);
        int leftScrolled = mRecyclerView.computeVerticalScrollOffset();
        //mScrollView.setScrollY(leftScrolled);
        //mRecyclerView.setScaleY(leftScrolled);

        Log.e("==>", "onNestedPreScroll : " + leftScrolled + ", (mScrollView.getScrollY() =" + mScrollView.getScrollY() + " , " + child.getTranslationY() + ", dy="+dy);

//        boolean res = (mScrollView.getScrollY() >= mScrollView.getMeasuredHeight() - mRecyclerView.getMeasuredHeight());
        boolean bScrollThis = (mScrollView.getScrollY() <= 320);
//        if(bScrollThis) {
//            mScrollView.setScrollY((int) (mScrollView.getTranslationY() + dy));
//        }
//        else {
//            if(mRecyclerView.computeVerticalScrollOffset() == 0) {
//                mScrollView.setScrollY((int) (mScrollView.getTranslationY() + dy));
//            }
//            mRecyclerView.setScrollY((int) (mRecyclerView.getTranslationY() + dy));
//        }

        mScrollView.setScrollY((int) (mScrollView.getTranslationY() + dy));
        mRecyclerView.setScrollY((int) (mRecyclerView.getTranslationY() + dy));

//        mRecyclerView.getLocationOnScreen(locations);
//        isHeadVisible = (mRecyclerView.computeVerticalScrollOffset() == 0) || (locations[1] > 0);

//
//        mRecyclerView.getLocationOnScreen(locations);
//        boolean res = (mRecyclerView.computeVerticalScrollOffset() == 0) && (locations[1] > 0);
//        if(!res) {
////            mRecyclerView.setScrollY(leftScrolled);
//        }
//        else {
//            mScrollView.setScrollY(leftScrolled);
//        }
    }

    @Override
    public boolean onNestedPreFling(CoordinatorLayout coordinatorLayout, View child, View target, float velocityX, float velocityY) {
        Log.e("==>", "onNestedPreFling : " + velocityY + "...");

//        mRecyclerView.getLocationOnScreen(locations);
//        isHeadVisible = !((mRecyclerView.computeVerticalScrollOffset() == 0) || (locations[1] > 0));
//        if(isHeadVisible) {
//            Log.w("==>", "ScrollView : " + "fling");
//            ((FriendInfoScrollView)child).fling((int)velocityY);
//        }

//        boolean res = (mScrollView.getScrollY() >= mScrollView.getMeasuredHeight() - mRecyclerView.getMeasuredHeight());
//        if(res) {
//            mRecyclerView.fling(0, (int)velocityY);
//        }

//        mRecyclerView.fling(0, (int) velocityY);
//
//        mRecyclerView.getLocationOnScreen(locations);
//        boolean res = (mRecyclerView.computeVerticalScrollOffset() == 0) && (locations[1] > 0);
//        if(!res) {
////            mRecyclerView.fling(0, (int)velocityY);
//        }
//        else {
//            mScrollView.fling((int)velocityY);
//        }
        return true;
    }
}
