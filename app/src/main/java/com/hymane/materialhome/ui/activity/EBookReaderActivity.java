package com.hymane.materialhome.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.hymane.materialhome.R;
import com.hymane.materialhome.api.presenter.impl.EBookReadPresenterImpl;
import com.hymane.materialhome.api.view.IEBookReadView;
import com.hymane.materialhome.bean.http.ebook.BookChapter;
import com.hymane.materialhome.bean.http.ebook.BookChapter.MixToc.Chapters;
import com.hymane.materialhome.bean.http.ebook.ChapterRead;
import com.hymane.materialhome.common.Constant;
import com.hymane.materialhome.ui.widget.ReaderViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/9/29
 * Description: 图书阅读页
 */

public class EBookReaderActivity extends BaseActivity implements IEBookReadView {
    @BindView(R.id.readerViewPager)
    ReaderViewPager mReaderViewPager;
    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;
    private String bookId;
    private String bookName;
    private EBookReadPresenterImpl bookReadPresenter;

    private List<Chapters> mBookChapterList;
    private int chapterIndex = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_reader);
        ButterKnife.bind(this);
        if (savedInstanceState != null) {
            chapterIndex = savedInstanceState.getInt("chapterIndex");
        }
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initEvents() {
        bookId = getIntent().getStringExtra("bookId");
        bookName = getIntent().getStringExtra("bookName");
        if (TextUtils.isEmpty(bookId)) {
            setResult(Constant.BOOK_READER_RESULT_FAILED);
            this.finish();
            return;
        }
        mBookChapterList = new ArrayList<>();
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
            mBookChapterList.clear();
            mBookChapterList.addAll(((BookChapter.MixToc) result).getChapters());
            readCurrentChapter();
        } else if (result instanceof ChapterRead.Chapter) {
            //阅读内容

        }
    }

    //阅读当前章节图书（是否缓存章节为true时）
    //先读缓存，无缓存就在线获取图书，然后缓存，再阅读
    //如果有缓存就直接读缓存
    private synchronized void readCurrentChapter() {

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("chapterIndex", chapterIndex);
        super.onSaveInstanceState(outState);
    }

    class ReaderPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            return super.instantiateItem(container, position);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return false;
        }
    }
}
