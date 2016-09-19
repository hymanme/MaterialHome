package com.hymane.materialhome.api.view;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/3/4 0004
 * Description:
 */
public interface IHotSearchView {

    void updateView(Object result);

    void showMessage(String msg);

    void showProgress();

    void hideProgress();
}
