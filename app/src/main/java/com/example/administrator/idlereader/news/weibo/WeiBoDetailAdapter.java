package com.example.administrator.idlereader.news.weibo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.example.administrator.idlereader.base.BaseRecyclerViewAdapter;
import com.example.administrator.idlereader.bean.weibo.WeiBoDetail;

public class WeiBoDetailAdapter extends BaseRecyclerViewAdapter<WeiBoDetail.RootCommentsData> {
    public WeiBoDetailAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreate(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBind(RecyclerView.ViewHolder holder, int position, WeiBoDetail.RootCommentsData data) {

    }
}
