package com.hymane.materialhome.ui.widget.BottomSheet;

import android.support.annotation.NonNull;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hymane.materialhome.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Author   :hymane
 * Email    :hymanme@163.com
 * Create at 2016-12-27
 * Description:
 */
public class SimpleShareAdapter extends RecyclerView.Adapter {
    private List<Pair<String, Integer>> mData = new ArrayList<>();
    private OnSheetClickListener mListener;

    public SimpleShareAdapter(@NonNull List<Pair<String, Integer>> mData, @NonNull OnSheetClickListener mListener) {
        this.mData = mData;
        this.mListener = mListener;
    }

    @Override

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_share_sheet, null, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemHolder) {
            final Pair<String, Integer> pair = mData.get(holder.getAdapterPosition());
            ((ItemHolder) holder).tv_ceil_name.setText(pair.first);
            ((ItemHolder) holder).iv_ceil_icon.setImageResource(pair.second);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onClick(v, holder.getAdapterPosition());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ItemHolder extends RecyclerView.ViewHolder {
        private ImageView iv_ceil_icon;
        private TextView tv_ceil_name;

        public ItemHolder(View itemView) {
            super(itemView);
            iv_ceil_icon = (ImageView) itemView.findViewById(R.id.iv_ceil_icon);
            tv_ceil_name = (TextView) itemView.findViewById(R.id.tv_ceil_name);
        }
    }
}
