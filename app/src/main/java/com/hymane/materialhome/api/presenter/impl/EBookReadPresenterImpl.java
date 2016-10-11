package com.hymane.materialhome.api.presenter.impl;

import com.hymane.materialhome.R;
import com.hymane.materialhome.api.ApiCompleteListener;
import com.hymane.materialhome.api.model.IEBookReadModel;
import com.hymane.materialhome.api.model.impl.EBookReadModelImpl;
import com.hymane.materialhome.api.presenter.IEBookReadPresenter;
import com.hymane.materialhome.api.view.IEBookReadView;
import com.hymane.materialhome.bean.http.douban.BaseResponse;
import com.hymane.materialhome.utils.common.NetworkUtils;
import com.hymane.materialhome.utils.common.UIUtils;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/9/29
 * Description:
 */

public class EBookReadPresenterImpl implements IEBookReadPresenter, ApiCompleteListener {
    private IEBookReadModel mEBookReadModel;
    private IEBookReadView mEBookReadView;

    public EBookReadPresenterImpl(IEBookReadView mBookListView) {
        this.mEBookReadView = mBookListView;
        this.mEBookReadModel = new EBookReadModelImpl();
    }

    @Override
    public void getBookChapters(String bookId) {
        if (!NetworkUtils.isConnected(UIUtils.getContext())) {
            mEBookReadView.showMessage(UIUtils.getContext().getString(R.string.poor_network));
            mEBookReadView.hideProgress();
            //没网络读取缓存
//            return;
        }
        mEBookReadView.showProgress();
        mEBookReadModel.getBookChapters(bookId, this);
    }

    @Override
    public void getChapterContent(String url, String bookId, int chapter, boolean isCache) {
        if (!NetworkUtils.isConnected(UIUtils.getContext())) {
            mEBookReadView.showMessage(UIUtils.getContext().getString(R.string.poor_network));
            mEBookReadView.hideProgress();
            //没网络读取缓存
//            return;
        }
        mEBookReadView.showProgress();
        mEBookReadModel.getChapterContent(url, bookId, chapter, isCache, this);
    }

    @Override
    public void cancelLoading() {

    }

    @Override
    public void onComplected(Object result) {
        mEBookReadView.refreshData(result);
        mEBookReadView.hideProgress();
    }

    @Override
    public void onFailed(BaseResponse msg) {
        mEBookReadView.hideProgress();
        if (msg == null) {
            return;
        }
        mEBookReadView.showMessage(msg.getMsg());
    }
}
