package com.github.Humilton;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.databinding.BindingAdapter;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Hamilton on 2017/7/10.
 */

public class HorizonClickView extends RelativeLayout {
    protected ImageView leftImage, rightImage;
    protected TextView leftText, rightText;
    protected RelativeLayout titleLayout;

    public HorizonClickView(Context context) {
        super(context);
        init(context, null);
    }

    public HorizonClickView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public HorizonClickView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public HorizonClickView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    protected int getLayout() {
        return R.layout.layout_horizon_click_view;
    }

    private void init(Context context, AttributeSet attrs){
        LayoutInflater.from(context).inflate(getLayout(), this);
        leftImage = (ImageView) findViewById(R.id.left_image);
        rightImage = (ImageView) findViewById(R.id.right_image);
        leftText = (TextView) findViewById(R.id.left_txt);
        rightText = (TextView) findViewById(R.id.right_txt);
        titleLayout = (RelativeLayout) findViewById(R.id.root);

        this.setBackgroundColor(Color.WHITE);

        parseStyle(context, attrs);
    }

    protected void parseStyle(Context context, AttributeSet attrs){
        if(attrs != null){
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.HorizonClickView);
            String leftTxt = ta.getString(R.styleable.HorizonClickView_leftTxt);
            leftText.setText(leftTxt);
            String rightTxt = ta.getString(R.styleable.HorizonClickView_rightTxt);
            rightText.setText(rightTxt);

            Drawable leftDrawable = ta.getDrawable(R.styleable.HorizonClickView_leftImage);
            if (null != leftDrawable) {
                leftImage.setImageDrawable(leftDrawable);
            }

            int leftMargin = (int) ta.getDimension(R.styleable.HorizonClickView_leftMargin, -1f);
            if(leftMargin >= 0) {
                RelativeLayout.LayoutParams rl = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                rl.setMargins(leftMargin, 0 , 0 , 0);
                rl.addRule(RelativeLayout.CENTER_VERTICAL);
                leftImage.setLayoutParams(rl);
            }

            int rightMargin = (int) ta.getDimension(R.styleable.HorizonClickView_rightMargin, -1f);
            if(rightMargin >= 0) {
                RelativeLayout.LayoutParams rl = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                rl.setMargins(0, 0 , rightMargin , 0);
                rl.addRule(RelativeLayout.CENTER_VERTICAL);
                rightImage.setLayoutParams(rl);
            }

            boolean rightImageInvisible = ta.getBoolean(R.styleable.HorizonClickView_rightImageInvisible, false);
            if(rightImageInvisible) {
                rightImage.setVisibility(GONE);
                RelativeLayout.LayoutParams lp = (LayoutParams) rightText.getLayoutParams();
                lp.rightMargin = 40;
                rightText.setLayoutParams(lp);
            }

            Drawable rightDrawable = ta.getDrawable(R.styleable.HorizonClickView_rightImage);
            if (null != rightDrawable) {
                rightImage.setImageDrawable(rightDrawable);
            }

            int leftPadding = (int) ta.getDimension(R.styleable.HorizonClickView_leftPadding, 0f);
            int rightPadding = (int) ta.getDimension(R.styleable.HorizonClickView_rightPadding, 0f);
            if(leftPadding != 0) leftText.setPadding(leftPadding, 0, 0, 0);
            if(rightPadding != 0) rightText.setPadding(0, 0, rightPadding, 0);

            Drawable background = ta.getDrawable(R.styleable.HorizonClickView_horizonBackground);
            if(null != background) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    titleLayout.setBackground(background);
                }
                else {
                    titleLayout.setBackgroundDrawable(background);
                }
            }

            String activityName = ta.getString(R.styleable.HorizonClickView_jumpActivity);
            if(!TextUtils.isEmpty(activityName)) {
                this.tryJumpActivityOnClick(activityName);
            }

            ta.recycle();
        }
    }

    private void tryJumpActivityOnClick(final String className) {
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Class cls = Class.forName(className);
                    Intent i = new Intent(getContext(), cls);
                    getContext().startActivity(i);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void setTitle(CharSequence str) { this.leftText.setText(str); }
    public void setValue(CharSequence str) { this.rightText.setText(str); }
    public String getTitle() { return this.leftText.getText().toString(); }
    public String getValue() { return this.rightText.getText().toString(); }

    @BindingAdapter({"leftTxt"})
    public static void setLeftTxt(final HorizonClickView v, CharSequence str) { v.setTitle(str); }
    @BindingAdapter({"rightTxt"})
    public static void setRightTxt(final HorizonClickView v, CharSequence str) { v.setValue(str); }
}
