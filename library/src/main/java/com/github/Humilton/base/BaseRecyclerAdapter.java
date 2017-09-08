package com.github.Humilton.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRecyclerAdapter<Bean> extends RecyclerView.Adapter<BaseViewHolder> {
    private RecyclerView mRecyclerView;
    private View VIEW_FOOTER;
    private View VIEW_HEADER;
    protected AppCompatActivity mContext;
    private BaseViewHolder viewHolder;
    protected List<Bean> bean;

    //Type
    protected int TYPE_NORMAL = 1000;
    protected final static int EMPTY_VIEW = 10086111;

    @SuppressWarnings("all")
    public BaseViewHolder getViewHolder() {
        return viewHolder;
    }

    private int emptyViewId = -1;

    public void setEmptyView(int emptyViewId) {
        this.emptyViewId = emptyViewId;
        notifyDataSetChanged();
    }

    public BaseRecyclerAdapter(AppCompatActivity mContext, List<Bean> bean) {
        this.mContext = mContext;
        if (bean == null) {
            this.bean = new ArrayList<>();
        } else {
            this.bean = bean;
        }
    }

    /**
     * 更新数据源
     */
    public void update(Bean step) {
        bean.add(step);
        notifyItemChanged(getItemCount() + 1);
    }

    /**
     * 增加数据源
     */
    public void addAll(List<Bean> bean) {
        if (bean == null) return;
        this.bean.addAll(bean);
        notifyDataSetChanged();
    }

    /**
     * 改变数据源
     */
    public void change(List<Bean> bean) {
        if (bean != null) {
            this.bean = bean;
        } else {
            this.bean.clear();
        }
        notifyDataSetChanged();
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        initBindingViewHolder(parent, viewType);
        if (viewType == EMPTY_VIEW) {
            addBinding(EMPTY_VIEW, emptyViewId, parent);
        }
        return viewHolder;
    }

    protected abstract void initBindingViewHolder(ViewGroup parent, int viewType);

    public void callInitBindingViewHolder(ViewGroup parent, int viewType) {
        initBindingViewHolder(parent, viewType);
    }

    public List<Bean> getBean() {
        return bean;
    }

    public void setBean(List<Bean> bean) {
        this.bean = bean;
    }

    @Override
    public int getItemCount() {
        return getCount();
    }

    protected ViewDataBinding addBinding(int typeId, int layoutId, ViewGroup parent) {
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), layoutId, parent, false);
        viewHolder = new BaseViewHolder(typeId, binding);
        return binding;
    }

    public int getCount() {
        int count = (bean == null ? 1 : bean.size());
        if (VIEW_FOOTER != null) {
            count++;
        }
        if (VIEW_HEADER != null) {
            count++;
        }
        if (count == 0 && emptyViewId != -1) {
            count++;
        }
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        if (getItemCount() == 1 && emptyViewId != -1 && (bean == null || bean.size() == 0)) {
            return EMPTY_VIEW;
        }
        if (isHeaderView(position)) {
            return 1001;
        } else if (isFooterView(position)) {
            return 1002;
        } else {
            return TYPE_NORMAL;
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        try {
            if (mRecyclerView == null && null != recyclerView) {
                mRecyclerView = recyclerView;
            }
            ifGridLayoutManager();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("all")
    public void addHeaderView(View headerView) {
        if (haveHeaderView()) {
            throw new IllegalStateException("hearview has already exists!");
        } else {
            //避免出现宽度自适应
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            headerView.setLayoutParams(params);
            VIEW_HEADER = headerView;
            ifGridLayoutManager();
            notifyItemInserted(0);
        }
    }

    @SuppressWarnings("all")
    public void addFooterView(View footerView) {
        if (haveFooterView()) {
            throw new IllegalStateException("footerView has already exists!");
        } else {
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            footerView.setLayoutParams(params);
            VIEW_FOOTER = footerView;
            ifGridLayoutManager();
            notifyItemInserted(getItemCount() - 1);
        }
    }

    private void ifGridLayoutManager() {
        if (mRecyclerView == null) return;
        final RecyclerView.LayoutManager layoutManager = mRecyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            ((GridLayoutManager) layoutManager).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return (isHeaderView(position) || isFooterView(position)) ?
                            ((GridLayoutManager) layoutManager).getSpanCount() :
                            1;
                }
            });
        }
    }

    protected boolean haveHeaderView() {
        return VIEW_HEADER != null;
    }

    protected boolean haveFooterView() {
        return VIEW_FOOTER != null;
    }

    protected boolean isHeaderView(int position) {
        return haveHeaderView() && position == 0;
    }

    protected boolean isFooterView(int position) {
        return haveFooterView() && position == getItemCount() - 1;
    }

}
