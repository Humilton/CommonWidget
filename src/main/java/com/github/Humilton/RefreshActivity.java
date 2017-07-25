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
    private int pageNo = 1;
    private static final int pageSize = 10;

    @Override
    public int initBinding() {
        return R.layout.refresh;
    }

    @Override
    public void initView() {
        mBinding.titleBar.setTitle(R.string.pull_refresh);

        for (int i = 0; i < pageSize*5 + 2; i++) {
            Person person = new Person("name" + i, "" + i);
            personList.add(person);
        }

        xRefreshView = mBinding.xrefreshview;
        xRefreshView.setPullLoadEnable(true);
        recyclerView = mBinding.list;
        recyclerView.setHasFixedSize(true);

        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new SimpleAdapter(this, personList.subList(0, maxSize()));
        recyclerView.setAdapter(adapter);
        xRefreshView.setPinnedTime(1000);
        xRefreshView.setMoveForHorizontal(true);
        adapter.setCustomLoadMoreView(new XRefreshViewFooter(this));

        xRefreshView.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {

            @Override
            public void onRefresh(boolean isPullDown) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        xRefreshView.stopRefresh();
                        xRefreshView.setLoadComplete(false);
                        pageNo = 1;
                        adapter.change(personList.subList(0, maxSize()));
                    }
                }, 2000);
            }

            @Override
            public void onLoadMore(boolean isSilence) {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        pageNo++;
                        adapter.change(personList.subList(0, maxSize()));
                        if (pageNo*pageSize >= personList.size()) {
                            xRefreshView.setLoadComplete(true);
                        } else {
                            // 刷新完成必须调用此方法停止加载
                            xRefreshView.stopLoadMore();
                        }
                    }
                }, 1000);
            }
        });
    }

    private int maxSize() {
        return Math.min(personList.size(), pageNo*pageSize);
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
