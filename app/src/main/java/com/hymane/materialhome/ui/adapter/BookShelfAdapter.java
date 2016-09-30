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
import com.hymane.materialhome.utils.common.DensityUtils;
import com.hymane.materialhome.utils.common.KeyBoardUtils;
import com.hymane.materialhome.utils.common.UIUtils;

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
            holder.itemView.setTag(bookshelf.getTitle());
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
                                    KeyBoardUtils.closeKeyBord(bookShelfHolder.et_bookshelf_name, mContext);
                                }
                            })
                            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (!bookShelfHolder.check()) {
                                        Snackbar.make(BaseActivity.activity.getToolbar(), R.string.bookshelf_name_is_empty, Snackbar.LENGTH_SHORT).show();
                                        return;
                                    } else {
                                        // TODO: 2016/9/13 修改书架

                                    }
                                    KeyBoardUtils.closeKeyBord(bookShelfHolder.et_bookshelf_name, mContext);
                                }
                            }).create().show();
                    return true;
                });
            } else {
                holder.itemView.setAlpha(0.4f);
                holder.itemView.setOnLongClickListener(null);
                //分屏拖拽
//                ClipData.Item item = new ClipData.Item((CharSequence) holder.itemView.getTag());
//
//                // Create a new ClipData using the tag as a label, the plain text MIME type, and
//                // the already-created item. This will create a new ClipDescription object within the
//                // ClipData, and set its MIME type entry to "text/plain"
//                ClipData dragData = new ClipData(holder.itemView.getTag().toString(), new String[]{
//                        ClipDescription.MIMETYPE_TEXT_PLAIN}, item);
//
//                // Instantiates the drag shadow builder.
//                View.DragShadowBuilder myShadow = new MyDragShadowBuilder(holder.itemView);
//
//                // Starts the drag
//
//                holder.itemView.startDrag(dragData,  // the data to be dragged
//                        myShadow,  // the drag shadow builder
//                        null,      // no need to use local data
//                        0          // flags (not currently used, set to 0)
//                );
            }
        }
    }

//    private static class MyDragShadowBuilder extends View.DragShadowBuilder {
//
//        // The drag shadow image, defined as a drawable thing
//        private static Drawable shadow;
//
//        // Defines the constructor for myDragShadowBuilder
//        public MyDragShadowBuilder(View v) {
//
//            // Stores the View parameter passed to myDragShadowBuilder.
//            super(v);
//
//            // Creates a draggable image that will fill the Canvas provided by the system.
//            shadow = new ColorDrawable(Color.LTGRAY);
//        }
//
//        // Defines a callback that sends the drag shadow dimensions and touch point back to the
//        // system.
//        @Override
//        public void onProvideShadowMetrics(Point size, Point touch) {
//            // Defines local variables
//            int width, height;
//
//            // Sets the width of the shadow to half the width of the original View
//            width = getView().getWidth() / 2;
//
//            // Sets the height of the shadow to half the height of the original View
//            height = getView().getHeight() / 2;
//
//            // The drag shadow is a ColorDrawable. This sets its dimensions to be the same as the
//            // Canvas that the system will provide. As a result, the drag shadow will fill the
//            // Canvas.
//            shadow.setBounds(0, 0, width, height);
//
//            // Sets the size parameter's width and height values. These get back to the system
//            // through the size parameter.
//            size.set(width, height);
//
//            // Sets the touch point's position to be in the middle of the drag shadow
//            touch.set(width / 2, height / 2);
//        }
//
//        // Defines a callback that draws the drag shadow in a Canvas that the system constructs
//        // from the dimensions passed in onProvideShadowMetrics().
//        @Override
//        public void onDrawShadow(Canvas canvas) {
//
//            // Draws the ColorDrawable in the Canvas passed in from the system.
//            shadow.draw(canvas);
//        }
//    }

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
