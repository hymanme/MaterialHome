package com.hymane.materialhome.bean.http.ebook;

import java.util.List;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/9/22
 * Description:
 */
public class BooksByCats extends Base {
    /**
     * _id : 555abb2d91d0eb814e5db04f
     * title : 全职法师
     * author : 乱
     * shortIntro : 一觉醒来，世界大变。 熟悉的高中传授的是魔法，告诉大家要成为一名出色的魔法师。 居住的都市之外游荡着袭击人类的魔物妖兽，虎视眈眈。
     * 崇尚科学的世界变成了崇尚魔法...
     * cover : /agent/http://image.cmfu.com/books/3489766/3489766.jpg
     * site : zhuishuvip
     * majorCate : 玄幻
     * latelyFollower : 109257
     * latelyFollowerBase : 0
     * minRetentionRatio : 0
     * retentionRatio : 72.88
     * lastChapter : 第1173章 文泰之死
     * tags : ["腹黑","玄幻","异界大陆"]
     */

    private List<BookDetail> books;

    public List<BookDetail> getBooks() {
        return books;
    }

    public void setBooks(List<BookDetail> books) {
        this.books = books;
    }
}
