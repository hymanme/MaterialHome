package com.hymane.materialhome.api.presenter;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/2/23 0023
 * Description:
 */
public interface IEBookDetailPresenter {
    /***
     * 获取电子图书详情
     *
     * @param bookId bookid
     */
    void getBookDetail(String bookId);

    /**
     * 取消加载数据
     */
    void cancelLoading();
}
