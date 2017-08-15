package com.github.Humilton.base;


import android.databinding.ViewDataBinding;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class BaseViewHolder extends RecyclerView.ViewHolder {
    private SparseArrayCompat<ViewDataBinding> mViewHolder = new SparseArrayCompat<>();

    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    public BaseViewHolder(int typeId, ViewDataBinding itemView) {
        super(itemView.getRoot());
        mViewHolder.put(typeId, itemView);
    }

    public ViewDataBinding getBinding(int id) {
        return mViewHolder.get(id);
    }
}
