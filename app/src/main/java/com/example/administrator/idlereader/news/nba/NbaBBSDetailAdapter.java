package com.example.administrator.idlereader.news.nba;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.example.administrator.idlereader.base.BaseRecyclerViewAdapter;
import com.example.administrator.idlereader.bean.hupu.NbaBBSComment;

public class NbaBBSDetailAdapter extends BaseRecyclerViewAdapter<NbaBBSComment.ResultData.ListData>{
    public NbaBBSDetailAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreate(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBind(RecyclerView.ViewHolder holder, int position, NbaBBSComment.ResultData.ListData data) {

    }
}
