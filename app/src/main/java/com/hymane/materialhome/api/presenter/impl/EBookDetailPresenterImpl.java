package com.hymane.materialhome.api.presenter.impl;

import com.hymane.materialhome.R;
import com.hymane.materialhome.api.ApiCompleteListener;
import com.hymane.materialhome.api.model.IEBookModel;
import com.hymane.materialhome.api.model.impl.EBookModelImpl;
import com.hymane.materialhome.api.presenter.IEBookDetailPresenter;
import com.hymane.materialhome.api.view.IEBookDetailView;
import com.hymane.materialhome.bean.http.douban.BaseResponse;
import com.hymane.materialhome.utils.common.NetworkUtils;
import com.hymane.materialhome.utils.common.UIUtils;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/2/23 0023
 * Description:
 */
public class EBookDetailPresenterImpl implements IEBookDetailPresenter, ApiCompleteListener {
    private IEBookModel mEBookDetailModel;
    private IEBookDetailView mBookDetailView;

    public EBookDetailPresenterImpl(IEBookDetailView mBookDetailView) {
        this.mBookDetailView = mBookDetailView;
        this.mEBookDetailModel = new EBookModelImpl();
    }

    @Override
    public void getBookDetail(String bookId) {
        if (!NetworkUtils.isConnected(UIUtils.getContext())) {
            mBookDetailView.showMessage(UIUtils.getContext().getString(R.string.poor_network));
            mBookDetailView.hideProgress();
            //没网络读取缓存
//            return;
        }
        mBookDetailView.showProgress();
        mEBookDetailModel.getBookDetail(bookId, this);
    }

    @Override
    public void getBookReviewList(String bookId, String sort, int start, int limit) {
        if (!NetworkUtils.isConnected(UIUtils.getContext())) {
            mBookDetailView.showMessage(UIUtils.getContext().getString(R.string.poor_network));
            mBookDetailView.hideProgress();
            //没网络读取缓存
//            return;
        }
        mBookDetailView.showProgress();
        mEBookDetailModel.getBookReviewList(bookId, sort, start, limit, this);
    }

    @Override
    public void getBooksByTag(String tags, int start, int limit) {
        if (!NetworkUtils.isConnected(UIUtils.getContext())) {
            mBookDetailView.showMessage(UIUtils.getContext().getString(R.string.poor_network));
            mBookDetailView.hideProgress();
            //没网络读取缓存
//            return;
        }
        mBookDetailView.showProgress();
        mEBookDetailModel.getBooksByTag(tags, start, limit, this);
    }

    @Override
    public void getHotReview(String bookId, int limit) {
        if (!NetworkUtils.isConnected(UIUtils.getContext())) {
            mBookDetailView.showMessage(UIUtils.getContext().getString(R.string.poor_network));
            mBookDetailView.hideProgress();
            //没网络读取缓存
//            return;
        }
        mBookDetailView.showProgress();
        mEBookDetailModel.getHotReview(bookId, limit, this);
    }

    @Override
    public void getRecommendBookList(String bookId, int limit) {
        if (!NetworkUtils.isConnected(UIUtils.getContext())) {
            mBookDetailView.showMessage(UIUtils.getContext().getString(R.string.poor_network));
            mBookDetailView.hideProgress();
            //没网络读取缓存
//            return;
        }
        mBookDetailView.showProgress();
        mEBookDetailModel.getRecommendBookList(bookId, limit, this);
    }

    @Override
    public void getBookZoneDetail(String bookListId) {
        if (!NetworkUtils.isConnected(UIUtils.getContext())) {
            mBookDetailView.showMessage(UIUtils.getContext().getString(R.string.poor_network));
            mBookDetailView.hideProgress();
            //没网络读取缓存
//            return;
        }
        mBookDetailView.showProgress();
        mEBookDetailModel.getBookZoneDetail(bookListId, this);
    }


    @Override
    public void cancelLoading() {
        mEBookDetailModel.cancelLoading();
    }

    @Override
    public void onComplected(Object result) {
        mBookDetailView.updateView(result);
        mBookDetailView.hideProgress();
    }

    @Override
    public void onFailed(BaseResponse msg) {
        mBookDetailView.hideProgress();
        if (msg == null) {
            return;
        }
        mBookDetailView.showMessage(msg.getMsg());
    }
}
