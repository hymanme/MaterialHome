package com.hymane.materialhome.holder;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.hymane.materialhome.R;


/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/3/17 0017
 * Description:书架修改dialog
 */
public class BookShelfEditorHolder {
    private Context mContext;
    private View mContentView;
    private TextInputLayout til_bookshelf;
    public EditText et_bookshelf_name;
    private EditText et_bookshelf_remark;

    public BookShelfEditorHolder(Context context) {
        this(context, "", "");
    }

    public BookShelfEditorHolder(Context context, String name, String remark) {
        mContext = context;
        initView(name, remark);
        initEvent();
    }

    private void initView(String name, String remark) {
        mContentView = LayoutInflater.from(mContext).inflate(R.layout.item_add_bookshelf, null);
        til_bookshelf = (TextInputLayout) mContentView.findViewById(R.id.til_bookshelf);
        et_bookshelf_name = (EditText) mContentView.findViewById(R.id.et_bookshelf_name);
        et_bookshelf_remark = (EditText) mContentView.findViewById(R.id.et_bookshelf_remark);
        et_bookshelf_name.setText(name);
        et_bookshelf_remark.setText(remark);
    }

    private void initEvent() {
    }

    public View getContentView() {
        return mContentView;
    }

    public boolean check() {
        if (TextUtils.isEmpty(et_bookshelf_name.getText().toString())) {
            return false;
        } else {
            return true;
        }
    }

    public String getName() {
        return et_bookshelf_name.getText().toString();
    }

    public String getRemark() {
        return et_bookshelf_remark.getText().toString();
    }
}
