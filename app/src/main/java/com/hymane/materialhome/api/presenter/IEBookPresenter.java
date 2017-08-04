package com.hymane.materialhome.api.presenter;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/9/21
 * Description:
 */

public interface IEBookPresenter {
    /***
     * 获取最热排行
     * 获取留存排行
     * 获取完结排行
     * 获取潜力榜排行
     */
    void getRanking(String rankingId);

    /**
     * 获取电子书分类
     */
    void getCategoryList();

    /***
     * 获取分类图书列表
     *
     * @param gender 性别
     * @param type   排序类别
     * @param major  分类
     * @param minor  二级分类
     * @param limit  限制
     */
    void getCategoryListDetail(String gender, String type, String major, String minor, int start, int limit);

    /***
     * 获取热门标签
     */
    void getHotWord();

    /***
     * 搜索图书
     * @param query
     * @param start
     * @param limit
     */
    void searchBooks(String query, int start,  int limit);

    /**
     * 取消加载数据
     */
    void cancelLoading();
}
