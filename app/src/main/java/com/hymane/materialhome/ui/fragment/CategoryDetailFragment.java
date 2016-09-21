package com.hymane.materialhome.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.hymane.materialhome.R;
import com.hymane.materialhome.api.presenter.impl.BookListPresenterImpl;
import com.hymane.materialhome.api.view.IBookListView;
import com.hymane.materialhome.bean.http.douban.BookInfoResponse;
import com.hymane.materialhome.bean.http.douban.BookListResponse;
import com.hymane.materialhome.ui.activity.BaseActivity;
import com.hymane.materialhome.ui.adapter.BookListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/1/9 0009
 * Description:
 */
public class CategoryDetailFragment extends BaseFragment implements IBookListView, SwipeRefreshLayout.OnRefreshListener {
    //接口调用参数 tag：标签，q：搜索关键词，fields：过滤词，count：一次返回数据数，
    // page：当前已经加载的页数，PS:tag,q只存在其中一个，另一个置空
    private static final String fields = "id,title,subtitle,origin_title,rating,author,translator,publisher,pubdate,summary,images,pages,price,binding,isbn13";
    private static int count = 10;
    private static int page = 0;
    private String tag;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh_widget)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private GridLayoutManager mLayoutManager;
    private BookListAdapter mListAdapter;
    private List<BookInfoResponse> bookInfoResponses;
    private BookListPresenterImpl bookListPresenter;
    private int spanCount = 1;

    public static CategoryDetailFragment newInstance(String child) {

        Bundle args = new Bundle();
        args.putString("child", child);
        CategoryDetailFragment fragment = new CategoryDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.recycler_content, null, false);
        tag = getArguments().getString("child");
    }

    @Override
    protected void initEvents() {
        spanCount = getResources().getInteger(R.integer.home_span_count);
        bookListPresenter = new BookListPresenterImpl(this);
        bookInfoResponses = new ArrayList<>();
        mSwipeRefreshLayout.setColorSchemeResources(R.color.recycler_color1, R.color.recycler_color2,
                R.color.recycler_color3, R.color.recycler_color4);

        mLayoutManager = new GridLayoutManager(getActivity(), spanCount);
        mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return mListAdapter.getItemColumnSpan(position);
            }
        });
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //设置adapter
        mListAdapter = new BookListAdapter(getActivity(), bookInfoResponses, spanCount);
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
    }

    @Override
    protected void initData(boolean isSavedNull) {
        onRefresh();
    }

    @Override
    public void showMessage(String msg) {
        Snackbar.make(((BaseActivity) getActivity()).getToolbar(), msg, Snackbar.LENGTH_LONG).show();
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
        bookInfoResponses.clear();
        bookInfoResponses.addAll(((BookListResponse) result).getBooks());
        mListAdapter.notifyDataSetChanged();
        page++;
    }

    @Override
    public void addData(Object result) {
        bookInfoResponses.addAll(((BookListResponse) result).getBooks());
        mListAdapter.notifyDataSetChanged();
        page++;
    }

    @Override
    public void onRefresh() {
        bookListPresenter.loadBooks(null, tag, 0, count, fields);
    }

    private void onLoadMore() {
        bookListPresenter.loadBooks(null, tag, page * count, count, fields);
    }
}
