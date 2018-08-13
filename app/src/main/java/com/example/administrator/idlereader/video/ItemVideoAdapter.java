package com.example.administrator.idlereader.video;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.administrator.idlereader.R;
import com.example.administrator.idlereader.bean.TodayContentBean;

import java.util.ArrayList;
import java.util.List;

import cn.jzvd.JZVideoPlayerStandard;


public class ItemVideoAdapter extends RecyclerView.Adapter<ItemVideoAdapter.ViewHolder> {

    private List<TodayContentBean> mTodayContentBeans = new ArrayList<TodayContentBean>();
    private List<String> mVideoList = new ArrayList<>();

    private Context context;

    public ItemVideoAdapter(Context context) {
        this.context = context;
    }
    public void setData(List<TodayContentBean> objects,List<String> videoList) {
        this.mTodayContentBeans = objects;
        this.mVideoList = videoList;
        notifyDataSetChanged();
    }

    public void addData(List<TodayContentBean> objects, List<String> videoList) {
        mTodayContentBeans.addAll(objects);
        mVideoList.addAll(videoList);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_video, parent, false);
        return new ItemVideoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TodayContentBean bean=mTodayContentBeans.get(position);
        if (!TextUtils.isEmpty(bean.getTitle())){
            holder.videoPlayer.setUp(mVideoList.get(position),
                    JZVideoPlayerStandard.SCREEN_WINDOW_LIST,
                    bean.getTitle());
        }


        Glide.with(context)
                .load(bean.getVideo_detail_info().getDetail_video_large_image().getUrl())
                .into( holder.videoPlayer.thumbImageView);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mTodayContentBeans.size();
    }


    protected class ViewHolder extends RecyclerView.ViewHolder{
        private JZVideoPlayerStandard videoPlayer;

        public ViewHolder(View view) {
            super(view);
            videoPlayer = (JZVideoPlayerStandard) view.findViewById(R.id.video_player);
        }
    }
}
