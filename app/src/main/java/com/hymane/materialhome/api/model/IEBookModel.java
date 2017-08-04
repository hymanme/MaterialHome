package com.hymane.materialhome.api.model;

import com.hymane.materialhome.api.ApiCompleteListener;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/9/21
 * Description:
 */

public interface IEBookModel {
    /***
     * 获取最热排行
     * 获取留存排行
     * 获取完结排行
     * 获取潜力榜排行
     */
    void getRanking(String rankingId, ApiCompleteListener listener);

    /**
     * 获取电子书分类
     */
    void getCategoryList(ApiCompleteListener listener);

    /***
     * 获取电子图书详情
     *
     * @param bookId   bookid
     * @param listener 监听
     */
    void getBookDetail(String bookId, ApiCompleteListener listener);

    /**
     * 获取书籍详情书评列表
     *
     * @param bookId bookId
     * @param sort   updated(默认排序)、created(最新发布)、helpful(最有用的)、comment-count(最多评论)
     * @param start  0
     * @param limit  20
     * @return
     */
    void getBookReviewList(String bookId, String sort, int start, int limit, ApiCompleteListener listener);

    /**
     * 热门评论 图书详情页
     *
     * @param bookId
     * @param limit
     * @return
     */
    void getHotReview(String bookId, int limit, ApiCompleteListener listener);

    /***
     * 获取分类图书列表
     *
     * @param gender 性别
     * @param type   排序类别
     * @param major  分类
     * @param minor  二级分类
     * @param limit  限制
     */
    void getCategoryListDetail(String gender, String type, String major, String minor, int start, int limit, ApiCompleteListener listener);

    /**
     * 图书列表通过tag查找
     *
     * @param tags
     * @return
     */
    void getBooksByTag(String tags, int start, int limit, ApiCompleteListener listener);

    /**
     * 含有该书的书单列表-推荐书单
     *
     * @param bookId
     * @return
     */
    void getRecommendBookList(String bookId, int limit, ApiCompleteListener listener);

    /***
     * 获取书单详情
     * @param bookListId
     */
    void getBookZoneDetail(String bookListId, ApiCompleteListener listener);

    /***
     * 获取热门标签
     * @param listener
     */
    void getHotWord(ApiCompleteListener listener);

    /***
     * 搜索图书
     * @param query
     * @param start
     * @param limit
     */
    void searchBooks(String query, int start, int limit, ApiCompleteListener listener);

    /**
     * 取消加载数据
     */
    void cancelLoading();
}
