package com.hymane.materialhome.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hymane.materialhome.R;
import com.hymane.materialhome.api.presenter.impl.EBookReadPresenterImpl;
import com.hymane.materialhome.api.view.IEBookReadView;
import com.hymane.materialhome.bean.http.ebook.BookChapter;
import com.hymane.materialhome.bean.http.ebook.BookChapter.MixToc.Chapters;
import com.hymane.materialhome.bean.http.ebook.ChapterPage;
import com.hymane.materialhome.bean.http.ebook.ChapterRead;
import com.hymane.materialhome.common.Constant;
import com.hymane.materialhome.ui.widget.ReaderViewPager;
import com.hymane.materialhome.utils.BookChapterFactory;
import com.hymane.materialhome.utils.common.ToastUtils;
import com.hymane.materialhome.utils.common.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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
    private BookChapterFactory chapterFactory;
    private ReaderPagerAdapter readerPagerAdapter;

    private List<Chapters> mBookChapterList;
    private SparseArray<ArrayList<ChapterPage>> pages;
    private ArrayList<ChapterPage> viewPagerDatas;
    private int currentChapter = 0;
    private int currentPage = 0;
    private int cacheChapter = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_reader);
        ButterKnife.bind(this);
        if (savedInstanceState != null) {
            currentChapter = savedInstanceState.getInt("currentChapter");
        }
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initEvents() {
        bookId = getIntent().getStringExtra("bookId");
        bookName = getIntent().getStringExtra("bookName");
        setTitle(bookName);
        if (TextUtils.isEmpty(bookId)) {
            setResult(Constant.BOOK_READER_RESULT_FAILED);
            showMessage("获取图书信息失败");
            this.finish();
            return;
        }
        pages = new SparseArray<>();
        viewPagerDatas = new ArrayList<>();
        mBookChapterList = new ArrayList<>();
        bookReadPresenter = new EBookReadPresenterImpl(this);
        final TextView textView = new TextView(UIUtils.getContext());
        chapterFactory = new BookChapterFactory(bookId, textView.getLineHeight());
        bookReadPresenter.getBookChapters(bookId);
        readerPagerAdapter = new ReaderPagerAdapter();
        mReaderViewPager.setAdapter(readerPagerAdapter);
        mReaderViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (viewPagerDatas.size() - position == 3) {
                    //最后3页了
                    getFutureChapterContent(Math.min(++currentChapter, mBookChapterList.size()));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mReaderViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP:
//                        ToastUtils.showShort("x:" + event.getX() + "|y:" + event.getY());
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public void showMessage(String msg) {
        ToastUtils.showShort(msg);
    }


    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    protected boolean isInitSystemBar() {
        return false;
    }

    @Override
    public void refreshData(Object result) {
        if (result instanceof BookChapter.MixToc) {
            //章节列表
            //先获取章节列表，然后获取当前章节的内容
            mBookChapterList.clear();
            mBookChapterList.addAll(((BookChapter.MixToc) result).getChapters());
            //如果当前阅读的缓存数接近缓存的章节数，开始更新
            getFutureChapterContent(currentChapter);
        } else if (result instanceof ChapterRead.Chapter) {
            //该请求已经将内容缓存至文件
            //某一章节的id
            final int resultId = ((ChapterRead.Chapter) result).getChapterId();
            //阅读内容
            //该书章节列表已经加载完毕
            //阅读当前章节图书内容
            Observable.create((Subscriber<? super ArrayList<ChapterPage>> subscriber) -> {
                //异步操作相关代码
                final ArrayList<ChapterPage> chapterContent = chapterFactory.getChapterContent(resultId);
                if (chapterContent != null) {
                    subscriber.onNext(chapterContent);
                } else {
                    subscriber.onError(new NullPointerException("null chapter content"));
                }
                subscriber.onCompleted();
            })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<ArrayList<ChapterPage>>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            showMessage(e.toString());
                        }

                        @Override
                        public void onNext(ArrayList<ChapterPage> chapterContent) {
                            pages.append(resultId, chapterContent);
                            viewPagerDatas.addAll(chapterContent);
                            readerPagerAdapter.notifyDataSetChanged();
                            mProgressBar.setVisibility(View.GONE);
                        }
                    });
        }
    }

    //获取章节内容，如果已经缓存则直接取缓存
    private void getFutureChapterContent(int chapter) {
        Observable.create((Subscriber<? super ArrayList<ChapterPage>> subscriber) -> {
            //异步操作相关代码
            final ArrayList<ChapterPage> chapterContent = chapterFactory.getChapterContent(chapter);//从缓存读单个章节内容
            subscriber.onNext(chapterContent);
            subscriber.onCompleted();
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArrayList<ChapterPage>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        showMessage(e.toString());
                    }

                    @Override
                    public void onNext(ArrayList<ChapterPage> chapterContent) {
                        if (chapterContent == null) {
                            //LRU未缓存且本地文件未缓存
                            //需要读取网络获取内容
                            bookReadPresenter.getChapterContent(mBookChapterList.get(chapter).getLink(), bookId, chapter, true);//读网络内容
                        } else {
                            pages.append(chapter, chapterContent);
                            viewPagerDatas.addAll(chapterContent);
                            readerPagerAdapter.notifyDataSetChanged();
                            mProgressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("currentChapter", currentChapter);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        chapterFactory = null;
        super.onDestroy();
    }

    class ReaderPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return viewPagerDatas.size();
//            if (pages == null || pages.get(currentChapter) == null) {
//                return 0;
//            } else {
//                count = 0;
//                for (int i = 0; i < pages.size(); i++) {
//                    final int key = pages.keyAt(i);
//                    count += pages.get(key).size();
//                }
//                return count;
//            }
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = View.inflate(EBookReaderActivity.this, R.layout.item_reader_page, null);
            TextView tv_book_content = (TextView) view.findViewById(R.id.tv_book_content);
            final TextView title = (TextView) view.findViewById(R.id.tv_chapter_name);
            tv_book_content.setText(viewPagerDatas.get(position).getBody());
            final int chapterId = viewPagerDatas.get(position).getChapterId();
            title.setText(mBookChapterList.get(chapterId).getTitle());
            container.addView(view);
            return view;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            super.setPrimaryItem(container, position, object);
        }
    }
}
