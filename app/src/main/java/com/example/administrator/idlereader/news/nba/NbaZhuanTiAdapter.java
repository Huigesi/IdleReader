package com.example.administrator.idlereader.news.nba;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.idlereader.R;
import com.example.administrator.idlereader.base.BaseRecyclerViewAdapter;
import com.example.administrator.idlereader.bean.hupu.NbaZhuanti;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NbaZhuanTiAdapter extends BaseRecyclerViewAdapter<NbaZhuanti.ResultData.GroupsData> {
    public NbaNewsAdapter mNbaNewsAdapter;

    public NbaZhuanTiAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreate(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_nba_zhuanti, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBind(RecyclerView.ViewHolder holder, int position, NbaZhuanti.ResultData.GroupsData data) {
        if (holder instanceof ViewHolder) {
            ((ViewHolder) holder).mTvNbaZhuantiTitle.setText(data.getTitle());
            mNbaNewsAdapter = new NbaNewsAdapter(mContext);
            mNbaNewsAdapter.setData(data.getNews(),true);
            ((ViewHolder) holder).mRvNbaZhuantiNews.setAdapter(mNbaNewsAdapter);
            ((ViewHolder) holder).mRvNbaZhuantiNews.setLayoutManager(new LinearLayoutManager(mContext));
        }
    }

    static class ViewHolder extends Holder{
        @BindView(R.id.tv_nba_zhuanti_title)
        TextView mTvNbaZhuantiTitle;
        @BindView(R.id.rv_nba_zhuanti_news)
        RecyclerView mRvNbaZhuantiNews;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
