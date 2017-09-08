package com.github.Humilton.util;

import com.github.Humilton.DemoApp;
import com.github.Humilton.entity.GoogleGeoCodeResponse;

import org.json.JSONException;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;

/**
 * Created by Hamilton on 2017/9/8.
 */

public class LocationUtil {
    private static final String iNFOMATION_URL = "http://maps.google.cn/maps/api/geocode/json?language=zh-CN&sensor=true&latlng=%1$s,%2$s";

    public interface GoogleGeoCodeResponseCallback {
        void onResponseSuccess(GoogleGeoCodeResponse data);
    }

    public static final void queryLocation(double lat, double lon, final GoogleGeoCodeResponseCallback callback ) {
        String urlString = String.format(iNFOMATION_URL, lat, lon);
        Request request = new Request.Builder()
                .url(urlString)
                .build();
        new OkHttpClient().newCall(request).enqueue(new okhttp3.Callback() {

            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                if (response == null) return;
                ResponseBody resp = response.body();
                if (resp == null) return;

                // deconstruct like https://stackoverflow.com/questions/14314183/get-country-name-from-latitude-and-longitude
                try {
                    GoogleGeoCodeResponse data = DemoApp.gson.fromJson(resp.string(), GoogleGeoCodeResponse.class);
                    if (data == null) return;
                    if (data.status.toUpperCase().equals("OK")) {
                        callback.onResponseSuccess(data);
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
