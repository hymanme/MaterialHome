package com.hymane.materialhome.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hymane.materialhome.R;
import com.hymane.materialhome.bean.http.ebook.CategoryList;
import com.hymane.materialhome.common.Constant;
import com.hymane.materialhome.ui.activity.EBookCategoryDetailActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/1/12
 * Description:
 */
public class EBookCategoryAdapter extends RecyclerView.Adapter {
    private static final int TYPE_TITLE = 0;
    private static final int TYPE_ITEM_MALE = 1;
    private static final int TYPE_ITEM_FEMALE = 2;
    private List<CategoryList.CategoryBean> male;
    private List<CategoryList.CategoryBean> female;
    private Context mContext;

    public EBookCategoryAdapter(Context mContext, List<CategoryList.CategoryBean> male, List<CategoryList.CategoryBean> female) {
        this.male = male;
        this.female = female;
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //此处 inflate()第二个参数要设置成 null，不然 gridView 的 item 边距无效，全部挤在一起
        View view;
        if (viewType == TYPE_TITLE) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ebook_cat_title, null, false);
            return new TitleHolder(view);
        } else if (viewType == TYPE_ITEM_MALE) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ebook_category, null, false);
            return new MaleCategoryHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ebook_category, null, false);
            return new FemaleCategoryHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TitleHolder) {
            if (position == 0) {
                ((TitleHolder) holder).tv_title.setText(mContext.getString(R.string.male));
            } else {
                ((TitleHolder) holder).tv_title.setText(mContext.getString(R.string.female));
            }
        } else if (holder instanceof MaleCategoryHolder) {
            ((MaleCategoryHolder) holder).tv_ceil_name.setText(male.get(position - 1).getName());
            ((MaleCategoryHolder) holder).tv_ceil_book_count.setText(male.get(position - 1).getBookCount() + "本");
            ((MaleCategoryHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, EBookCategoryDetailActivity.class);
                    intent.putExtra("major", male.get(position - 1).getName());
                    intent.putExtra("gender", Constant.Gender.MALE);
                    mContext.startActivity(intent);
                }
            });
        } else if (holder instanceof FemaleCategoryHolder) {
            int index = position - male.size() - 2;
            ((FemaleCategoryHolder) holder).tv_ceil_name.setText(female.get(index).getName());
            ((FemaleCategoryHolder) holder).tv_ceil_book_count.setText(female.get(index).getBookCount() + "本");
            ((FemaleCategoryHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, EBookCategoryDetailActivity.class);
                    intent.putExtra("major", female.get(index).getName());
                    intent.putExtra("gender", Constant.Gender.FEMALE);
                    mContext.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 || position == male.size() + 1) {
            return TYPE_TITLE;
        } else if (position > male.size() + 1) {
            return TYPE_ITEM_FEMALE;
        } else {
            return TYPE_ITEM_MALE;
        }
    }

    @Override
    public int getItemCount() {
        return male.size() + female.size() + 2;
    }

    class TitleHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView tv_title;

        public TitleHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class MaleCategoryHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_ceil_name)
        TextView tv_ceil_name;
        @BindView(R.id.tv_ceil_book_count)
        TextView tv_ceil_book_count;

        public MaleCategoryHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class FemaleCategoryHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_ceil_name)
        TextView tv_ceil_name;
        @BindView(R.id.tv_ceil_book_count)
        TextView tv_ceil_book_count;

        public FemaleCategoryHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
