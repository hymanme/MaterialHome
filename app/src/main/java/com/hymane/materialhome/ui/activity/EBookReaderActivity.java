package com.hymane.materialhome.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.hymane.materialhome.R;
import com.hymane.materialhome.api.presenter.impl.EBookReadPresenterImpl;
import com.hymane.materialhome.api.view.IEBookListView;
import com.hymane.materialhome.bean.http.ebook.BookChapter;
import com.hymane.materialhome.bean.http.ebook.ChapterRead;
import com.hymane.materialhome.common.Constant;
import com.hymane.materialhome.ui.widget.ReaderViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/9/29
 * Description: 图书阅读页
 */

public class EBookReaderActivity extends BaseActivity implements IEBookListView {
    @BindView(R.id.readerViewPager)
    ReaderViewPager mReaderViewPager;
    private String bookId;
    private EBookReadPresenterImpl bookReadPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_reader);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initEvents() {
        bookId = getIntent().getStringExtra("bookId");
        if (TextUtils.isEmpty(bookId)) {
            setResult(Constant.BOOK_READER_RESULT_FAILED);
            this.finish();
            return;
        }
        bookReadPresenter = new EBookReadPresenterImpl(this);
        bookReadPresenter.getBookChapters(bookId);
        mReaderViewPager.setAdapter(new ReaderPagerAdapter());
    }

    @Override
    public void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void refreshData(Object result) {
        if (result instanceof BookChapter.MixToc) {
            //章节
        } else if (result instanceof ChapterRead.Chapter) {
            //阅读内容
        }
    }

    class ReaderPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return false;
        }
    }
}
