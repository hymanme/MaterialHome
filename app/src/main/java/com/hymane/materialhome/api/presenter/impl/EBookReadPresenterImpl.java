package com.hymane.materialhome.api.presenter.impl;

import com.hymane.materialhome.R;
import com.hymane.materialhome.api.ApiCompleteListener;
import com.hymane.materialhome.api.model.IEBookReadModel;
import com.hymane.materialhome.api.model.impl.EBookReadModelImpl;
import com.hymane.materialhome.api.presenter.IEBookReadPresenter;
import com.hymane.materialhome.api.view.IEBookListView;
import com.hymane.materialhome.bean.http.douban.BaseResponse;
import com.hymane.materialhome.utils.NetworkUtils;
import com.hymane.materialhome.utils.UIUtils;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/9/29
 * Description:
 */

public class EBookReadPresenterImpl implements IEBookReadPresenter, ApiCompleteListener {
    private IEBookReadModel mEBookReadModel;
    private IEBookListView mEBookListView;

    public EBookReadPresenterImpl(IEBookListView mBookListView) {
        this.mEBookListView = mBookListView;
        this.mEBookReadModel = new EBookReadModelImpl();
    }

    @Override
    public void getBookChapters(String bookId) {
        if (!NetworkUtils.isConnected(UIUtils.getContext())) {
            mEBookListView.showMessage(UIUtils.getContext().getString(R.string.poor_network));
            mEBookListView.hideProgress();
            //没网络读取缓存
//            return;
        }
        mEBookListView.showProgress();
        mEBookReadModel.getBookChapters(bookId, this);
    }

    @Override
    public void getChapterContent(String url) {
        if (!NetworkUtils.isConnected(UIUtils.getContext())) {
            mEBookListView.showMessage(UIUtils.getContext().getString(R.string.poor_network));
            mEBookListView.hideProgress();
            //没网络读取缓存
//            return;
        }
        mEBookListView.showProgress();
        mEBookReadModel.getChapterContent(url, this);
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
