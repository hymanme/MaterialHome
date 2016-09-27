package com.hymane.materialhome.ui.adapter;

import android.animation.ObjectAnimator;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hymane.expandtextview.ExpandTextView;
import com.hymane.materialhome.R;
import com.hymane.materialhome.bean.http.douban.BookReviewsListResponse;
import com.hymane.materialhome.bean.http.douban.BookSeriesListResponse;
import com.hymane.materialhome.bean.http.ebook.BookDetail;

import java.util.Random;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/2/22 0022
 * Description:
 */
public class EBookDetailAdapter extends RecyclerView.Adapter {
    private static final int TYPE_BOOK_INFO = 0;
    private static final int TYPE_BOOK_BRIEF = 1;
    private static final int TYPE_BOOK_COMMENT = 2;
    private static final int TYPE_BOOK_RECOMMEND = 3;

    public static final int HEADER_COUNT = 2;
    private static final int AVATAR_SIZE_DP = 24;
    private static final int ANIMATION_DURATION = 600;
    //模拟加载时间
    private static final int PROGRESS_DELAY_MIN_TIME = 300;
    private static final int PROGRESS_DELAY_SIZE_TIME = 800;

    private BookDetail mBookInfo;
    private final BookReviewsListResponse mReviewsListResponse;
    private final BookSeriesListResponse mSeriesListResponse;

    //图书出版信息是否展开
    private boolean flag;

    public EBookDetailAdapter(BookDetail bookInfo, BookReviewsListResponse reviewsListResponse, BookSeriesListResponse seriesListResponse) {
        mBookInfo = bookInfo;
        mReviewsListResponse = reviewsListResponse;
        mSeriesListResponse = seriesListResponse;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == TYPE_BOOK_INFO) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ebook_info, parent, false);
            return new BookInfoHolder(view);
        } else if (viewType == TYPE_BOOK_BRIEF) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book_brief, parent, false);
            return new BookBriefHolder(view);
        } else if (viewType == TYPE_BOOK_COMMENT) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book_comment, parent, false);
            return new BookCommentHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book_series, parent, false);
            return new BookSeriesHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof BookInfoHolder) {
            float ratio = 0;
            try {
                ratio = Float.parseFloat(mBookInfo.getRetentionRatio()) / 20;
                ratio = (float) (Math.round(ratio * 100)) / 100;
            } catch (Exception e) {
                e.printStackTrace();
            }
            ((BookInfoHolder) holder).ratingBar_hots.setRating(ratio);
            ((BookInfoHolder) holder).tv_hots_num.setText(ratio + "");
            ((BookInfoHolder) holder).tv_words_num.setText(mBookInfo.getWordCount() / 10000 + "万字");
            ((BookInfoHolder) holder).tv_book_info.setText(mBookInfo.getBookInfoString());
            ((BookInfoHolder) holder).rl_more_info.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (flag) {
                        ObjectAnimator.ofFloat(((BookInfoHolder) holder).iv_more_info, "rotation", 90, 0).start();
                        ((BookInfoHolder) holder).progressBar.setVisibility(View.GONE);
                        ((BookInfoHolder) holder).ll_publish_info.setVisibility(View.GONE);
                        flag = false;
                    } else {
                        ObjectAnimator.ofFloat(((BookInfoHolder) holder).iv_more_info, "rotation", 0, 90).start();
                        ((BookInfoHolder) holder).progressBar.setVisibility(View.VISIBLE);
                        //// TODO: 2016/9/27 may occur oom
                        new Handler() {
                            @Override
                            public void handleMessage(Message msg) {
                                super.handleMessage(msg);
                                if (flag) {
                                    ((BookInfoHolder) holder).ll_publish_info.setVisibility(View.VISIBLE);
                                    ((BookInfoHolder) holder).progressBar.setVisibility(View.GONE);
                                }
                            }
                        }.sendEmptyMessageDelayed(0, getDelayTime());
                        flag = true;
                    }
                }
            });
            ((BookInfoHolder) holder).tv_author.setText("作者：" + mBookInfo.getAuthor());
            ((BookInfoHolder) holder).tv_followers.setText("追书人数：" + mBookInfo.getLatelyFollower());
            ((BookInfoHolder) holder).retention.setText("读者留存率：" + mBookInfo.getRetentionRatio() + "%");
            ((BookInfoHolder) holder).tv_day_words.setText("日更新字数：" + mBookInfo.getSerializeWordCount());
            ((BookInfoHolder) holder).tv_chapters.setText("总章节数：" + mBookInfo.getChaptersCount());
            ((BookInfoHolder) holder).tv_publish_date.setText("更新时间：" + mBookInfo.getUpdated());
            ((BookInfoHolder) holder).tv_last_hapter.setText("最新章节：" + mBookInfo.getLastChapter());
            ((BookInfoHolder) holder).tv_serial.setText("是否连载：" + (mBookInfo.isSerial() ? "是" : "否"));
            ((BookInfoHolder) holder).tv_minor_cate.setText("次级分类：" + mBookInfo.getMajorCate());
            ((BookInfoHolder) holder).tv_creater.setText("Creater：" + mBookInfo.getCreater());
        } else if (holder instanceof BookBriefHolder) {
            if (mBookInfo.getLongIntro() != null) {
                ((BookBriefHolder) holder).etv_brief.setContent(mBookInfo.getLongIntro());
            } else if (mBookInfo.getShortIntro() != null) {
                ((BookBriefHolder) holder).etv_brief.setContent(mBookInfo.getShortIntro());
            }
        } else if (holder instanceof BookCommentHolder) {
//            List<BookReviewResponse> reviews = mReviewsListResponse.getReviews();
//            if (reviews.isEmpty()) {
//                ((BookCommentHolder) holder).itemView.setVisibility(View.GONE);
//            } else if (position == HEADER_COUNT) {
//                ((BookCommentHolder) holder).tv_comment_title.setVisibility(View.VISIBLE);
//            } else if (position == reviews.size() + 1) {
//                ((BookCommentHolder) holder).tv_more_comment.setVisibility(View.VISIBLE);
//                ((BookCommentHolder) holder).tv_more_comment.setText(UIUtils.getContext().getString(R.string.more_brief) + mReviewsListResponse.getTotal() + "条");
//                ((BookCommentHolder) holder).tv_more_comment.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent intent = new Intent(UIUtils.getContext(), BookReviewsActivity.class);
//                        intent.putExtra("bookId", mBookInfo.getId());
//                        intent.putExtra("bookName", mBookInfo.getTitle());
//                        UIUtils.startActivity(intent);
//                    }
//                });
//            }
//            Glide.with(UIUtils.getContext())
//                    .load(reviews.get(position - HEADER_COUNT).getAuthor().getAvatar())
//                    .asBitmap()
//                    .centerCrop()
//                    .into(new BitmapImageViewTarget(((BookCommentHolder) holder).iv_avatar) {
//                        @Override
//                        protected void setResource(Bitmap resource) {
//                            RoundedBitmapDrawable circularBitmapDrawable =
//                                    RoundedBitmapDrawableFactory.create(UIUtils.getContext().getResources(), resource);
//                            circularBitmapDrawable.setCircular(true);
//                            ((BookCommentHolder) holder).iv_avatar.setImageDrawable(circularBitmapDrawable);
//                        }
//                    });
//            ((BookCommentHolder) holder).tv_user_name.setText(reviews.get(position - HEADER_COUNT).getAuthor().getName());
//            if (reviews.get(position - HEADER_COUNT).getRating() != null) {
//                ((BookCommentHolder) holder).ratingBar_hots.setRating(Float.valueOf(reviews.get(position - HEADER_COUNT).getRating().getValue()));
//            }
//            ((BookCommentHolder) holder).tv_comment_content.setText(reviews.get(position - HEADER_COUNT).getSummary());
//            ((BookCommentHolder) holder).tv_favorite_num.setText(reviews.get(position - HEADER_COUNT).getVotes() + "");
//            ((BookCommentHolder) holder).tv_update_time.setText(reviews.get(position - HEADER_COUNT).getUpdated().split(" ")[0]);
        } else if (holder instanceof BookSeriesHolder) {
//            final List<BookInfoResponse> seriesBooks = mSeriesListResponse.getBooks();
//            if (seriesBooks.isEmpty()) {
//                ((BookSeriesHolder) holder).itemView.setVisibility(View.GONE);
//            } else {
//                BookSeriesCeilHolder ceilHolder;
//                ((BookSeriesHolder) holder).ll_series_content.removeAllViews();
//                for (int i = 0; i < seriesBooks.size(); i++) {
//                    ceilHolder = new BookSeriesCeilHolder(seriesBooks.get(i));
//                    ((BookSeriesHolder) holder).ll_series_content.addView(ceilHolder.getContentView());
//                }
//            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_BOOK_INFO;
        } else if (position == 1) {
            return TYPE_BOOK_BRIEF;
        } else if (position > 1 && position < (mReviewsListResponse == null ? HEADER_COUNT : (mReviewsListResponse.getReviews().size() + HEADER_COUNT))) {
            return TYPE_BOOK_COMMENT;
        } else {
            return TYPE_BOOK_RECOMMEND;
        }
    }

    @Override
    public int getItemCount() {
        int count = HEADER_COUNT;
        if (mReviewsListResponse != null) {
            count += mReviewsListResponse.getReviews().size();
        }
        if (mSeriesListResponse != null && !mSeriesListResponse.getBooks().isEmpty()) {
            count += 1;
        }
        return count;
    }

    public void setBookInfo(BookDetail bookInfo) {
        this.mBookInfo = bookInfo;
    }

    class BookInfoHolder extends RecyclerView.ViewHolder {
        private AppCompatRatingBar ratingBar_hots;
        private TextView tv_hots_num;
        private TextView tv_words_num;
        private TextView tv_book_info;
        private ImageView iv_more_info;
        private ProgressBar progressBar;
        private RelativeLayout rl_more_info;
        private LinearLayout ll_publish_info;

        private TextView tv_author;
        private TextView tv_followers;
        private TextView retention;
        private TextView tv_day_words;
        private TextView tv_chapters;
        private TextView tv_publish_date;
        private TextView tv_last_hapter;
        private TextView tv_serial;
        private TextView tv_minor_cate;
        private TextView tv_creater;


        public BookInfoHolder(View itemView) {
            super(itemView);
            ratingBar_hots = (AppCompatRatingBar) itemView.findViewById(R.id.ratingBar_hots);
            tv_hots_num = (TextView) itemView.findViewById(R.id.tv_hots_num);
            tv_words_num = (TextView) itemView.findViewById(R.id.tv_words_num);
            tv_book_info = (TextView) itemView.findViewById(R.id.tv_book_info);
            iv_more_info = (ImageView) itemView.findViewById(R.id.iv_more_info);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
            rl_more_info = (RelativeLayout) itemView.findViewById(R.id.rl_more_info);
            ll_publish_info = (LinearLayout) itemView.findViewById(R.id.ll_publish_info);

            tv_author = (TextView) itemView.findViewById(R.id.tv_author);
            tv_followers = (TextView) itemView.findViewById(R.id.tv_followers);
            retention = (TextView) itemView.findViewById(R.id.tv_retention);
            tv_day_words = (TextView) itemView.findViewById(R.id.tv_day_words);
            tv_chapters = (TextView) itemView.findViewById(R.id.tv_chapters);
            tv_publish_date = (TextView) itemView.findViewById(R.id.tv_publish_date);
            tv_last_hapter = (TextView) itemView.findViewById(R.id.tv_last_hapter);
            tv_serial = (TextView) itemView.findViewById(R.id.tv_serial);
            tv_minor_cate = (TextView) itemView.findViewById(R.id.tv_minor_cate);
            tv_creater = (TextView) itemView.findViewById(R.id.tv_creater);
        }
    }

    class BookBriefHolder extends RecyclerView.ViewHolder {
        private ExpandTextView etv_brief;

        public BookBriefHolder(View itemView) {
            super(itemView);
            etv_brief = (ExpandTextView) itemView.findViewById(R.id.etv_brief);
            etv_brief.setMinVisibleLines(3);
        }
    }

    class BookCommentHolder extends RecyclerView.ViewHolder {
        private TextView tv_comment_title;
        private ImageView iv_avatar;
        private TextView tv_user_name;
        private AppCompatRatingBar ratingBar_hots;
        private TextView tv_comment_content;
        private ImageView iv_favorite;
        private TextView tv_favorite_num;
        private TextView tv_update_time;
        private TextView tv_more_comment;

        public BookCommentHolder(View itemView) {
            super(itemView);
            tv_comment_title = (TextView) itemView.findViewById(R.id.tv_comment_title);
            iv_avatar = (ImageView) itemView.findViewById(R.id.iv_avatar);
            tv_user_name = (TextView) itemView.findViewById(R.id.tv_user_name);
            ratingBar_hots = (AppCompatRatingBar) itemView.findViewById(R.id.ratingBar_hots);
            tv_comment_content = (TextView) itemView.findViewById(R.id.tv_comment_content);
            iv_favorite = (ImageView) itemView.findViewById(R.id.iv_favorite);
            tv_favorite_num = (TextView) itemView.findViewById(R.id.tv_favorite_num);
            tv_update_time = (TextView) itemView.findViewById(R.id.tv_update_time);
            tv_more_comment = (TextView) itemView.findViewById(R.id.tv_more_comment);
        }
    }

    class BookSeriesHolder extends RecyclerView.ViewHolder {
        private HorizontalScrollView hsv_series;
        private LinearLayout ll_series_content;

        public BookSeriesHolder(View itemView) {
            super(itemView);
            hsv_series = (HorizontalScrollView) itemView.findViewById(R.id.hsv_series);
            ll_series_content = (LinearLayout) itemView.findViewById(R.id.ll_series_content);
        }
    }

    private int getDelayTime() {
        return new Random().nextInt(PROGRESS_DELAY_SIZE_TIME) + PROGRESS_DELAY_MIN_TIME;
    }
}
