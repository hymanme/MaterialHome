package com.hymane.materialhome.ui.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.flyco.labelview.LabelView;
import com.hymane.materialhome.R;
import com.hymane.materialhome.bean.table.Bookshelf;
import com.hymane.materialhome.holder.BookShelfEditorHolder;
import com.hymane.materialhome.ui.activity.BaseActivity;
import com.hymane.materialhome.utils.DensityUtils;
import com.hymane.materialhome.utils.UIUtils;

import java.util.List;
import java.util.Random;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/1/9 0009
 */
public class BookShelfAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_EMPTY = 0;
    private static final int TYPE_DEFAULT = 1;
    private final List<Bookshelf> bookshelfs;
    private Context mContext;
    private int columns;
    private boolean isSortable;

    public BookShelfAdapter(Context context, List<Bookshelf> responses, int columns) {
        this.bookshelfs = responses;
        this.columns = columns;
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == TYPE_DEFAULT) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book_shelf, parent, false);
            return new BookShelfHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_empty, parent, false);
            return new EmptyHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (bookshelfs == null || bookshelfs.isEmpty()) {
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
        if (holder instanceof BookShelfHolder) {
            final Bookshelf bookshelf = bookshelfs.get(position);
            Random random = new Random();   //创建随机颜色
            int red = random.nextInt(200) + 22;
            int green = random.nextInt(200) + 22;
            int blue = random.nextInt(200) + 22;
            int color = Color.rgb(red, green, blue);
            ((BookShelfHolder) holder).rl_content.setBackgroundColor(color);
            ((BookShelfHolder) holder).labelView.setText("0本");
            ((BookShelfHolder) holder).tv_bookshelf_name.setText(bookshelf.getTitle());
            ((BookShelfHolder) holder).tv_remark.setText(bookshelf.getRemark());
            ((BookShelfHolder) holder).tv_create_time.setText(bookshelf.getCreateTime());
            ((BookShelfHolder) holder).itemView.setOnClickListener(v -> Toast.makeText(mContext, "click", Toast.LENGTH_SHORT).show());
            if (!isSortable) {
                holder.itemView.setAlpha(1.0f);
                ((BookShelfHolder) holder).itemView.setOnLongClickListener(v -> {
                    final BookShelfEditorHolder bookShelfHolder = new BookShelfEditorHolder(mContext, bookshelfs.get(position).getTitle(), bookshelfs.get(position).getRemark());
                    final int space = DensityUtils.dp2px(UIUtils.getContext(), 16);
                    final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setCancelable(false)
                            .setView(bookShelfHolder.getContentView(), space, space, space, space)
                            .setTitle(UIUtils.getContext().getString(R.string.edit_bookshelf))
                            .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (!bookShelfHolder.check()) {
                                        Snackbar.make(BaseActivity.activity.getToolbar(), R.string.bookshelf_name_is_empty, Snackbar.LENGTH_SHORT).show();
                                        return;
                                    } else {

                                    }
                                }
                            }).create().show();
                    return true;
                });
            } else {
                holder.itemView.setAlpha(0.4f);
                holder.itemView.setOnLongClickListener(null);
            }
        }
    }

    public boolean isSortable() {
        return isSortable;
    }

    public void setSortable(boolean sortable) {
        isSortable = sortable;
    }

    @Override
    public int getItemCount() {
        if (bookshelfs.isEmpty()) {
            return 1;
        }
        return bookshelfs.size();
    }

    class BookShelfHolder extends RecyclerView.ViewHolder {
        private RelativeLayout rl_content;
        private LabelView labelView;
        private TextView tv_bookshelf_name;
        private TextView tv_remark;
        private TextView tv_create_time;

        public BookShelfHolder(View itemView) {
            super(itemView);
            rl_content = (RelativeLayout) itemView.findViewById(R.id.rl_content);
            labelView = (LabelView) itemView.findViewById(R.id.label_layout);
            tv_bookshelf_name = (TextView) itemView.findViewById(R.id.tv_bookshelf_name);
            tv_remark = (TextView) itemView.findViewById(R.id.tv_remark);
            tv_create_time = (TextView) itemView.findViewById(R.id.tv_create_time);
        }
    }

    class EmptyHolder extends RecyclerView.ViewHolder {
        public EmptyHolder(View itemView) {
            super(itemView);
        }
    }
}
