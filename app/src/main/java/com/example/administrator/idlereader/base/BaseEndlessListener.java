package com.example.administrator.idlereader.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.administrator.idlereader.R;
import com.example.administrator.idlereader.utils.MathUtils;

public class BaseEndlessListener<T> extends RecyclerView.OnScrollListener {
    private int visibleThreshold = 1;
    private boolean mLoading = false;
    private boolean isError = false;

    private View mLoadMoreView;
    private View mErrorView;

    private ProgressBar mMoreProgressBar;
    private TextView mMoreTip;

    private BaseRecyclerViewAdapter<T> mRecyclerAdapter;
    private EndlessListener mListener;
    private RecyclerView.OnScrollListener mOnScrollListener;
    private boolean mLastItemVisible;

    public interface EndlessListener {
        void onLoadData();

        boolean shouldLoadData();
    }


    /**
     * @param adapter
     */
    public BaseEndlessListener(@NonNull Context context, BaseRecyclerViewAdapter<T> adapter) {
        mRecyclerAdapter = adapter;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        mLoadMoreView = inflater.inflate(R.layout.layout_load_more, null);
        mLoadMoreView.setLayoutParams(layoutParams);
        mErrorView = inflater.inflate(R.layout.layout_load_error, null);
        mErrorView.setOnClickListener(mErrorListener);
        mErrorView.setLayoutParams(layoutParams);

        mMoreProgressBar = (ProgressBar) mLoadMoreView.findViewById(R.id.pb_load_more);
        mMoreTip = (TextView) mLoadMoreView.findViewById(R.id.tv_load_more);
    }

    /**
     * @param listener
     */
    public void setListener(EndlessListener listener) {
        this.mListener = listener;
    }

    /**
     * @param listener
     */
    public void setParentOnScrollListener(RecyclerView.OnScrollListener listener) {
        this.mOnScrollListener = listener;
    }

    private View.OnClickListener mErrorListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            boolean error = isError;
            isError = false;
            if (mRecyclerAdapter != null) {
                mRecyclerAdapter.removeFooterView();
            }

            if (null != mListener
                    && mLastItemVisible
                    && !mLoading
                    && !isError
                    ) {
                if (error || mListener.shouldLoadData()) {
                    onLoading();
                    mListener.onLoadData();
                }
            }
        }
    };

    /**
     * @param visibleThreshold
     */
    public void setVisibleThreshold(int visibleThreshold) {
        this.visibleThreshold = visibleThreshold;
    }

    /**
     */
    public void onLoadComplete() {
        mLoading = false;
        isError = false;
        if (mRecyclerAdapter != null) {
            mRecyclerAdapter.removeFooterView();
        }
    }

    public void onLoadError() {
        /**
         * 无数据就不显示error view
         */
        if (mRecyclerAdapter != null && mRecyclerAdapter.getItemCount() == 0) {
            return;
        }
        isError = true;
        mLoading = false;
        if (mRecyclerAdapter != null) {
            mRecyclerAdapter.removeFooterView();
            mRecyclerAdapter.setFooterView(mErrorView);
        }
    }

    protected void onLoading() {
        mLoading = true;
        isError = false;
        if (mRecyclerAdapter != null) {
            mRecyclerAdapter.removeFooterView();
            mRecyclerAdapter.setFooterView(mLoadMoreView);
            mMoreProgressBar.setVisibility(View.VISIBLE);
            mMoreTip.setText("正在加载中...");
        }
    }

    public void onNomore() {
        if (mRecyclerAdapter != null) {
            mRecyclerAdapter.removeFooterView();
            mRecyclerAdapter.setFooterView(mLoadMoreView);
            mMoreProgressBar.setVisibility(View.GONE);
            if (mRecyclerAdapter.getData() != null && mRecyclerAdapter.getData().size() > 0) {
                mMoreTip.setText("没有更多数据了");
            } else {
                mMoreTip.setText("");
            }
        }
    }

    private int findLastVisibleItemPosition(RecyclerView.LayoutManager layoutManager) {
        if (layoutManager instanceof LinearLayoutManager) {
            return ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            int[] lastVisibleItemPositions = ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(null);
            return MathUtils.maximum(lastVisibleItemPositions);
        }
        return -1;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        int totalCount = mRecyclerAdapter.getItemCount();
        mLastItemVisible = totalCount > 0 &&
                (findLastVisibleItemPosition(recyclerView.getLayoutManager()) + visibleThreshold >= totalCount);
        if (null != mListener
                && mLastItemVisible
                && !mLoading
                && !isError
                && mListener.shouldLoadData()) {
            onLoading();
            mListener.onLoadData();
        }
        if (this.mOnScrollListener != null)
            this.mOnScrollListener.onScrolled(recyclerView, dx, dy);
    }

    @Override
    public void onScrollStateChanged(RecyclerView view, int scrollState) {

        if (this.mOnScrollListener != null)
            this.mOnScrollListener.onScrollStateChanged(view, scrollState);
    }
}
