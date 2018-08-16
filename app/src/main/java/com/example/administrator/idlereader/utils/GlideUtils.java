package com.example.administrator.idlereader.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.administrator.idlereader.R;

public class GlideUtils {

    public static void load(Context context, String url, ImageView view, int weight, int height) {

        Glide.with(context)
                .load(url)
                .asBitmap()
                .centerCrop()
                .placeholder(R.drawable.picture)
                .skipMemoryCache(false)
                .error(R.drawable.picture_error)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .override(weight, height)
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
    public static void loadAuto(final Context context, String url, final ImageView view, final int weight) {
        Glide.with(context)
                .load(url)
                .asBitmap()
                .centerCrop()
                .placeholder(R.drawable.picture)
                .skipMemoryCache(false)
                .error(R.drawable.picture_error)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        int imageWidth = resource.getWidth();
                        int imageHeight = resource.getHeight();
                        int width = Resolution.getScreenPixWidth(context);//固定宽度
                        //宽度固定,然后根据原始宽高比得到此固定宽度需要的高度
                        int height = width * imageHeight / imageWidth;
                        ViewGroup.LayoutParams para = view.getLayoutParams();
                        para.height = height;
                        para.width = width;
                        view.setImageBitmap(resource);
                    }
                });
    }
}
