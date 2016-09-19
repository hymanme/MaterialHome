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
import android.widget.Toast;

import com.github.hymanme.tagflowlayout.OnTagClickListener;
import com.github.hymanme.tagflowlayout.TagAdapter;
import com.github.hymanme.tagflowlayout.TagFlowLayout;
import com.github.hymanme.tagflowlayout.bean.TagBean;
import com.github.hymanme.tagflowlayout.tags.ColorfulTagView;
import com.github.hymanme.tagflowlayout.tags.DefaultTagView;
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
import butterknife.ButterKnife;

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
                //设置监听(单击和长按事件)
                ((HotSearchHolder) holder).tagFlowLayout.setTagListener(new OnTagClickListener() {
                    @Override
                    public void onClick(TagFlowLayout parent, View view, int position) {
                        Toast.makeText(UIUtils.getContext(), "click==" + ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onLongClick(TagFlowLayout parent, View view, int position) {
                        Toast.makeText(UIUtils.getContext(), "long click==" + ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
                    }
                });
                TagAdapter<TagBean> tagAdapter = new TagAdapter<TagBean>() {

                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        //定制tag的样式，包括背景颜色，点击时背景颜色，背景形状等
                        DefaultTagView textView = new ColorfulTagView(UIUtils.getContext());
                        textView.setText(((TagBean) getItem(position)).getName());
                        return textView;
                    }
                };
                //设置adapter
                ((HotSearchHolder) holder).tagFlowLayout.setTagAdapter(tagAdapter);


                //给adapter绑定数据
                List<TagBean> tagBeans = new ArrayList<>();
                for (int i = 0; i < 100; i++) {
                    tagBeans.add(new TagBean(i, "tags" + i));
                }
                tagAdapter.addAllTags(tagBeans);
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
        @BindView(R.id.tv_search)
        TextView tv_search;
        @BindView(R.id.iv_scan)
        ImageView iv_scan;

        public SearchHeaderHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
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
        @BindView(R.id.tag_flow_layout)
        TagFlowLayout tagFlowLayout;

        public HotSearchHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
