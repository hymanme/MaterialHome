package com.hymane.materialhome.ui.activity;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.hymane.materialhome.R;
import com.hymane.materialhome.api.presenter.IEBookPresenter;
import com.hymane.materialhome.api.presenter.impl.EBookPresenterImpl;
import com.hymane.materialhome.api.view.IEBookListView;
import com.hymane.materialhome.bean.http.ebook.BookDetail;
import com.hymane.materialhome.bean.http.ebook.BooksByCats;
import com.hymane.materialhome.common.Constant;
import com.hymane.materialhome.ui.adapter.EBookListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/2/26
 * Description:
 */
public class EBookCategoryDetailActivity extends BaseActivity implements IEBookListView, SwipeRefreshLayout.OnRefreshListener {

    private static int limit = 20;
    private int start = 0;
    //二级排序分类
    //hot(热门)、new(新书)、reputation(好评)、over(完结)
    private String type = Constant.EBOOK_FILTER_HOT;
    //图书类别
    private String major;
    //性别
    private String gender = "";
    //二级类别：为空
    private static String minor = "";

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh_widget)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private GridLayoutManager mLayoutManager;
    private EBookListAdapter mListAdapter;
    private List<BookDetail> bookInfoResponses;
    private IEBookPresenter eBookRankPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_reviews);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initEvents() {
        major = getIntent().getStringExtra("major");
        gender = getIntent().getStringExtra("gender");
        setTitle(major == null ? "category" : major);

        int spanCount = getResources().getInteger(R.integer.home_span_count);
        eBookRankPresenter = new EBookPresenterImpl(this);
        bookInfoResponses = new ArrayList<>();
        mSwipeRefreshLayout.setColorSchemeResources(R.color.recycler_color1, R.color.recycler_color2,
                R.color.recycler_color3, R.color.recycler_color4);

        //设置布局管理器
        mLayoutManager = new GridLayoutManager(this, spanCount);
        mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return mListAdapter.getItemColumnSpan(position);
            }
        });
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //设置adapter
        mListAdapter = new EBookListAdapter(this, bookInfoResponses, spanCount);
        mRecyclerView.setAdapter(mListAdapter);

        //设置Item增加、移除动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addOnScrollListener(new RecyclerViewScrollDetector());
        mSwipeRefreshLayout.setOnRefreshListener(this);
        onRefresh();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_filter_hot:
                type = Constant.EBOOK_FILTER_HOT;
                onRefresh();
                break;
            case R.id.action_filter_new:
                type = Constant.EBOOK_FILTER_NEW;
                onRefresh();
                break;
            case R.id.action_filter_reputation:
                type = Constant.EBOOK_FILTER_REPUTATION;
                onRefresh();
                break;
            case R.id.action_filter_over:
                type = Constant.EBOOK_FILTER_OVER;
                onRefresh();
                break;
        }
        item.setChecked(true);
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getMenuID() {
        return R.menu.menu_ebook_category;
    }

    @Override
    public void showMessage(String msg) {
        Snackbar.make(mToolbar, msg, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showProgress() {
        mSwipeRefreshLayout.post(() -> mSwipeRefreshLayout.setRefreshing(true));
    }

    @Override
    public void hideProgress() {
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void refreshData(Object result) {
        if (result instanceof BooksByCats) {
            if (start == 0) {
                bookInfoResponses.clear();
            }
            bookInfoResponses.addAll(((BooksByCats) result).getBooks());
            mListAdapter.notifyDataSetChanged();
            start += limit;
        }
    }

    @Override
    public void onRefresh() {
        start = 0;
        eBookRankPresenter.getCategoryListDetail(gender, type, major, minor, start, limit);
    }

    private void onLoadMore() {
        eBookRankPresenter.getCategoryListDetail(gender, type, major, minor, start, limit);
    }

    class RecyclerViewScrollDetector extends RecyclerView.OnScrollListener {
        private int lastVisibleItem;

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == mListAdapter.getItemCount()) {
                onLoadMore();
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
        }
    }
}
