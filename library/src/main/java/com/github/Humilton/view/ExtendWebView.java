package com.github.Humilton.view;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.view.Window;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.content.DialogInterface.OnClickListener;
import android.webkit.WebViewClient;

/**
 * Created by Hamilton on 2017/6/29.
 */

public class ExtendWebView extends WebView {
    public ExtendWebView(Context context) {
        super(context);
        init();
    }

    public ExtendWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ExtendWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ExtendWebView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        WebSettings settings = this.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setGeolocationEnabled(true);
        String dir = this.getContext().getApplicationContext().getDir("database", Context.MODE_PRIVATE).getPath();
        settings.setGeolocationDatabasePath(dir);
        settings.setAppCacheEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setDomStorageEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
        this.setVerticalScrollBarEnabled(false);

        this.setWebChromeClient(new ExtWebChromeClient());
        this.setWebViewClient(new ExtWebViewClient());
    }
}
