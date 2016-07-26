package com.hymane.materialhome.ui.widget;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/7/18
 * Description:
 */

public class HSwipeRecyclerView extends SwipeRefreshLayout implements SwipeRefreshLayout.OnRefreshListener {
    private Context mContext;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayout;
    private RecyclerView.Adapter mAdapter;
    private OnRefreshLoadListener mListener;

    public HSwipeRecyclerView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public HSwipeRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    private void init() {
        mRecyclerView = new RecyclerView(mContext);
        this.addView(mRecyclerView);
    }

    public void setHasFixedSize(boolean hasFixedSize) {
        mRecyclerView.setHasFixedSize(hasFixedSize);
    }

    public void setLayoutManager(RecyclerView.LayoutManager layout) {
        if (layout == mLayout) {
            return;
        }
        if (layout != null) {
            mRecyclerView.setLayoutManager(layout);
        }
    }

    public void addItemDecoration(RecyclerView.ItemDecoration decor) {
        if (mRecyclerView != null) {
            mRecyclerView.addItemDecoration(decor, -1);
        }
    }

    public void setItemAnimator(RecyclerView.ItemAnimator animator) {
        if (animator != null) {
            mRecyclerView.setItemAnimator(animator);
        }
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        mRecyclerView.setAdapter(adapter);
    }

    public void setOnLoadListener(OnRefreshLoadListener listener) {
        if (listener != null) {
            mListener = listener;
            this.setOnRefreshListener(this);
            mRecyclerView.addOnScrollListener(new RecyclerViewScrollDetector());
        }
    }

    @Override
    public void onRefresh() {
        mListener.onRefresh();
    }

    class RecyclerViewScrollDetector extends RecyclerView.OnScrollListener {
        private int[] lastVisibleItem;

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                if (mLayout instanceof LinearLayoutManager &&
                        (lastVisibleItem[0] + 1 == mAdapter.getItemCount())) {
                    mListener.onLoadMore();
                } else if (mLayout instanceof GridLayoutManager && (lastVisibleItem[0] + ((GridLayoutManager) mLayout).getSpanCount() >= mAdapter.getItemCount())) {
                    mListener.onLoadMore();
                } else if (mLayout instanceof StaggeredGridLayoutManager && (lastVisibleItem[0] + ((StaggeredGridLayoutManager) mLayout).getSpanCount() >= mAdapter.getItemCount())) {
                    mListener.onLoadMore();
                }
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (mLayout instanceof LinearLayoutManager) {
                lastVisibleItem[0] = ((LinearLayoutManager) mLayout).findLastVisibleItemPosition();
            } else if (mLayout instanceof GridLayoutManager) {
                lastVisibleItem[0] = ((GridLayoutManager) mLayout).findLastVisibleItemPosition();
            } else if (mLayout instanceof StaggeredGridLayoutManager) {
                lastVisibleItem = ((StaggeredGridLayoutManager) mLayout).findLastVisibleItemPositions(null);
            }
        }
    }

    public interface OnRefreshLoadListener {
        void onRefresh();

        void onLoadMore();
    }
}
