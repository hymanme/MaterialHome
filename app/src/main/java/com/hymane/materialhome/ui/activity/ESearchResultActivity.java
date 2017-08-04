package com.hymane.materialhome.ui.activity;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hymane.materialhome.R;
import com.hymane.materialhome.api.presenter.IEBookDetailPresenter;
import com.hymane.materialhome.api.presenter.IEBookPresenter;
import com.hymane.materialhome.api.presenter.impl.EBookDetailPresenterImpl;
import com.hymane.materialhome.api.presenter.impl.EBookPresenterImpl;
import com.hymane.materialhome.api.view.IEBookDetailView;
import com.hymane.materialhome.api.view.IEBookListView;
import com.hymane.materialhome.bean.http.ebook.BookDetail;
import com.hymane.materialhome.bean.http.ebook.BooksByCats;
import com.hymane.materialhome.bean.http.ebook.BooksByTag;
import com.hymane.materialhome.ui.adapter.EBookListAdapter;
import com.hymane.materialhome.utils.common.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/1/14 0014
 * Description:
 */
public class ESearchResultActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, IEBookDetailView, IEBookListView {
    //接口调用参数 tag：标签，q：搜索关键词，fields：过滤词，count：一次返回数据数，
    // page：当前已经加载的页数，PS:tag,q只存在其中一个，另一个置空
    private static final int PRO_LOADING_SIZE = 2;//上滑加载提前N个item开始加载更多数据(暂时有bug)
    private static int PAGE_SIZE = 20;
    private int page = 0;
    private String q;

    private boolean isLoadAll;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh_widget)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private GridLayoutManager mLayoutManager;
    private EBookListAdapter mListAdapter;
    private List<BookDetail> bookInfoResponses;
    private IEBookDetailPresenter eBookDetailPresenter;
    private IEBookPresenter eBookPresenter;

    private int spanCount = 1;
    private int type;//0:查书，1：查分类

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            isLoadAll = savedInstanceState.getBoolean("isLoadAll");
        }
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initEvents() {
        q = getIntent().getStringExtra("q");
        type = getIntent().getIntExtra("type", 0);
        setTitle(q);
        spanCount = (int) getResources().getInteger(R.integer.home_span_count);
        eBookDetailPresenter = new EBookDetailPresenterImpl(this);
        eBookPresenter = new EBookPresenterImpl(this);
        bookInfoResponses = new ArrayList<>();
        mSwipeRefreshLayout.setColorSchemeResources(R.color.recycler_color1, R.color.recycler_color2,
                R.color.recycler_color3, R.color.recycler_color4);

        mLayoutManager = new GridLayoutManager(ESearchResultActivity.this, spanCount);
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
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
        });
        mSwipeRefreshLayout.setOnRefreshListener(this);
        onRefresh();
    }

    @Override
    public void showMessage(String msg) {
        Snackbar.make(mToolbar, msg, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showProgress() {
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
            }
        });
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
            final List<BookDetail> books = ((BooksByCats) result).getBooks();
            if (books == null) {
                ToastUtils.showShort("搜索失败，请重试");
                return;
            }
            isLoadAll = books.size() < PAGE_SIZE;
            if (page == 0) {
                bookInfoResponses.clear();
            }
            bookInfoResponses.addAll(books);
            mListAdapter.notifyDataSetChanged();
            page++;
        }
    }


    @Override
    public void updateView(Object result) {
        if (result instanceof BooksByTag) {
            final List<BookDetail> books = ((BooksByTag) result).getBooks();
            if (books == null) {
                ToastUtils.showShort("搜索失败，请重试");
                return;
            }
            isLoadAll = books.size() < PAGE_SIZE;
            if (page == 0) {
                bookInfoResponses.clear();
            }
            bookInfoResponses.addAll(books);
            mListAdapter.notifyDataSetChanged();
            page++;
        }
    }

    @Override
    public void onRefresh() {
        page = 0;
        if (type == 0) {
            eBookPresenter.searchBooks(q, page * PAGE_SIZE, PAGE_SIZE);
        } else {
            eBookDetailPresenter.getBooksByTag(q, page * PAGE_SIZE, PAGE_SIZE);
        }
    }

    private void onLoadMore() {
        if (!isLoadAll) {
            if (!mSwipeRefreshLayout.isRefreshing()) {
                if (type == 0) {
                    eBookPresenter.searchBooks(q, page * PAGE_SIZE, PAGE_SIZE);
                } else {
                    eBookDetailPresenter.getBooksByTag(q, page * PAGE_SIZE, PAGE_SIZE);
                }
            }
        } else {
            showMessage(getResources().getString(R.string.no_more));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("isLoadAll", isLoadAll);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        eBookDetailPresenter.cancelLoading();
        super.onDestroy();
    }
}
