package com.hymane.materialhome.api.presenter.impl;

import com.hymane.materialhome.api.ApiCompleteListener;
import com.hymane.materialhome.api.model.IHotSearchModel;
import com.hymane.materialhome.api.model.impl.HotSearchModelImpl;
import com.hymane.materialhome.api.presenter.IHotSearchPresenter;
import com.hymane.materialhome.api.view.IHotSearchView;
import com.hymane.materialhome.bean.http.douban.BaseResponse;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/9/19
 * Description:
 */
public class HotSearchPresenterImpl implements IHotSearchPresenter, ApiCompleteListener {
    private IHotSearchView mHotSearchView;
    private IHotSearchModel mHotSearchModel;

    public HotSearchPresenterImpl(IHotSearchView view) {
        mHotSearchView = view;
        mHotSearchModel = new HotSearchModelImpl();
    }

    @Override
    public void loadHotSearch(int page) {

    }

    @Override
    public void cancelLoading() {

    }

    @Override
    public void onComplected(Object result) {

    }

    @Override
    public void onFailed(BaseResponse msg) {

    }
}
