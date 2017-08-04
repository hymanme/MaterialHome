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
import com.github.hymanme.tagflowlayout.tags.ColorfulTagView;
import com.github.hymanme.tagflowlayout.tags.DefaultTagView;
import com.hymane.materialhome.R;
import com.hymane.materialhome.api.presenter.IEBookPresenter;
import com.hymane.materialhome.api.presenter.impl.EBookPresenterImpl;
import com.hymane.materialhome.api.presenter.impl.HotSearchPresenterImpl;
import com.hymane.materialhome.api.view.IEBookListView;
import com.hymane.materialhome.api.view.IHotSearchView;
import com.hymane.materialhome.bean.http.douban.HotSearchResponse;
import com.hymane.materialhome.bean.http.ebook.HotWords;
import com.hymane.materialhome.ui.activity.CaptureActivity;
import com.hymane.materialhome.ui.activity.ESearchResultActivity;
import com.hymane.materialhome.ui.activity.MainActivity;
import com.hymane.materialhome.ui.activity.SearchResultActivity;
import com.hymane.materialhome.utils.common.PermissionUtils;
import com.hymane.materialhome.utils.common.UIUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/9/14
 * Description:
 */
public class DiscoverFragment extends BaseFragment implements IEBookListView {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh_widget)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private LinearLayoutManager mLayoutManager;
    private DiscoverAdapter mDiscoverAdapter;
    private IEBookPresenter mHotSearchPresenter;
    private List<String> mTags;
    private int type;//0:douban,1:ebook

    public static DiscoverFragment newInstance(int type) {

        Bundle args = new Bundle();
        args.putInt("type", type);

        DiscoverFragment fragment = new DiscoverFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.recycler_content, null, false);
        type = getArguments().getInt("type");
        mHotSearchPresenter = new EBookPresenterImpl(this);
    }

    @Override
    protected void initEvents() {
        mSwipeRefreshLayout.setEnabled(false);
        //设置布局管理器
        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //设置adapter
        mDiscoverAdapter = new DiscoverAdapter(type);
        mRecyclerView.setAdapter(mDiscoverAdapter);

        //设置Item增加、移除动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    protected void initData(boolean isSavedNull) {
        mTags = new ArrayList<>();
        if (type == 0) {
            mTags.add("小说");
            mTags.add("日本");
            mTags.add("历史");
            mTags.add("外国文学");
            mTags.add("文学");
            mTags.add("漫画");
            mTags.add("中国");
            mTags.add("心理学");
            mTags.add("随笔");
            mTags.add("哲学");
            mTags.add("中国文学");
            mTags.add("绘本");
            mTags.add("推理");
            mTags.add("美国");
            mTags.add("爱情");
            mTags.add("经典");
            mTags.add("日本文学");
            mTags.add("传记");
            mTags.add("文化");
            mTags.add("散文");
            mTags.add("青春");
            mTags.add("社会学");
            mTags.add("旅行");
            mTags.add("英国");
            mTags.add("科普");
            mTags.add("东野圭吾");
            mTags.add("科幻");
            mTags.add("言情");
            mTags.add("生活");
            mTags.add("艺术");
            mTags.add("成长");
            mTags.add("村上春树");
            mTags.add("悬疑");
            mTags.add("经济学");
            mTags.add("台湾");
            mTags.add("设计");
            mTags.add("管理");
            mTags.add("励志");
            mTags.add("法国");
            mTags.add("思维");
            mTags.add("社会");
            mTags.add("心理");
            mTags.add("政治");
            mTags.add("武侠");
            mTags.add("经济");
            mTags.add("奇幻");
            mTags.add("诗歌");
            mTags.add("童话");
            mTags.add("摄影");
            mTags.add("日本漫画");
            mTags.add("韩寒");
            mTags.add("商业");
            mTags.add("建筑");
            mTags.add("女性");
            mTags.add("金融");
            mTags.add("耽美");
            mTags.add("亦舒");
            mTags.add("人生");
            mTags.add("宗教");
            mTags.add("电影");
            mTags.add("互联网");
            mTags.add("英国文学");
            mTags.add("推理小说");
            mTags.add("王小波");
            mTags.add("计算机");
            mTags.add("杂文");
            mTags.add("古典文学");
            mTags.add("儿童文学");
            mTags.add("美国文学");
            mTags.add("三毛");
            mTags.add("数学");
            mTags.add("投资");
            mTags.add("网络小说");
            mTags.add("政治学");
            mTags.add("名著");
            mTags.add("职场");
            mTags.add("余华");
            mTags.add("张爱玲");
            mTags.add("好书，值得一读");
            mTags.add("香港");
            mTags.add("美食");
            mTags.add("安妮宝贝");
            mTags.add("教育");
            mTags.add("我想读这本书");
            mTags.add("个人管理");
            mTags.add("人类学");
            mTags.add("郭敬明");
            mTags.add("回忆录");
            mTags.add("工具书");
            mTags.add("穿越");
            mTags.add("德国");
            mTags.add("東野圭吾");
            mTags.add("纪实");
            mTags.add("金庸");
            mTags.add("中国历史");
            mTags.add("人性");
            mTags.add("游记");
            mTags.add("编程");
            mTags.add("轻小说");
            mTags.add("思想");
            mTags.add("营销");
            mTags.add("阿加莎·克里斯蒂");
            mTags.add("教材");
            mTags.add("英语");
            mTags.add("国学");
            mTags.add("时间管理");
            mTags.add("散文随笔");
            mTags.add("心灵");
            mTags.add("当代文学");
            mTags.add("日系推理");
            mTags.add("灵修");
            mTags.add("法国文学");
            mTags.add("几米");
            mTags.add("治愈");
            mTags.add("政治哲学");
            mTags.add("BL");
            mTags.add("科学");
            mTags.add("科幻小说");
            mTags.add("音乐");
            mTags.add("人文");
        } else {
            mHotSearchPresenter.getHotWord();
        }
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

    @Override
    public void refreshData(Object result) {
        if (result instanceof HotWords) {
            final String[] hotWords = ((HotWords) result).getHotWords();
            mTags.clear();
            mTags.addAll(Arrays.asList(hotWords));
            mDiscoverAdapter.notifyItemChanged(1);
        }
    }

    class DiscoverAdapter extends RecyclerView.Adapter {
        private static final int TYPE_SEARCH_HEADER = 0;
        private static final int TYPE_HOT_SEARCH = 1;
        private int type;//0:douban,1:ebook

        public DiscoverAdapter(int type) {
            this.type = type;
        }

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
                        Intent intent;
                        if (type == 0) {
                            intent = new Intent(UIUtils.getContext(), SearchResultActivity.class);
                        } else {
                            intent = new Intent(UIUtils.getContext(), ESearchResultActivity.class);
                            intent.putExtra("type", 0);
                        }
                        intent.putExtra("q", mTags.get(position));
                        UIUtils.startActivity(intent);
                    }

                    @Override
                    public void onLongClick(TagFlowLayout parent, View view, int position) {
                    }
                });
                TagAdapter<String> tagAdapter = new TagAdapter<String>() {

                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        //定制tag的样式，包括背景颜色，点击时背景颜色，背景形状等
                        DefaultTagView textView = new ColorfulTagView(UIUtils.getContext());
                        textView.setText((String) getItem(position));
                        return textView;
                    }
                };
                //设置adapter
                ((HotSearchHolder) holder).tagFlowLayout.setTagAdapter(tagAdapter);

                //给adapter绑定数据
                tagAdapter.addAllTags(mTags);
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
