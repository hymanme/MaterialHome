package com.hymane.materialhome.ui.widget.RecyclerViewDecoration;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/1/12
 * Description:
 */
public class StaggeredGridDecoration extends RecyclerView.ItemDecoration {
    private int left;
    private int top;
    private int right;
    private int bottom;
    private int spanCount;

    public StaggeredGridDecoration(int left, int top, int right, int bottom, int spanCount) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
        this.spanCount = spanCount;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.bottom = bottom;
        if (parent.getChildAdapterPosition(view) < spanCount) {
            outRect.top = 2 * top;
        } else {
            outRect.top = top;
        }
        if (parent.getChildAdapterPosition(view) % spanCount == 0) {
            outRect.left = 2 * left;
            outRect.right = right;
        } else {
            outRect.left = left;
            outRect.right = 2 * right;
        }
    }
}
