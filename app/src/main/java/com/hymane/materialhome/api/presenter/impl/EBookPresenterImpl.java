package com.hymane.materialhome.api.presenter.impl;

import com.hymane.materialhome.R;
import com.hymane.materialhome.api.ApiCompleteListener;
import com.hymane.materialhome.api.model.IEBookModel;
import com.hymane.materialhome.api.model.impl.EBookModelImpl;
import com.hymane.materialhome.api.presenter.IEBookPresenter;
import com.hymane.materialhome.api.view.IEBookListView;
import com.hymane.materialhome.bean.http.douban.BaseResponse;
import com.hymane.materialhome.utils.common.NetworkUtils;
import com.hymane.materialhome.utils.common.UIUtils;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/9/21
 * Description:
 */

public class EBookPresenterImpl implements IEBookPresenter, ApiCompleteListener {
    private IEBookModel mEBookModel;
    private IEBookListView mEBookListView;

    public EBookPresenterImpl(IEBookListView mBookListView) {
        this.mEBookListView = mBookListView;
        this.mEBookModel = new EBookModelImpl();
    }

    @Override
    public void getRanking(String rankingId) {
        if (!NetworkUtils.isConnected(UIUtils.getContext())) {
            mEBookListView.showMessage(UIUtils.getContext().getString(R.string.poor_network));
            mEBookListView.hideProgress();
            //没网络读取缓存
//            return;
        }
        mEBookListView.showProgress();
        mEBookModel.getRanking(rankingId, this);
    }

    @Override
    public void getCategoryList() {
        if (!NetworkUtils.isConnected(UIUtils.getContext())) {
            mEBookListView.showMessage(UIUtils.getContext().getString(R.string.poor_network));
            mEBookListView.hideProgress();
            //没网络读取缓存
//            return;
        }
        mEBookListView.showProgress();
        mEBookModel.getCategoryList(this);
    }

    @Override
    public void getCategoryListDetail(String gender, String type, String major, String minor, int start, int limit) {
        if (!NetworkUtils.isConnected(UIUtils.getContext())) {
            mEBookListView.showMessage(UIUtils.getContext().getString(R.string.poor_network));
            mEBookListView.hideProgress();
            //没网络读取缓存
//            return;
        }
        mEBookListView.showProgress();
        mEBookModel.getCategoryListDetail(gender, type, major, minor, start, limit, this);
    }

    @Override
    public void getHotWord() {
        if (!NetworkUtils.isConnected(UIUtils.getContext())) {
            mEBookListView.showMessage(UIUtils.getContext().getString(R.string.poor_network));
            mEBookListView.hideProgress();
            //没网络读取缓存
//            return;
        }
        mEBookListView.showProgress();
        mEBookModel.getHotWord(this);
    }

    @Override
    public void searchBooks(String query, int start, int limit) {
        if (!NetworkUtils.isConnected(UIUtils.getContext())) {
            mEBookListView.showMessage(UIUtils.getContext().getString(R.string.poor_network));
            mEBookListView.hideProgress();
            //没网络读取缓存
//            return;
        }
        mEBookListView.showProgress();
        mEBookModel.searchBooks(query, start, limit, this);
    }

    @Override
    public void cancelLoading() {

    }

    @Override
    public void onComplected(Object result) {
        mEBookListView.refreshData(result);
        mEBookListView.hideProgress();
    }

    @Override
    public void onFailed(BaseResponse msg) {
        mEBookListView.hideProgress();
        if (msg == null) {
            return;
        }
        mEBookListView.showMessage(msg.getMsg());
    }
}
