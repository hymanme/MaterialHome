package com.hymane.materialhome.api.common;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/8/29
 * Description:
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "MaterialHome.db";
    private static final int DATABASE_VERSION = 1;
    private static DatabaseHelper mDatabaseHelper = null;

    public static DatabaseHelper getInstance(Context context) {
        if (mDatabaseHelper == null) {
            synchronized (DatabaseHelper.class) {
                if (mDatabaseHelper == null) {
                    mDatabaseHelper = new DatabaseHelper(context);
                }
            }
        }
        return mDatabaseHelper;
    }

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists bookshelf("
                + "id integer primary key,"
                + "bookCount integer,"
                + "title varchar not null,"
                + "remark varchar,"
                + "orders integer,"
                + "create_at varchar not null)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch (oldVersion) {
            case 1:
//                db.execSQL("alter table bookshelf add column 'order' integer");
            default:
                break;
        }
    }
}
