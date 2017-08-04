package com.hymane.materialhome.api.common.service;

import com.hymane.materialhome.bean.http.ebook.BookChapter;
import com.hymane.materialhome.bean.http.ebook.BookDetail;
import com.hymane.materialhome.bean.http.ebook.BookReview;
import com.hymane.materialhome.bean.http.ebook.BookZoneBean;
import com.hymane.materialhome.bean.http.ebook.BooksByCats;
import com.hymane.materialhome.bean.http.ebook.BooksByTag;
import com.hymane.materialhome.bean.http.ebook.CategoryList;
import com.hymane.materialhome.bean.http.ebook.ChapterRead;
import com.hymane.materialhome.bean.http.ebook.HotReview;
import com.hymane.materialhome.bean.http.ebook.HotWords;
import com.hymane.materialhome.bean.http.ebook.LikedBookList;
import com.hymane.materialhome.bean.http.ebook.Rankings;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/9/20
 * Description:
 */

public interface IEBooksService {

    /**
     * 获取单一排行榜
     * 周榜：rankingId->_id
     * 月榜：rankingId->monthRank
     * 总榜：rankingId->totalRank
     *
     * @return
     */
    @GET("/ranking/{rankingId}")
    Observable<Rankings> getRanking(@Path("rankingId") String rankingId);

    /**
     * 获取一级分类
     *
     * @return
     */
    @GET("/cats/lv2/statistics")
    Observable<CategoryList> getCategoryList();

    /**
     * 图书详情
     *
     * @param bookId
     * @return
     */
    @GET("/book/{bookId}")
    Observable<Response<BookDetail>> getBookDetail(@Path("bookId") String bookId);

    /**
     * 按分类获取书籍列表，分类详细页面
     *
     * @param gender male、female
     * @param type   hot(热门)、new(新书)、reputation(好评)、over(完结)
     * @param major  玄幻
     * @param minor  东方玄幻、异界大陆、异界争霸、远古神话
     * @param limit  50
     * @return
     */
    @GET("/book/by-categories")
    Observable<BooksByCats> getBooksByCats(@Query("gender") String gender, @Query("type") String type, @Query("major") String major, @Query("minor") String minor, @Query("start") int start, @Query("limit") int limit);

    /**
     * 热门评论 图书详情页
     *
     * @param book
     * @param limit
     * @return
     */
    @GET("/post/review/best-by-book")
    Observable<HotReview> getHotReview(@Query("book") String book, @Query("limit") int limit);

    /**
     * 获取书籍详情书评列表 查看更多评论
     *
     * @param book  bookId
     * @param sort  updated(默认排序)、created(最新发布)、helpful(最有用的)、comment-count(最多评论)
     * @param start 0
     * @param limit 20（默认，改不了）
     * @return
     */
    @GET("/post/review/by-book")
    Observable<HotReview> getBookReviewList(@Query("book") String book, @Query("sort") String sort, @Query("start") int start, @Query("limit") int limit);

    /**
     * 获取书评区帖子详情 书评详情页
     *
     * @param bookReviewId->_id
     * @return
     */
    @GET("/post/review/{bookReviewId}")
    Observable<BookReview> getBookReviewDetail(@Path("bookReviewId") String bookReviewId);

    /**
     * 图书列表通过tag查找
     *
     * @param tags
     * @return
     */
    @GET("/book/by-tags")
    Observable<BooksByTag> getBooksByTag(@Query("tags") String tags, @Query("start") int start, @Query("limit") int limit);

    /**
     * 含有该书的书单列表-推荐书单
     *
     * @param bookId
     * @return
     */
    @GET("/book-list/{bookId}/recommend")
    Observable<LikedBookList> getRecommendBookList(@Path("bookId") String bookId, @Query("limit") int limit);

    /***
     * 获取一本书所有章节
     *
     * @param bookId bookid
     * @return
     */
    @GET("/mix-toc/{bookId}")
    Observable<BookChapter> getBookChapters(@Path("bookId") String bookId);

    /***
     * 获取图书阅读内容
     *
     * @param url
     * @return
     */
    @GET("http://chapter2.zhuishushenqi.com/chapter/{url}")
    Observable<ChapterRead> getChapterContent(@Path("url") String url);

    /***
     * 图书推荐
     *
     * @param gender male or female,按性别推荐
     * @return
     */
//    @GET("/book/recommend")
//    Observable<Recommend> getRecomend(@Query("gender") String gender);

//    @GET("/atoc")
//    Observable<List<BookSource>> getBookSource(@Query("view") String view, @Query("book") String book);

//    @GET("/mix-atoc/{bookId}")
//    Observable<BookToc> getBookToc(@Path("bookId") String bookId, @Query("view") String view);
//
//    @GET("/btoc/{bookId}")
//    Observable<BookToc> getBookBToc(@Path("bookId") String bookId, @Query("view") String view);

    /***
     * 获取某章节内容
     *
     * @param url 章节url
     * @return
     */
//    @GET("http://chapter2.zhuishushenqi.com/chapter/{url}")
//    Observable<ChapterRead> getChapterRead(@Path("url") String url);

    /***
     * 获取一本书的post count
     *
     * @param bookId bookid
     * @return
     */
//    @GET("/post/post-count-by-book")
//    Observable<PostCount> postCountByBook(@Query("bookId") String bookId);

    /***
     * 获取一本书的post total count
     *
     * @param books bookid
     * @return
     */
//    @GET("/post/total-count")
//    Observable<PostCount> postTotalCount(@Query("books") String bookId);

    /***
     * 获取热门搜索词
     *
     * @return
     */
    @GET("/book/hot-word")
    Observable<HotWords> getHotWord();

    /**
     * 关键字自动补全
     *
     * @param query
     * @return
     */
//    @GET("/book/auto-complete")
//    Observable<AutoComplete> autoComplete(@Query("query") String query);

    /**
     * 书籍查询
     *
     * @param query
     * @return
     */
    @GET("/book/fuzzy-search")
    Observable<BooksByCats> searchBooks(@Query("query") String query, @Query("start") int start, @Query("limit") int limit);

    /**
     * 通过作者查询书名
     *
     * @param author
     * @return
     */
//    @GET("/book/accurate-search")
//    Observable<BooksByTag> searchBooksByAuthor(@Query("author") String author);

//
//    /**
//     * 获取所有排行榜
//     *
//     * @return
//     */
//    @GET("/ranking/gender")
//    Observable<RankingList> getRanking();

//    /**
//     * 获取主题书单列表
//     * 本周最热：duration=last-seven-days&sort=collectorCount
//     * 最新发布：duration=all&sort=created
//     * 最多收藏：duration=all&sort=collectorCount
//     *
//     * @param tag    都市、古代、架空、重生、玄幻、网游
//     * @param gender male、female
//     * @param limit  20
//     * @return
//     */
//    @GET("/book-list")
//    Observable<BookLists> getBookLists(@Query("duration") String duration, @Query("sort") String sort, @Query("start") String start, @Query("limit") String limit, @Query("tag") String tag, @Query("gender") String gender);
//
//    /**
//     * 获取主题书单标签列表
//     *
//     * @return
//     */
//    @GET("/book-list/tagType")
//    Observable<BookListTags> getBookListTags();
//

    /**
     * 获取书单详情
     *
     * @return
     */
    @GET("/book-list/{bookListId}")
    Observable<BookZoneBean> getBookZoneDetail(@Path("bookListId") String bookListId);

//    /**
//     * 获取综合讨论区帖子列表
//     * 全部、默认排序  http://api.zhuishushenqi.com/post/by-block?block=ramble&duration=all&sort=updated&type=all&start=0&limit=20&distillate=
//     * 精品、默认排序  http://api.zhuishushenqi.com/post/by-block?block=ramble&duration=all&sort=updated&type=all&start=0&limit=20&distillate=true
//     *
//     * @param block      ramble
//     * @param duration   all
//     * @param sort       updated(默认排序)、created(最新发布)、comment-count(最多评论)
//     * @param type       all
//     * @param start      0
//     * @param limit      20
//     * @param distillate true(精品)
//     * @return
//     */
//    @GET("/post/by-block")
//    Observable<DiscussionList> getBookDisscussionList(@Query("block") String block, @Query("duration") String duration, @Query("sort") String sort, @Query("type") String type, @Query("start") String start, @Query("limit") String limit, @Query("distillate") String distillate);
//
//    /**
//     * 获取综合讨论区帖子详情
//     *
//     * @param disscussionId->_id
//     * @return
//     */
//    @GET("/post/{disscussionId}")
//    Observable<Disscussion> getBookDisscussionDetail(@Path("disscussionId") String disscussionId);
//
//    /**
//     * 获取神评论列表(综合讨论区、书评区、书荒区皆为同一接口)
//     *
//     * @param disscussionId->_id
//     * @return
//     */
//    @GET("/post/{disscussionId}/comment/best")
//    Observable<CommentList> getBestComments(@Path("disscussionId") String disscussionId);
//
//    /**
//     * 获取综合讨论区帖子详情内的评论列表
//     *
//     * @param disscussionId->_id
//     * @param start              0
//     * @param limit              30
//     * @return
//     */
//    @GET("/post/{disscussionId}/comment")
//    Observable<CommentList> getBookDisscussionComments(@Path("disscussionId") String disscussionId, @Query("start") String start, @Query("limit") String limit);
//
//    /**
//     * 获取书评区帖子列表
//     * 全部、全部类型、默认排序  http://api.zhuishushenqi.com/post/review?duration=all&sort=updated&type=all&start=0&limit=20&distillate=
//     * 精品、玄幻奇幻、默认排序  http://api.zhuishushenqi.com/post/review?duration=all&sort=updated&type=xhqh&start=0&limit=20&distillate=true
//     *
//     * @param duration   all
//     * @param sort       updated(默认排序)、created(最新发布)、helpful(最有用的)、comment-count(最多评论)
//     * @param type       all(全部类型)、xhqh(玄幻奇幻)、dsyn(都市异能)
//     * @param start      0
//     * @param limit      20
//     * @param distillate true(精品) 、空字符（全部）
//     * @return
//     */
//    @GET("/post/review")
//    Observable<BookReviewList> getBookReviewList(@Query("duration") String duration, @Query("sort") String sort, @Query("type") String type, @Query("start") String start, @Query("limit") String limit, @Query("distillate") String distillate);
//
//    /**
//     * 获取书评区帖子详情
//     *
//     * @param bookReviewId->_id
//     * @return
//     */
//    @GET("/post/review/{bookReviewId}")
//    Observable<BookReview> getBookReviewDetail(@Path("bookReviewId") String bookReviewId);
//
//    /**
//     * 获取书评区、书荒区帖子详情内的评论列表
//     *
//     * @param bookReviewId->_id
//     * @param start             0
//     * @param limit             30
//     * @return
//     */
//    @GET("/post/review/{bookReviewId}/comment")
//    Observable<CommentList> getBookReviewComments(@Path("bookReviewId") String bookReviewId, @Query("start") String start, @Query("limit") String limit);
//
//    /**
//     * 获取书荒区帖子列表
//     * 全部、默认排序  http://api.zhuishushenqi.com/post/help?duration=all&sort=updated&start=0&limit=20&distillate=
//     * 精品、默认排序  http://api.zhuishushenqi.com/post/help?duration=all&sort=updated&start=0&limit=20&distillate=true
//     *
//     * @param duration   all
//     * @param sort       updated(默认排序)、created(最新发布)、comment-count(最多评论)
//     * @param start      0
//     * @param limit      20
//     * @param distillate true(精品) 、空字符（全部）
//     * @return
//     */
//    @GET("/post/help")
//    Observable<BookHelpList> getBookHelpList(@Query("duration") String duration, @Query("sort") String sort, @Query("start") String start, @Query("limit") String limit, @Query("distillate") String distillate);
//
//    /**
//     * 获取书荒区帖子详情
//     *
//     * @param helpId->_id
//     * @return
//     */
//    @GET("/post/help/{helpId}")
//    Observable<BookHelp> getBookHelpDetail(@Path("helpId") String helpId);
//
//    /**
//     * 第三方登陆
//     *
//     * @param platform_uid
//     * @param platform_token
//     * @param platform_code  “QQ”
//     * @return
//     */
//    @POST("/user/login")
//    Observable<Login> login(@Body LoginReq loginReq);
//
//    @GET("/user/followings/{userid}")
//    Observable<Following> getFollowings(@Path("userid") String userId);
//
//    /**
//     * 获取书籍详情讨论列表
//     *
//     * @param book       bookId
//     * @param sort       updated(默认排序)、created(最新发布)、comment-count(最多评论)
//     * @param type       normal,vote
//     * @param start      0
//     * @param limit      20
//     * @return
//     */
//    @GET("/post/by-book")
//    Observable<DiscussionList> getBookDetailDisscussionList(@Query("book") String book, @Query("sort") String sort, @Query("type") String type, @Query("start") String start, @Query("limit") String limit);
}
