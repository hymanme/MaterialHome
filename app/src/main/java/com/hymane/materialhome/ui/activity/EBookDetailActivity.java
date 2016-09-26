package com.hymane.materialhome.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Animatable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.hymane.materialhome.R;
import com.hymane.materialhome.api.presenter.impl.EBookDetailPresenterImpl;
import com.hymane.materialhome.api.view.IEBookDetailView;
import com.hymane.materialhome.bean.http.douban.BookReviewsListResponse;
import com.hymane.materialhome.bean.http.douban.BookSeriesListResponse;
import com.hymane.materialhome.bean.http.ebook.BookDetail;
import com.hymane.materialhome.ui.adapter.EBookDetailAdapter;
import com.hymane.materialhome.utils.Blur;
import com.hymane.materialhome.utils.UIUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/9/22
 * Description: 电子图书详情页
 */
public class EBookDetailActivity extends BaseActivity implements IEBookDetailView, SwipeRefreshLayout.OnRefreshListener {
    private static final int REVIEWS_COUNT = 5;
    private static final int SERIES_COUNT = 6;
    private static final int PAGE = 0;

    @BindView(R.id.collapsingToolbarLayout)
    CollapsingToolbarLayout mCollapsingLayout;
    @BindView(R.id.fab)
    FloatingActionButton mFab;
    @BindView(R.id.swipe_refresh_widget)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private EBookDetailAdapter mDetailAdapter;
    private ImageView iv_book_img;
    private ImageView iv_book_bg;
    private String bookId;

    //    private BookDetail bookDetail;
    private BookDetail bookInfo;
    private BookReviewsListResponse mReviewsListResponse;
    private BookSeriesListResponse mSeriesListResponse;

    private EBookDetailPresenterImpl eBookPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_ebook_detail);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        mToolbar.setNavigationIcon(AppCompatResources.getDrawable(this, R.drawable.ic_action_clear));
    }

    @Override
    protected void initEvents() {
        eBookPresenter = new EBookDetailPresenterImpl(this);
        mReviewsListResponse = new BookReviewsListResponse();
        mSeriesListResponse = new BookSeriesListResponse();
        bookId = getIntent().getStringExtra("bookId");
        bookInfo = getIntent().getParcelableExtra("BookDetail");
        mLayoutManager = new LinearLayoutManager(EBookDetailActivity.this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mDetailAdapter = new EBookDetailAdapter(bookInfo, mReviewsListResponse, mSeriesListResponse);
        mRecyclerView.setAdapter(mDetailAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mSwipeRefreshLayout.setColorSchemeResources(R.color.recycler_color1, R.color.recycler_color2,
                R.color.recycler_color3, R.color.recycler_color4);
        //头部图片
        iv_book_img = (ImageView) findViewById(R.id.iv_book_img);
        iv_book_bg = (ImageView) findViewById(R.id.iv_book_bg);
        mCollapsingLayout.setTitle(bookInfo.getTitle());

        Bitmap book_img = getIntent().getParcelableExtra("book_img");
        if (book_img != null) {
            iv_book_img.setImageBitmap(book_img);
            iv_book_bg.setImageBitmap(Blur.apply(book_img));
            iv_book_bg.setAlpha(0.9f);
        } else {
            Glide.with(this)
                    .load(bookInfo.getCover())
                    .asBitmap()
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            iv_book_img.setImageBitmap(resource);
                            iv_book_bg.setImageBitmap(Blur.apply(resource));
                            iv_book_bg.setAlpha(0.9f);
                        }
                    });
        }
        mFab.setOnClickListener(v -> Toast.makeText(EBookDetailActivity.this, "click", Toast.LENGTH_SHORT).show());
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    protected int getMenuID() {
        return R.menu.menu_book_detail;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(this, MainActivity.class));
                return true;
            case R.id.action_share:
                StringBuilder sb = new StringBuilder();
                sb.append(getString(R.string.your_friend));
                sb.append(getString(R.string.share_book_1));
                sb.append(bookInfo.getTitle());
                sb.append(getString(R.string.share_book_2));
                UIUtils.share(this, sb.toString(), null);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    if (item.getIcon() instanceof Animatable) {
                        ((Animatable) item.getIcon()).start();
                    }
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void showMessage(String msg) {
        Snackbar.make(mToolbar, msg, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showProgress() {
        mSwipeRefreshLayout.post(() -> mSwipeRefreshLayout.setRefreshing(true));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (mFab.getDrawable() instanceof Animatable) {
                ((Animatable) mFab.getDrawable()).start();
            }
        } else {
            //低于5.0，显示其他动画
//            showMessage(getString(R.string.loading));
        }
    }

    @Override
    public void hideProgress() {
        mSwipeRefreshLayout.post(() -> mSwipeRefreshLayout.setRefreshing(false));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (mFab.getDrawable() instanceof Animatable) {
                ((Animatable) mFab.getDrawable()).stop();
            }
        } else {
            //低于5.0，显示其他动画
        }
    }

    @Override
    public void updateView(Object result) {
        if (result instanceof BookDetail) {
            bookInfo = (BookDetail) result;
            mDetailAdapter.notifyItemChanged(0);
        }
//        if (result instanceof BookReviewsListResponse) {
//            final BookReviewsListResponse response = (BookReviewsListResponse) result;
//            mReviewsListResponse.setTotal(response.getTotal());
//            mReviewsListResponse.getReviews().addAll(response.getReviews());
//            mDetailAdapter.notifyDataSetChanged();
//            if (mBookInfoResponse.getSeries() != null) {
////                eBookPresenter.loadSeries(mBookInfoResponse.getSeries().getId(), PAGE * SERIES_COUNT, 6, SERIES_FIELDS);
//            }
//        } else if (result instanceof BookSeriesListResponse) {
//            final BookSeriesListResponse response = (BookSeriesListResponse) result;
//            mSeriesListResponse.setTotal(response.getTotal());
//            mSeriesListResponse.getBooks().addAll(response.getBooks());
//            mDetailAdapter.notifyDataSetChanged();
//        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
//        eBookPresenter.cancelLoading();
        if (mFab.getDrawable() instanceof Animatable) {
            ((Animatable) mFab.getDrawable()).stop();
        }
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onRefresh() {
        if (!TextUtils.isEmpty(bookId)) {
            eBookPresenter.getBookDetail(bookId);
        }
    }
}
