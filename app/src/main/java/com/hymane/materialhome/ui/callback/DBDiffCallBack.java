package com.hymane.materialhome.ui.callback;

import com.hymane.materialhome.bean.http.douban.BookInfoResponse;

import java.util.List;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/9/29
 * Description:
 */

public class DBDiffCallBack extends BaseDiffCallBack<BookInfoResponse> {
    public DBDiffCallBack(List<BookInfoResponse> mOldData, List<BookInfoResponse> mNewData) {
        super(mOldData, mNewData);
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return mOldData.get(oldItemPosition).getId().equals(mNewData.get(newItemPosition).getId());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        BookInfoResponse beanOld = mOldData.get(oldItemPosition);
        BookInfoResponse beanNew = mNewData.get(newItemPosition);
        if (!beanOld.getIsbn13().equals(beanNew.getIsbn13())) {
            return false;//如果有内容不同，就返回false
        }
        if (!beanOld.getTitle().equals(beanNew.getTitle())) {
            return false;//如果有内容不同，就返回false
        }
        return true; //默认两个data内容是相同的
    }
}
