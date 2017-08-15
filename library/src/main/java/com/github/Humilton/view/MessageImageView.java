package com.github.Humilton.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.support.v7.widget.AppCompatImageView;
import android.widget.LinearLayout;

/**
 * Created by Hamilton on 2017/7/5.
 * if Bitmap width > height, width take 3/4 of parent;
 * if Bitmap width < height, width take half of parent;
 */

public class MessageImageView extends AppCompatImageView {
    private int bmpW = 0, bmpH = 0;
    public MessageImageView(Context context) {
        super(context);
    }

    public MessageImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MessageImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if(bmpW != 0) {
            int w = this.getMeasuredWidth();
            int h = (int) (1.0f * bmpH / bmpW * w);
            setMeasuredDimension(w, h);
        }
    }

    @Override
    public void setImageDrawable(@Nullable Drawable drawable) {
        bmpW = drawable.getIntrinsicWidth();
        bmpH = drawable.getIntrinsicHeight();
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) this.getLayoutParams();
        lp.weight = (bmpW > bmpH) ? 3.0f : 2.0f;
        this.setLayoutParams(lp);

        super.setImageDrawable(drawable);
    }
}
