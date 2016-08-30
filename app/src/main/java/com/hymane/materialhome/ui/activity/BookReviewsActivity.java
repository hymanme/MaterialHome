package com.hymane.materialhome.ui.activity;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hymane.materialhome.R;
import com.hymane.materialhome.api.presenter.impl.BookDetailPresenterImpl;
import com.hymane.materialhome.api.view.IReviewsView;
import com.hymane.materialhome.bean.http.BookReviewResponse;
import com.hymane.materialhome.bean.http.BookReviewsListResponse;
import com.hymane.materialhome.ui.adapter.BookReviewsAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/2/26
 * Description:
 */
public class BookReviewsActivity extends BaseActivity implements IReviewsView, SwipeRefreshLayout.OnRefreshListener {
    //接口调用参数 tag：标签，q：搜索关键词，fields：过滤词，count：一次返回数据数，
    // page：当前已经加载的页数，PS:tag,q只存在其中一个，另一个置空
    private static final int PRO_LOADING_SIZE = 2;//上滑加载提前N个item开始加载更多数据(暂时有bug)
    private static final String COMMENT_FIELDS = "id,rating,author,title,updated,comments,summary,votes,useless";
    private static int count = 20;
    private static int page = 0;
    private static String bookId;
    private static String bookName;

    private boolean isLoadAll;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh_widget)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private LinearLayoutManager mLayoutManager;
    private BookReviewsAdapter mReviewsAdapter;
    private BookReviewsListResponse mReviewsListResponse;
    private BookDetailPresenterImpl bookDetailPresenter;

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
        bookDetailPresenter = new BookDetailPresenterImpl(this);
        mReviewsListResponse = new BookReviewsListResponse();
        mReviewsListResponse.setReviews(new ArrayList<BookReviewResponse>());
        mSwipeRefreshLayout.setColorSchemeResources(R.color.recycler_color1, R.color.recycler_color2,
                R.color.recycler_color3, R.color.recycler_color4);

        mLayoutManager = new LinearLayoutManager(BookReviewsActivity.this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //设置adapter
        mReviewsAdapter = new BookReviewsAdapter(mReviewsListResponse);
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
        final BookReviewsListResponse response = (BookReviewsListResponse) result;
        if (response.getStart() == 0) {
            mReviewsListResponse.getReviews().clear();
        }
        mReviewsListResponse.setTotal(response.getTotal());
        mReviewsListResponse.getReviews().addAll(response.getReviews());
        mReviewsAdapter.notifyDataSetChanged();


        if (response.getTotal() > page * count) {
            page++;
            isLoadAll = false;
        } else {
            isLoadAll = true;
        }
    }

    @Override
    public void onRefresh() {
        bookDetailPresenter.loadReviews(bookId, 0, count, COMMENT_FIELDS);
    }

    private void onLoadMore() {
        if (!isLoadAll) {
            bookDetailPresenter.loadReviews(bookId, page * count, count, COMMENT_FIELDS);
        } else {
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
