package com.github.Humilton.base;

import android.content.Context;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.andview.refreshview.XRefreshView;
import com.github.Humilton.view.CustomGifHeader;
import com.github.Humilton.view.CustomerGifFooter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hamilton on 2017/7/25.
 */

public abstract class PagedXRefreshListener2 implements XRefreshView.XRefreshViewListener {
    public int pageNo = 1;
    public int pageSize = 20;
    protected XRefreshView xRefreshView;
    private BaseBindingRecyclerAdapter mAdapter;
    private static Handler mHandler = new Handler();
    private CustomGifHeader mHeader;
    private CustomerGifFooter mFooter;
    private boolean isLoadComplete = false;

    private Runnable resetStatus = new Runnable() {
        @Override
        public void run() {
            modifiedData(-1);
        }
    };
    private static final int RESET_TIMEOUT = 5000;

    public PagedXRefreshListener2(RecyclerView recyclerView) {
        recyclerView.setHasFixedSize(true);

        this.xRefreshView = (XRefreshView) recyclerView.getParent();
        this.mAdapter = (BaseBindingRecyclerAdapter) recyclerView.getAdapter();

        Context mContext = recyclerView.getContext();
        xRefreshView.setPullLoadEnable(true);
        xRefreshView.setPinnedTime(1000);
        xRefreshView.setMoveForHorizontal(true);
        xRefreshView.setPinnedContent(true);
        mHeader = new CustomGifHeader( mContext );
        xRefreshView.setCustomHeaderView(new CustomGifHeader( mContext ));

        mFooter = new CustomerGifFooter(mContext);
        mAdapter.setCustomLoadMoreView(mFooter);
        showFooter(false);
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
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

    public void reset() {
        pageNo = 1;
    }

    @Override
    public void onRefresh(boolean isPullDown) {
        isLoadComplete = false;
        showFooter(false);
        xRefreshView.stopRefresh();
        xRefreshView.setLoadComplete(false);
        xRefreshView.setAutoLoadMore(true);
        pageNo = 1;
        modifyData(pageNo*pageSize);

        mHandler.postDelayed(resetStatus, RESET_TIMEOUT);
    }

    @Override
    public void onLoadMore(boolean isSilence) {
        showFooter(true);
        if(isLoadComplete){
            modifiedData(-1);
            return;
        }
        pageNo++;
        modifyData(pageNo*pageSize);

        mHandler.postDelayed(resetStatus, RESET_TIMEOUT);
    }

    public void modifiedData(final int dataCount) {
        modifiedData(dataCount, null);
    }

    public void modifiedData(final int dataCount, final Runnable r) {
        mHandler.removeCallbacks(resetStatus);
        if(r != null) mHandler.postDelayed(r, 300);

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(xRefreshView == null)  return;
                if (pageNo*pageSize > dataCount) {
                    isLoadComplete = true;
                }
                xRefreshView.stopLoadMore();
                showFooter(false);
            }
        }, 700);
    }

    private void showFooter(boolean show) {
        if(mFooter == null)  return;

        if(show) mFooter.setVisibility(View.VISIBLE);
        else mFooter.setVisibility(View.GONE);
    }

    protected <T> void handleWorkData(Object rawdata, List<T> list) {
        List<T> data = (List<T>) rawdata;
        if (data != null && data.size() > 0){
            list.addAll(data);
        }
    }

    private <T> void doDirtyWork(Context mContext, Object obj, List<T> list, BaseRecyclerAdapter mAdapter) {
        int res;
        String msg;
        Object data;

        Class clazz = obj.getClass();
        try {
            Method m1 = clazz.getDeclaredMethod("getRes");
            Method m2 = clazz.getDeclaredMethod("getMsg");
            Method m3 = clazz.getDeclaredMethod("getMsgData");
            res = (int) m1.invoke(obj);
            msg = (String) m2.invoke(obj);

            /// Handle Result
            if (res == 1) {
                data = m3.invoke(obj);
                handleWorkData(data, list);
                mAdapter.change(list);
            } else {
                Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
            }

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return;
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public final <T> void doDirtyWork(Class cls, ArrayList<T> data, Object obj, BaseRecyclerAdapter mAdapter, View emptyView ) {
        if(obj == null) return;
        if(!cls.isInstance(obj))  return;
        if(pageNo <= 1) data.clear();

       doDirtyWork(xRefreshView.getContext(), obj, data, mAdapter);

       modifiedData(data.size());

        if(emptyView != null) {
            boolean isEmpty = data.size() == 0;
            xRefreshView.setVisibility(isEmpty ? View.GONE : View.VISIBLE);
            emptyView.setVisibility(!isEmpty ? View.GONE : View.VISIBLE);
        }
    }

    public final <T> void  doDirtyWork(Class cls, ArrayList<T> data, Object obj, BaseRecyclerAdapter mAdapter, View emptyView, RelativeLayout loadfail) {
        if(obj == null) return;
        if (obj instanceof Exception) {
            if(loadfail != null) loadfail.setVisibility(View.VISIBLE);
        } else if(cls.isInstance(obj)) {
            if (pageNo <= 1) data.clear();

            doDirtyWork(xRefreshView.getContext(), obj, data, mAdapter);

            modifiedData(data.size());
        }

        if(loadfail != null && loadfail.getVisibility() != View.VISIBLE) {
            boolean isEmpty = data.size() == 0;
            xRefreshView.setVisibility(isEmpty ? View.GONE : View.VISIBLE);
            if(emptyView != null) emptyView.setVisibility(!isEmpty ? View.GONE : View.VISIBLE);
        }
        else {
            xRefreshView.setVisibility(View.GONE);
            if(emptyView != null) emptyView.setVisibility(View.GONE);
        }
    }

    public abstract void modifyData(int maxCount);

    public static class BaseBindingRecyclerAdapterConvert extends BaseBindingRecyclerAdapter{
        private BaseRecyclerAdapter mAdapter;
        private RecyclerView.AdapterDataObserver mObserver = new RecyclerView.AdapterDataObserver() {
            public void onChanged() {
                BaseBindingRecyclerAdapterConvert.this.notifyDataSetChanged();
            }

            public void onItemRangeChanged(int positionStart, int itemCount) {
                BaseBindingRecyclerAdapterConvert.this.notifyDataSetChanged();
            }

            public void onItemRangeInserted(int positionStart, int itemCount) {
                BaseBindingRecyclerAdapterConvert.this.notifyDataSetChanged();
            }

            public void onItemRangeRemoved(int positionStart, int itemCount) {
                BaseBindingRecyclerAdapterConvert.this.notifyDataSetChanged();
            }

            public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
                BaseBindingRecyclerAdapterConvert.this.notifyDataSetChanged();
            }
        };

        public BaseBindingRecyclerAdapterConvert(AppCompatActivity mContext, BaseRecyclerAdapter adapter) {
            super(mContext, adapter.getBean());
            this.mAdapter = adapter;
            this.mAdapter.registerAdapterDataObserver(mObserver);
            this.setHasStableIds(true);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        protected void initBindingViewHolder(ViewGroup parent, int viewType, boolean isItem) {
            mAdapter.callInitBindingViewHolder(parent, viewType);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, boolean isItem) {
            mAdapter.onBindViewHolder(holder, position);
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == VIEW_TYPES.FOOTER || viewType == VIEW_TYPES.HEADER) {
                return super.onCreateViewHolder(parent, viewType);
            }
            return mAdapter.onCreateViewHolder(parent, viewType);
        }
    }
}
