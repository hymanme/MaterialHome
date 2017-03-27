package com.hymane.materialhome.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.hymane.materialhome.R;
import com.hymane.materialhome.api.presenter.IEBookDetailPresenter;
import com.hymane.materialhome.api.presenter.impl.EBookDetailPresenterImpl;
import com.hymane.materialhome.api.view.IEBookDetailView;
import com.hymane.materialhome.bean.http.ebook.BookZoneBean;
import com.hymane.materialhome.common.Constant;

import butterknife.ButterKnife;

/**
 * Author   :hymane
 * Email    :hymanme@163.com
 * Create at 2017-03-27
 * Description: 书单列表页
 */
public class EBookZoneActivity extends BaseActivity implements IEBookDetailView {
    private IEBookDetailPresenter mBookDetailPresenter;
    private String bookZoneId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.acivity_book_zone);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initData() {
        bookZoneId = getIntent().getStringExtra(Constant.BOOK_ZONE_ID);
        mBookDetailPresenter = new EBookDetailPresenterImpl(this);
    }

    @Override
    protected void initEvents() {
        mBookDetailPresenter.getBookZoneDetail(bookZoneId);
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
    public void updateView(Object result) {
        if (result instanceof BookZoneBean) {

        }
    }
}
