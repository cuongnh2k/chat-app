package com.vn.chat.common.utils;

import android.app.Activity;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.vn.chat.R;
import com.vn.chat.common.DataStatic;

public class ImageUtils {

    public static void loadUrl(Activity context, ImageView imv, String url) {
        if (url != null && !url.equalsIgnoreCase("null") && url.length() > 0) {
            Glide.with(context)
                    .load(url)
                    .error(R.drawable.no_image_available)
                    .thumbnail(0.2f)
                    .dontAnimate()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .into(imv);
        }else{
            Glide.with(context)
                    .load(R.drawable.no_image)
                    .thumbnail(0.2f)
                    .dontAnimate()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .into(imv);
        }
    }

    public static void loadUri(Activity context, ImageView imv, Uri uri){
        Glide.with(context)
                .load(uri)
                .into(imv);
    }
}
