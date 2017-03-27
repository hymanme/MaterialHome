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
     * 获取书籍详情书评列表
     *
     * @param bookId bookId
     * @param sort   updated(默认排序)、created(最新发布)、helpful(最有用的)、comment-count(最多评论)
     * @param start  0
     * @param limit  20
     * @return
     */
    void getBookReviewList(String bookId, String sort, int start, int limit);

    /**
     * 图书列表通过tag查找
     *
     * @param tags
     * @return
     */
    void getBooksByTag(String tags, int start, int limit);

    /**
     * 热门评论 图书详情页
     *
     * @param book
     * @param limit
     * @return
     */
    void getHotReview(String bookId, int limit);

    /**
     * 含有该书的书单列表-推荐书单
     *
     * @param bookId
     * @return
     */
    void getRecommendBookList(String bookId, int limit);

    /***
     * 获取书单详情
     * @param bookListId
     */
    void getBookZoneDetail(String bookListId);

    /**
     * 取消加载数据
     */
    void cancelLoading();
}
