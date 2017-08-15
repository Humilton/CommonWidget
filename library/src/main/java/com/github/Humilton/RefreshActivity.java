package com.github.Humilton;

import android.databinding.ViewDataBinding;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.XRefreshViewFooter;
import com.github.Humilton.base.BaseBindingRecyclerAdapter;
import com.github.Humilton.base.BaseViewHolder;
import com.github.Humilton.base.PagedXRefreshListener;
import com.github.Humilton.databinding.RefreshBinding;
import com.github.Humilton.entity.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hamilton on 2017/7/25.
 */

public class RefreshActivity extends BaseActivity<RefreshBinding>  {
    RecyclerView recyclerView;
    SimpleAdapter adapter;
    List<Person> personList = new ArrayList<Person>();
    XRefreshView xRefreshView;

    @Override
    public int initBinding() {
        return R.layout.refresh;
    }

    @Override
    public void initView() {
        mBinding.titleBar.setTitle(R.string.pull_refresh);

        for (int i = 0; i < PagedXRefreshListener.pageSize*5 + 2; i++) {
            Person person = new Person("name" + i, "" + i);
            personList.add(person);
        }

        xRefreshView = mBinding.xrefreshview;
        xRefreshView.setPullLoadEnable(true);
        recyclerView = mBinding.list;
        recyclerView.setHasFixedSize(true);

        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new SimpleAdapter(this, personList.subList(0, PagedXRefreshListener.pageSize));
        recyclerView.setAdapter(adapter);
        xRefreshView.setPinnedTime(1000);
        xRefreshView.setMoveForHorizontal(true);
        adapter.setCustomLoadMoreView(new XRefreshViewFooter(this));

        xRefreshView.setXRefreshViewListener(new PagedXRefreshListener(xRefreshView, adapter) {
            @Override
            public void modifyData(int maxCount) {
                adapter.change(personList.subList(0, Math.min(maxCount, personList.size())));
            }

            @Override
            public boolean reachEnd(int maxCount) {
                return  maxCount >= personList.size();
            }
        });
    }

    public class SimpleAdapter extends BaseBindingRecyclerAdapter<Person> {

        public SimpleAdapter(AppCompatActivity mContext, List<Person> bean) {
            super(mContext, bean);
        }

        @Override
        protected void initBindingViewHolder(ViewGroup parent, int viewType, boolean isItem) {
            ViewDataBinding binding = addBinding(VIEW_TYPES.NORMAL, R.layout.item_recylerview, parent);
            binding.setVariable(BR.mSimpleAdapter, this);
        }

        @Override
        public void onBindViewHolder(BaseViewHolder holder, int position, boolean isItem) {
            Person person = bean.get(position);
            holder.getBinding(VIEW_TYPES.NORMAL).setVariable(BR.person, person);
        }
    }
}
