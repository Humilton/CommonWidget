package com.github.Humilton.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.github.Humilton.entity.Location;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Hamilton on 2017/8/2.
 */

public class ExtWebViewClient extends WebViewClient {
    private static final String TAG = ExtWebViewClient.class.getSimpleName();

    private static final String ACTION = "action";
    private static final String ACTION_CLOSE = "close";
    private static final String ACTION_SUBMIT = "submit";
    private static final String ACTION_SHARE = "action";

    public static final int RESULT_LOCATION = 1985;
    public static final String LOCATION_DATA = "_Location_Data_";

    private Context mContext;

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if(url.startsWith("vbox://vigourbox")) {
            mContext = view.getContext();

            String data = url.replaceFirst("^.*\\?","");
            Log.e(TAG,"url = " + url);
            try{
                handleSchemaData(data);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }

    private void handleSchemaData(String param) throws NullPointerException, UnsupportedEncodingException {
        Map<String ,String> data = new HashMap<>();
        String[] res = param.split("&");
        for(String str : res) {
            String[] tmp = str.split("=");
            data.put(tmp[0], tmp[1]);
        }

        String action = data.get(ACTION);
        switch (action) {
            case ACTION_SUBMIT:
                try {
                    double lat = Double.parseDouble(data.get("lat"));
                    double lng = Double.parseDouble(data.get("lon"));
                    String addr = URLDecoder.decode(data.get("addr"), "utf-8");
                    Intent i = new Intent();
                    i.putExtra(LOCATION_DATA, new Location(lat, lng, addr));
                    ((Activity) mContext).setResult(RESULT_LOCATION, i);
                } catch (NullPointerException e) {
                }
            case ACTION_CLOSE:
                ((Activity)mContext).finish();
                break;
            case ACTION_SHARE:
            default:
                break;
        }
    }
}
