package com.hymane.materialhome.bean.http.ebook;

import java.util.ArrayList;
import java.util.List;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/9/28
 * Description: 推荐书单
 */


public class LikedBookList extends Base {
    /**
     * id : 5617c5f3e8a2065627e4cb85
     * title : 此单在手，书荒不再有！
     * author : 选择
     * desc : 应有尽有！注：随时有可能添加新书！
     * bookCount : 498
     * cover : /agent/http://image.cmfu.com/books/3582111/3582111.jpg
     * collectorCount : 3925
     */

    private List<RecommendBook> booklists;

    public LikedBookList() {
        booklists = new ArrayList<>();
    }

    public List<RecommendBook> getBookList() {
        return booklists;
    }

    public void addBookList(List<RecommendBook> booklists) {
        this.booklists.clear();
        this.booklists.addAll(booklists);
    }

    public void setBookList(List<RecommendBook> booklists) {
        this.booklists = booklists;
    }

    public static class RecommendBook {
        private String id;
        private String title;
        private String author;
        private String desc;
        private int bookCount;
        private String cover;
        private int collectorCount;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public int getBookCount() {
            return bookCount;
        }

        public void setBookCount(int bookCount) {
            this.bookCount = bookCount;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public int getCollectorCount() {
            return collectorCount;
        }

        public void setCollectorCount(int collectorCount) {
            this.collectorCount = collectorCount;
        }

        public String getInfo() {
            return "共" + bookCount + "本书 | " + collectorCount + "人收藏";
        }
    }
}