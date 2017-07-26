package com.github.Humilton.base;

import android.os.Handler;

import com.andview.refreshview.XRefreshView;

/**
 * Created by Hamilton on 2017/7/25.
 */

public abstract class PagedXRefreshListener implements XRefreshView.XRefreshViewListener {
    private int pageNo = 1;
    public static final int pageSize = 20;
    private XRefreshView xRefreshView;
    private BaseBindingRecyclerAdapter adapter;

    public PagedXRefreshListener(XRefreshView xRefreshView, BaseBindingRecyclerAdapter adapter) {
        this.xRefreshView = xRefreshView;
        this.adapter = adapter;
    }

    @Override
    public void onRelease(float direction) {

    }

    @Override
    public void onHeaderMove(double headerMovePercent, int offsetY) {

    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onRefresh(boolean isPullDown) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                xRefreshView.stopRefresh();
                xRefreshView.setLoadComplete(false);
                pageNo = 1;
                modifyData(pageNo*pageSize);
            }
        }, 2000);
    }

    @Override
    public void onLoadMore(boolean isSilence) {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                pageNo++;
                modifyData(pageNo*pageSize);
                if (reachEnd(pageNo*pageSize)) {
                    xRefreshView.setLoadComplete(true);
                } else {
                    // 刷新完成必须调用此方法停止加载
                    xRefreshView.stopLoadMore();
                }
            }
        }, 1000);
    }

    public abstract void modifyData(int maxCount);
    public abstract boolean reachEnd(int maxCount);
}
