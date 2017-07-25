package com.github.Humilton.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseBindingRecyclerAdapter<Bean> extends BaseRecyclerAdapter<BaseViewHolder> {
    protected AppCompatActivity mContext;
    private BaseViewHolder viewHolder;
    protected List<Bean> bean;

    @SuppressWarnings("all")
    public BaseViewHolder getViewHolder(View view) {
        return new BaseViewHolder(view);
    }

    public BaseBindingRecyclerAdapter(AppCompatActivity mContext, List<Bean> bean) {
        this.mContext = mContext;
        this.bean = (bean == null ? new ArrayList<Bean>() : bean);
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
        bean.addAll(bean);
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
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
        initBindingViewHolder(parent, viewType, isItem);
        return viewHolder;
    }

    protected abstract void initBindingViewHolder(ViewGroup parent, int viewType, boolean isItem);

    @Override
    public int getAdapterItemCount() {
        return bean.size();
    }

    @Override
    public int getAdapterItemViewType(int position) {
        return 0;
    }

    protected ViewDataBinding addBinding(int typeId, int layoutId, ViewGroup parent) {
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), layoutId, parent, false);
        viewHolder = new BaseViewHolder(typeId, binding);
        return binding;
    }
}
