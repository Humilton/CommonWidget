package com.github.Humilton;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.github.Humilton.databinding.SampleBinding;

import java.util.List;

/**
 * Created by Hamilton on 2017/7/20.
 */

public class SampleActivity extends BaseActivity<SampleBinding> implements ViewTreeObserver.OnGlobalLayoutListener {
    private int dp45 = 0, dp3 = 0;
    String[] icon = new String[]{"http://vbox-bucket-001.oss-cn-shanghai.aliyuncs.com/15000/BYGHeadUrl","http://vbox-bucket-001.oss-cn-shanghai.aliyuncs.com/15093/BYGHeadUrl"};
    private static final String FIRST_ENTER_APP = "prefFirstEnterApp";
    private View rootView, targetView;

    @Override
    public int initBinding() {
        return R.layout.sample;
    }

    @Override
    public void initView() {
        dp45 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 45, mContext.getResources().getDisplayMetrics());
        dp3 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, mContext.getResources().getDisplayMetrics());

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
                .setDismissText("Hi, Coder");

        builder.setListener(new ShowCaseView.IShowcaseListener() {
            @Override
            public void onShowcaseDisplayed(ShowCaseView showcaseView) {
            }

            @Override
            public void onShowcaseDismissed(ShowCaseView showcaseView) {
//                if (this == null) return;

//                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(SampleActivity.this);
//                sp.edit().putBoolean(FIRST_ENTER_APP, false).commit();
            }
        });
        builder.show();
    }
}
