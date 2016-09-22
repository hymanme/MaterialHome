package com.hymane.materialhome.bean.http.ebook;

import java.util.List;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/9/22
 * Description:
 */

public class BooksBean {
    private String _id;
    private String title;
    private String author;
    private String shortIntro;
    private String cover;
    private String site;
    private String majorCate;
    private String cat;
    private int banned;
    private int latelyFollower;
    private int latelyFollowerBase;
    private String minRetentionRatio;
    private String retentionRatio;
    private String lastChapter;
    private List<String> tags;

    public BooksBean(String _id, String cover, String title, String author, String majorCate, String shortIntro, int latelyFollower, String retentionRatio) {
        this._id = _id;
        this.cover = cover;
        this.title = title;
        this.author = author;
        this.majorCate = majorCate;
        this.shortIntro = shortIntro;
        this.latelyFollower = latelyFollower;
        this.retentionRatio = retentionRatio;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public int getBanned() {
        return banned;
    }

    public void setBanned(int banned) {
        this.banned = banned;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
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

    public String getShortIntro() {
        return shortIntro;
    }

    public void setShortIntro(String shortIntro) {
        this.shortIntro = shortIntro;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getMajorCate() {
        return majorCate;
    }

    public void setMajorCate(String majorCate) {
        this.majorCate = majorCate;
    }

    public int getLatelyFollower() {
        return latelyFollower;
    }

    public void setLatelyFollower(int latelyFollower) {
        this.latelyFollower = latelyFollower;
    }

    public int getLatelyFollowerBase() {
        return latelyFollowerBase;
    }

    public void setLatelyFollowerBase(int latelyFollowerBase) {
        this.latelyFollowerBase = latelyFollowerBase;
    }

    public String getMinRetentionRatio() {
        return minRetentionRatio;
    }

    public void setMinRetentionRatio(String minRetentionRatio) {
        this.minRetentionRatio = minRetentionRatio;
    }

    public String getRetentionRatio() {
        return retentionRatio;
    }

    public void setRetentionRatio(String retentionRatio) {
        this.retentionRatio = retentionRatio;
    }

    public String getLastChapter() {
        return lastChapter;
    }

    public void setLastChapter(String lastChapter) {
        this.lastChapter = lastChapter;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getBookInfoString() {
        if (cat == null) {
            cat = majorCate;
        }
        return author + " | " + cat + " | " + retentionRatio + "%读者留存";
    }
}
