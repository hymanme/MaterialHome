package com.hymane.materialhome.api.presenter;

import com.hymane.materialhome.bean.table.Bookshelf;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/8/29
 * Description:
 */
public interface IBookshelfPresenter {
    /**
     * 获取我的书架
     */
    void loadBookshelf();

    /**
     * 添加一个书架
     *
     * @param title    书架名称
     * @param remark   备注
     * @param createAt 创建时间
     */
    void addBookshelf(String title, String remark, String createAt);

    /**
     * 修改一个书架
     *
     * @param bookshelf bookshelf
     */
    void updateBookshelf(Bookshelf bookshelf);

    /**
     * 排序
     *
     * @param id     id
     * @param front  前一个bookshelf order
     * @param behind 后一个bookshelf order
     */
    void orderBookshelf(int id, long front, long behind);

    /**
     * 清空书架
     *
     * @param id id
     */
    void deleteBookshelf(String id);

    /**
     * 取消订阅
     */
    void unSubscribe();
}
