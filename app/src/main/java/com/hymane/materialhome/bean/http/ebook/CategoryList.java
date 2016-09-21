package com.hymane.materialhome.bean.http.ebook;

import java.util.ArrayList;
import java.util.List;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/9/21
 * Description:
 */

public class CategoryList extends Base {

    /**
     * male : [{"name":"玄幻","bookCount":188244},{"name":"奇幻","bookCount":24183}]
     * ok : true
     */

    private List<CategoryBean> male;

    public CategoryList() {
        male = new ArrayList<>();
        female = new ArrayList<>();
    }

    /**
     * name : 古代言情
     * bookCount : 125103
     */

    private List<CategoryBean> female;

    public List<CategoryBean> getMale() {
        return male;
    }

    public void setMale(List<CategoryBean> male) {
        this.male = male;
    }

    public List<CategoryBean> getFemale() {
        return female;
    }

    public void setFemale(List<CategoryBean> female) {
        this.female = female;
    }

    public static class CategoryBean {
        private String name;
        private int bookCount;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getBookCount() {
            return bookCount;
        }

        public void setBookCount(int bookCount) {
            this.bookCount = bookCount;
        }
    }
}
