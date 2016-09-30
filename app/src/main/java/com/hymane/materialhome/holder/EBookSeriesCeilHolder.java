package com.hymane.materialhome.holder;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.AppCompatRatingBar;
import android.view.LayoutInflater;
import android.view.View;
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

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/1/26 0026
 * Description:useless
 */
public class EBookSeriesCeilHolder {
    private View mContentView;
    private ImageView iv_book_img;
    private TextView tv_title;
    private AppCompatRatingBar ratingBar_hots;
    private TextView tv_hots_num;
    private BookDetail tagBook;

    public EBookSeriesCeilHolder(BookDetail tagBook) {
        this.tagBook = tagBook;
        initView();
        initEvent();
    }

    private void initView() {
        mContentView = LayoutInflater.from(UIUtils.getContext()).inflate(R.layout.item_book_series_ceil, null, false);
        iv_book_img = (ImageView) mContentView.findViewById(R.id.iv_book_img);
        tv_title = (TextView) mContentView.findViewById(R.id.tv_title);
        ratingBar_hots = (AppCompatRatingBar) mContentView.findViewById(R.id.ratingBar_hots);
        tv_hots_num = (TextView) mContentView.findViewById(R.id.tv_hots_num);
    }

    private void initEvent() {
        Glide.with(UIUtils.getContext())
                .load(EBookUtils.getImageUrl(tagBook.getCover()))
                .into(iv_book_img);
        float ratio = 0;
        try {
            ratio = Float.parseFloat(tagBook.getRetentionRatio()) / 20;
        } catch (Exception e) {
            e.printStackTrace();
        }
        tv_title.setText(tagBook.getTitle());
        ratingBar_hots.setRating(ratio);
        tv_hots_num.setText(tagBook.getLatelyFollower() + "人在追");
        mContentView.setOnClickListener(v -> {
            Bundle b = new Bundle();
            b.putParcelable("BookDetail", tagBook);
            b.putString("bookId", tagBook.getId());
            Bitmap bitmap;
            GlideBitmapDrawable imageDrawable = (GlideBitmapDrawable) iv_book_img.getDrawable();
            if (imageDrawable != null) {
                bitmap = imageDrawable.getBitmap();
                b.putParcelable("book_img", bitmap);
            }
            Intent intent = new Intent(UIUtils.getContext(), EBookDetailActivity.class);
            intent.putExtras(b);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                if (BaseActivity.activity == null) {
                    UIUtils.startActivity(intent);
                    return;
                }
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(BaseActivity.activity, iv_book_img, "book_img");
                BaseActivity.activity.startActivity(intent, options.toBundle());
            } else {
                UIUtils.startActivity(intent);
            }
        });
    }

    public View getContentView() {
        return mContentView;
    }
}
