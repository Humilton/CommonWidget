package com.github.Humilton;

import android.databinding.ViewDataBinding;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Display;
import android.view.ViewGroup;

import com.github.Humilton.base.BaseRecyclerAdapter;
import com.github.Humilton.base.BaseViewHolder;
import com.github.Humilton.base.PagedXRefreshListener;
import com.github.Humilton.base.PagedXRefreshListener2;
import com.github.Humilton.databinding.BehaviorBinding;
import com.github.Humilton.entity.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hamilton on 2017/9/4.
 */

public class BehaviorActivity  extends BaseSampleActivity<BehaviorBinding> {
    private PagedXRefreshListener2 mListener;

    private List<Person> mData = new ArrayList<Person>();
    private SimpleAdapter adapter;
    private PagedXRefreshListener2.BaseBindingRecyclerAdapterConvert mRealAdapter ;

    @Override
    public int initBinding() {
        return R.layout.behavior;
    }

    @Override
    public void initView() {
        super.initView();

        Display display = mContext.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        ViewGroup.LayoutParams params1 = mBinding.emptyRecord.getLayoutParams();
        params1.height = size.y;
        mBinding.emptyRecord.setLayoutParams(params1);

        ViewGroup.LayoutParams params2 = mBinding.xrefreshview.getLayoutParams();
        params2.height = size.y;
        mBinding.xrefreshview.setLayoutParams(params2);

        mBinding.xrefreshview.post(new Runnable() {
            @Override
            public void run() {
                mBinding.scrollView.setRefresgView(mBinding.listRecord, mBinding.head.getMeasuredHeight());
                mBinding.listRecord.setScrollView(mBinding.scrollView);
            }
        });

        adapter = new SimpleAdapter(this, mData);
        mBinding.listRecord.setLayoutManager(new LinearLayoutManager(mContext));
        mRealAdapter = new PagedXRefreshListener2.BaseBindingRecyclerAdapterConvert(mContext, adapter);
        mBinding.listRecord.setAdapter(mRealAdapter);
        mListener = new PagedXRefreshListener2(mBinding.listRecord) {
            @Override
            public void modifyData(int maxCount) {
                updateData(maxCount);
            }
        };
        mBinding.xrefreshview.setXRefreshViewListener(mListener);

        updateData(mListener.pageSize) ;
    }

    private void updateData(int maxCount) {
        if(mListener.pageNo == 1) mData.clear();
        for (int i = 0; i < mListener.pageSize && i< (mListener.pageSize*5 + 2); i++) {
            Person person = new Person("name" + (i + (mListener.pageNo - 1) * mListener.pageSize), "" + (i + (mListener.pageNo - 1) * mListener.pageSize));
            mData.add(person);
        }
        mListener.modifiedData(mData.size());
        adapter.notifyDataSetChanged();
        mRealAdapter.notifyDataSetChanged();
    }

    public static class SimpleAdapter extends BaseRecyclerAdapter<Person> {
        private static final int TYPE_NORMAL = 0;

        public SimpleAdapter(AppCompatActivity mContext, List<Person> bean) {
            super(mContext, bean);
        }

        @Override
        protected void initBindingViewHolder(ViewGroup parent, int viewType) {
            ViewDataBinding binding = addBinding(TYPE_NORMAL, R.layout.item_recylerview, parent);
        }

        @Override
        public void onBindViewHolder(BaseViewHolder holder, int position) {
            Person person = bean.get(position);
            holder.getBinding(TYPE_NORMAL).setVariable(com.github.Humilton.BR.person, person);
        }
    }
}
