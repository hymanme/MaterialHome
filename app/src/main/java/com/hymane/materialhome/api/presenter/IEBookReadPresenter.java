package com.hymane.materialhome.api.presenter;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/9/29
 * Description:
 */

public interface IEBookReadPresenter {
    /***
     * 获取一本书所有章节
     *
     * @param bookId bookid
     * @return
     */
    void getBookChapters(String bookId);

    /***
     * 获取图书阅读内容
     *
     * @param url
     * @return
     */
    void getChapterContent(String url, String bookId, int chapter, boolean isCache);

    /**
     * 取消加载数据
     */
    void cancelLoading();
}
