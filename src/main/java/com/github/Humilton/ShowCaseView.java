package com.github.Humilton;

/*
 * @Description: 遮罩控件
 * @author:      Hamilton
 * @data:        2015-09-23 19:46
 * @version:     V1.0
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ShowCaseView extends FrameLayout implements View.OnClickListener, View.OnTouchListener {
    private View contentView;
    private int mMaskColour;
    private View mContentBox;
    private TextView mContentTextView;
    private TextView mDismissButton;
    private ImageView mIVTip;

    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Paint mEraser;

    private List<View> mTargets = new ArrayList<View>();
    private boolean mShouldRender = false;

    private int mOldHeight;
    private int mOldWidth;

    public static final int BOTTON_LEFT = 0;
    public static final int BOTTON_RIGHT = 1;
    public static final int TOP_LEFT = 2;
    public static final int TOP_RIGHT = 3;

    public ShowCaseView(Context context) {
        super(context);
        init(context);
    }

    public ShowCaseView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ShowCaseView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        setWillNotDraw(false);

        setOnTouchListener(this);

        mMaskColour = Color.parseColor("#88FFFFFF");
        setVisibility(INVISIBLE);

        contentView = LayoutInflater.from(getContext()).inflate(R.layout.showcase_content, this, true);
        mContentBox = contentView.findViewById(R.id.content_box);
        mDismissButton = (TextView) contentView.findViewById(R.id.tv_dismiss);
        mIVTip = (ImageView) contentView.findViewById(R.id.iv_tip);
        mIVTip.setOnClickListener(this);
        mDismissButton.setOnClickListener(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (!mShouldRender) return;

        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        if(width <= 0 || height <= 0) return;

        // build a new canvas if needed i.e first pass or new dimensions
        if (mBitmap == null || mCanvas == null || mOldHeight != height || mOldWidth != width) {
            if (mBitmap != null) mBitmap.recycle();
            mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            mCanvas = new Canvas(mBitmap);
        }
        mOldWidth = width;
        mOldHeight = height;

        // clear canvas
        mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

        // draw solid background
        mCanvas.drawColor(mMaskColour);

        // Erase a circle
        if (mEraser == null) {
            mEraser = new Paint();
            mEraser.setColor(0xFFFFFFFF);
            mEraser.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
            mEraser.setFlags(Paint.ANTI_ALIAS_FLAG);
        }

        Iterator<View> iter = mTargets.iterator();
        while (iter.hasNext()) {
            View v = iter.next();
            int[] location = new int[2];
            v.getLocationInWindow(location);

            mCanvas.drawRect(location[0], location[1], location[0] + v.getWidth(), location[1] + v.getHeight(), mEraser);
        }

        // Draw the bitmap on our views  canvas.
        canvas.drawBitmap(mBitmap, 0, 0, null);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return true;
    }

    @Override
    public void onClick(View v) {
        removeFromWindow();
    }

    public void removeFromWindow() {
        if (getParent() != null && getParent() instanceof ViewGroup) {
            ((ViewGroup) getParent()).removeView(this);
        }

        if (mBitmap != null) {
            mBitmap.recycle();
            mBitmap = null;
        }

        mEraser = null;
        mCanvas = null;
    }

    public void addTarget(View target) {
        if(!mTargets.contains(target))
            mTargets.add(target);
    }

    private void setMaskColour(int maskColour) {
        mMaskColour = maskColour;
    }

    private void setDismissText(CharSequence dismissText) {
        if (mDismissButton != null) {
            mDismissButton.setText(dismissText);
        }
    }

    private void setDismissTextColor(int textColour) {
        if (mDismissButton != null) {
            mDismissButton.setTextColor(textColour);
        }
    }

    private void setDismissDrawable(Drawable d) {
        if (mDismissButton != null) {
            mDismissButton.setCompoundDrawables(d, null, null, null);
        }
    }

    private void setDismissBackground(int resId) {
        if (mDismissButton != null) {
            mDismissButton.setBackgroundResource(resId);
        }
    }

    private void setBottomTipSrc(int resId){
        if (mIVTip != null){
            mIVTip.setImageResource(resId);
        }
    }

    private void setShouldRender(boolean shouldRender) {
        mShouldRender = shouldRender;
    }

    public boolean show(final Activity activity) {
        ((ViewGroup) activity.getWindow().getDecorView()).addView(this);
        setShouldRender(true);
        setVisibility(VISIBLE);
        notifyOnDisplayed();
        return true;
    }

    public static class Builder {
        final ShowCaseView showcaseView;

        private final Activity activity;

        public Builder(Activity activity) {
            this.activity = activity;
            showcaseView = new ShowCaseView(activity);
        }

        public Builder(Activity activity, boolean allCancelable) {
            this.activity = activity;
            showcaseView = new ShowCaseView(activity);
            if (allCancelable) {
                showcaseView.setAllOnClick();
            }
        }

        public Builder setTarget(View target) {
            showcaseView.addTarget(target);
            return this;
        }

        public Builder addView(View v) {
            showcaseView.addView(v);
            return this;
        }

        public Builder setMaskColour(int maskColour) {
            showcaseView.setMaskColour(maskColour);
            return this;
        }

        public Builder setBottomTipRes(int resId){
            showcaseView.setBottomTipSrc(resId);
            return this;
        }

        public Builder setDismissTextColor(int textColour) {
            showcaseView.setDismissTextColor(textColour);
            return this;
        }

        public Builder setDismissText(int resId) {
            return setDismissText(activity.getString(resId));
        }

        public Builder setDismissText(CharSequence dismissText) {
            showcaseView.setDismissText(dismissText);
            return this;
        }

        public Builder setDismissDrawable(int resId) {
            Drawable d = activity.getResources().getDrawable(resId);
            d.setBounds(0, 0, d.getMinimumWidth() , d.getMinimumHeight());
            showcaseView.setDismissDrawable(d);
            return this;
        }

        public Builder setDismissDrawable(Drawable d) {
            showcaseView.setDismissDrawable(d);
            return this;
        }

        public Builder setDismissBackground(int resId) {
            showcaseView.setDismissBackground(resId);
            return this;
        }

        public Builder setListener(IShowcaseListener listener) {
            showcaseView.addShowcaseListener(listener);
            return this;
        }

        public ShowCaseView show() {
            showcaseView.show(activity);
            return showcaseView;
        }
    }

    private void setAllOnClick() {
        contentView.setOnClickListener(this);
        Log.e("allCancelable", "d");
    }

    // Define Listener
    public interface IShowcaseListener {
        void onShowcaseDisplayed(ShowCaseView showcaseView);
        void onShowcaseDismissed(ShowCaseView showcaseView);
    }

    List<IShowcaseListener> mListeners = new ArrayList<IShowcaseListener>();

    public void addShowcaseListener(IShowcaseListener showcaseListener) {
        mListeners.add(showcaseListener);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        notifyOnDismissed();
    }

    private void notifyOnDisplayed() {
        for (IShowcaseListener listener : mListeners) {
            listener.onShowcaseDisplayed(this);
        }
    }

    private void notifyOnDismissed() {
        if (mListeners != null) {
            for (IShowcaseListener listener : mListeners) {
                listener.onShowcaseDismissed(this);
            }

            mListeners.clear();
            mListeners = null;
        }

        mTargets.clear();
        mTargets = null;
    }

    public static ImageView getTipView(Context context, int[] locations, View targetView, int resId, int position) {
        return getTipView(context, locations, targetView, resId, position, 0.5f);
    }

    /**
     *
     * @param context
     * @param locations
     * @param targetView
     * @param resId
     * @param position
     * @param horizontalProportion 0.0 ~ 1.0
     * @return
     */
    public static ImageView getTipView(Context context, int[] locations, View targetView, int resId, int position, float horizontalProportion) {
        ImageView iv = new ImageView(context);
        iv.setImageResource(resId);
        LayoutParams flp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        switch (position){
            case BOTTON_LEFT:
                flp.leftMargin = (int) (locations[0] + targetView.getWidth() * horizontalProportion - iv.getDrawable().getIntrinsicWidth());
                flp.topMargin = locations[1] + targetView.getHeight();
                break;
            case BOTTON_RIGHT:
                flp.leftMargin = (int) (locations[0] + targetView.getWidth()  * horizontalProportion);
                flp.topMargin = locations[1] + targetView.getHeight();
                break;
            case TOP_LEFT:
                flp.leftMargin = (int) (locations[0] + targetView.getWidth()  * horizontalProportion - iv.getDrawable().getIntrinsicWidth());
                flp.topMargin = locations[1] - iv.getDrawable().getIntrinsicHeight();
                break;
            case TOP_RIGHT:
                flp.leftMargin = (int) (locations[0] + targetView.getWidth()  * horizontalProportion);
                flp.topMargin = locations[1] - iv.getDrawable().getIntrinsicHeight();
                break;
        }

        iv.setLayoutParams(flp);
        return iv;
    }
}
