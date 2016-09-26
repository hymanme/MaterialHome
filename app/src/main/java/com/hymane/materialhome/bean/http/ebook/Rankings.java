package com.hymane.materialhome.bean.http.ebook;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/9/21
 * Description:
 */

public class Rankings extends Base {

    /**
     * _id : 54d42d92321052167dfb75e3
     * updated : 2016-08-14T21:20:21.090Z
     * title : 追书最热榜 Top100
     * tag : zhuishuLatelyFollowerMale
     * cover : /ranking-cover/142319144267827
     * __v : 509
     * monthRank : 564d820bc319238a644fb408
     * totalRank : 564d8494fe996c25652644d2
     * isSub : false
     * collapse : false
     * new : true
     * gender : male
     * priority : 250
     * books : [{"_id":"53855a750ac0b3a41e00c7e6","title":"择天记","author":"猫腻",
     * "shortIntro":"太始元年，有神石自太空飞来，分散落在人间，其中落在东土大陆的神石，上面镌刻着奇怪的图腾，人因观其图腾而悟道，后立国教。
     * 数千年后，十四岁的少年孤儿陈长生，为治病...","cover":"/agent/http://image.cmfu.com/books/3347595/3347595.jpg",
     * "site":"qidian","cat":"玄幻","banned":0,"latelyFollower":182257,"latelyFollowerBase":0,
     * "minRetentionRatio":0,"retentionRatio":"52.48"}]
     * created : 2015-02-06T02:57:22.000Z
     * id : 54d42d92321052167dfb75e3
     */

    private RankingBean ranking;

    public RankingBean getRanking() {
        return ranking;
    }

    public void setRanking(RankingBean ranking) {
        this.ranking = ranking;
    }

    public static class RankingBean {
        private String _id;
        private String updated;
        private String title;
        private String tag;
        private String cover;
        private int __v;
        private String monthRank;
        private String totalRank;
        private boolean isSub;
        private boolean collapse;
        @SerializedName("new")
        private boolean newX;
        private String gender;
        private int priority;
        private String created;
        private String id;
        /**
         * _id : 53855a750ac0b3a41e00c7e6
         * title : 择天记
         * author : 猫腻
         * shortIntro : 太始元年，有神石自太空飞来，分散落在人间，其中落在东土大陆的神石，上面镌刻着奇怪的图腾，人因观其图腾而悟道，后立国教。 数千年后，十四岁的少年孤儿陈长生，为治病...
         * cover : /agent/http://image.cmfu.com/books/3347595/3347595.jpg
         * site : qidian
         * cat : 玄幻
         * banned : 0
         * latelyFollower : 182257
         * latelyFollowerBase : 0
         * minRetentionRatio : 0
         * retentionRatio : 52.48
         */

        public List<BookDetail> books;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getUpdated() {
            return updated;
        }

        public void setUpdated(String updated) {
            this.updated = updated;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public int get__v() {
            return __v;
        }

        public void set__v(int __v) {
            this.__v = __v;
        }

        public String getMonthRank() {
            return monthRank;
        }

        public void setMonthRank(String monthRank) {
            this.monthRank = monthRank;
        }

        public String getTotalRank() {
            return totalRank;
        }

        public void setTotalRank(String totalRank) {
            this.totalRank = totalRank;
        }

        public boolean isSub() {
            return isSub;
        }

        public void setSub(boolean sub) {
            isSub = sub;
        }

        public boolean isCollapse() {
            return collapse;
        }

        public void setCollapse(boolean collapse) {
            this.collapse = collapse;
        }

        public boolean isNewX() {
            return newX;
        }

        public void setNewX(boolean newX) {
            this.newX = newX;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public int getPriority() {
            return priority;
        }

        public void setPriority(int priority) {
            this.priority = priority;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public List<BookDetail> getBooks() {
            return books;
        }

        public void setBooks(List<BookDetail> books) {
            this.books = books;
        }
    }
}
