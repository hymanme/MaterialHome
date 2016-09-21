package com.hymane.materialhome.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.hymane.materialhome.R;
import com.hymane.materialhome.common.Constant;
import com.hymane.materialhome.ui.activity.MainActivity;
import com.hymane.materialhome.utils.EBookUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/9/20
 * Description:
 */

public class EBookFragment extends BaseFragment {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.fab)
    FloatingActionButton mFab;
    private List<BaseFragment> fragments;

    public static EBookFragment newInstance() {

        Bundle args = new Bundle();

        EBookFragment fragment = new EBookFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.home_fragment, container, false);
    }

    @Override
    protected void initEvents() {
        mToolbar.setTitle("EBook");
    }

    @Override
    protected void initData(boolean isSavedNull) {
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
        ((MainActivity) getActivity()).setToolbar(mToolbar);
        ((MainActivity) getActivity()).setFab(mFab);
    }

    private void init() {
        fragments = new ArrayList<>();
        fragments.add(EBookListFragment.newInstance(EBookUtils.getHotRankingId(Constant.Gender.MALE)));
        fragments.add(EBookListFragment.newInstance(EBookUtils.getRetainedRankingId(Constant.Gender.MALE)));
        fragments.add(EBookListFragment.newInstance(EBookUtils.getFinishedRankingId(Constant.Gender.MALE)));
        fragments.add(EBookListFragment.newInstance(EBookUtils.getPotentialRankingId(Constant.Gender.MALE)));
        fragments.add(EBookCategoryFragment.newInstance());
        fragments.add(DiscoverFragment.newInstance());

        mViewPager.setAdapter(new EBookFragment.MainAdapter(getChildFragmentManager(), fragments));
        mViewPager.setOffscreenPageLimit(5);
        mViewPager.setCurrentItem(2);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setSelectedTabIndicatorColor(getContext().getResources().getColor(R.color.white));
    }

    class MainAdapter extends FragmentStatePagerAdapter {
        private List<BaseFragment> mFragments;
        private final String[] titles;

        public MainAdapter(FragmentManager fm, List<BaseFragment> fragments) {
            super(fm);
            this.titles = getResources().getStringArray(R.array.ebook_tab_type);
            mFragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }
}
