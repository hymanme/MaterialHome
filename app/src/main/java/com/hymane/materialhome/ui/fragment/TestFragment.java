package com.hymane.materialhome.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hymane.materialhome.R;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/7/13
 * Description:
 */

public class TestFragment extends BaseFragment {
    private View mRootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.test_fragment, container, false);
        return mRootView;
    }
}
