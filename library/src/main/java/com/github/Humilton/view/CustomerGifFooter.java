package com.github.Humilton.view;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.callback.IFooterCallBack;
import com.andview.refreshview.utils.Utils;
import com.github.Humilton.R;

public class CustomerGifFooter extends LinearLayout implements IFooterCallBack {
    private Context mContext;

    private View mContentView;
    private ImageView gifView;
    private TextView mHintView;
    private TextView mClickView;
    private AnimationDrawable animationDrawable;
    private boolean showing = false;

    public CustomerGifFooter(Context context) {
        super(context);
        initView(context);
    }

    public CustomerGifFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private RecyclerView recyclerView;

    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    @Override
    public void callWhenNotAutoLoadMore(final XRefreshView xRefreshView) {
        if (recyclerView != null) {
            //当数据不满一屏时不显示点击加载更多
            show(Utils.isRecyclerViewFullscreen(recyclerView));
        }
        mClickView.setText(R.string.xrefreshview_footer_hint_click);
        mClickView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                xRefreshView.notifyLoadMore();
            }
        });
    }

    @Override
    public void onStateReady() {
        animationDrawable.start();
        mHintView.setVisibility(View.GONE);
        gifView.setVisibility(View.VISIBLE);
        mClickView.setVisibility(View.GONE);
        mClickView.setText(R.string.xrefreshview_footer_hint_click);
    }

    @Override
    public void onStateRefreshing() {
        mHintView.setVisibility(View.GONE);
        gifView.setVisibility(View.VISIBLE);
        mClickView.setVisibility(View.GONE);
        show(true);
    }

    @Override
    public void onReleaseToLoadMore() {
        mHintView.setVisibility(View.GONE);
        gifView.setVisibility(View.VISIBLE);
        mClickView.setVisibility(View.GONE);
        mClickView.setText(R.string.xrefreshview_footer_hint_release);
    }

    @Override
    public void onStateFinish(boolean hideFooter) {
        if (hideFooter) {
            mHintView.setText(R.string.xrefreshview_footer_hint_normal);
        } else {
            //处理数据加载失败时ui显示的逻辑，也可以不处理，看自己的需求
            mHintView.setText(R.string.xrefreshview_footer_hint_fail);
        }
        mHintView.setVisibility(View.VISIBLE);
        gifView.setVisibility(View.GONE);
        mClickView.setVisibility(View.GONE);
    }

    @Override
    public void onStateComplete() {
        mHintView.setText(R.string.xrefreshview_footer_hint_complete);
        mHintView.setVisibility(View.VISIBLE);
        gifView.setVisibility(View.GONE);
        show(true);
    }

    @Override
    public void show(boolean show) {
        showing = show;
        LayoutParams lp = (LayoutParams) mContentView
                .getLayoutParams();
        lp.height = show ? LayoutParams.WRAP_CONTENT : 0;
        mContentView.setLayoutParams(lp);
    }

    @Override
    public boolean isShowing() {
        return showing;
    }

    private void initView(Context context) {
        mContext = context;
        ViewGroup moreView = (ViewGroup) LayoutInflater.from(mContext).inflate(R.layout.gif_footer, this);
        moreView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

        mContentView = moreView.findViewById(R.id.xrefreshview_footer_content);
        gifView = moreView.findViewById(R.id.gif);
        mHintView = moreView
                .findViewById(R.id.xrefreshview_footer_hint_textview);
        mClickView = moreView
                .findViewById(R.id.xrefreshview_footer_click_textview);

        animationDrawable = (AnimationDrawable) gifView.getDrawable();
        animationDrawable.start();
    }

    @Override
    public int getFooterHeight() {
        return getMeasuredHeight();
    }
}
