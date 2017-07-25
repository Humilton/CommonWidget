package com.github.Humilton;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.Humilton.databinding.SampleBinding;
import com.github.Humilton.util.GlideCircleTransform;
import com.github.Humilton.util.ImageUtil;
import com.github.Humilton.view.ShowCaseView;

/**
 * Created by Hamilton on 2017/7/20.
 */

public class SampleActivity extends BaseActivity<SampleBinding> implements ViewTreeObserver.OnGlobalLayoutListener, View.OnClickListener {
    private int dp45 = 0, dp3 = 0;
    String[] icon = new String[]{"http://vbox-bucket-001.oss-cn-shanghai.aliyuncs.com/15000/BYGHeadUrl","http://vbox-bucket-001.oss-cn-shanghai.aliyuncs.com/15093/BYGHeadUrl"};
    private static final String FIRST_ENTER_APP = "prefFirstEnterApp";
    private View rootView, targetView;
    private LayoutInflater mInflater;
    private PopupWindow mDropdown;

    @Override
    public int initBinding() {
        return R.layout.sample;
    }

    @Override
    public void initView() {
        dp45 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 45, mContext.getResources().getDisplayMetrics());
        dp3 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, mContext.getResources().getDisplayMetrics());

        mBinding.titleBar.setTitle("这是标题！");

        handleUserList(mBinding.mainContainer, icon);

        mBinding.charImg1.setText("测试");
        mBinding.charImg2.setText("Test long text");
        mBinding.charImg3.setText("百");
        mBinding.charImg4.setText("家");
        mBinding.charImg5.setText("姓");

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isFirstEnterApp = sp.getBoolean(FIRST_ENTER_APP, true);

        rootView = ((ViewGroup) (getWindow().getDecorView().findViewById(android.R.id.content))).getChildAt(0);
        targetView = mBinding.caseViewImg;
        if (isFirstEnterApp) {
            rootView.getViewTreeObserver().addOnGlobalLayoutListener(this);
        }

        mBinding.caseViewImg.setOnClickListener(this);

        //
        new Thread(initRunnable(), "InitRunnable").start();
        String url1 = "https://ak3.picdn.net/shutterstock/videos/3514871/thumb/1.jpg";
        Glide.with(mContext).load(url1).placeholder(R.mipmap.holder).into(mBinding.imgContent);
        Glide.with(mContext).load( "http://BadUrl/" ).thumbnail( ImageUtil.defaultPlaceHolder(mContext) ).transform(new GlideCircleTransform(mContext)).into(mBinding.imgAdmin);
    }

    private Runnable initRunnable() {
        return new Runnable() {
            @Override
            public void run() {
                Glide.get(mContext).clearDiskCache();
            }
        };
    }

    private void handleUserList(LinearLayout userContainer, String[] userList) {
        userContainer.removeAllViews();
        int height = dp45;
        for(String url : userList) {
            ImageView v = new ImageView(mContext);
            v.setLayoutParams(new LinearLayout.LayoutParams(height,height));
            v.setPadding(dp3,0,dp3,0);
            Glide.with(mContext).load(url).transform(new GlideCircleTransform(mContext)).into(v);
            userContainer.addView(v);
        }
    }

    @Override
    public void onGlobalLayout() {
        if (Build.VERSION.SDK_INT < 16) {
            rootView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
        } else {
            rootView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        }

        int[] locations = new int[2];
        targetView.getLocationOnScreen(locations);
        showShowCase(locations);
    }

    private void showShowCase(int[] locations) {
        ShowCaseView.Builder builder = new ShowCaseView.Builder(this)
                .setMaskColour(Color.parseColor("#AA000000"))
                .setTarget(targetView)
                .addView(ShowCaseView.getTipView(this, locations, targetView, R.mipmap.guide_partner, ShowCaseView.BOTTON_LEFT, .6f));

        builder.setDismissDrawable(R.mipmap.guide_i_know)
                .setDismissText(R.string.can_click);

        builder.setListener(new ShowCaseView.IShowcaseListener() {
            @Override
            public void onShowcaseDisplayed(ShowCaseView showcaseView) {
            }

            @Override
            public void onShowcaseDismissed(ShowCaseView showcaseView) {
                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(SampleActivity.this);
                sp.edit().putBoolean(FIRST_ENTER_APP, false).commit();
            }
        });
        builder.show();
    }

    @Override
    public void onClick(View v) {
        if(mDropdown != null)  mDropdown.dismiss();

        if(v == mBinding.caseViewImg) {
            initiatePopupWindow();
        }
        else if (v.getId() == R.id.ItemA) {
            String shareUrl = mContext.getResources().getString(R.string.share_url);
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, shareUrl);
            sendIntent.setType("text/plain");
            mContext.startActivity(Intent.createChooser(sendIntent, mContext.getResources().getText(R.string.send_to)));
        }
        else if (v.getId() == R.id.ItemB) {
            Intent swipeIntent = new Intent();
            swipeIntent.setClass(mContext, RefreshActivity.class);
            mContext.startActivity(swipeIntent);
        }
    }

    private PopupWindow initiatePopupWindow() {
        try {
            mInflater = (LayoutInflater) getApplicationContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = mInflater.inflate(R.layout.layout_popup_menu, null);

            //If you want to add any listeners to your textviews, these are two //textviews.
            final TextView itema = (TextView) layout.findViewById(R.id.ItemA);
            final TextView itemb = (TextView) layout.findViewById(R.id.ItemB);
            itema.setOnClickListener(this);
            itemb.setOnClickListener(this);

            layout.measure(View.MeasureSpec.UNSPECIFIED,
                    View.MeasureSpec.UNSPECIFIED);
            mDropdown = new PopupWindow(layout, FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT,true);
            Drawable background = getResources().getDrawable(android.R.drawable.editbox_dropdown_dark_frame);
            mDropdown.setBackgroundDrawable(background);
            mDropdown.showAsDropDown(mBinding.caseViewImg, 5, 5);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return mDropdown;

    }
}
