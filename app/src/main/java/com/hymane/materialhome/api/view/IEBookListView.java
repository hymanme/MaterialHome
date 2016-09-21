package com.hymane.materialhome.api.view;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/9/21
 * Description:
 */

public interface IEBookListView {
    void showMessage(String msg);

    void showProgress();

    void hideProgress();

    void refreshData(Object result);
}
