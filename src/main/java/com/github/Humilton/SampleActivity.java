package com.github.Humilton;

import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.github.Humilton.databinding.SampleBinding;

import java.util.List;

/**
 * Created by Hamilton on 2017/7/20.
 */

public class SampleActivity extends BaseActivity<SampleBinding> {
    private int dp45 = 0, dp3 = 0;
    String[] icon = new String[]{"http://vbox-bucket-001.oss-cn-shanghai.aliyuncs.com/15000/BYGHeadUrl","http://vbox-bucket-001.oss-cn-shanghai.aliyuncs.com/15093/BYGHeadUrl"};

    @Override
    public int initBinding() {
        return R.layout.sample;
    }

    @Override
    public void initView() {
        dp45 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 45, mContext.getResources().getDisplayMetrics());
        dp3 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, mContext.getResources().getDisplayMetrics());

        handleUserList(mBinding.mainContainer, icon);
    }

    private void handleUserList(LinearLayout userContainer, String[] userList) {
        userContainer.removeAllViews();
        int height = dp45;
        for(String url : userList) {
            ImageView v = new ImageView(mContext);
            v.setLayoutParams(new LinearLayout.LayoutParams(height,height));
            v.setPadding(dp3,0,dp3,0);
            //v.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Glide.with(mContext).load(url).transform(new GlideCircleTransform(mContext)).into(v);
            userContainer.addView(v);
        }
    }
}
