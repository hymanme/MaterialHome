package com.hymane.materialhome.api.presenter.impl;

import com.hymane.materialhome.api.ApiCompleteListener;
import com.hymane.materialhome.api.model.IBookshelfModel;
import com.hymane.materialhome.api.model.impl.BookshelfModelImpl;
import com.hymane.materialhome.api.presenter.IBookshelfPresenter;
import com.hymane.materialhome.api.view.IBookListView;
import com.hymane.materialhome.bean.http.BaseResponse;

import java.util.List;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/8/29
 * Description:
 */
public class BookshelfPresenterImpl implements IBookshelfPresenter, ApiCompleteListener {
    private IBookListView mBookListView;
    private IBookshelfModel mBookshelfModel;

    public BookshelfPresenterImpl(IBookListView view) {
        mBookListView = view;
        mBookshelfModel = new BookshelfModelImpl();
    }

    @Override
    public void loadBookshelf() {
        mBookListView.showProgress();
        mBookshelfModel.loadBookshelf(this);
    }

    @Override
    public void addBookshelf(String title, String remark) {
        mBookListView.showProgress();
        mBookshelfModel.addBookshelf(title, remark, this);
    }

    @Override
    public void updateBookshelf(String id, String title, String remark) {
        mBookListView.showProgress();
        mBookshelfModel.updateBookshelf(id, title, remark, this);
    }

    @Override
    public void deleteBookshelf(String id) {
        mBookListView.showProgress();
        mBookshelfModel.deleteBookshelf(id, this);
    }

    @Override
    public void unSubscribe() {
        mBookshelfModel.unSubscribe();
    }

    @Override
    public void onComplected(Object result) {
        if (result instanceof List) {
            mBookListView.refreshData(result);
        } else if (result instanceof BaseResponse) {
            mBookListView.showMessage(((BaseResponse) result).getCode() + "|" + ((BaseResponse) result).getMsg());
        }
        mBookListView.hideProgress();
    }

    @Override
    public void onFailed(BaseResponse msg) {
        mBookListView.showMessage(msg.getCode() + "|" + msg.getMsg());
        mBookListView.hideProgress();
    }
}
