package com.hymane.materialhome.ui.activity;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hymane.materialhome.R;
import com.hymane.materialhome.api.presenter.impl.EBookDetailPresenterImpl;
import com.hymane.materialhome.api.view.IEBookDetailView;
import com.hymane.materialhome.bean.http.ebook.HotReview;
import com.hymane.materialhome.common.Constant;
import com.hymane.materialhome.ui.adapter.EBookReviewsAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/2/26
 * Description:
 */
public class EBookReviewsActivity extends BaseActivity implements IEBookDetailView, SwipeRefreshLayout.OnRefreshListener {
    private String sort = Constant.EBOOK_SORT_UPDATED;
    private static int count = 20;
    private int page = 0;
    private static String bookId;
    private static String bookName;

    private boolean isLoadAll;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh_widget)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private LinearLayoutManager mLayoutManager;
    private EBookReviewsAdapter mReviewsAdapter;
    private HotReview mHotReview;
    private EBookDetailPresenterImpl bookDetailPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            isLoadAll = savedInstanceState.getBoolean("isLoadAll");
        }
        setContentView(R.layout.activity_reviews);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initEvents() {
        bookDetailPresenter = new EBookDetailPresenterImpl(this);
        mHotReview = new HotReview();
        mSwipeRefreshLayout.setColorSchemeResources(R.color.recycler_color1, R.color.recycler_color2,
                R.color.recycler_color3, R.color.recycler_color4);

        mLayoutManager = new LinearLayoutManager(EBookReviewsActivity.this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //设置adapter
        mReviewsAdapter = new EBookReviewsAdapter(mHotReview);
        mRecyclerView.setAdapter(mReviewsAdapter);

        //设置Item增加、移除动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        bookName = getIntent().getStringExtra("bookName");
        bookId = getIntent().getStringExtra("bookId");
        setTitle(bookName + getString(R.string.comment_of_book));
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private int lastVisibleItem;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == mReviewsAdapter.getItemCount()) {
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
    public void updateView(Object result) {
        final HotReview response = (HotReview) result;
        if (page == 0) {
            mHotReview.getReviews().clear();
        }
        mHotReview.getReviews().addAll(response.getReviews());
        mReviewsAdapter.notifyDataSetChanged();

        if (response.getReviews().size() < count) {
            isLoadAll = true;
        } else {
            page++;
            isLoadAll = false;
        }
    }

    @Override
    public void onRefresh() {
        page = 0;
        bookDetailPresenter.getBookReviewList(bookId, sort, page * count, count);
    }

    private void onLoadMore() {
        if (!isLoadAll) {
            bookDetailPresenter.getBookReviewList(bookId, sort, page * count, count);
        } else {
            showMessage(getString(R.string.no_more));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("isLoadAll", isLoadAll);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        bookDetailPresenter.cancelLoading();
        super.onDestroy();
    }
}
