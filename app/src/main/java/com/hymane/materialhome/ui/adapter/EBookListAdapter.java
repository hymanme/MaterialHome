package com.hymane.materialhome.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.hymane.materialhome.R;
import com.hymane.materialhome.bean.http.ebook.BookDetail;
import com.hymane.materialhome.ui.activity.BaseActivity;
import com.hymane.materialhome.ui.activity.EBookDetailActivity;
import com.hymane.materialhome.utils.EBookUtils;
import com.hymane.materialhome.utils.common.UIUtils;

import java.util.List;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/1/9 0009
 */
public class EBookListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_EMPTY = 0;
    private static final int TYPE_DEFAULT = 1;
    private final List<BookDetail> bookInfoResponses;
    private Context mContext;
    private int columns;

    public EBookListAdapter(Context context, List<BookDetail> responses, int columns) {
        this.bookInfoResponses = responses;
        this.columns = columns;
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == TYPE_DEFAULT) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book_list, parent, false);
            return new EBookListHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_empty, parent, false);
            return new EmptyHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (bookInfoResponses == null || bookInfoResponses.isEmpty()) {
            return TYPE_EMPTY;
        } else {
            return TYPE_DEFAULT;
        }
    }

    public int getItemColumnSpan(int position) {
        switch (getItemViewType(position)) {
            case TYPE_DEFAULT:
                return 1;
            default:
                return columns;
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof EBookListHolder) {
            final BookDetail bookInfo = bookInfoResponses.get(position);
            Glide.with(mContext)
                    .load(EBookUtils.getImageUrl(bookInfo.getCover()))
                    .into(((EBookListHolder) holder).iv_book_img);
            ((EBookListHolder) holder).tv_book_title.setText(bookInfo.getTitle());
            float ratio = 0;
            try {
                ratio = Float.parseFloat(bookInfo.getRetentionRatio()) / 20;
            } catch (Exception e) {
                e.printStackTrace();
            }
            ((EBookListHolder) holder).ratingBar_hots.setRating(ratio);
            ((EBookListHolder) holder).tv_hots_num.setText(bookInfo.getLatelyFollower() + "人在追");
            ((EBookListHolder) holder).tv_book_info.setText(bookInfo.getBookInfoString());
            ((EBookListHolder) holder).tv_book_description.setText("\u3000" + bookInfo.getShortIntro());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle b = new Bundle();
                    b.putParcelable("BookDetail", bookInfo);
                    b.putString("bookId", bookInfo.getId());
                    Bitmap bitmap;
                    GlideBitmapDrawable imageDrawable = (GlideBitmapDrawable) ((EBookListHolder) holder).iv_book_img.getDrawable();
                    if (imageDrawable != null) {
                        bitmap = imageDrawable.getBitmap();
                        b.putParcelable("book_img", bitmap);
                    }
                    Intent intent = new Intent(mContext, EBookDetailActivity.class);
                    intent.putExtras(b);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        if (BaseActivity.activity == null) {
                            UIUtils.startActivity(intent);
                            return;
                        }
                        ActivityOptionsCompat options = ActivityOptionsCompat.
                                makeSceneTransitionAnimation(BaseActivity.activity, ((EBookListHolder) holder).iv_book_img, "book_img");
                        BaseActivity.activity.startActivity(intent, options.toBundle());
                    } else {
                        UIUtils.startActivity(intent);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (bookInfoResponses.isEmpty()) {
            return 1;
        }
        return bookInfoResponses.size();
    }

    class EBookListHolder extends RecyclerView.ViewHolder {

        private final ImageView iv_book_img;
        private final TextView tv_book_title;
        private final AppCompatRatingBar ratingBar_hots;
        private final TextView tv_hots_num;
        private final TextView tv_book_info;
        private final TextView tv_book_description;

        public EBookListHolder(View itemView) {
            super(itemView);
            iv_book_img = (ImageView) itemView.findViewById(R.id.iv_book_img);
            tv_book_title = (TextView) itemView.findViewById(R.id.tv_book_title);
            ratingBar_hots = (AppCompatRatingBar) itemView.findViewById(R.id.ratingBar_hots);
            tv_hots_num = (TextView) itemView.findViewById(R.id.tv_hots_num);
            tv_book_info = (TextView) itemView.findViewById(R.id.tv_book_info);
            tv_book_description = (TextView) itemView.findViewById(R.id.tv_book_description);
        }
    }

    class EmptyHolder extends RecyclerView.ViewHolder {
        public EmptyHolder(View itemView) {
            super(itemView);
        }
    }
}
