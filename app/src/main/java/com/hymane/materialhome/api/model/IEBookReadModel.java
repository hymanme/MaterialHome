package com.hymane.materialhome.api.model;

import com.hymane.materialhome.api.ApiCompleteListener;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/9/29
 * Description:
 */

public interface IEBookReadModel {
    /***
     * 获取一本书所有章节
     *
     * @param bookId bookid
     * @return
     */
    void getBookChapters(String bookId, ApiCompleteListener listener);

    /***
     * 获取图书阅读内容
     *
     * @param url
     * @return
     */
    void getChapterContent(String url, String bookId, int chapter, boolean isCache, ApiCompleteListener listener);
}
