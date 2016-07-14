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
import android.view.View;
import android.view.ViewGroup;

import com.hymane.materialhome.ui.activity.MainActivity;
import com.hymane.materialhome.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/7/13
 * Description:
 */

public class ImportFragment extends BaseFragment {
    private View mRootView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.fab)
    FloatingActionButton mFab;
    private List<BaseFragment> fragments;
    public static ImportFragment mInstance;

    public static ImportFragment getInstance() {
        if (mInstance == null) {
            mInstance = new ImportFragment();
        }
        return mInstance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.import_fragment, container, false);
        ButterKnife.bind(this, mRootView);
        return mRootView;
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
        fragments.add(new TestFragment());
        fragments.add(new TestFragment());
        fragments.add(new TestFragment());
        fragments.add(new TestFragment());
        fragments.add(new TestFragment());
        fragments.add(new TestFragment());
        mViewPager.setAdapter(new MainAdapter(getChildFragmentManager(), fragments));
        mViewPager.setOffscreenPageLimit(5);
        mViewPager.setCurrentItem(2);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    static class MainAdapter extends FragmentStatePagerAdapter {
        private List<BaseFragment> mFragments;
        private final String[] titles = new String[]{"Tab", "Tab", "Tab", "Tab", "Tab", "Tab"};

        public MainAdapter(FragmentManager fm, List<BaseFragment> fragments) {
            super(fm);
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
