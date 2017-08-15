package com.github.Humilton;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Hamilton on 2017/7/20.
 */

public abstract class BaseActivity<B extends ViewDataBinding> extends AppCompatActivity {
    public B mBinding;
    public AppCompatActivity mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mContext=this;
        mBinding= DataBindingUtil.setContentView(this, initBinding());
        initView();
    }

    public abstract int initBinding();
    public abstract void initView();
}
