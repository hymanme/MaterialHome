package com.hymane.materialhome.bean.http.ebook;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/9/22
 * Description:
 */

public class BookDetail implements Parcelable {


    /**
     * _id : 525253d094336b3155000dd8
     * author : w风雪
     * banned : 0
     * cover : /agent/http://image.cmfu.com/books/2797907/2797907.jpg
     * creater : iPhone 5 (GSM+CDMA)
     * dramaPoint : null
     * followerCount : 35
     * gradeCount : 0
     * isSerial : true
     * lastChapter : 请安装【追书神器】，本应用已停用
     * latelyFollower : 2385
     * longIntro : 您当前所使用的软件已改名为【追书神器】。
     * 请搜索“追书神器”下载安装最新版【追书神器】。
     * 无广告；不闪退；章节更新自动通知。
     * postCount : 111
     * serializeWordCount : 4614
     * site : zhuishuvip
     * tags : ["热血","洪荒封神","洪荒","架空","修炼","仙侠"]
     * title : 洪荒造化
     * tocs : ["525253d194336b3155000dd9","525253e6a4e35219120006a6","525253e6a4e35219120006a7","525253e6a4e35219120006a8","525253e6a4e35219120006a9","525253e6a4e35219120006ab","52c65225c1988af227000251","52c68d2cf91d99312b000d92","52c690bc0f3d8bda2b000873","532cf9dd39493253790020f4"]
     * totalPoint : null
     * type : wxxx
     * updated : 2016-04-03T13:48:05.907Z
     * writingPoint : null
     * hasNotice : false
     * tagStuck : 0
     * chaptersCount : 1294
     * tocCount : 15
     * tocUpdated : 2016-03-25T21:03:42.962Z
     * retentionRatio : 20.56
     * hasCmread : true
     * thirdFlagsUpdated : 2014-09-01T06:01:24.259Z
     * categories : ["洪荒封神","仙侠"]
     * wordCount : 5947980
     * cat : 仙侠
     * gender : ["male"]
     * majorCate : 仙侠
     * minorCate : 洪荒封神
     * reviewCount : 9
     * monthFollower : {"11":2170}
     * totalFollower : 2170
     * cpOnly : false
     * hasCp : true
     * _le : false
     */

    private String _id;
    private String author;
    private int banned;
    private String cover;
    private String creater;
    private Object dramaPoint;
    private int followerCount;
    private int gradeCount;
    private boolean isSerial;
    private String lastChapter;
    private int latelyFollower;
    private String longIntro;
    private int postCount; // 社区帖子数
    private int serializeWordCount;
    private String site;

    //bookbean
    private String shortIntro;
    private int latelyFollowerBase;
    private String minRetentionRatio;

    private String title;
    private Object totalPoint;
    private String type;
    private String updated;
    private Object writingPoint;
    private boolean hasNotice;
    private int tagStuck;
    private int chaptersCount;
    private int tocCount;
    private String tocUpdated;
    private String retentionRatio;
    private boolean hasCmread;
    private String thirdFlagsUpdated;
    private int wordCount;
    private String cat;
    private String majorCate;
    private String minorCate;
    private int reviewCount;
    private int totalFollower;
    private boolean cpOnly;
    private boolean hasCp;
    private boolean _le;
    private List<String> tags;
    private List<String> tocs;
    private List<String> categories;
    private List<String> gender;

    protected BookDetail(Parcel in) {
        _id = in.readString();
        author = in.readString();
        banned = in.readInt();
        cover = in.readString();
        creater = in.readString();
        followerCount = in.readInt();
        gradeCount = in.readInt();
        isSerial = in.readByte() != 0;
        lastChapter = in.readString();
        latelyFollower = in.readInt();
        longIntro = in.readString();
        postCount = in.readInt();
        serializeWordCount = in.readInt();
        site = in.readString();
        shortIntro = in.readString();
        latelyFollowerBase = in.readInt();
        minRetentionRatio = in.readString();
        title = in.readString();
        type = in.readString();
        updated = in.readString();
        hasNotice = in.readByte() != 0;
        tagStuck = in.readInt();
        chaptersCount = in.readInt();
        tocCount = in.readInt();
        tocUpdated = in.readString();
        retentionRatio = in.readString();
        hasCmread = in.readByte() != 0;
        thirdFlagsUpdated = in.readString();
        wordCount = in.readInt();
        cat = in.readString();
        majorCate = in.readString();
        minorCate = in.readString();
        reviewCount = in.readInt();
        totalFollower = in.readInt();
        cpOnly = in.readByte() != 0;
        hasCp = in.readByte() != 0;
        _le = in.readByte() != 0;
        tags = in.createStringArrayList();
        tocs = in.createStringArrayList();
        categories = in.createStringArrayList();
        gender = in.createStringArrayList();
    }

    public static final Creator<BookDetail> CREATOR = new Creator<BookDetail>() {
        @Override
        public BookDetail createFromParcel(Parcel in) {
            return new BookDetail(in);
        }

        @Override
        public BookDetail[] newArray(int size) {
            return new BookDetail[size];
        }
    };

    public String getId() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getBanned() {
        return banned;
    }

    public void setBanned(int banned) {
        this.banned = banned;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public Object getDramaPoint() {
        return dramaPoint;
    }

    public void setDramaPoint(Object dramaPoint) {
        this.dramaPoint = dramaPoint;
    }

    public int getFollowerCount() {
        return followerCount;
    }

    public void setFollowerCount(int followerCount) {
        this.followerCount = followerCount;
    }

    public int getGradeCount() {
        return gradeCount;
    }

    public void setGradeCount(int gradeCount) {
        this.gradeCount = gradeCount;
    }

    public boolean isSerial() {
        return isSerial;
    }

    public void setSerial(boolean serial) {
        isSerial = serial;
    }

    public String getLastChapter() {
        return lastChapter;
    }

    public void setLastChapter(String lastChapter) {
        this.lastChapter = lastChapter;
    }

    public int getLatelyFollower() {
        return latelyFollower;
    }

    public void setLatelyFollower(int latelyFollower) {
        this.latelyFollower = latelyFollower;
    }

    public String getLongIntro() {
        return longIntro;
    }

    public void setLongIntro(String longIntro) {
        this.longIntro = longIntro;
    }

    public int getPostCount() {
        return postCount;
    }

    public void setPostCount(int postCount) {
        this.postCount = postCount;
    }

    public int getSerializeWordCount() {
        return serializeWordCount;
    }

    public void setSerializeWordCount(int serializeWordCount) {
        this.serializeWordCount = serializeWordCount;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getShortIntro() {
        return shortIntro;
    }

    public void setShortIntro(String shortIntro) {
        this.shortIntro = shortIntro;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Object getTotalPoint() {
        return totalPoint;
    }

    public void setTotalPoint(Object totalPoint) {
        this.totalPoint = totalPoint;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public Object getWritingPoint() {
        return writingPoint;
    }

    public void setWritingPoint(Object writingPoint) {
        this.writingPoint = writingPoint;
    }

    public boolean isHasNotice() {
        return hasNotice;
    }

    public void setHasNotice(boolean hasNotice) {
        this.hasNotice = hasNotice;
    }

    public int getTagStuck() {
        return tagStuck;
    }

    public void setTagStuck(int tagStuck) {
        this.tagStuck = tagStuck;
    }

    public int getChaptersCount() {
        return chaptersCount;
    }

    public void setChaptersCount(int chaptersCount) {
        this.chaptersCount = chaptersCount;
    }

    public int getTocCount() {
        return tocCount;
    }

    public void setTocCount(int tocCount) {
        this.tocCount = tocCount;
    }

    public String getTocUpdated() {
        return tocUpdated;
    }

    public void setTocUpdated(String tocUpdated) {
        this.tocUpdated = tocUpdated;
    }

    public String getRetentionRatio() {
        return retentionRatio;
    }

    public void setRetentionRatio(String retentionRatio) {
        this.retentionRatio = retentionRatio;
    }

    public boolean isHasCmread() {
        return hasCmread;
    }

    public void setHasCmread(boolean hasCmread) {
        this.hasCmread = hasCmread;
    }

    public String getThirdFlagsUpdated() {
        return thirdFlagsUpdated;
    }

    public void setThirdFlagsUpdated(String thirdFlagsUpdated) {
        this.thirdFlagsUpdated = thirdFlagsUpdated;
    }

    public int getWordCount() {
        return wordCount;
    }

    public void setWordCount(int wordCount) {
        this.wordCount = wordCount;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public String getMajorCate() {
        return majorCate;
    }

    public void setMajorCate(String majorCate) {
        this.majorCate = majorCate;
    }

    public String getMinorCate() {
        return minorCate;
    }

    public void setMinorCate(String minorCate) {
        this.minorCate = minorCate;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(int reviewCount) {
        this.reviewCount = reviewCount;
    }

    public int getTotalFollower() {
        return totalFollower;
    }

    public void setTotalFollower(int totalFollower) {
        this.totalFollower = totalFollower;
    }

    public boolean isCpOnly() {
        return cpOnly;
    }

    public void setCpOnly(boolean cpOnly) {
        this.cpOnly = cpOnly;
    }

    public boolean isHasCp() {
        return hasCp;
    }

    public void setHasCp(boolean hasCp) {
        this.hasCp = hasCp;
    }

    public boolean is_le() {
        return _le;
    }

    public void set_le(boolean _le) {
        this._le = _le;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<String> getTocs() {
        return tocs;
    }

    public void setTocs(List<String> tocs) {
        this.tocs = tocs;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public List<String> getGender() {
        return gender;
    }

    public void setGender(List<String> gender) {
        this.gender = gender;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getBookInfoString() {
        if (cat == null) {
            cat = majorCate;
        }
        return author + " | " + cat + " | " + retentionRatio + "%读者留存";
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(author);
        dest.writeInt(banned);
        dest.writeString(cover);
        dest.writeString(creater);
        dest.writeInt(followerCount);
        dest.writeInt(gradeCount);
        dest.writeByte((byte) (isSerial ? 1 : 0));
        dest.writeString(lastChapter);
        dest.writeInt(latelyFollower);
        dest.writeString(longIntro);
        dest.writeInt(postCount);
        dest.writeInt(serializeWordCount);
        dest.writeString(site);
        dest.writeString(shortIntro);
        dest.writeInt(latelyFollowerBase);
        dest.writeString(minRetentionRatio);
        dest.writeString(title);
        dest.writeString(type);
        dest.writeString(updated);
        dest.writeByte((byte) (hasNotice ? 1 : 0));
        dest.writeInt(tagStuck);
        dest.writeInt(chaptersCount);
        dest.writeInt(tocCount);
        dest.writeString(tocUpdated);
        dest.writeString(retentionRatio);
        dest.writeByte((byte) (hasCmread ? 1 : 0));
        dest.writeString(thirdFlagsUpdated);
        dest.writeInt(wordCount);
        dest.writeString(cat);
        dest.writeString(majorCate);
        dest.writeString(minorCate);
        dest.writeInt(reviewCount);
        dest.writeInt(totalFollower);
        dest.writeByte((byte) (cpOnly ? 1 : 0));
        dest.writeByte((byte) (hasCp ? 1 : 0));
        dest.writeByte((byte) (_le ? 1 : 0));
        dest.writeStringList(tags);
        dest.writeStringList(tocs);
        dest.writeStringList(categories);
        dest.writeStringList(gender);
    }
}
