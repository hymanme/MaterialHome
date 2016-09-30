package com.hymane.materialhome.ui.fragment;

import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hymane.materialhome.R;
import com.hymane.materialhome.api.presenter.impl.BookshelfPresenterImpl;
import com.hymane.materialhome.api.view.IBookListView;
import com.hymane.materialhome.bean.table.Bookshelf;
import com.hymane.materialhome.holder.BookShelfEditorHolder;
import com.hymane.materialhome.ui.activity.BaseActivity;
import com.hymane.materialhome.ui.activity.MainActivity;
import com.hymane.materialhome.ui.adapter.BookShelfAdapter;
import com.hymane.materialhome.ui.widget.RecyclerViewDecoration.StaggeredGridDecoration;
import com.hymane.materialhome.utils.common.DensityUtils;
import com.hymane.materialhome.utils.common.KeyBoardUtils;
import com.hymane.materialhome.utils.common.TimeUtils;
import com.hymane.materialhome.utils.common.UIUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import butterknife.BindView;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/7/13
 * Description:
 */

public class BookshelfFragment extends BaseFragment implements IBookListView, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.fab)
    FloatingActionButton mFab;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh_widget)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private GridLayoutManager mLayoutManager;
    private BookShelfAdapter mbookshelfAdapter;
    private List<Bookshelf> mBookshelfs;
    private BookshelfPresenterImpl mBookshelfPresenter;
    private int spanCount = 1;
    private boolean isSortable;

    private ItemTouchHelper touchHelper;

    public static BookshelfFragment mInstance;

    public static BookshelfFragment newInstance() {

        Bundle args = new Bundle();

        BookshelfFragment fragment = new BookshelfFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.bookshelf_fragment, container, false);
        if (savedInstanceState != null) {
            isSortable = savedInstanceState.getBoolean("isSortable");
        }
    }

    @Override
    protected void initEvents() {
        mToolbar.setTitle("Bookshelf");
        spanCount = getResources().getInteger(R.integer.gallery_span_count);
        mBookshelfPresenter = new BookshelfPresenterImpl(this);
        mBookshelfs = new ArrayList<>();
        mSwipeRefreshLayout.setColorSchemeResources(R.color.recycler_color1, R.color.recycler_color2,
                R.color.recycler_color3, R.color.recycler_color4);

        //设置布局管理器
        mLayoutManager = new GridLayoutManager(getActivity(), spanCount);
        mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return mbookshelfAdapter.getItemColumnSpan(position);
            }
        });
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //设置adapter
        mbookshelfAdapter = new BookShelfAdapter(getActivity(), mBookshelfs, spanCount);
        mbookshelfAdapter.setSortable(isSortable);
        mRecyclerView.setAdapter(mbookshelfAdapter);

        //设置Item增加、移除动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addOnScrollListener(new RecyclerViewScrollDetector());
        final int space = DensityUtils.dp2px(getActivity(), 4);
        mRecyclerView.addItemDecoration(new StaggeredGridDecoration(space, space, space, space, spanCount));
        mSwipeRefreshLayout.setOnRefreshListener(this);
//        mFab.setImageDrawable(AppCompatResources.getDrawable(getActivity(), R.drawable.ic_action_add_white));
        mFab.setOnClickListener(v -> {
            final BookShelfEditorHolder bookShelfHolder = new BookShelfEditorHolder(getActivity(), "", "");
            final int inputSpace = DensityUtils.dp2px(getActivity(), 16);
            final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setCancelable(false)
                    .setView(bookShelfHolder.getContentView(), inputSpace, inputSpace, inputSpace, inputSpace)
                    .setTitle(UIUtils.getContext().getString(R.string.add_bookshelf))
                    .setNegativeButton(R.string.cancel, (dialog, which) -> {
                        dialog.dismiss();
                        KeyBoardUtils.closeKeyBord(bookShelfHolder.et_bookshelf_name, getActivity());
                    })
                    .setPositiveButton(R.string.ok, (dialog, which) -> {
                        if (!bookShelfHolder.check()) {
                            Snackbar.make(BaseActivity.activity.getToolbar(), R.string.bookshelf_name_is_empty, Snackbar.LENGTH_SHORT).show();
                        } else {
                            mBookshelfPresenter.addBookshelf(bookShelfHolder.getName(), bookShelfHolder.getRemark(), TimeUtils.getCurrentTime());
                        }
                        KeyBoardUtils.closeKeyBord(bookShelfHolder.et_bookshelf_name, getActivity());
                    }).create().show();
        });
        touchHelper = new ItemTouchHelper(new SimpleItemTouchHelperCallback(ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_sort) {
            if (isSortable) {
                touchHelper.attachToRecyclerView(null);
            } else {
                touchHelper.attachToRecyclerView(mRecyclerView);
            }
            isSortable = !isSortable;
            mbookshelfAdapter.setSortable(isSortable);
            mbookshelfAdapter.notifyDataSetChanged();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void initData(boolean isSavedNull) {
        onRefresh();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((MainActivity) getActivity()).setToolbar(mToolbar);
        init();
    }

    private void init() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("isSortable", isSortable);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onRefresh() {
        mBookshelfPresenter.loadBookshelf();
    }

    private void onLoadMore() {

    }

    @Override
    public void showMessage(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgress() {
        mSwipeRefreshLayout.post(() -> mSwipeRefreshLayout.setRefreshing(true));
    }

    @Override
    public void hideProgress() {
        mSwipeRefreshLayout.post(() -> mSwipeRefreshLayout.setRefreshing(false));
    }

    @Override
    public void refreshData(Object result) {
        if (result instanceof List) {
            mBookshelfs.clear();
            mBookshelfs.addAll((List<Bookshelf>) result);
            mbookshelfAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void addData(Object result) {

    }

    class RecyclerViewScrollDetector extends RecyclerView.OnScrollListener {
        private int lastVisibleItem;
        private int mScrollThreshold = DensityUtils.dp2px(UIUtils.getContext(), 1);

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == mbookshelfAdapter.getItemCount()) {
                onLoadMore();
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            boolean isSignificantDelta = Math.abs(dy) > mScrollThreshold;

            if (isSignificantDelta) {
                if (dy > 0) {
                    mFab.hide();
                } else {
                    mFab.show();
                }
            }
            lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
        }
    }

    class SimpleItemTouchHelperCallback extends ItemTouchHelper.SimpleCallback {
        //保存被删除item信息，用于撤销操作
        BlockingQueue queue = new ArrayBlockingQueue(3);

        public SimpleItemTouchHelperCallback(int dragDirs, int swipeDirs) {
            super(dragDirs, swipeDirs);
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            if (mBookshelfs.isEmpty()) {
                return false;
            }
            //得到拖动ViewHolder的position
            int fromPosition = viewHolder.getAdapterPosition();
            //得到目标ViewHolder的position
            int toPosition = target.getAdapterPosition();
            if (fromPosition < toPosition) {
                //分别把中间所有的item的位置重新交换
                for (int i = fromPosition; i < toPosition; i++) {
                    Collections.swap(mBookshelfs, i, i + 1);
                }
            } else {
                for (int i = fromPosition; i > toPosition; i--) {
                    Collections.swap(mBookshelfs, i, i - 1);
                }
            }
            mbookshelfAdapter.notifyItemMoved(fromPosition, toPosition);
//            Log.i("TAG", "fromPosition=" + fromPosition + "||toPosition=" + toPosition);
//            long front = 0;
//            long behind = 0;
//            if (toPosition == 0) {
//                fromPosition = 0;
//            } else if (toPosition == mBookshelfs.size() - 1) {
//                behind = System.currentTimeMillis();
//            } else {
//                front = mBookshelfs.get(toPosition).getOrder();
//                behind = mBookshelfs.get(toPosition + 1).getOrder();
//            }
//            mBookshelfPresenter.orderBookshelf(mBookshelfs.get(fromPosition).getId(),front,behind);
            //返回true表示执行拖动
            return true;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            if (mBookshelfs.isEmpty()) {
                return;
            }
            int position = viewHolder.getAdapterPosition();
            final Bookshelf bookshelf = mBookshelfs.get(position);
            bookshelf.setIndex(position);
            queue.add(bookshelf);
            mBookshelfs.remove(position);
            mbookshelfAdapter.notifyItemRemoved(position);
        }

        @Override
        public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            if (mBookshelfs.isEmpty()) {
                return;
            }
            if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                //滑动时改变Item的透明度
                final float alpha = 1 - Math.abs(dX) / (float) viewHolder.itemView.getWidth();
                viewHolder.itemView.setAlpha(alpha);
                viewHolder.itemView.setTranslationX(dX);
            } else {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        }

        @Override
        public void clearView(final RecyclerView recyclerView, final RecyclerView.ViewHolder viewHolder) {
            super.clearView(recyclerView, viewHolder);
            if (!queue.isEmpty()) {
                Snackbar.make(mToolbar, R.string.delete_bookshelf_success, Snackbar.LENGTH_LONG).setAction(R.string.repeal, v -> {
                    final Bookshelf bookshelf = (Bookshelf) queue.remove();
                    mbookshelfAdapter.notifyItemInserted(bookshelf.getIndex());
                    mBookshelfs.add(bookshelf.getIndex(), bookshelf);
                    if (bookshelf.getIndex() == 0) {
                        mRecyclerView.smoothScrollToPosition(0);
                    }
                }).setCallback(new Snackbar.Callback() {
                    @Override
                    public void onDismissed(Snackbar snackbar, int event) {
                        super.onDismissed(snackbar, event);
                        if (event != DISMISS_EVENT_ACTION) {
                            final Bookshelf bookshelf = (Bookshelf) queue.remove();
                            mBookshelfPresenter.deleteBookshelf(bookshelf.getId() + "");
                        }
                    }
                }).show();
            }
        }

        @Override
        public boolean isLongPressDragEnabled() {
            return true;
        }
    }
}
