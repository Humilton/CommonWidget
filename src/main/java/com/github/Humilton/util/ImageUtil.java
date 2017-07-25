package com.github.Humilton.util;

import android.content.Context;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.github.Humilton.R;

/**
 * Created by Hamilton on 2017/7/25.
 */

public class ImageUtil {
    private static DrawableRequestBuilder<Integer> defPlaceHolder = null;

    public static synchronized DrawableRequestBuilder<Integer> defaultPlaceHolder(Context mContext) {
        if(defPlaceHolder == null) {
            defPlaceHolder = defaultPlaceHolder(mContext, R.mipmap.logo);
        }
        return defPlaceHolder;
    }

    public static synchronized DrawableRequestBuilder<Integer> defaultPlaceHolder(Context mContext, int resId) {
        return Glide.with(mContext.getApplicationContext())
                .load(resId)
                .bitmapTransform(new GlideCircleTransform(mContext.getApplicationContext(), 4));
    }
}
