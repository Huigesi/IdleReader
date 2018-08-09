package com.example.administrator.idlereader.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.administrator.idlereader.R;

public class GlideUtils {

    public static void load(Context context, String url, ImageView view,int weight,int height) {

        Glide.with(context)
                .load(url)
                .asBitmap()
                .centerCrop()
                .placeholder(R.drawable.picture)
                .skipMemoryCache(false)
                .error(R.drawable.picture_error)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .override(weight,height)
                .into(view);
    }
    public static void load(Context context, String url, ImageView view) {

        Glide.with(context)
                .load(url)
                .asBitmap()
                .centerCrop()
                .placeholder(R.drawable.picture)
                .skipMemoryCache(false)
                .error(R.drawable.picture_error)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(view);
    }
}
