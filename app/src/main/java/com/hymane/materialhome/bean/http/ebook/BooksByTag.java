package com.hymane.materialhome.bean.http.ebook;

import java.util.ArrayList;
import java.util.List;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/9/28
 * Description:
 */

public class BooksByTag extends Base {
    /**
     * _id : 559b29837a0f8b65521464a7
     * title : 申公豹传承
     * author : 第九天命
     * shortIntro : 本书主角玉独秀获得应灾劫大道而生的申公豹传承，然后又在无意间融合了一丝诸天劫难本源，有了执掌、引动大劫之力量，为众生带来劫难，可以借助大劫，来加快自己的修炼速，...
     * cover : /agent/http://image.cmfu.com/books/3533952/3533952.jpg
     * cat : 仙侠
     * majorCate : 仙侠
     * minorCate : 洪荒封神
     * latelyFollower : 776
     * retentionRatio : 69.68
     * lastChapter : 第1337章 狐媚子
     * tags : ["洪荒封神","仙侠"]
     */

    private List<BookDetail> books;

    public BooksByTag() {
        books = new ArrayList<>();
    }

    public List<BookDetail> getBooks() {
        return books;
    }

    public void setBooks(List<BookDetail> books) {
        this.books = books;
    }

    public void addBooks(List<BookDetail> books) {
        this.books.clear();
        this.books.addAll(books);
    }
//    public static class TagBook {
//        private String _id;
//        private String title;
//        private String author;
//        private String shortIntro;
//        private String cover;
//        private String site;
//        private String cat;
//        private String majorCate;
//        private String minorCate;
//        private int latelyFollower;
//        private String retentionRatio;
//        private String lastChapter;
//        private List<String> tags;
//
//        public String get_id() {
//            return _id;
//        }
//
//        public void set_id(String _id) {
//            this._id = _id;
//        }
//
//        public String getTitle() {
//            return title;
//        }
//
//        public void setTitle(String title) {
//            this.title = title;
//        }
//
//        public String getAuthor() {
//            return author;
//        }
//
//        public void setAuthor(String author) {
//            this.author = author;
//        }
//
//        public String getShortIntro() {
//            return shortIntro;
//        }
//
//        public void setShortIntro(String shortIntro) {
//            this.shortIntro = shortIntro;
//        }
//
//        public String getCover() {
//            return cover;
//        }
//
//        public void setCover(String cover) {
//            this.cover = cover;
//        }
//
//        public String getSite() {
//            return site;
//        }
//
//        public void setSite(String site) {
//            this.site = site;
//        }
//
//        public String getCat() {
//            return cat;
//        }
//
//        public void setCat(String cat) {
//            this.cat = cat;
//        }
//
//        public String getMajorCate() {
//            return majorCate;
//        }
//
//        public void setMajorCate(String majorCate) {
//            this.majorCate = majorCate;
//        }
//
//        public String getMinorCate() {
//            return minorCate;
//        }
//
//        public void setMinorCate(String minorCate) {
//            this.minorCate = minorCate;
//        }
//
//        public int getLatelyFollower() {
//            return latelyFollower;
//        }
//
//        public void setLatelyFollower(int latelyFollower) {
//            this.latelyFollower = latelyFollower;
//        }
//
//        public String getRetentionRatio() {
//            return retentionRatio;
//        }
//
//        public void setRetentionRatio(String retentionRatio) {
//            this.retentionRatio = retentionRatio;
//        }
//
//        public String getLastChapter() {
//            return lastChapter;
//        }
//
//        public void setLastChapter(String lastChapter) {
//            this.lastChapter = lastChapter;
//        }
//
//        public List<String> getTags() {
//            return tags;
//        }
//
//        public void setTags(List<String> tags) {
//            this.tags = tags;
//        }
//    }
}
