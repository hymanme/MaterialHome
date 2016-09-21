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
public class SpacesCategoryDecoration extends RecyclerView.ItemDecoration {
    private int space;

    public SpacesCategoryDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left = 0;
        outRect.right = 0;
        outRect.bottom = space;
    }
}
