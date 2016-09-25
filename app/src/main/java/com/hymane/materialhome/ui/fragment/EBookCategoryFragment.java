package com.hymane.materialhome.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.hymane.materialhome.R;
import com.hymane.materialhome.api.presenter.impl.EBookPresenterImpl;
import com.hymane.materialhome.api.view.IEBookListView;
import com.hymane.materialhome.bean.http.ebook.CategoryList;
import com.hymane.materialhome.ui.adapter.EBookCategoryAdapter;
import com.hymane.materialhome.ui.widget.RecyclerViewDecoration.SpacesCategoryDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/1/12
 * Description:
 */
public class EBookCategoryFragment extends BaseFragment implements IEBookListView, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.swipe_refresh_widget)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private EBookCategoryAdapter mCategoryAdapter;
    private EBookPresenterImpl mEBookPresenter;
    private List<CategoryList.CategoryBean> male;
    private List<CategoryList.CategoryBean> female;

    public static EBookCategoryFragment newInstance() {

        Bundle args = new Bundle();

        EBookCategoryFragment fragment = new EBookCategoryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.recycler_content, null, false);
        mEBookPresenter = new EBookPresenterImpl(this);
    }

    @Override
    protected void initEvents() {
        int spanCount = getResources().getInteger(R.integer.category_span_count);
        male = new ArrayList<>();
        female = new ArrayList<>();
        mSwipeRefreshLayout.setColorSchemeResources(R.color.recycler_color1, R.color.recycler_color2,
                R.color.recycler_color3, R.color.recycler_color4);
        //添加装饰器
        mRecyclerView.addItemDecoration(new SpacesCategoryDecoration(5));
        //设置布局管理器
        GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), spanCount);
        mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position == 0 || position == male.size() + 1) {
                    return spanCount;
                } else {
                    return 1;
                }
            }
        });
        mRecyclerView.setLayoutManager(mLayoutManager);
        //设置adapter
        mCategoryAdapter = new EBookCategoryAdapter(getActivity(), male, female);
        mRecyclerView.setAdapter(mCategoryAdapter);
        //设置Item增加、移除动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    protected void initData(boolean isSavedNull) {
        mEBookPresenter.getCategoryList();
    }

    @Override
    public void showMessage(String msg) {

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
        if (result instanceof CategoryList) {
            male.clear();
            female.clear();
            male.addAll(((CategoryList) result).getMale());
            female.addAll(((CategoryList) result).getFemale());
            mCategoryAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onRefresh() {
        mEBookPresenter.getCategoryList();
    }
}
