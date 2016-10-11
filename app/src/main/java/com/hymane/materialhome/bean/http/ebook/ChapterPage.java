package com.hymane.materialhome.bean.http.ebook;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/10/10
 * Description:
 */

public class ChapterPage {
    private int chapterId;
    private int pageIndex;
    private String body;

    public ChapterPage(int chapterId, int pageIndex, String body) {
        this.chapterId = chapterId;
        this.pageIndex = pageIndex;
        this.body = body;
    }

    public int getChapterId() {
        return chapterId;
    }

    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
