package com.hymane.materialhome.ui.callback;

import android.support.v7.util.DiffUtil;

import java.util.List;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/9/29
 * Description:
 */

public abstract class BaseDiffCallBack<T> extends DiffUtil.Callback {
    protected List<T> mOldData, mNewData;

    public BaseDiffCallBack(List<T> mOldData, List<T> mNewData) {
        this.mOldData = mOldData;
        this.mNewData = mNewData;
    }

    //返回旧的数据集合大小
    @Override
    public int getOldListSize() {
        return mOldData != null ? mOldData.size() : 0;
    }

    //返回新的数据集合大小
    @Override
    public int getNewListSize() {
        return mNewData != null ? mNewData.size() : 0;
    }

    /**
     * Called by the DiffUtil to decide whether two object represent the same Item.
     * 被DiffUtil调用，用来判断 两个对象是否是相同的Item。
     * For example, if your items have unique ids, this method should check their id equality.
     * 例如，如果你的Item有唯一的id字段，这个方法就 判断id是否相等。
     *
     * @param oldItemPosition The position of the item in the old list
     * @param newItemPosition The position of the item in the new list
     * @return True if the two items represent the same object or false if they are different.
     */
    @Override
    public abstract boolean areItemsTheSame(int oldItemPosition, int newItemPosition);

    /**
     * Called by the DiffUtil when it wants to check whether two items have the same data.
     * 被DiffUtil调用，用来检查 两个item是否含有相同的数据
     * DiffUtil uses this information to detect if the contents of an item has changed.
     * DiffUtil用返回的信息（true false）来检测当前item的内容是否发生了变化
     * DiffUtil uses this method to check equality instead of {@link Object#equals(Object)}
     * DiffUtil 用这个方法替代equals方法去检查是否相等。
     * so that you can change its behavior depending on your UI.
     * 所以你可以根据你的UI去改变它的返回值
     * For example, if you are using DiffUtil with a
     * {@link android.support.v7.widget.RecyclerView.Adapter RecyclerView.Adapter}, you should
     * return whether the items' visual representations are the same.
     * 例如，如果你用RecyclerView.Adapter 配合DiffUtil使用，你需要返回Item的视觉表现是否相同。
     * This method is called only if {@link #areItemsTheSame(int, int)} returns
     * {@code true} for these items.
     * 这个方法仅仅在areItemsTheSame()返回true时，才调用。
     *
     * @param oldItemPosition The position of the item in the old list
     * @param newItemPosition The position of the item in the new list which replaces the
     *                        oldItem
     * @return True if the contents of the items are the same or false if they are different.
     */
    @Override
    public abstract boolean areContentsTheSame(int oldItemPosition, int newItemPosition);

}