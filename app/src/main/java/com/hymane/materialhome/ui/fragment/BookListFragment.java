package com.hymane.materialhome.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hymane.materialhome.R;
import com.hymane.materialhome.api.presenter.impl.BookListPresenterImpl;
import com.hymane.materialhome.api.view.IBookListView;
import com.hymane.materialhome.bean.http.douban.BookInfoResponse;
import com.hymane.materialhome.bean.http.douban.BookListResponse;
import com.hymane.materialhome.ui.activity.MainActivity;
import com.hymane.materialhome.ui.adapter.BookListAdapter;
import com.hymane.materialhome.utils.common.DensityUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/7/13
 * Description:
 */

public class BookListFragment extends BaseFragment implements IBookListView, SwipeRefreshLayout.OnRefreshListener {
    //接口调用参数 tag：标签，q：搜索关键词，fields：过滤词，count：一次返回数据数，
    // page：当前已经加载的页数，PS:tag,q只存在其中一个，另一个置空
    private String tag = "hot";
    private static final String fields = "id,title,subtitle,origin_title,rating,author,translator,publisher,pubdate,summary,images,pages,price,binding,isbn13,series,alt";
    private static int count = 20;
    private static int page = 0;
    private static final int group = 2;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh_widget)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private GridLayoutManager mLayoutManager;
    private BookListAdapter mListAdapter;
    private List<BookInfoResponse> bookInfoResponses;
    private BookListPresenterImpl bookListPresenter;

    public static BookListFragment newInstance(String tag) {

        Bundle args = new Bundle();
        args.putString("tag", tag);
        BookListFragment fragment = new BookListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        if (bookInfoResponses == null || bookInfoResponses.size() == 0) {
            page = 0;
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.recycler_content, container, false);
        String result = getArguments().getString("tag");
        if (!TextUtils.isEmpty(result)) {
            tag = result;
        }
    }

    @Override
    protected void initEvents() {
        int spanCount = getResources().getInteger(R.integer.home_span_count);
        bookListPresenter = new BookListPresenterImpl(this);
        bookInfoResponses = new ArrayList<>();
        mSwipeRefreshLayout.setColorSchemeResources(R.color.recycler_color1, R.color.recycler_color2,
                R.color.recycler_color3, R.color.recycler_color4);

        //设置布局管理器
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
        mRecyclerView.addOnScrollListener(new RecyclerViewScrollDetector());
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void initData(boolean isSavedNull) {
        if (isSavedNull) {
            onRefresh();
        }
    }

    @Override
    public void showMessage(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgress() {
        mSwipeRefreshLayout.post(() -> mSwipeRefreshLayout.setRefreshing(true));
    }

    @Override
    public void hideProgress() {
        mSwipeRefreshLayout.post(() -> mSwipeRefreshLayout.setRefreshing(false));
    }

    @Override
    public void refreshData(Object result) {
        if (result instanceof BookListResponse) {
            bookInfoResponses.clear();
            bookInfoResponses.addAll(((BookListResponse) result).getBooks());
            mListAdapter.notifyDataSetChanged();
            page++;
        }
    }

    @Override
    public void addData(Object result) {
        final int start = bookInfoResponses.size();
        bookInfoResponses.addAll(((BookListResponse) result).getBooks());
        mListAdapter.notifyItemRangeInserted(start, bookInfoResponses.size());
        page++;
    }

    @Override
    public void onRefresh() {
        bookListPresenter.loadBooks(null, tag, 0, count, fields);
    }

    public void onLoadMore() {
        if (!mSwipeRefreshLayout.isRefreshing()) {
            bookListPresenter.loadBooks(null, tag, page * count, count, fields);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && bookInfoResponses != null && bookInfoResponses.isEmpty()) {
            onRefresh();
        }
    }

    class RecyclerViewScrollDetector extends RecyclerView.OnScrollListener {
        private int lastVisibleItem;
        private int mScrollThreshold = DensityUtils.dp2px(getActivity(), 1);

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
            boolean isSignificantDelta = Math.abs(dy) > mScrollThreshold;

            if (isSignificantDelta) {
                if (dy > 0) {
                    ((MainActivity) getActivity()).hideFloatingBar();
                } else {
                    ((MainActivity) getActivity()).showFloatingBar();
                }
            }

            lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
        }
    }
}
