package com.hymane.materialhome.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hymane.materialhome.R;
import com.hymane.materialhome.api.presenter.impl.HotSearchPresenterImpl;
import com.hymane.materialhome.api.view.IHotSearchView;
import com.hymane.materialhome.bean.http.HotSearchResponse;
import com.hymane.materialhome.ui.activity.CaptureActivity;
import com.hymane.materialhome.ui.activity.MainActivity;
import com.hymane.materialhome.utils.PermissionUtils;
import com.hymane.materialhome.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/1/14 0014
 * Description:
 */
public class DiscoverFragment extends BaseFragment implements IHotSearchView {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh_widget)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private LinearLayoutManager mLayoutManager;
    private DiscoverAdapter mDiscoverAdapter;
    private HotSearchPresenterImpl mHotSearchPresenter;
    private List<HotSearchResponse> mHotSearchResponses;

    public static DiscoverFragment newInstance() {

        Bundle args = new Bundle();

        DiscoverFragment fragment = new DiscoverFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.recycler_content, null, false);
        mHotSearchPresenter = new HotSearchPresenterImpl(this);
    }

    @Override
    protected void initEvents() {
        mHotSearchResponses = new ArrayList<>();
        mSwipeRefreshLayout.setEnabled(false);
        //设置布局管理器
        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //设置adapter
        mDiscoverAdapter = new DiscoverAdapter();
        mRecyclerView.setAdapter(mDiscoverAdapter);

        //设置Item增加、移除动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
//        mHotSearchPresenter.loadHotSearch(0);
    }

    @Override
    protected void initData(boolean isSavedNull) {

    }

    @Override
    public void updateView(Object result) {
        mHotSearchResponses.clear();
        mHotSearchResponses.addAll((List<HotSearchResponse>) result);
        mDiscoverAdapter.notifyItemChanged(1);
    }

    @Override
    public void showMessage(String msg) {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    class DiscoverAdapter extends RecyclerView.Adapter {
        private static final int TYPE_SEARCH_HEADER = 0;
        private static final int TYPE_HOT_SEARCH = 1;

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view;
            if (viewType == TYPE_SEARCH_HEADER) {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_discover_search, parent, false);
                return new SearchHeaderHolder(view);
            } else {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hot_search, parent, false);
                return new HotSearchHolder(view);
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof HotSearchHolder) {
            }
        }

        @Override
        public int getItemViewType(int position) {
            if (position == 0) {
                return TYPE_SEARCH_HEADER;
            } else {
                return TYPE_HOT_SEARCH;
            }
        }

        @Override
        public int getItemCount() {
            return 2;
        }
    }

    class SearchHeaderHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tv_search;
        private ImageView iv_scan;

        public SearchHeaderHolder(View itemView) {
            super(itemView);
            tv_search = (TextView) itemView.findViewById(R.id.tv_search);
            iv_scan = (ImageView) itemView.findViewById(R.id.iv_scan);
            tv_search.setOnClickListener(this);
            iv_scan.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.tv_search) {
                ((MainActivity) getActivity()).showSearchView();
            } else if (v.getId() == R.id.iv_scan) {
                if (PermissionUtils.requestCameraPermission(getActivity())) {
                    UIUtils.startActivity(new Intent(UIUtils.getContext(), CaptureActivity.class));
                }
            }
        }
    }

    class HotSearchHolder extends RecyclerView.ViewHolder {

        public HotSearchHolder(View itemView) {
            super(itemView);
        }
    }
}
