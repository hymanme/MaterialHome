package com.hymane.materialhome.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.hymane.materialhome.R;

import butterknife.ButterKnife;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/9/29
 * Description:
 */

public class EBookListActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.recycler_content);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initEvents() {

    }
}
