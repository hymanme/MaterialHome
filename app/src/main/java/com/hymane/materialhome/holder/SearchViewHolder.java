package com.hymane.materialhome.holder;

import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hymane.materialhome.R;
import com.hymane.materialhome.utils.common.AnimationUtils;
import com.hymane.materialhome.utils.common.KeyBoardUtils;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/1/26 0026
 * Description:useless
 */
public class SearchViewHolder implements View.OnClickListener {
    public static final int RESULT_SEARCH_EMPTY_KEYWORD = 0;
    public static final int RESULT_SEARCH_SEARCH = 1;
    public static final int RESULT_SEARCH_GO_SCAN = 2;
    public static final int RESULT_SEARCH_CANCEL = 3;
    private Activity mContext;
    private View mContentView;
    private OnSearchHandlerListener mListener;

    private ImageView iv_arrow_back;
    private ImageView iv_scan;
    private ImageView iv_fork_clear;
    private ImageView iv_search;
    public EditText et_search_content;

    public SearchViewHolder(Activity context, OnSearchHandlerListener listener) {
        this.mContext = context;
        mListener = listener;
        this.mContentView = LayoutInflater.from(context).inflate(R.layout.search_layout, null);
        initView();
        initEvent();
    }

    private void initView() {
        iv_arrow_back = (ImageView) mContentView.findViewById(R.id.iv_arrow_back);
        iv_scan = (ImageView) mContentView.findViewById(R.id.iv_scan);
        iv_fork_clear = (ImageView) mContentView.findViewById(R.id.iv_fork_clear);
        iv_search = (ImageView) mContentView.findViewById(R.id.iv_search);
        et_search_content = (EditText) mContentView.findViewById(R.id.et_search_content);
    }

    private void initEvent() {
        iv_arrow_back.setOnClickListener(this);
        iv_scan.setOnClickListener(this);
        iv_fork_clear.setOnClickListener(this);
        iv_search.setOnClickListener(this);
        et_search_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    iv_fork_clear.setVisibility(View.VISIBLE);
                } else {
                    iv_fork_clear.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
//                    if (s.length() > 1) {
//                        Snackbar.make(mContentView, s.toString(), Snackbar.LENGTH_SHORT).show();
//                    }
            }
        });
        et_search_content.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (et_search_content.getText().toString().isEmpty()) {
                        if (mListener != null) {
                            et_search_content.startAnimation(AnimationUtils.shakeAnimation(5));
                            mListener.onSearch(SearchViewHolder.RESULT_SEARCH_EMPTY_KEYWORD);
                        }
                        return false;
                    }
                    if (mListener != null) {
                        KeyBoardUtils.closeKeyBord(et_search_content, mContext);
                        mListener.onSearch(SearchViewHolder.RESULT_SEARCH_SEARCH);
//                        Intent intent = new Intent(mContext, SearchResultActivity.class);
//                        intent.putExtra("q", et_search_content.getText().toString());
//                        mContext.startActivity(intent);
                    }
                    return true;
                }
                return false;
            }
        });
    }

    public View getContentView() {
        return mContentView;
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.iv_arrow_back:
                KeyBoardUtils.closeKeyBord(et_search_content, mContext);
                if (mListener != null) {
                    mListener.onSearch(SearchViewHolder.RESULT_SEARCH_CANCEL);
                }
                break;
            case R.id.iv_scan:
                if (mListener != null) {
                    mListener.onSearch(SearchViewHolder.RESULT_SEARCH_GO_SCAN);
                }
//                if (PermissionUtils.requestCameraPermission(mContext)) {
//                    UIUtils.startActivity(new Intent(UIUtils.getContext(), CaptureActivity.class));
//                }
                break;
            case R.id.iv_fork_clear:
                et_search_content.setText("");
                break;
            case R.id.iv_search:
                if (et_search_content.getText().toString().isEmpty()) {
                    et_search_content.startAnimation(AnimationUtils.shakeAnimation(5));
                    if (mListener != null) {
                        et_search_content.startAnimation(AnimationUtils.shakeAnimation(5));
                        mListener.onSearch(SearchViewHolder.RESULT_SEARCH_EMPTY_KEYWORD);
                    }
                    break;
                }
                if (mListener != null) {
                    KeyBoardUtils.closeKeyBord(et_search_content, mContext);
                    mListener.onSearch(SearchViewHolder.RESULT_SEARCH_SEARCH);
                }
                break;
            default:
                break;
        }
    }

    public interface OnSearchHandlerListener {
        void onSearch(int code);
    }

    public void onDestroy() {
        mContentView = null;
        mContext = null;
    }
}
