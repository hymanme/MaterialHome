package com.hymane.materialhome.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hymane.materialhome.R;
import com.hymane.materialhome.api.presenter.impl.EBookPresenterImpl;
import com.hymane.materialhome.api.view.IEBookListView;
import com.hymane.materialhome.bean.event.GenderChangedEvent;
import com.hymane.materialhome.bean.http.ebook.BookDetail;
import com.hymane.materialhome.bean.http.ebook.Rankings;
import com.hymane.materialhome.common.Constant;
import com.hymane.materialhome.ui.activity.MainActivity;
import com.hymane.materialhome.ui.adapter.EBookListAdapter;
import com.hymane.materialhome.utils.EBookUtils;
import com.hymane.materialhome.utils.RxBus;
import com.hymane.materialhome.utils.common.DensityUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.functions.Action1;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/7/13
 * Description:
 */

public class EBookListFragment extends BaseFragment implements IEBookListView, SwipeRefreshLayout.OnRefreshListener {
    //categoryId 分类id
    private int type;
    private String categoryId = "";
    private String gender = Constant.Gender.MALE;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh_widget)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private GridLayoutManager mLayoutManager;

    private EBookListAdapter mListAdapter;
    private List<BookDetail> bookInfoResponses;
    private EBookPresenterImpl eBookPresenter;

    public static EBookListFragment newInstance(int type, String gender) {

        Bundle args = new Bundle();
        args.putString("gender", gender);
        args.putInt("type", type);
        EBookListFragment fragment = new EBookListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.recycler_content, container, false);
        gender = getArguments().getString("gender");
        type = getArguments().getInt("type");
        categoryId = EBookUtils.getRankId(type, gender);
    }

    @Override
    protected void initEvents() {
        int spanCount = getResources().getInteger(R.integer.home_span_count);
        eBookPresenter = new EBookPresenterImpl(this);
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
        mListAdapter = new EBookListAdapter(getActivity(), bookInfoResponses, spanCount);
        mRecyclerView.setAdapter(mListAdapter);

        //设置Item增加、移除动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addOnScrollListener(new RecyclerViewScrollDetector());
        mSwipeRefreshLayout.setOnRefreshListener(this);
        // rxSubscription是一个Subscription的全局变量，这段代码可以在onCreate/onStart等生命周期内
        RxBus.getDefault().toObservable(GenderChangedEvent.class)
                .subscribe(new Action1<GenderChangedEvent>() {
                               @Override
                               public void call(GenderChangedEvent genderEvent) {
                                   categoryId = EBookUtils.getRankId(type, genderEvent.getGender());
                                   onRefresh();
                               }
                           },
                        new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                // TODO: 处理异常
                            }
                        });
    }

    @Override
    protected void initData(boolean isSavedNull) {
//        if (isSavedNull) {
        onRefresh();
//        }
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
        if (result instanceof Rankings.RankingBean) {
            bookInfoResponses.clear();
            bookInfoResponses.addAll(((Rankings.RankingBean) result).getBooks());
            mListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onRefresh() {
        eBookPresenter.getRanking(categoryId);
    }

    public void onLoadMore() {
//        if (!mSwipeRefreshLayout.isRefreshing()) {
//            bookListPresenter.loadBooks(null, tag, page * count, count, fields);
//        }
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
