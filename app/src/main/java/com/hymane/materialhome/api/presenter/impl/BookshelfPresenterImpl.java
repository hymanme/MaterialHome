package com.hymane.materialhome.api.presenter.impl;

import com.hymane.materialhome.api.ApiCompleteListener;
import com.hymane.materialhome.api.model.IBookshelfModel;
import com.hymane.materialhome.api.model.impl.BookshelfModelImpl;
import com.hymane.materialhome.api.presenter.IBookshelfPresenter;
import com.hymane.materialhome.api.view.IBookListView;
import com.hymane.materialhome.bean.http.douban.BaseResponse;
import com.hymane.materialhome.bean.table.Bookshelf;

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
    public void addBookshelf(String title, String remark, String createAt) {
        mBookListView.showProgress();
        mBookshelfModel.addBookshelf(title, remark, createAt, this);
    }

    @Override
    public void updateBookshelf(Bookshelf bookshelf) {
        mBookListView.showProgress();
        mBookshelfModel.updateBookshelf(bookshelf, this);
    }

    @Override
    public void orderBookshelf(int id, long front, long behind) {
        mBookListView.showProgress();
        mBookshelfModel.orderBookshelf(id, front, behind, this);
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
        mBookListView.hideProgress();
        if (msg != null) {
            mBookListView.showMessage(msg.getCode() + "|" + msg.getMsg());
        }
    }
}
