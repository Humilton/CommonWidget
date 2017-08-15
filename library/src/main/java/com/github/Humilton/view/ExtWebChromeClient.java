package com.github.Humilton.view;


import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;

/**
 * Created by Hamilton on 2017/8/2.
 */

public class ExtWebChromeClient extends WebChromeClient {
    @Override
    public void onGeolocationPermissionsShowPrompt(final String origin,
                                                   final GeolocationPermissions.Callback callback) {
        callback.invoke(origin, true, false);
        super.onGeolocationPermissionsShowPrompt(origin, callback);
    }
}
