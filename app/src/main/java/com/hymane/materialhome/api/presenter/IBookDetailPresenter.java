package com.hymane.materialhome.api.presenter;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/2/23 0023
 * Description:
 */
public interface IBookDetailPresenter {
    /**
     * 获取图书评论
     *
     * @param bookId 图书id
     * @param start  起始index
     * @param count  请求条数
     * @param fields 过滤字段(多个字段用","分隔)
     */
    void loadReviews(String bookId, int start, int count, String fields);

    /**
     * 获取图书推荐丛书
     *
     * @param bookId 图书id
     * @param start  起始index
     * @param count  请求条数
     * @param fields 过滤字段(多个字段用","分隔)
     */
    void loadSeries(String bookId, int start, int count, String fields);

    /**
     * 取消加载数据
     */
    void cancelLoading();
}
