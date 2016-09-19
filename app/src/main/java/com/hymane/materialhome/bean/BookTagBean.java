package com.hymane.materialhome.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/1/8
 * Description:
 */
public class BookTagBean implements Parcelable{
    private int count;
    private String name;
    private String title;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(count);
        dest.writeString(name);
        dest.writeString(title);
    }

    protected BookTagBean(Parcel in) {
        count = in.readInt();
        name = in.readString();
        title = in.readString();
    }

    public static final Creator<BookTagBean> CREATOR = new Creator<BookTagBean>() {
        @Override
        public BookTagBean createFromParcel(Parcel in) {
            return new BookTagBean(in);
        }

        @Override
        public BookTagBean[] newArray(int size) {
            return new BookTagBean[size];
        }
    };
}
