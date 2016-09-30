package com.hymane.materialhome.api.model.impl;

import android.content.ContentValues;
import android.database.Cursor;

import com.hymane.materialhome.api.ApiCompleteListener;
import com.hymane.materialhome.api.common.DatabaseHelper;
import com.hymane.materialhome.api.model.IBookshelfModel;
import com.hymane.materialhome.bean.http.douban.BaseResponse;
import com.hymane.materialhome.bean.table.Bookshelf;
import com.hymane.materialhome.utils.common.UIUtils;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/8/29
 * Description:
 */
public class BookshelfModelImpl implements IBookshelfModel {
    private SqlBrite sqlBrite = SqlBrite.create();
    private BriteDatabase db = sqlBrite.wrapDatabaseHelper(DatabaseHelper.getInstance(UIUtils.getContext()), Schedulers.io());
    private Subscription subscribe;

    @Override
    public void loadBookshelf(ApiCompleteListener listener) {
        if (db != null) {
            Observable<SqlBrite.Query> bookshelf = db.createQuery("bookshelf", "SELECT * FROM bookshelf order by orders DESC");
            subscribe = bookshelf.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<SqlBrite.Query>() {
                        @Override
                        public void call(SqlBrite.Query query) {
                            Cursor cursor = query.run();
                            if (cursor == null || cursor.getCount() < 0) {
                                return;
                            }
                            List<Bookshelf> bookshelfs = new ArrayList<>();
                            while (cursor.moveToNext()) {
                                Bookshelf bookshelfBean = new Bookshelf();
                                bookshelfBean.setId(cursor.getInt(cursor.getColumnIndex("id")));
                                bookshelfBean.setBookCount(cursor.getInt(cursor.getColumnIndex("bookCount")));
                                bookshelfBean.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                                bookshelfBean.setRemark(cursor.getString(cursor.getColumnIndex("remark")));
                                bookshelfBean.setOrder(cursor.getInt(cursor.getColumnIndex("orders")));
                                bookshelfBean.setCreateTime(cursor.getString(cursor.getColumnIndex("create_at")));
                                bookshelfs.add(bookshelfBean);
                            }
                            listener.onComplected(bookshelfs);
                        }
                    });
        } else {
            listener.onFailed(new BaseResponse(500, "db error : init"));
        }
    }

    @Override
    public void addBookshelf(String title, String remark, String createAt, ApiCompleteListener listener) {
        if (db != null) {
            ContentValues values = new ContentValues();
            values.put("title", title);
            values.put("remark", remark);
            values.put("create_at", createAt);
            values.put("orders", System.currentTimeMillis());
            db.insert("bookshelf", values);
        } else {
            listener.onFailed(new BaseResponse(500, "db error : add"));
        }
    }

    @Override
    public void updateBookshelf(Bookshelf bookshelf, ApiCompleteListener listener) {
        if (db != null) {
            ContentValues values = new ContentValues();
            values.put("title", bookshelf.getTitle());
            values.put("remark", bookshelf.getRemark());
            db.update("bookshelf", values, "id=?", bookshelf.getId() + "");
        } else {
            listener.onFailed(new BaseResponse(500, "db error : update"));
        }
    }

    @Override
    public void orderBookshelf(int id, long front, long behind, ApiCompleteListener listener) {
        if (db != null) {
            long mOrder = front + (behind - front) / 2;
            ContentValues values = new ContentValues();
            values.put("orders", mOrder);
            db.update("bookshelf", values, "id=?", id + "");
        } else {
            listener.onFailed(new BaseResponse(500, "db error : update"));
        }
    }

    @Override
    public void deleteBookshelf(String id, ApiCompleteListener listener) {
        if (db != null) {
            db.delete("bookshelf", "id=?", id);
        } else {
            listener.onFailed(new BaseResponse(500, "db error : delete"));
        }
    }

    @Override
    public void unSubscribe() {
        if (subscribe != null) {
            subscribe.unsubscribe();
            db.close();
        }
    }
}
