package com.hymane.materialhome.ui.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hymane.materialhome.R;
import com.hymane.materialhome.ui.activity.CategoryDetailActivity;
import com.hymane.materialhome.utils.common.UIUtils;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/1/12
 * Description:
 */
public class CategoryAdapter extends RecyclerView.Adapter {
    private String[] mCategory;

    public CategoryAdapter() {
        mCategory = UIUtils.getContext().getResources().getStringArray(R.array.book_category);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //此处 inflate()第二个参数要设置成 null，不然 gridView 的 item 边距无效，全部挤在一起
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, null, false);
        return new CategoryHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((CategoryHolder) holder).iv_category_icon.setImageResource(getCategoryIcon(holder.getAdapterPosition()));
        ((CategoryHolder) holder).tv_ceil_name.setText(mCategory[holder.getAdapterPosition()]);
        ((CategoryHolder) holder).ll_ceil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UIUtils.getContext(), CategoryDetailActivity.class);
                intent.putExtra("index", position);
                intent.putExtra("title", mCategory[holder.getAdapterPosition()]);
                UIUtils.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCategory.length;
    }

    class CategoryHolder extends RecyclerView.ViewHolder {
        private ImageView iv_category_icon;
        private LinearLayout ll_ceil;
        private TextView tv_ceil_name;

        public CategoryHolder(View itemView) {
            super(itemView);
            iv_category_icon = (ImageView) itemView.findViewById(R.id.iv_category_icon);
            ll_ceil = (LinearLayout) itemView.findViewById(R.id.ll_ceil);
            tv_ceil_name = (TextView) itemView.findViewById(R.id.tv_ceil_name);
        }
    }

    private static int getCategoryIcon(int index) {
        switch (index) {
            case 0:
                return R.mipmap.ic_category_literature;
            case 1:
                return R.mipmap.ic_category_popular;
            case 2:
                return R.mipmap.ic_category_culture;
            case 3:
                return R.mipmap.ic_category_life;
            case 4:
                return R.mipmap.ic_category_management;
            case 5:
                return R.mipmap.ic_category_technology;
            case 6:
                return R.mipmap.ic_category_country;
            case 7:
                return R.mipmap.ic_category_subject;
            case 8:
                return R.mipmap.ic_category_author;
            case 9:
                return R.mipmap.ic_category_publisher;
            case 10:
                return R.mipmap.ic_category_throng;
            case 11:
                return R.mipmap.ic_category_religion;
            case 12:
                return R.mipmap.ic_category_other;
            default:
                return R.mipmap.ic_category_literature;
        }
    }
}
