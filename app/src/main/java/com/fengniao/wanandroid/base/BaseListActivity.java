package com.fengniao.wanandroid.base;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import com.fengniao.wanandroid.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public abstract class BaseListActivity<T> extends BaseActivity implements FNAdapter.ViewProvider {
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;
    protected List<T> mList;
    protected FNAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    protected int mPage, mCount = 10;
    protected int maxId, minId = 0;
    protected int selectPosition = -1;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(0);
        initView();
    }

    //刷新控件开关
    public void enableRefresh(boolean status) {
        //禁止下拉刷新
        swipeRefreshLayout.setEnabled(status);
    }

    //是否显示refreshBar
    public void showRefreshBar(boolean isShow) {
        swipeRefreshLayout.setRefreshing(isShow);
    }

    public void setListAdapter() {
        if (getActivity() == null) return;

        if (mAdapter == null) {
            initAdapter();
            mRecyclerView.setAdapter(mAdapter);
        } else
            notifyDataSetChanged();

        if (swipeRefreshLayout.isRefreshing())
            showRefreshBar(false);

        mAdapter.setLoadMoreStatus(false);

    }

    public abstract void loadData();

    @Override
    public void loadMore() {
        mPage++;
        loadData();
    }

    public void initView() {
        mList = new ArrayList<>();
        showRefreshBar(true);
        initAdapter();
        mLayoutManager = onCreateLayoutManager();
        if (mLayoutManager == null)
            mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                BaseListActivity.this.onRefresh();
            }
        });
        afterCreateView();
        loadData();
    }

    public void afterCreateView() {

    }


    public void onRefresh() {
        if (!swipeRefreshLayout.isRefreshing())
            swipeRefreshLayout.setRefreshing(true);
        maxId = 0;
        minId = 0;
        mPage = 0;
        mList.clear();
        notifyDataSetChanged();
        loadData();
    }


    public void initAdapter() {
        mAdapter = new FNAdapter<T>(this, mList);
        mAdapter.setViewProvider(this);
    }

    public void enableLoadMore(boolean isLoadMore) {
        mAdapter.enableLoadMore(isLoadMore);
    }

    public RecyclerView.LayoutManager onCreateLayoutManager() {
        return null;
    }

    public void setLayoutManager(RecyclerView.LayoutManager manager) {
        mLayoutManager = manager;
    }

    public void notifyDataSetChanged() {
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


}
