package com.hymane.materialhome.ui.widget.BottomSheet;


import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hymane.materialhome.R;
import com.hymane.materialhome.ui.widget.RecyclerViewDecoration.SpacesItemDecoration;

/**
 * Author   :hymane
 * Email    :hymanme@163.com
 * Create at 2016-12-27
 * Description:
 */
public class BottomSheetDialogView {
    private static final int MAX_GRID_COLUMN = 3;

    private Context mContext;
    private ImageView iv_icon;
    private TextView tv_title;
    private BottomSheetDialog dialog;
    private RecyclerView recyclerView;

    public BottomSheetDialogView(Context context) {
        this.mContext = context;
        dialog = new BottomSheetDialog(context, R.style.MyBottomSheetDialogTheme);
        View view = LayoutInflater.from(context).inflate(R.layout.bottom_sheet_layout, null);
        iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        recyclerView = (RecyclerView) view.findViewById(R.id.bottom_sheet_recycler_view);
        dialog.setContentView(view);
        this.dialog.dismiss();

    }

    public BottomSheetDialogView sheet(String title) {
        this.tv_title.setText(title);
        this.iv_icon.setVisibility(View.GONE);
        return this;
    }

    public BottomSheetDialogView sheet(String title, @DrawableRes int icon) {
        this.tv_title.setText(title);
        this.iv_icon.setImageResource(icon);
        this.iv_icon.setVisibility(View.VISIBLE);
        return this;
    }

    public BottomSheetDialogView setTitle(String title) {
        this.tv_title.setText(title);
        return this;
    }

    public String getTitle() {
        return tv_title.getText().toString();
    }

    public BottomSheetDialogView setAdapter(RecyclerView.Adapter adapter) {
        if (adapter instanceof SimpleShareAdapter) {
            recyclerView.setLayoutManager(new GridLayoutManager(mContext, MAX_GRID_COLUMN));
            recyclerView.addItemDecoration(new SpacesItemDecoration(20));
        } else {
            recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        }
        recyclerView.setAdapter(adapter);
        return this;
    }

    public void show() {
        if (recyclerView.getAdapter() == null) {
            throw new IllegalArgumentException("A adapter must be setup before show this.");
        }
        if (dialog != null) {
            dialog.show();
        }
    }

    public void hide() {
        if (dialog != null) {
            dialog.hide();
        }
    }
}
